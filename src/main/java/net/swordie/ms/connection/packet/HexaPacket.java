package net.swordie.ms.connection.packet;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.hexa.HexaModule;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.constants.HexaConstants;
import net.swordie.ms.handlers.header.OutHeader;

/**
 * HEXA Matrix packets. Layouts from the reference tree (WvsContext hexa*, v265.3), checked against the client's readers
 * except the type-specific tail of HEXA_MESSAGE and the type 0 list of HEXA_SKILL_ERDA_CONVERSION.
 */
public class HexaPacket {

    /** Result of a HEXA_MATRIX_OPERATION request: type = the request's type, msg 0 = success */
    public static OutPacket message(int type, int msg, int coreId, int level) {
        OutPacket outPacket = new OutPacket(OutHeader.HEXA_MESSAGE);
        outPacket.encodeInt(type);
        outPacket.encodeInt(msg);
        switch (type) {
            case 0, 2 -> outPacket.encodeInt(coreId); // activate skill node / stat node
            case 1, 3 -> { // enhance skill node / stat node
                outPacket.encodeInt(coreId);
                outPacket.encodeInt(level);
            }
            default -> {
            }
        }
        return outPacket;
    }

    /** One byte; the client shows its notice (string 0x293c) only for 0, so 0 is taken as "not activated" */
    public static OutPacket activationResult(boolean success) {
        OutPacket outPacket = new OutPacket(OutHeader.HEXA_MATRIX_ACTIVATION_RESULT);
        outPacket.encodeByte(success);
        return outPacket;
    }

    public static OutPacket skillUpdate(Char chr) {
        OutPacket outPacket = new OutPacket(OutHeader.HEXA_SKILL_UPDATE);
        HexaModule.encodeSkills(chr, outPacket);
        outPacket.encodeByte(0);
        return outPacket;
    }

    public static OutPacket statUpdate(Char chr) {
        OutPacket outPacket = new OutPacket(OutHeader.HEXA_STAT_UPDATE);
        HexaModule.encodeStats(chr, outPacket);
        return outPacket;
    }

    /** The cost tables the HEXA Matrix window shows */
    public static OutPacket matrixInformation() {
        OutPacket outPacket = new OutPacket(OutHeader.HEXA_MATRIX_INFORMATION);
        int[] types = HexaConstants.NODE_TYPES;
        outPacket.encodeInt(types.length);
        for (int type : types) {
            outPacket.encodeInt(type);
            outPacket.encodeInt(HexaConstants.getActivateSolErda(type));
            outPacket.encodeInt(HexaConstants.getActivateFragments(type));
        }
        outPacket.encodeInt(types.length);
        for (int type : types) {
            outPacket.encodeInt(type);
            int levels = HexaConstants.getMaxNodeLevel(type) - 1;
            outPacket.encodeInt(levels);
            for (int level = 1; level <= levels; level++) {
                outPacket.encodeInt(level);
                outPacket.encodeInt(HexaConstants.getUpgradeSolErda(type, level));
                outPacket.encodeInt(HexaConstants.getUpgradeFragments(type, level));
            }
        }
        // the same two tables keyed by node subType; a subType node uses these instead of its type's rows
        int[] subTypes = HexaConstants.NODE_SUB_TYPES;
        outPacket.encodeInt(subTypes.length);
        for (int subType : subTypes) {
            outPacket.encodeInt(subType);
            outPacket.encodeInt(HexaConstants.getActivateSolErda(0, subType));
            outPacket.encodeInt(HexaConstants.getActivateFragments(0, subType));
        }
        outPacket.encodeInt(subTypes.length);
        for (int subType : subTypes) {
            outPacket.encodeInt(subType);
            int levels = HexaConstants.getSubTypeMaxLevel(subType) - 1;
            outPacket.encodeInt(levels);
            for (int level = 1; level <= levels; level++) {
                outPacket.encodeInt(level);
                outPacket.encodeInt(HexaConstants.getUpgradeSolErda(0, subType, level));
                outPacket.encodeInt(HexaConstants.getUpgradeFragments(0, subType, level));
            }
        }
        outPacket.encodeInt(HexaConstants.STAT_CORE_UNLOCK.length);
        for (int[] core : HexaConstants.STAT_CORE_UNLOCK) {
            outPacket.encodeInt(core[0]);
            outPacket.encodeInt(core[1]);
            outPacket.encodeInt(0);
            outPacket.encodeInt(core[2]);
            outPacket.encodeInt(HexaConstants.STAT_UPGRADE_SOL_ERDA.length);
            for (int level = 0; level < HexaConstants.STAT_UPGRADE_SOL_ERDA.length; level++) {
                outPacket.encodeInt(level);
                outPacket.encodeInt(HexaConstants.STAT_UPGRADE_SOL_ERDA[level]);
                outPacket.encodeInt(0);
                outPacket.encodeInt(HexaConstants.STAT_UPGRADE_FRAGMENTS[level]);
            }
            outPacket.encodeInt(HexaConstants.STAT_MAIN_WEIGHT.length);
            for (int level = 0; level < HexaConstants.STAT_MAIN_WEIGHT.length; level++) {
                outPacket.encodeInt(level);
                outPacket.encodeLong(Double.doubleToRawLongBits(HexaConstants.STAT_MAIN_WEIGHT[level]));
            }
            outPacket.encodeInt(21); // stat change meso, levels 10-30
            for (int level = 10; level <= 30; level++) {
                outPacket.encodeInt(level);
                outPacket.encodeLong(core[3] * 1_000_000L);
            }
            outPacket.encodeInt(31); // levels 0-30
            for (int level = 0; level <= 30; level++) {
                outPacket.encodeInt(level);
                outPacket.encodeLong(HexaConstants.STAT_CHANGE_MESO_0_TO_30);
            }
        }
        outPacket.encodeInt(HexaConstants.SOL_ERDA_FRAGMENTS.length);
        for (int fragment : HexaConstants.SOL_ERDA_FRAGMENTS) {
            outPacket.encodeInt(fragment);
        }
        return outPacket;
    }

    /** type 0: the conversion list; type 1: the result (Sol Erda used, item, amount) */
    public static OutPacket erdaConversion(int type, int solErda, int itemId, int quantity) {
        OutPacket outPacket = new OutPacket(OutHeader.HEXA_SKILL_ERDA_CONVERSION);
        outPacket.encodeInt(type);
        outPacket.encodeInt(0);
        if (type == 0) {
            outPacket.encodeInt(1);
            outPacket.encodeInt(HexaConstants.ERDA_CONVERSIONS.length);
            for (int[] conversion : HexaConstants.ERDA_CONVERSIONS) {
                outPacket.encodeInt(1);
                outPacket.encodeInt(conversion[0]);
                outPacket.encodeInt(conversion[1]);
            }
        } else if (type == 1) {
            outPacket.encodeShort(solErda);
            outPacket.encodeInt(itemId);
            outPacket.encodeInt(quantity);
        }
        return outPacket;
    }
}
