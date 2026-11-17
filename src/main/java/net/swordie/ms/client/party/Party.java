package net.swordie.ms.client.party;

import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.avatar.AvatarLook;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.connection.packet.UserRemote;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.events.Events;
import net.swordie.ms.world.World;
import net.swordie.ms.world.field.Field;
import net.swordie.ms.world.field.instance.Instance;
import net.swordie.orm.dao.CharDao;
import net.swordie.orm.dao.SworDaoFactory;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created on 3/19/2018.
 */
public class Party {

    private static final CharDao charDao = (CharDao) SworDaoFactory.getByClass(Char.class);

    private int id;
    private PartyMember[] partyMembers = new PartyMember[6];
    private boolean appliable;
    private boolean masterLoot;
    private String name;
    private int partyLeaderID;
    private World world;
    private Char applyingChar;
    /**
     * Characters invited to this party that haven't answered yet: joining by accepting is only possible with an
     * invite (a forged "accept" used to let anyone on the map join any party).
     */
    private final Set<Integer> pendingInvites = java.util.concurrent.ConcurrentHashMap.newKeySet();

    public void addPendingInvite(int charId) {
        pendingInvites.add(charId);
    }

    /**
     * @return whether the Char had an invite to this party (which is used up)
     */
    public boolean consumeInvite(int charId) {
        return pendingInvites.remove(charId);
    }

    /** Made by a boss entry for a player who went in alone (ScriptManagerImpl.createSoloParty): ends with the fight. */
    private boolean bossSolo;

    public boolean isBossSolo() {
        return bossSolo;
    }

    public void setBossSolo(boolean bossSolo) {
        this.bossSolo = bossSolo;
    }

    /** @return whether the Char has an invite to this party waiting (it isn't used up) */
    public boolean hasPendingInvite(int charId) {
        return pendingInvites.contains(charId);
    }
    private Instance instance;
    private boolean duoParty;
    private boolean kickMembers;

    public boolean isAppliable() {
        return appliable;
    }

