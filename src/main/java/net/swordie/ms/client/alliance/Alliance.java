package net.swordie.ms.client.alliance;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.guild.Guild;
import net.swordie.ms.connection.Encodable;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.connection.packet.UserLocal;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.enums.ChatType;
import net.swordie.ms.world.World;
import net.swordie.orm.dao.GuildDao;
import net.swordie.orm.dao.SworDaoFactory;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Sjonnie
 * Created on 9/1/2018.
 */
public class Alliance implements Encodable {

    private static final GuildDao guildDao = (GuildDao) SworDaoFactory.getByClass(Guild.class);

    private static final int MAX_GUILDS = 15;

    private World world;

    private int id;
    private Set<Guild> guilds;
    private List<String> gradeNames;
    private String name;
    private int maxMemberNum;
    private String notice;

    private final Set<Integer> pendingGuilds = new HashSet<>();

    public Alliance(boolean init) {
        if (init) {
            gradeNames = Arrays.asList("Master", "Junior", "Veteran", "Regular", "New");
            guilds = new HashSet<>();
        }
    }

    @Override
    public void encode(OutPacket outPacket) {
        outPacket.encodeInt(getId());
        outPacket.encodeString(getName());
        for (String gradeName : getGradeNames()) {
            outPacket.encodeString(gradeName);
        }
        outPacket.encodeByte(getGuilds().size());
        for (Guild guild : getGuilds()) {
            outPacket.encodeInt(guild.getId());
        }
        outPacket.encodeInt(getMaxMemberNum());
        outPacket.encodeString(getNotice());
    }

    /** v265 alliance data (client 0x14551edc0): id, name, 5 grade names, max guilds, then every guild's GuildData */
    public void encodeV265(OutPacket outPacket) {
        outPacket.encodeInt(getId());
        outPacket.encodeString(getName());
        for (int i = 0; i < 5; i++) {
            outPacket.encodeString(getGradeNames() != null && i < getGradeNames().size() ? getGradeNames().get(i) : "");
        }
        outPacket.encodeInt(getMaxMemberNum());
        outPacket.encodeByte(getGuilds().size());
        for (Guild guild : getGuilds()) {
            outPacket.encode(guild);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Guild> getGuilds() {
        if (guilds == null) {
            guilds = guildDao.byAlliance(this);
        }
        return guilds;
    }

    public void setGuilds(Set<Guild> guilds) {
        this.guilds = guilds;
    }

    public List<String> getGradeNames() {
        return gradeNames;
    }

    public void setGradeNames(List<String> gradeNames) {
        this.gradeNames = gradeNames;
    }

    public int getMaxMemberNum() {
        return maxMemberNum;
    }

    public void setMaxMemberNum(int maxMemberNum) {
        this.maxMemberNum = maxMemberNum;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public Guild getGuildByID(int guildID) {
        return getGuilds().stream().filter(g -> g.getId() == guildID).findAny().orElse(null);
    }

    public void broadcast(OutPacket outPacket) {
        for (Guild guild : getGuilds()) {
            guild.broadcast(outPacket);
        }
    }

    public void broadcast(OutPacket outPacket, Char exceptChar) {
        for (Guild guild : getGuilds()) {
            guild.broadcast(outPacket, exceptChar);
        }
    }

    public void broadcastByChr(Char byChr, OutPacket outPacket, Char exceptChar) {
        for (Guild guild : getGuilds()) {
            guild.broadcastByChr(byChr, outPacket, exceptChar);
        }
    }

    public void addGuild(Guild guild) {
        guild.setAlliance(this);
        guild.setAllianceID(getId());
        getGuilds().add(guild);
        removePendingGuild(guild);
        broadcast(net.swordie.ms.connection.packet.AlliancePacket.joined(this, guild));
        updateToMembers();
    }

    public void removeGuild(Guild guild) {
        Guild g = getGuildByID(guild.getId()); // to ensure it's the same instance as the one in the set
        if (g == null) {
            g = guild;
        }
        g.setAllianceID(0);
        g.setAlliance(null);
        getGuilds().remove(g);
    }

    public void disband() {
        broadcast(UserLocal.chatMsg(ChatType.SystemNotice, "The alliance has been disbanded."));
        for (Guild guild : new java.util.ArrayList<>(getGuilds())) {
            guild.setAlliance(null);
            guild.updateToMembers();
        }
        World w = getWorld();
        if (w == null) { // alliances loaded from the database or made by Lenario have no world set
            for (World each : net.swordie.ms.Server.getInstance().getWorlds()) {
                if (each.getAlliances().containsKey(getId())) {
                    w = each;
                }
            }
        }
        if (w != null) {
            w.removeAlliance(this);
        }
    }

    public void updateToMembers() {
        // v265 has no alliance opcode: the alliance rides on each member's guild load
        for (Guild guild : getGuilds()) {
            guild.updateToMembers();
        }
    }

    public void addPendingGuild(Guild guild) {
        pendingGuilds.add(guild.getId());
    }

    public void removePendingGuild(Guild guild) {
        pendingGuilds.remove(guild.getId());
    }

    public boolean isFull() {
        return getGuilds().size() >= (getMaxMemberNum() > 0 ? getMaxMemberNum() : 5);
    }

    public boolean hasPendingGuildInvite(int id) {
        return pendingGuilds.contains(id);
    }

    public boolean canBeDisbanded() {
        return getGuilds().size() <= 1;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }
}
