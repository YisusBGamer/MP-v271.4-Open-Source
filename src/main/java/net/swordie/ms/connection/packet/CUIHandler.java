package net.swordie.ms.connection.packet;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.union.UnionBoard;
import net.swordie.ms.client.character.union.UnionMember;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.handlers.header.OutHeader;
import net.swordie.ms.life.mob.Mob;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Sjonnie
 * Created on 10/10/2018.
 */
public class CUIHandler {

    /**
     * Opens the Legion window (client 0x143ec10d0): int unclaimed raid coins (the Claim Coins tooltip shows it against
     * the rank's coinStackMax), byte (1 = open on the artifact tab), then the union block 0x1411ffc00 that
     * UNION_ASSIGN_RESULT also carries.
     */
    public static OutPacket unionResult(int coin, int rank, List<Char> eligibleChars, UnionBoard activeBoard,
                                        UnionMember mobileMember, UnionMember labMember, UnionMember labEnhancedMember) {
        OutPacket outPacket = new OutPacket(OutHeader.UNION_RESULT);

        outPacket.encodeInt(coin);
        outPacket.encodeByte(0);
        outPacket.encodeInt(rank);

        outPacket.encodeInt(eligibleChars.size());
        for (Char chr : eligibleChars) {
            chr.createUnionMember().encode(outPacket, true);
        }

        outPacket.encodeInt(activeBoard.getActiveMembers().size());
        for (UnionMember unionMember : activeBoard.getActiveMembers()) {
            unionMember.encode(outPacket);
        }
        outPacket.encodeByte(mobileMember != null);
        if (mobileMember != null) {
            mobileMember.encode(outPacket);
        }
        outPacket.encodeByte(labMember != null);
        if (labMember != null) {
            labMember.encode(outPacket);
        }
        outPacket.encodeByte(labEnhancedMember != null);
        if (labEnhancedMember != null) {
            labEnhancedMember.encode(outPacket);
        }
        outPacket.encodeByte(false); // v265: one more optional member
        encodeChampions(outPacket, eligibleChars);
        for (int i = 0; i < 4; i++) {
            outPacket.encodeByte(0);
        }
        return outPacket;
    }
    
    /**
     * The 5 Legion presets, sent with the Legion window (client 0x1412011b0 -> 0x141200ee0 per preset): byte has; if
     * has: 8 ints (synergy grid), int n, n x union member (0x1411ff020). It used to send the eligible list as preset 1's
     * members.
     */
    public static OutPacket unionPresets(net.swordie.ms.client.character.union.Union union) {
        OutPacket outPacket = new OutPacket(OutHeader.UNION_PRESETS);
        for (int p = 0; p < net.swordie.ms.client.character.union.Union.MAX_PRESETS; p++) {
            UnionBoard board = union.hasPresetUnlocked(p) ? union.getBoardByPreset(p) : null;
            outPacket.encodeByte(board != null);
            if (board == null) {
                continue;
            }
            List<Integer> grid = board.getSynergyGrid();
            for (int i = 0; i < 8; i++) {
                outPacket.encodeInt(grid != null && i < grid.size() ? grid.get(i) : i);
            }
            var members = board.getActiveMembers().stream().filter(UnionMember::hasChr).collect(Collectors.toList());
            outPacket.encodeInt(members.size());
            for (UnionMember m : members) {
                m.encode(outPacket, false);
            }
        }
        return outPacket;
    }

    /**
     * The union block's champion map (0x1411ffc00, kept at the union manager +0xb0): compact count, then per champion
     * int key (the slot), byte 1, its union member (0x1411ff020).
     */
    private static void encodeChampions(OutPacket outPacket, List<Char> eligibleChars) {
        var acc = eligibleChars == null || eligibleChars.isEmpty() ? null : eligibleChars.get(0).getAccount();
        List<Integer> slots = new java.util.ArrayList<>();
        List<Char> chars = new java.util.ArrayList<>();
        if (acc != null) {
            var champions = net.swordie.ms.client.character.union.ChampionModule.getSlots(acc);
            for (int i = 0; i < champions.length; i++) {
                Char c = champions[i] == null ? null : acc.getCharById(champions[i].charId);
                if (c != null) {
                    slots.add(i);
                    chars.add(c);
                }
            }
        }
        encodeCompactInt(outPacket, slots.size());
        for (int i = 0; i < slots.size(); i++) {
            outPacket.encodeInt(slots.get(i));
            outPacket.encodeByte(1);
            chars.get(i).createUnionMember().encode(outPacket, true);
        }
    }

    /** The client's compact signed int (reader 0x140eb8340): zigzag, 7 bits a byte, high bit = more. */
    private static void encodeCompactInt(OutPacket outPacket, int value) {
        int z = (value << 1) ^ (value >> 31);
        while ((z & ~0x7F) != 0) {
            outPacket.encodeByte((z & 0x7F) | 0x80);
            z >>>= 7;
        }
        outPacket.encodeByte(z);
    }

