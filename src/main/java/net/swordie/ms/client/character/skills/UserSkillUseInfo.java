package net.swordie.ms.client.character.skills;

import net.swordie.ms.client.character.skills.info.SkillUseInfo;
import net.swordie.ms.connection.InPacket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created on 11-4-2019.
 */
public class UserSkillUseInfo {

    private static final Logger log = LogManager.getLogger();

    public SkillUseInfo skillUseInfo;

    public UserSkillUseInfo(SkillUseInfo skillUseInfo) {
        this.skillUseInfo = skillUseInfo;
    }

    /**
     * The client's per-skill extra info (v271 process types, encoder 0x141099180). A type the client has no case for
     * writes nothing. v271 vs v265: 3 and 25 gained fields, 26 and 52 are new, v265 42/43/45/48/49/51/52 are now
     * 41/42/44/46/47/49/50.
     */
    public void decode(InPacket inPacket) {
        inPacket.decodeInt(); // crc
        if (inPacket.decodeByte() == 0) {
            return;
        }
        int flag = inPacket.decodeInt();
        int type;
        while ((type = inPacket.decodeInt()) > 0) {
            switch (type) {
                case 1 -> {
                    if (inPacket.decodeByte() != 0) {
                        skillUseInfo.skillID = inPacket.decodeInt();
                        skillUseInfo.skillID2 = inPacket.decodeInt();
                        skillUseInfo.slv = inPacket.decodeInt();
                        inPacket.decodeByte();
                        int len = inPacket.decodeByte();
                        for (int i = 0; i < len; i++) {
                            inPacket.decodeInt();
                        }
                    }
                }
                case 2 -> {
                    if (inPacket.decodeByte() != 0) {
                        inPacket.decodeByte();
                        inPacket.decodeByte();
                        inPacket.decodeInt(); // skill id
                        inPacket.decodeInt();
                        inPacket.decodeByte();
                        skillUseInfo.endingPosition = inPacket.decodePositionInt();
                        for (int i = 0; i < 4; i++) {
                            inPacket.decodeInt();
                        }
                    }
                }
                case 3 -> {
                    if (inPacket.decodeByte() != 0) {
                        inPacket.decodeByte();
                        skillUseInfo.skillID2 = inPacket.decodeInt();
                        inPacket.decodeInt();
                    }
                }
                case 4 -> {
                    if (inPacket.decodeByte() != 0) {
                        skillUseInfo.rect = inPacket.decodeIntRect();
                        skillUseInfo.position = inPacket.decodePositionInt();
                        skillUseInfo.isLeft = inPacket.decodeInt() == -1;
                    }
                }
                case 7 -> {
                    if (inPacket.decodeByte() != 0) {
                        skillUseInfo.targetPosition = inPacket.decodePositionInt();
                        for (int i = 0; i < 4; i++) {
                            inPacket.decodeByte();
                        }
                        skillUseInfo.isLeft = inPacket.decodeByte() != 0;
                    }
                }
                case 8 -> {
                    if (inPacket.decodeByte() != 0) {
                        skillUseInfo.endingPosition = inPacket.decodePositionInt();
                        skillUseInfo.rushX = inPacket.decodeInt();
                        skillUseInfo.rushY = inPacket.decodeInt();
                    }
                }
                case 9 -> {
                    if (inPacket.decodeByte() != 0) {
                        inPacket.decodeInt();
                        skillUseInfo.lifeObjectId = inPacket.decodeInt();
                        skillUseInfo.endingPosition = inPacket.decodePositionInt();
                        skillUseInfo.isLeft = inPacket.decodeInt() == -1; // -1 = left, 1 = right
                    }
                }
                case 5, 6, 10, 11, 12, 13, 14, 22, 23 -> inPacket.decodeByte();
                case 15 -> {
                    if (inPacket.decodeByte() != 0) {
                        int size = inPacket.decodeInt();
                        for (int i = 0; i < size; i++) {
                            inPacket.decodeIntRect();
                        }
                        inPacket.decodeIntRect();
                    }
                }
                case 19 -> {
                    if (inPacket.decodeByte() != 0) {
                        skillUseInfo.lifeObjectId = inPacket.decodeInt(); // mob
                        skillUseInfo.unk = inPacket.decodeInt();
                        skillUseInfo.fieldId = inPacket.decodeInt();
                        inPacket.decodeInt();
                        inPacket.decodeInt();
                    }
                }
                case 20, 41 -> {
                    if (inPacket.decodeByte() != 0) {
                        int size = inPacket.decodeInt();
                        for (int i = 0; i < size; i++) {
                            inPacket.decodeInt();
                        }
                    }
                }
                case 24 -> {
                    if (inPacket.decodeByte() != 0) {
                        skillUseInfo.startingPosition = inPacket.decodePositionInt();
                        skillUseInfo.endingPosition = inPacket.decodePositionInt();
                        skillUseInfo.position = inPacket.decodePositionInt(); // hit position
                        skillUseInfo.unk = inPacket.decodeInt();
                        skillUseInfo.angle = inPacket.decodeInt();
                    }
                }
                case 25 -> {
                    if (inPacket.decodeByte() != 0) {
                        int size = inPacket.decodeInt();
                        for (int i = 0; i < size; i++) {
                            skillUseInfo.mobIds.add(inPacket.decodeInt());
                            skillUseInfo.positions.add(inPacket.decodePositionInt());
                            for (int j = 0; j < 4; j++) {
                                inPacket.decodeInt();
                            }
                        }
                        skillUseInfo.isLeft = inPacket.decodeByte() != 0;
                    }
                }
                case 26 -> {
                    if (inPacket.decodeByte() != 0) {
                        skillUseInfo.castPosition = inPacket.decodePositionInt();
                    }
                }
                case 29, 42 -> {
                    if (inPacket.decodeByte() != 0) {
                        inPacket.decodeInt();
                    }
                }
                case 34 -> {
                    if (inPacket.decodeByte() != 0) { // Photon Ray: mob object id, count
                        int size = inPacket.decodeInt();
                        Map<Integer, Integer> targetMap = new HashMap<>();
                        for (int i = 0; i < size; i++) {
                            targetMap.put(inPacket.decodeInt(), inPacket.decodeInt());
                        }
                        skillUseInfo.targetMap = targetMap;
                    }
                }
                case 37 -> {
                    if (inPacket.decodeByte() != 0) {
                        for (int i = 0; i < 6; i++) {
                            inPacket.decodeInt();
                        }
                        skillUseInfo.position = inPacket.decodePositionInt();
                        inPacket.decodeInt();
                        inPacket.decodeInt();
                        inPacket.decodeByte();
                        inPacket.decodeByte();
                        inPacket.decodeByte();
                    }
                }
                case 39 -> {
                    if (inPacket.decodeByte() != 0) {
                        skillUseInfo.multiAttackInfoSequenceCounter = inPacket.decodeInt();
                        inPacket.decodeInt();
                        int size = inPacket.decodeInt();
                        for (int i = 0; i < size; i++) {
                            inPacket.decodeInt(); // 4 bytes each (0x1410e5370)
                        }
                    }
                }
                case 44 -> {
                    if (inPacket.decodeByte() != 0) {
                        inPacket.decodeInt();
                        inPacket.decodeInt();
                        inPacket.decodeByte();
                        inPacket.decodeInt();
                        inPacket.decodeInt();
                    }
                }
                case 46 -> {
                    if (inPacket.decodeByte() != 0) {
                        int size = inPacket.decodeInt();
                        for (int i = 0; i < size; i++) {
                            inPacket.decodeLong();
                        }
                    }
                }
                case 47 -> {
                    if (inPacket.decodeByte() != 0) {
                        int size = inPacket.decodeInt();
                        for (int i = 0; i < size; i++) {
                            inPacket.decodeLong();
                        }
                        size = inPacket.decodeInt();
                        for (int i = 0; i < size; i++) {
                            inPacket.decodeInt();
                        }
                    }
                }
                case 49 -> {
                    if (inPacket.decodeByte() != 0) {
                        for (int i = 0; i < 10; i++) {
                            inPacket.decodeInt();
                        }
                        inPacket.decodeByte();
                        inPacket.decodeByte();
                    }
                }
                case 50 -> {
                    if (inPacket.decodeByte() != 0) {
                        inPacket.decodeInt();
                        inPacket.decodeInt();
                    }
                }
                case 52 -> {
                    if (inPacket.decodeByte() != 0) {
                        inPacket.decodeLong();
                        inPacket.decodeLong();
                        inPacket.decodeInt();
                        int size = inPacket.decodeInt();
                        for (int i = 0; i < size; i++) {
                            inPacket.decodeInt();
                        }
                    }
                }
                default -> {
                    // the client writes nothing for a type it has no case for
                }
            }
        }
        // v271 (0x141099180) also writes byte [int], byte [float, 8 bytes] after the list, but into the packet's
        // second buffer (+0x460), which goes after the whole body (logged: `00 00 02 00 00 00` = the two flags and
        // its length), not here.
        // A zigzag varint length (0 so far) after the list, then that many bytes
        byte b;
        int raw = 0;
        int shift = 0;
        do {
            b = inPacket.decodeByte();
            if (shift < 32) {
                raw |= (b & 0x7F) << shift;
            }
            shift += 7;
        } while ((b & 0x80) != 0);
        int n = (raw >>> 1) ^ -(raw & 1);
        if (n > 0 && n <= inPacket.getUnreadAmount()) {
            inPacket.decodeArr(n);
        }
    }

    public enum UserSkillUseType {
        UserSkill(1),
        Unk2(2),
        KeyDownInfo(3),
        AttackRectInfo(4),
        Unk5(5),
        Unk6(6),
        SummonLife(7),
        RushSkillInfo(8),
        Unk9(9),
        Unk10(10),
        Unk11(11),
        Unk12(12),
        Unk13(13),
        Unk14(14),
        Unk15(15),
        Unk19(19),
        Unk20(20),
        Unk21(21),
        Unk22(22),
        Unk23(23),
        Unk27(27),
        PhotonRay(32),
        DeathTrigger(35),
        MultiAttackInfo(37),
        ;

        private int val;

        UserSkillUseType(int val) {
            this.val = val;
        }

        public int getVal() {
            return val;
        }

        public static UserSkillUseType getByVal(int val) {
            return Arrays.stream(values()).filter(t -> t.getVal() == val).findFirst().orElse(null);
        }
    }
}
