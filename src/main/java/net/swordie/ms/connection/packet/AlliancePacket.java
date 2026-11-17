package net.swordie.ms.connection.packet;

import net.swordie.ms.client.alliance.Alliance;
import net.swordie.ms.client.guild.Guild;
import net.swordie.ms.client.guild.GuildMember;
import net.swordie.ms.client.guild.result.GuildResult;
import net.swordie.ms.client.guild.result.GuildType;
import net.swordie.ms.connection.OutPacket;

/**
 * v265 alliance events. There is no alliance opcode in v265: they are GUILD_RESULT sub-types read by the guild
 * result handler (0x14551f110), each starting with the alliance id the client checks against its own.
 */
public class AlliancePacket {

    private static OutPacket event(GuildType type, java.util.function.Consumer<OutPacket> body) {
        return WvsContext.guildResult(GuildResult.allianceEvent(type, body));
    }

    /** "'%s' Alliance has formed." with the alliance data. */
    public static OutPacket created(Alliance alliance) {
        return event(GuildType.Res_Alliance_Created, alliance::encodeV265);
    }

    /** A guild joined: int alliance, int guild, alliance data ("The guild [%s] has joined the alliance."). */
    public static OutPacket joined(Alliance alliance, Guild guild) {
        return event(GuildType.Res_Alliance_Joined, op -> {
            op.encodeInt(alliance.getId());
            op.encodeInt(guild.getId());
            alliance.encodeV265(op);
        });
    }

    /** A guild left (kicked = false) or was removed (true). */
    public static OutPacket withdraw(Alliance alliance, Guild guild, boolean kicked) {
        return event(GuildType.Res_Alliance_Withdraw, op -> {
            op.encodeInt(alliance.getId());
            op.encodeInt(guild.getId());
            op.encodeByte(kicked);
        });
    }

    public static OutPacket changeMaster(Alliance alliance, GuildMember oldMaster, GuildMember newMaster) {
        return event(GuildType.Res_Alliance_ChangeMaster, op -> {
            op.encodeInt(alliance.getId());
            op.encodeInt(oldMaster.getCharID());
            op.encodeInt(newMaster.getCharID());
        });
    }

    public static OutPacket setGradeNames(Alliance alliance) {
        return event(GuildType.Res_Alliance_SetGradeName, op -> {
            op.encodeInt(alliance.getId());
            op.encodeInt(0);
            for (int i = 0; i < 5; i++) {
                op.encodeString(alliance.getGradeNames() != null && i < alliance.getGradeNames().size()
                        ? alliance.getGradeNames().get(i) : "");
            }
        });
    }

    public static OutPacket setGrade(Alliance alliance, Guild guild, GuildMember member) {
        return event(GuildType.Res_Alliance_SetGrade, op -> {
            op.encodeInt(guild.getId());
            op.encodeInt(member.getCharID());
            op.encodeInt(alliance.getId());
            op.encodeByte(member.getAllianceGrade());
        });
    }

    public static OutPacket setNotice(Alliance alliance) {
        return event(GuildType.Res_Alliance_SetNotice, op -> {
            op.encodeInt(alliance.getId());
            op.encodeString(alliance.getNotice() == null ? "" : alliance.getNotice());
        });
    }

    public static OutPacket destroy(Alliance alliance) {
        return event(GuildType.Res_Alliance_Destroy, op -> op.encodeInt(alliance.getId()));
    }

    /**
     * The invitation to the invited guild's master: out GUILD_REQUEST 219 type 52 (0x1455251f0: int alliance id,
     * str), shown in the notification list (alarm type 0x1b); accepting sends in GUILD_REQUEST 53.
     */
    public static OutPacket invite(Alliance alliance, String inviterName) {
        OutPacket outPacket = new OutPacket(net.swordie.ms.handlers.header.OutHeader.GUILD_REQUEST);
        outPacket.encodeInt(50); // v271 (0x145b2bea0: the only type left; v265 52)
        outPacket.encodeInt(alliance.getId());
        outPacket.encodeString(inviterName);
        return outPacket;
    }

    /** "%s Guild is currently blocking / already sending / has rejected" an alliance invitation. */
    public static OutPacket inviteMessage(GuildType type, String guildName) {
        return event(type, op -> op.encodeString(guildName));
    }
}