    public static OutPacket unionAssignResult(int rank, List<Char> eligibleChars, UnionBoard activeBoard,
                                              UnionMember mobileMember, UnionMember labMember, UnionMember labEnhancedMember) {
        OutPacket outPacket = new OutPacket(OutHeader.UNION_ASSIGN_RESULT);

        outPacket.encodeInt(rank);

        outPacket.encodeInt(eligibleChars.size());
        for (Char chr : eligibleChars) {
            chr.createUnionMember().encode(outPacket, true);
        }

        var activeMembers = activeBoard.getActiveMembers().stream()
                .filter(UnionMember::hasChr)
                .collect(Collectors.toSet());
        outPacket.encodeInt(activeMembers.size());
        for (UnionMember unionMember : activeMembers) {
            unionMember.encode(outPacket, false);
        }
        outPacket.encodeByte(mobileMember != null);
        if (mobileMember != null) {
            mobileMember.encode(outPacket, false);
        }
        outPacket.encodeByte(labMember != null);
        if (labMember != null) {
            labMember.encode(outPacket, false);
        }
        outPacket.encodeByte(labEnhancedMember != null);
        if (labEnhancedMember != null) {
            labEnhancedMember.encode(outPacket, false);
        }
        outPacket.encodeByte(false); // v265: one more optional member
        encodeChampions(outPacket, eligibleChars); // v271 0x1459feb50 reads nothing after the union block
        return outPacket;
    }
    
    /**
     * Unclaimed-coin timer (client 0x143ec46e0): seconds until the unclaimed raid coins reach the cap, then the cap.
     * When the time runs out (or at 0 while QR 18098 coin equals the cap) the client shows the "coins full" popup;
     * a negative time removes the timer.
     */
    public static OutPacket unionCoin(int secondsUntilFull, int cap) {
        OutPacket outPacket = new OutPacket(OutHeader.UNION_COIN);

        outPacket.encodeInt(secondsUntilFull);
        outPacket.encodeInt(cap);

        return outPacket;
    }

    /** Unclaimed coin count for an open Legion window (v271 0x1444f7060: read only while the window exists). */
    public static OutPacket unionUnclaimedCoin(int coins) {
        OutPacket outPacket = new OutPacket(OutHeader.UNION_UNCLAIMED_COIN);

        outPacket.encodeInt(coins);

        return outPacket;
    }

    /** Legion window refusal (client 0x144911970): 1/2 busy, 3 incapacitated, 4 in the middle of a task. */
    public static OutPacket unionRequestFail(int reason) {
        OutPacket outPacket = new OutPacket(OutHeader.UNION_REQUEST_FAIL);

        outPacket.encodeShort(reason);

        return outPacket;
    }
    
    public static OutPacket unionRaidDamage(long damage) {
        OutPacket outPacket = new OutPacket(OutHeader.UNION_RAID_DAMAGE);

        outPacket.encodeLong(damage);

        return outPacket;
    }
    
    public static OutPacket unionRaidCoin(int coin, boolean noCoinAnimation) {
        OutPacket outPacket = new OutPacket(OutHeader.UNION_RAID_COIN);

        outPacket.encodeInt(coin);
        outPacket.encodeByte(noCoinAnimation); // No Coin Animation

        return outPacket;
    }

    public static OutPacket unionRaidBossUpdate(Mob shield, Mob boss) {
        return unionRaidBossUpdate(shield.getTemplateId(), boss.getTemplateId(), shield.getHp(), shield.getMaxHp(), boss.getHp(), boss.getMaxHp());
    }
    
    public static OutPacket unionRaidBossUpdate(int template, int template2, long curHP, long maxHP, long curHP2, long maxHP2) {
        OutPacket outPacket = new OutPacket(OutHeader.UNION_RAID_BOSS_UPDATE);

        outPacket.encodeInt(template);
        outPacket.encodeLong(curHP);
        outPacket.encodeLong(maxHP);
        outPacket.encodeInt(template2);
        outPacket.encodeLong(curHP2);
        outPacket.encodeLong(maxHP2);

        return outPacket;
    }

    public static OutPacket towerOfOzUpdate(boolean pause, int remainingTimeMS, int maxTimeMS) {
        OutPacket outPacket = new OutPacket(OutHeader.TOWER_OF_OZ_TIMER_INFO);

        var type = 1;
        outPacket.encodeInt(type);
        outPacket.encodeInt(pause ? 1 : 2); // 2 = Countdown | 1 = Pause
        outPacket.encodeInt(remainingTimeMS); // remainingTime MS
        outPacket.encodeInt(0);
        outPacket.encodeInt(maxTimeMS); // maxTime MS
        outPacket.encodeInt(42011); // OzTimer Quest Id

        return outPacket;
    }
}
