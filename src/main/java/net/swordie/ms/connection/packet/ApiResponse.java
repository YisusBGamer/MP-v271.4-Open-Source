package net.swordie.ms.connection.packet;

import net.swordie.ms.client.User;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.connection.api.ApiOutHeader;
import net.swordie.ms.enums.AccountCreateResult;
import net.swordie.ms.enums.ApiTokenResultType;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author Sjonnie
 * Created on 10/5/2018.
 */
public class ApiResponse {

    /** Longest string in a launcher packet (the launcher's receive buffer is 1 KB in total). */
    private static final int MAX_STRING_BYTES = 64;

    public static OutPacket tokenRequestResult(ApiTokenResultType atrt, String token, User user) {
        OutPacket outPacket = new OutPacket(ApiOutHeader.REQUEST_TOKEN_RESULT);

        outPacket.encodeByte(atrt.getVal());
        if (atrt == ApiTokenResultType.Success) {
            outPacket.encodeString(token);
            outPacket.encodeByte(user.getAccountType().ordinal());
        }

        return outPacket;
    }

    /** Launcher login: byte result, then the numeric token when the result is Success. */
    public static OutPacket tokenRequestResultX64(ApiTokenResultType atrt, int token) {
        OutPacket outPacket = new OutPacket(ApiOutHeader.REQUEST_TOKEN_RESULT_X64);

        outPacket.encodeByte(atrt.getVal());
        if (atrt == ApiTokenResultType.Success) {
            outPacket.encodeInt(token);
        }

        return outPacket;
    }

    public static OutPacket createAccountResult(AccountCreateResult acr) {
        OutPacket outPacket = new OutPacket(ApiOutHeader.CREATE_ACCOUNT_RESULT);

        outPacket.encodeByte(acr.ordinal());

        return outPacket;
    }

    public record CharSummary(String name, int level, int job, String jobName, int fieldId) {
    }

    /**
     * Launcher account overview: byte result (0 ok, 1 bad token), then int NX, int DP, int VP, byte count,
     * per character: name, int level, int job, job name, int field, and finally the account email (empty when none).
     */
    public static OutPacket accountDataResult(boolean ok, int nx, int dp, int vp, List<CharSummary> chars,
                                              String email) {
        OutPacket outPacket = new OutPacket(ApiOutHeader.ACCOUNT_DATA_RESULT_X64);

        outPacket.encodeByte(ok ? 0 : 1);
        if (ok) {
            outPacket.encodeInt(nx);
            outPacket.encodeInt(dp);
            outPacket.encodeInt(vp);
            outPacket.encodeByte(chars.size());
            for (CharSummary cs : chars) {
                encodeUtf8(outPacket, cs.name());
                outPacket.encodeInt(cs.level());
                outPacket.encodeInt(cs.job());
                encodeUtf8(outPacket, cs.jobName());
                outPacket.encodeInt(cs.fieldId());
            }
            encodeUtf8(outPacket, email);
        }

        return outPacket;
    }

    /**
     * Launcher account settings: byte result.
     * 0 = saved, 1 = invalid or expired token, 2 = wrong current password, 3 = invalid new password or email,
     * 4 = email already used by another account, 5 = too many attempts.
     */
    public static OutPacket accountUpdateResult(int result) {
        OutPacket outPacket = new OutPacket(ApiOutHeader.ACCOUNT_UPDATE_RESULT);

        outPacket.encodeByte(result);

        return outPacket;
    }

    public record ChannelPlayers(int channelId, int players) {
    }

    public record WorldChannels(int worldId, List<ChannelPlayers> channels) {
    }

    /**
     * Launcher world stats: byte result (0 ok), int total players in game, byte world count and per world:
     * byte world id, byte channel count, then per channel: byte channel id, short players.
     */
    public static OutPacket worldStatsResult(boolean ok, int totalPlayers, List<WorldChannels> worlds) {
        OutPacket outPacket = new OutPacket(ApiOutHeader.WORLD_STATS_RESULT);

        outPacket.encodeByte(ok ? 0 : 1);
        if (ok) {
            outPacket.encodeInt(totalPlayers);
            outPacket.encodeByte(worlds.size());
            for (WorldChannels w : worlds) {
                outPacket.encodeByte(w.worldId());
                outPacket.encodeByte(w.channels().size());
                for (ChannelPlayers ch : w.channels()) {
                    outPacket.encodeByte(ch.channelId());
                    outPacket.encodeShort((short) Math.min(Short.MAX_VALUE, Math.max(0, ch.players())));
                }
            }
        }

        return outPacket;
    }

    /** The launcher reads strings as a short byte count followed by UTF-8. */
    private static void encodeUtf8(OutPacket outPacket, String s) {
        byte[] bytes = (s == null ? "" : s).getBytes(StandardCharsets.UTF_8);
        if (bytes.length > MAX_STRING_BYTES) {
            byte[] cut = new byte[MAX_STRING_BYTES];
            System.arraycopy(bytes, 0, cut, 0, MAX_STRING_BYTES);
            bytes = new String(cut, StandardCharsets.UTF_8).replace("�", "").getBytes(StandardCharsets.UTF_8);
        }
        outPacket.encodeShort((short) bytes.length);
        outPacket.encodeArr(bytes);
    }
}
