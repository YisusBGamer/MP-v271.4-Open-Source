package net.swordie.ms.handlers;

import net.swordie.ms.client.Client;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.DailyGiftModule;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.handlers.header.InHeader;
import net.swordie.ms.handlers.header.OutHeader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Client packets that need no reply: login/field-entry syncs and client-side reports.
 */
public class ClientReportHandler {
    private static final Logger log = LogManager.getLogger(ClientReportHandler.class);
    private static final int MAX_RESOURCE_ERRORS = 200;
    private static final Set<String> seenResourceErrors = ConcurrentHashMap.newKeySet();

    @Handler(ops = {
            InHeader.UNK232_195, InHeader.UNK232_197, InHeader.LOGIN_BASIC_INFO, InHeader.CHECK_SPW_EXIST_REQUEST,
            InHeader.DONE_LOADING, InHeader.UNK232_629, InHeader.UNK232_927, InHeader.UNK232_931,
            InHeader.UNK232_1031, InHeader.UNK232_1624, InHeader.CANCEL_INVITE_PARTY_MATCH,
            InHeader.E_N_D__F_I_S_H_I_N_G_Z_O_N_E_P_O_O_L, InHeader.REQUEST_FARM_FRIEND_RECOMMEND,
            InHeader.ITEM_COLLECTION_SET_FLAG, InHeader.ITEM_COLLECTION_CHECK_COMPLETE,
            InHeader.SELF_STAT_CHANGE_REQUEST, InHeader.MIRROR_STUDY_COMPLETE, InHeader.WVS_SET_UP_STEP,
            InHeader.USE_AUTH_SERVER, InHeader.MOB_CRC_DATA_RESULT, InHeader.UNKNOWN_OPCODE_675_SINK,
            InHeader.UNKNOWN_OPCODE_202_SINK, InHeader.UNKNOWN_OPCODE_1157_SINK,
            InHeader.UNKNOWN_OPCODE_760_SINK, InHeader.UNKNOWN_OPCODE_678_SINK,
            InHeader.UNKNOWN_OPCODE_1026_SINK, InHeader.UNKNOWN_OPCODE_263_SINK, InHeader.UNKNOWN_OPCODE_506_SINK,
            InHeader.UNKNOWN_OPCODE_507_SINK, InHeader.UNKNOWN_OPCODE_1342_SINK, InHeader.UNKNOWN_OPCODE_1109_SINK,
            InHeader.UNKNOWN_OPCODE_212_SINK, InHeader.UNKNOWN_OPCODE_1930_SINK,
            InHeader.TELEMETRY_CHAR_NAME_SINK, InHeader.TELEMETRY_HARDWARE_SINK, InHeader.TELEMETRY_DISPLAY_SINK,
            InHeader.TELEMETRY_FIELD_ENTER_SINK, InHeader.TELEMETRY_FIELD_POS_SINK, InHeader.TELEMETRY_895_SINK,
            InHeader.TELEMETRY_CLIENT_REPORT_SINK, InHeader.TELEMETRY_RPCTEST_SINK,
            InHeader.TELEMETRY_SNAPSHOT_START, InHeader.TELEMETRY_SNAPSHOT_1892, InHeader.TELEMETRY_SNAPSHOT_1893,
            InHeader.TELEMETRY_SNAPSHOT_1894, InHeader.TELEMETRY_SNAPSHOT_1896, InHeader.TELEMETRY_SNAPSHOT_1897,
            InHeader.TELEMETRY_SNAPSHOT_1898, InHeader.TELEMETRY_SNAPSHOT_1899, InHeader.TELEMETRY_SNAPSHOT_1901,
            InHeader.TELEMETRY_SNAPSHOT_1904, InHeader.TELEMETRY_SNAPSHOT_1905,
            InHeader.SELECT_WORLD_RESULT_ACK,
            InHeader.FIELD_INIT_NOTICE_789, InHeader.FIELD_INIT_NOTICE_846, InHeader.UNION_OPEN_NOTICE,
            InHeader.ARAN_SKILL_AREA_NOTICE, InHeader.WEAPON_AURA_READY_NOTICE, InHeader.WEAPON_AURA_TRIGGER_NOTICE,
            InHeader.RIOTOUS_HEART_USE_NOTICE, InHeader.USER_SKILL_USE_PRE_NOTICE,
            InHeader.USER_CALC_DAMAGE_STAT_SET_REQUEST, InHeader.USER_COMPLETE_COMBO_KILL_COUNT_CHECK,
            InHeader.UNKNOWN_OPCODE_1855_SINK, InHeader.UNKNOWN_OPCODE_1871_SINK, InHeader.UNKNOWN_OPCODE_1915_SINK,
            InHeader.UNKNOWN_OPCODE_1917_SINK, InHeader.UNKNOWN_OPCODE_1147_SINK,
            InHeader.UNKNOWN_OPCODE_464_SINK, InHeader.UNKNOWN_OPCODE_1017_SINK, InHeader.UNKNOWN_OPCODE_1957_SINK,
            InHeader.UNKNOWN_OPCODE_2022_SINK, InHeader.UNKNOWN_OPCODE_1178_SINK, InHeader.BONUS_ATTACK_FAILED,
            InHeader.MOB_FREEZE_EFFECT_ACK, InHeader.LUCID_UNK212_1302, InHeader.USER_AFFECTED_AREA_CREATED,
            InHeader.USER_AFFECTED_AREA_REMOVED, InHeader.UNKNOWN_OPCODE_662_SINK,
            InHeader.UNKNOWN_OPCODE_1007_SINK, InHeader.UNKNOWN_OPCODE_1063_SINK,
            InHeader.UNKNOWN_OPCODE_1076_SINK, InHeader.UNKNOWN_OPCODE_1098_SINK, InHeader.UNKNOWN_OPCODE_1726_SINK,
            InHeader.UNKNOWN_OPCODE_1831_SINK,
            InHeader.UNKNOWN_OPCODE_1832_SINK, InHeader.UNKNOWN_OPCODE_2006_SINK,
            InHeader.UNKNOWN_OPCODE_579_SINK, InHeader.UNKNOWN_OPCODE_720_SINK,
            InHeader.UNKNOWN_OPCODE_801_SINK, InHeader.UNKNOWN_OPCODE_1154_SINK,
            InHeader.UNKNOWN_OPCODE_1105_SINK
    })
    public static void handleIgnoredClientPacket(Client c, InPacket inPacket) {
        // sent on login, field entry or as a periodic client report; the client expects no reply
    }

