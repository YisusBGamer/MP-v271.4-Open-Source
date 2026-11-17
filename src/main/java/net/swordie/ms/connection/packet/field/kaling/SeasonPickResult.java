package net.swordie.ms.connection.packet.field.kaling;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.party.Party;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.util.FileTime;

import java.util.List;

/** Kaling's season picker. Seasons: 0 Spring, 1 Summer, 2 Fall, -1 none. */
public class SeasonPickResult {

    private final SeasonPickType type;
    private int charId;
    private int pick;
    private boolean ready;
    private Char member;
    private List<Char> members;
    private int[] picks;
    private boolean[] readies;
    private long deadlineMillis;

    private SeasonPickResult(SeasonPickType type) {
        this.type = type;
    }

    /** The picker opens with everyone in the hub; the deadline is the pick time limit. */
    public static SeasonPickResult init(List<Char> members, int[] picks, boolean[] ready, long deadlineMillis) {
        SeasonPickResult r = new SeasonPickResult(SeasonPickType.Init);
        r.members = members;
        r.picks = picks;
        r.readies = ready;
        r.deadlineMillis = deadlineMillis;
        return r;
    }

    public static SeasonPickResult member(Char member, int pick, boolean ready) {
        SeasonPickResult r = new SeasonPickResult(SeasonPickType.Member);
        r.member = member;
        r.pick = pick;
        r.ready = ready;
        return r;
    }

    public static SeasonPickResult choice(int charId, int pick) {
        SeasonPickResult r = new SeasonPickResult(SeasonPickType.Choice);
        r.charId = charId;
        r.pick = pick;
        return r;
    }

    public static SeasonPickResult ready(int charId, boolean ready) {
        SeasonPickResult r = new SeasonPickResult(SeasonPickType.Ready);
        r.charId = charId;
        r.ready = ready;
        return r;
    }

    public static SeasonPickResult leave(int charId) {
        SeasonPickResult r = new SeasonPickResult(SeasonPickType.Leave);
        r.charId = charId;
        return r;
    }

    public void encode(OutPacket outPacket) {
        outPacket.encodeInt(type.getVal());
        switch (type) {
            case Init:
                outPacket.encodeInt(members.size());
                for (int i = 0; i < members.size(); i++) {
                    outPacket.encodeInt(members.get(i).getId());
                    encodeMember(outPacket, members.get(i), picks[i], readies[i]);
                }
                FileTime.fromEpochMillis(deadlineMillis).encode(outPacket);
                break;
            case Member:
                outPacket.encodeInt(member.getId());
                encodeMember(outPacket, member, pick, ready);
                break;
            case Choice:
                outPacket.encodeInt(charId);
                outPacket.encodeInt(pick);
                outPacket.encodeInt(0);
                break;
            case Ready:
                outPacket.encodeInt(charId);
                outPacket.encodeByte(ready);
                break;
            case Leave:
                outPacket.encodeInt(charId);
                break;
        }
    }

    /** Client 0x1404050a0: pick, pick extra (-1 without a pick), ready, packed look, name, job. */
    private static void encodeMember(OutPacket outPacket, Char member, int pick, boolean ready) {
        outPacket.encodeInt(pick);
        outPacket.encodeInt(pick < 0 ? -1 : 0);
        outPacket.encodeByte(ready);
        outPacket.encodeArr(Party.packedLook480(member));
        outPacket.encodeString(member.getName());
        outPacket.encodeInt(member.getJob());
    }
}