    public void setAppliable(boolean appliable) {
        this.appliable = appliable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PartyMember[] getPartyMembers() {
        return partyMembers;
    }

    public boolean isFull() {
        return Arrays.stream(getPartyMembers()).noneMatch(Objects::isNull);
    }

    public boolean isEmpty() {
        return Arrays.stream(getPartyMembers()).allMatch(Objects::isNull);
    }

    /**
     * v265 party block, as the reference server sends it (PARTY_RESULT 29 with a party, 404 bytes for one
     * member): id, byte, 6 member entries (char id, then the details when not 0), leader, Party Finder applicants
     * (count, entries), 6 field ids, 6 town portals, byte, the name (short 30 + 30 bytes), appliable, loot permission,
     * the Party Finder ad (boss -1: none).
     */
    public void encode(OutPacket outPacket, Char receiver) {
        outPacket.encodeInt(getId());
        outPacket.encodeByte(0);
        encodeMembers(outPacket);
        outPacket.encodeInt(getPartyLeaderID());
        PartyRecruitment.encodeApplicantsOf(outPacket, this);
        for (PartyMember pm : partyMembers) {
            int fieldId = GameConstants.NO_MAP_ID;
            if (pm != null && (receiver == null || pm.getChannel() == receiver.getChannel())) {
                fieldId = pm.getFieldID();
            }
            outPacket.encodeInt(fieldId);
        }
        for (PartyMember pm : partyMembers) {
            if (pm != null && pm.getTownPortal() != null) {
                pm.getTownPortal().encode(outPacket, false);
            } else {
                emptyTownPortal(outPacket);
            }
        }
        outPacket.encodeByte(false);
        encodeName(outPacket);
        outPacket.encodeByte(isAppliable() && !isFull());
        outPacket.encodeByte(0); // v271 name block (0x140e41220): new byte before the loot int
        outPacket.encodeInt(1); // loot permission
        PartyRecruitment.encodeAdOf(outPacket, this); // Party Finder ad (-1: none)
        outPacket.encodeString(""); // v271 (0x140e41d40): new string at the end
    }

    public void encodeMembers(OutPacket outPacket) {
        for (PartyMember pm : partyMembers) {
            encodeMember(outPacket, pm);
        }
    }

    public void encodeMember(OutPacket outPacket, PartyMember pm) {
        int charID = pm != null ? pm.getCharID() : 0;
        outPacket.encodeInt(charID);
        if (charID == 0) {
            return;
        }
        Char c = pm.getChr();
        outPacket.encodeInt(charID);
        outPacket.encodeString(pm.getCharName());
        outPacket.encodeInt(pm.getJob());
        outPacket.encodeInt(pm.getSubSob());
        outPacket.encodeInt(pm.getLevel());
        outPacket.encodeInt(getWorld() != null && getWorld().getWorldId() != null ? getWorld().getWorldId().getVal() : 0);
        outPacket.encodeInt(pm.isOnline() ? 1 : 0);
        outPacket.encodeInt(pm.isOnline() ? (pm.getFieldID() == -1 ? -1 : pm.getChannel() - 1) : -2);
        outPacket.encodeByte(0);
        outPacket.encodeByte(0);
        outPacket.encodeByte(0); // v271 (0x140e416a0): third byte
        outPacket.encodeInt(0); // arcane force
        outPacket.encodeInt(0); // sacred force
        outPacket.encodeInt(0); // dojo rank
        outPacket.encodeLong(0); // dojo rank time
        outPacket.encodeLong(0); // v271: new long (0x140e42ba0)
        outPacket.encodeArr(packedLook480(c));
    }

    /**
     * v271 packed look is 480 bytes with its version in the last byte; a 0 there makes the client take the version
     * from byte 119 (the v265 120-byte layout, unpacker 0x14064e015), so the v265 look goes in front.
     */
    /** A character's v271 480-byte packed look (an empty look when it has none). */
    public static byte[] packedLook480(net.swordie.ms.client.character.Char c) {
        boolean v41 = !"v265".equalsIgnoreCase(net.swordie.ms.ServerSettings.getString("test.packedLook", "v41"));
        if (v41 && c != null && c.getAvatarData() != null && c.getAvatarData().getAvatarLook() != null) {
            return c.getAvatarData().getAvatarLook().getPackedLook480(); // the real v271 layout (version 41)
        }
        byte[] look = null;
        if (look == null) {
            look = new byte[120];
            look[119] = 29;
        }
        return packedLook480(look);
    }

    public static byte[] packedLook480(byte[] look120) {
        byte[] look = new byte[480];
        System.arraycopy(look120, 0, look, 0, Math.min(120, look120.length));
        return look;
    }

    private static void emptyTownPortal(OutPacket outPacket) {
        outPacket.encodeInt(GameConstants.NO_MAP_ID);
        outPacket.encodeInt(GameConstants.NO_MAP_ID);
        outPacket.encodeInt(0);
        outPacket.encodeInt(-1);
        outPacket.encodeInt(-1);
    }

    /** short 30, then the name in a fixed 30-byte field */
    public void encodeName(OutPacket outPacket) {
        outPacket.encodeShort(30);
        outPacket.encodeString(getName() == null ? "" : getName(), 30);
    }

    public int getPartyLeaderID() {
        return partyLeaderID;
    }

    public void setPartyLeaderID(int partyLeaderID) {
        this.partyLeaderID = partyLeaderID;
    }

    /**
     * Adds a {@link Char} to this Party. Will do nothing if this Party is full.
     *
     * @param chr The Char to add.
     */
    public void addPartyMember(Char chr) {
        if (isFull()) {
            return;
        }
        boolean initial = isEmpty();
        if (!initial) {
            bossSolo = false; // someone joined: a real party now, kept after the fight
        }
        PartyMember pm = new PartyMember(chr);
        if (isEmpty()) {
            setPartyLeaderID(chr.getId());
        }
        PartyMember[] partyMembers = getPartyMembers();
        boolean added = false;
        for (int i = 0; i < partyMembers.length; i++) {
            if (partyMembers[i] == null) {
                partyMembers[i] = pm;
                chr.setParty(this);
                added = true;
                break;
            }
        }
        if (added && !initial) {
            broadcast(WvsContext.partyResult(PartyResult.joinParty(this, chr.getName())));
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TownPortal getTownPortal() {
        PartyMember pm = Arrays.stream(getPartyMembers()).filter(Objects::nonNull)
                .filter(p -> p.getTownPortal() != null)
                .findFirst().orElse(null);
        return pm != null ? pm.getTownPortal() : new TownPortal();
    }

    public PartyMember getPartyLeader() {
        return Arrays.stream(getPartyMembers()).filter(p -> p != null && p.getCharID() == getPartyLeaderID()).findFirst().orElse(null);
    }

    public boolean hasCharAsLeader(Char chr) {
        return getPartyLeaderID() == chr.getId();
    }

    public void disband() {
        Events.onPartyDisband(this);
        PartyRecruitment.onDisband(this);

        broadcastWithdraw();
        for (Char chr : getOnlineChars()) {
            chr.setParty(null);
        }
        for (int i = 0; i < getPartyMembers().length; i++) {
            removePartyMember(getPartyMembers()[i]);
            getPartyMembers()[i] = null;
        }
        if (getWorld() != null) {
            getWorld().removeParty(this); // null once disbanded already
        }
        setWorld(null);
    }

    public void broadcastWithdraw() {
        for (PartyMember pm : getOnlineMembers()) {
            pm.getChr().write(WvsContext.partyResult(PartyResult.withdrawParty(this, getPartyLeader(), false, false, pm.getChr())));
        }
    }

    public List<Char> getOnlineChars() {
        return getOnlineMembers().stream().filter(pm -> pm.getChr() != null).map(PartyMember::getChr).collect(Collectors.toList());
    }

    public List<Char> getPartyMembersInField(Char chr) {
        return getOnlineChars().stream().filter(chr::isInSameField).collect(Collectors.toList());
    }

    public List<PartyMember> getOnlineMembers() {
        return Arrays.stream(getPartyMembers()).filter(pm -> pm != null && pm.isOnline()).collect(Collectors.toList());
    }

    public List<PartyMember> getMembers() {
        return Arrays.stream(getPartyMembers()).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public void updateFull() {
        broadcastLoadParty();
    }

    public void broadcastLoadParty() {
        for (PartyMember pm : getOnlineMembers()) {
            pm.getChr().write(WvsContext.partyResult(PartyResult.loadParty(this, pm.getChr())));
        }
    }

    public void broadcastMigration() {
        for (PartyMember pm : getOnlineMembers()) {
            pm.getChr().write(WvsContext.partyResult(PartyResult.userMigration(this, pm.getChr())));
        }
    }

    public PartyMember getPartyMemberByID(int charID) {
        return Arrays.stream(getPartyMembers()).filter(p -> p != null && p.getCharID() == charID).findFirst().orElse(null);
    }

    public void broadcast(OutPacket outPacket) {
        for (PartyMember pm : getOnlineMembers()) {
            pm.getChr().write(outPacket);
        }
    }

    public void broadcast(OutPacket outPacket, Char exceptChar) {
        for (PartyMember pm : getOnlineMembers()) {
            if (!pm.getChr().equals(exceptChar)) {
                pm.getChr().write(outPacket);
            }
        }
    }

    public void broadcastByChr(Char byChr, OutPacket outPacket, Char exceptChar) {
        for (PartyMember pm : getOnlineMembers()) {
            if (pm.getChr() != null // chr is not null
                    && !pm.getChr().equals(exceptChar) // pm.chr is not exceptChr
                    && !pm.getChr().hasChrBlacklisted(byChr.getId())) { // pm.chr has byChr not blacklisted
                pm.getChr().write(outPacket);
            }
        }
    }

    public void removePartyMember(PartyMember partyMember) {
        for (int i = 0; i < getPartyMembers().length; i++) {
            PartyMember pm = getPartyMembers()[i];
            if (pm != null && pm.equals(partyMember)) {
                var pmChr = pm.getChr();
                if (pmChr != null) {
                    pm.getChr().setParty(null);
                    pm.getChr().getJobHandler().onLeaveParty();
                } else {
                    var dbChar = charDao.getById(pm.getCharID());
                    if (dbChar != null) {
                        charDao.savePartyId(dbChar);
                    }
                }
                getPartyMembers()[i] = null;
                break;
            }
        }
        if (partyMember != null && partyMember.getChr() != null) {
            Char chr = partyMember.getChr();
            if (chr.getInstance() != null) {
                chr.getInstance().removeCharNoReentrance(chr);
            }
        }
    }

    public void expel(int expelID) {
        PartyMember leaver = getPartyMemberByID(expelID);
        if (leaver == null) {
            return;
        }
        broadcastLeave(leaver, true);
        removePartyMember(leaver);
        updateFull();
    }

    public void broadcastLeave(PartyMember leaver) {
        broadcastLeave(leaver, true);
    }

    /** Tells every online member (the leaver too) that this member left (expelled: kicked by the leader). */
    public void broadcastLeave(PartyMember leaver, boolean expelled) {
        for (PartyMember pm : getOnlineMembers()) {
            pm.getChr().write(WvsContext.partyResult(PartyResult.withdrawParty(this, leaver, true, expelled, pm.getChr())));
        }
    }

    /**
     * Gives the lead to another member, an online one first (none online: any other member), as when the leader leaves
     * or logs off. @param reason the client's reason byte (0: handed over, 1: the leader went offline)
     * @return whether the lead changed
     */
    public boolean passLeadership(int fromCharId, int reason) {
        PartyMember next = null;
        for (PartyMember pm : getPartyMembers()) {
            if (pm != null && pm.getCharID() != fromCharId && (next == null || (pm.isOnline() && !next.isOnline()))) {
                next = pm;
            }
        }
        if (next == null) {
            return false;
        }
        setPartyLeaderID(next.getCharID());
        broadcast(WvsContext.partyResult(PartyResult.changePartyBoss(this, reason)));
        return true;
    }

    public static Party createNewParty(boolean appliable, boolean masterLoot, String name, World world) {
        Party party = new Party();
        party.setAppliable(appliable);
        party.setMasterLoot(masterLoot);
        party.setName(name);
        party.setWorld(world);
        world.addParty(party);

        Events.onPartyCreated(party);

        return party;
    }

    public int getAvgLevel() {
        Collection<PartyMember> partyMembers = getMembers();
        return partyMembers.stream()
                .filter(pm -> pm != null && pm.getChr() != null)
                .mapToInt(pm -> pm.getChr().getLevel())
                .sum() / partyMembers.size();
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public World getWorld() {
        return world;
    }

    public Char getApplyingChar() {
        return applyingChar;
    }

    public void setApplyingChar(Char applyingChar) {
        this.applyingChar = applyingChar;
    }

    public boolean isPartyMember(Char chr) {
        return getPartyMemberByID(chr.getId()) != null;
    }

    public void updatePartyMemberInfoByChr(Char chr) {
        if (!isPartyMember(chr)) {
            return;
        }
        getPartyMemberByID(chr.getId()).updateInfoByChar(chr);
        updateFull();
    }

    /**
     * Returns the average party member's level, according to the given Char's field.
     *
     * @param chr the chr to get the map to
     * @return the average level of the party in the Char's field
     */
    public int getAvgPartyLevel(Char chr) {
        Field field = chr.getField();
        return (int) getOnlineMembers().stream().filter(om -> om.getChr().getField() == field)
                .mapToInt(PartyMember::getLevel).average().orElse(chr.getLevel());
    }

    /**
     * Gets a list of party members in the same Field instance as the given Char, excluding the given Char.
     *
     * @param chr the given Char
     * @return a set of Characters that are in the same field as the given Char
     */
    public Set<Char> getPartyMembersInSameField(Char chr) {
        return getOnlineMembers().stream()
                .filter(pm -> pm.getChr() != null && pm.getChr() != chr && chr.isInSameField(pm.getChr()))
                .map(PartyMember::getChr)
                .collect(Collectors.toSet());
    }

    /**
     * Checks if this Party has a member with the given character id.
     *
     * @param charID the charID to look for
     * @return if the corresponding char is in the party
     */
    public boolean hasPartyMember(int charID) {
        return getPartyMemberByID(charID) != null;
    }

    public boolean isDuoParty() {
        return duoParty;
    }

    public void setDuoParty(boolean duoParty) {
        this.duoParty = duoParty;
    }

    public boolean canKickMembers() {
        return kickMembers;
    }

    public void setKickMembers(boolean kickMembers) {
        this.kickMembers = kickMembers;
    }

    public Instance getInstance() {
        return instance;
    }

    public void setInstance(Instance instance) {
        this.instance = instance;
    }

    public void updateHealth() {
        List<Char> onlineMembers = getOnlineChars();
        onlineMembers.forEach(pm -> {
            onlineMembers.stream().filter(pm2 -> pm != pm2).forEach(pm2 -> {
                pm.write(UserRemote.receiveHP(pm2));
                pm2.write(UserRemote.receiveHP(pm));
            });
        });
    }

    public boolean isMasterLoot() {
        return masterLoot;
    }

    public void setMasterLoot(boolean masterLoot) {
        this.masterLoot = masterLoot;
    }

    public void encodeSettings(OutPacket outPacket) {
        // v265 (reference): name block, appliable, loot permission
        encodeName(outPacket);
        outPacket.encodeByte(isAppliable() && !isFull());
        outPacket.encodeByte(0); // v271
        outPacket.encodeInt(1);
    }
}