    /**
     * The client's login loader finished. The Daily Gift calendar has to be sent here, not with the login burst:
     * the client creates its Daily Gift manager inside that loader (the packet handler drops the calendar while the
     * manager is still null, so the window's gate finds no calendar and never opens).
     */
    @Handler(op = InHeader.CLIENT_LOAD_DONE_NOTICE)
    public static void handleClientLoadDone(Client c, InPacket inPacket) {
        Char chr = c.getChr();
        if (chr != null) {
            DailyGiftModule.onLogin(chr);
        }
    }

    private static volatile long lastDebugReport;

    @Handler(op = InHeader.LANGUAGE_SETTING_CHANGE_ANSWER)
    public static void handleLanguageSettingChangeAnswer(Client c, InPacket inPacket) {
        // int counter, int option, int agreed; the notice is shown once after a locale change and expects no reply
        if (inPacket.getUnreadAmount() >= 12) {
            log.info("Language setting change answer: counter {}, option {}, agreed {}",
                    inPacket.decodeInt(), inPacket.decodeInt(), inPacket.decodeInt());
        }
    }

    /** Client-side debug strings (force atom position errors and the like), logged at most once a minute */
    @Handler(op = InHeader.CLIENT_DEBUG_REPORT)
    public static void handleClientDebugReport(Client c, InPacket inPacket) {
        long now = System.currentTimeMillis();
        if (now - lastDebugReport < 60_000 || inPacket.getUnreadAmount() < 2) {
            return;
        }
        lastDebugReport = now;
        String chr = c.getChr() == null ? "?" : c.getChr().getName() + " job " + c.getChr().getJob();
        log.info("[ClientDebug] " + chr + ": " + inPacket.decodeString());
    }

    @Handler(op = InHeader.USER_CHARACTER_INFO_SELF_REQUEST)
    public static void handleUserCharacterInfoSelfRequest(Client c, InPacket inPacket) {
        // reference server's answer: byte 0, int 0, int 0, byte 1
        net.swordie.ms.connection.OutPacket outPacket =
                new net.swordie.ms.connection.OutPacket(net.swordie.ms.handlers.header.OutHeader.CHARACTER_INFO_LOCAL);
        outPacket.encodeByte(0);
        outPacket.encodeInt(0);
        outPacket.encodeInt(0);
        outPacket.encodeByte(1);
        c.write(outPacket);
    }

    @Handler(op = InHeader.REQUEST_STATUS_CHECK)
    public static void handleRequestStatusCheck(Client c, InPacket inPacket) {
        // int request id, int type, byte; the client matches the answer by id (60 s timeout, retried)
        int requestId = inPacket.decodeInt();
        inPacket.decodeInt();
        boolean action = inPacket.getUnreadAmount() > 0 && inPacket.decodeByte() != 0;
        OutPacket outPacket = new OutPacket(OutHeader.REQUEST_STATUS_CHECK);
        outPacket.encodeInt(requestId);
        outPacket.encodeByte(action);
        c.write(outPacket);
    }

    @Handler(ops = {InHeader.USER_ACTIVATE_DAMAGE_SKIN_UNIT, InHeader.USER_ACTIVATE_DAMAGE_SKIN_UNK})
    public static void handleUserActivateDamageSkinExtra(Client c, InPacket inPacket) {
        // the client syncs these with 0 on field entry; a non-zero value means a UI action we don't support yet
        int value = inPacket.getUnreadAmount() >= 4 ? inPacket.decodeInt() : 0;
        if (value != 0) {
            log.info("Damage skin request {} from {}", value,
                    c.getChr() == null ? "not in game" : c.getChr().getName());
        }
    }

    @Handler(op = InHeader.CLIENT_RESOURCE_ERROR)
    public static void handleClientResourceError(Client c, InPacket inPacket) {
        if (inPacket.getUnreadAmount() < 2) {
            return;
        }
        String report = inPacket.decodeString();
        if (seenResourceErrors.size() < MAX_RESOURCE_ERRORS && seenResourceErrors.add(report)) {
            log.info("Client resource error: {}", report);
        }
    }
}
