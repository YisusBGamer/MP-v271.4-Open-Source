package net.swordie.ms.handlers.boss;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.handlers.Handler;
import net.swordie.ms.handlers.header.InHeader;
import net.swordie.ms.world.field.instance.instancehelper.InstanceHelperFactory;
import net.swordie.ms.world.field.instance.instancehelper.boss.KalingInstanceHelper;

/**
 * Kaling's field requests.
 */
public class KalingHandler {

    private static KalingInstanceHelper helper(Char chr) {
        if (chr.getInstance() == null) {
            return null;
        }
        return InstanceHelperFactory.getHelperByInstance(chr.getInstance()) instanceof KalingInstanceHelper k ? k : null;
    }

    /** The Four Seasons hub's season picker: 0 = pick, 1 = ready, 2 = start (KalingSeasonPick). */
    @Handler(op = InHeader.KALING_SEASON_PICK_REQUEST)
    public static void handleSeasonPick(Char chr, InPacket inPacket) {
        net.swordie.ms.world.field.instance.instancehelper.boss.KalingSeasonPick.onRequest(chr, inPacket);
    }

    /** 1 = a lit black flame sees the player (int id, int value); 0x3ed = the player stood in shock zones too long. */
    @Handler(op = InHeader.KALING_FIELD_REQUEST)
    public static void handleFieldRequest(Char chr, InPacket inPacket) {
        KalingInstanceHelper kaling = helper(chr);
        if (kaling == null || inPacket.getUnreadAmount() < 4) {
            return;
        }
        int type = inPacket.decodeInt();
        if (type == 1 && inPacket.getUnreadAmount() >= 8) {
            int id = inPacket.decodeInt();
            int value = inPacket.decodeInt();
            kaling.onFlameTrigger(chr, id, value);
        } else if (type == 0x3ed) {
            kaling.onZoneStun(chr);
        }
    }

    /** Taowu's map: 5 = harvest a mini coil (int key); the other subtypes are read and left to the server's checks. */
    @Handler(op = InHeader.KALING_ELECTRIC_REQUEST)
    public static void handleElectricRequest(Char chr, InPacket inPacket) {
        KalingInstanceHelper kaling = helper(chr);
        if (kaling == null || inPacket.getUnreadAmount() < 4) {
            return;
        }
        int type = inPacket.decodeInt();
        if (type == 2 && inPacket.getUnreadAmount() >= 5) {
            // Taowu's attack landed: byte 1 + coil it hit, or byte 0 + key + the shock zones it leaves
            boolean coil = inPacket.decodeByte() != 0;
            if (coil) {
                kaling.onTaowuAttackCoil(chr, inPacket.decodeInt());
            } else {
                int key = inPacket.decodeInt();
                int n = inPacket.getUnreadAmount() >= 4 ? inPacket.decodeInt() : 0;
                n = Math.max(0, Math.min(n, inPacket.getUnreadAmount() / 12));
                int[][] spots = new int[n][];
                for (int i = 0; i < n; i++) {
                    spots[i] = new int[]{inPacket.decodeInt(), inPacket.decodeInt(), inPacket.decodeInt()};
                }
                kaling.onTaowuZonesReported(chr, key, spots);
            }
        } else if (type == 8 && inPacket.getUnreadAmount() >= 4) {
            kaling.onTaowuZonesConfirmed(chr, inPacket.decodeInt());
        } else if (type == 12) {
            kaling.onTaowuTimerReport(chr);
        } else if (type == 5 && inPacket.getUnreadAmount() >= 4) {
            kaling.onMiniCoilHarvest(chr, inPacket.decodeInt());
        }
    }

    /** Field skill 100028 (Dark Cloud): the boss's controller reports it entering or leaving the cloud. */
    @Handler(op = InHeader.FIELD_SKILL_REQUEST)
    public static void handleFieldSkillRequest(Char chr, InPacket inPacket) {
        KalingInstanceHelper kaling = helper(chr);
        if (kaling == null || inPacket.getUnreadAmount() < 13) {
            return;
        }
        int type = inPacket.decodeInt();
        int key = inPacket.decodeInt();
        int mobObjectId = inPacket.decodeInt();
        boolean on = inPacket.decodeByte() != 0;
        if (type == 0) {
            kaling.onCloudReport(chr, key, mobObjectId, on);
        }
    }
}
