package net.swordie.ms.world.field.instance.instancehelper.boss;

import net.swordie.ms.connection.packet.field.*;
import net.swordie.ms.connection.packet.field.kaling.*;
import net.swordie.ms.world.field.fieldskill.KalingSpiritParade;
import net.swordie.ms.world.field.fieldskill.HundunDarkCloud;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.party.Party;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.ext.ServerHooks;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.world.field.Field;
import net.swordie.ms.world.field.instance.Instance;

import java.util.ArrayList;
import java.util.List;

/**
 * Kaling's season picker, the Four Seasons hub (fieldType 312: Easy 410008000, Normal 410007100, Hard 410008300,
 * Extreme 410008600), as GMS v271: the picker opens on entry (KALING_SEASON_PICK 0) with a 5-minute deadline; each member
 * picks a season and readies up (KALING_SEASON_PICK_REQUEST 0 / 1, echoed as 3 / 4); the leader starts (2) once everyone
 * is ready, and the party goes to the leader's season's scene: the hub + 20 + 40 * season (Spring, Summer, Fall).
 * The order after the first Peril is the helper's (KalingInstanceHelper picks the next living Peril).
 */
public final class KalingSeasonPick {

    private static final String PICK = "kalingPick_";
    private static final String READY = "kalingReady_";
    private static final String DEADLINE = "kalingPickDeadline";
    private static final String STARTED = "kalingPickStarted";
    private static final long PICK_MILLIS = 5 * 60_000L;
    private static final int STOP_THE_INVASION_FIELD_2 = 410007100;
    private static final int STOP_THE_INVASION_FIELD = 410008000;
    private static final int STOP_THE_INVASION_FIELD_3 = 410008300;
    private static final int STOP_THE_INVASION_FIELD_4 = 410008600;

    private KalingSeasonPick() {
    }

    public static boolean isHub(int fieldId) {
        return fieldId == STOP_THE_INVASION_FIELD || fieldId == STOP_THE_INVASION_FIELD_2 || fieldId == STOP_THE_INVASION_FIELD_3 || fieldId == STOP_THE_INVASION_FIELD_4;
    }

    /** The hub's enter script: open the picker for this character and show it to the others. */
    public static void open(Char chr) {
        Instance inst = chr.getInstance();
        Field hub = chr.getField();
        if (inst == null || hub == null || !isHub(hub.getId())) {
            return;
        }
        int hubId = hub.getId();
        long deadline;
        synchronized (inst) {
            Object d = inst.getProperty(DEADLINE);
            if (d instanceof Long) {
                deadline = (Long) d;
            } else {
                deadline = System.currentTimeMillis() + PICK_MILLIS;
                inst.addProperty(DEADLINE, deadline);
                EventManager.addEvent(() -> start(inst, hubId, true), PICK_MILLIS);
            }
        }
        List<Char> members = membersIn(inst, hubId);
        int[] picks = new int[members.size()];
        boolean[] ready = new boolean[members.size()];
        for (int i = 0; i < members.size(); i++) {
            picks[i] = pick(inst, members.get(i).getId());
            ready[i] = ready(inst, members.get(i).getId());
        }
        chr.write(KalingFieldPacket.seasonPick(SeasonPickResult.init(members, picks, ready, deadline)));
        for (Char m : members) {
            if (m != chr) {
                m.write(KalingFieldPacket.seasonPick(SeasonPickResult.member(chr, pick(inst, chr.getId()), ready(inst, chr.getId()))));
            }
        }
        if (ServerHooks.isAutomated(chr)) {
            // bots pick Spring (the order the rest of the fight was built for), ready up, and a leading bot starts
            setPick(inst, hub, chr, 0);
            setReady(inst, hub, chr, true);
            if (isLeader(chr)) {
                EventManager.addEvent(() -> start(inst, hubId, false), 2000);
            }
        }
    }

    /** KALING_SEASON_PICK_REQUEST: 0 = int season, 1 = byte ready, 2 = start (the leader), 3 = leave. */
    public static void onRequest(Char chr, InPacket in) {
        Instance inst = chr.getInstance();
        Field hub = chr.getField();
        if (inst == null || hub == null || !isHub(hub.getId()) || in.getUnreadAmount() < 4) {
            return;
        }
        int sub = in.decodeInt();
        switch (sub) {
            case 0 -> {
                if (in.getUnreadAmount() >= 4) {
                    int season = in.decodeInt();
                    if (season >= 0 && season <= 2) {
                        setPick(inst, hub, chr, season);
                    }
                }
            }
            case 1 -> {
                if (in.getUnreadAmount() >= 1) {
                    setReady(inst, hub, chr, in.decodeByte() != 0);
                }
            }
            case 2 -> {
                if (isLeader(chr)) {
                    start(inst, hub.getId(), false);
                }
            }
            case 3 -> {
                // Exit, confirmed in the client's "leave?" dialog: the others see the member go (type 5) and it
                // leaves the fight the normal way (the instance's forced return)
                inst.addProperty(PICK + chr.getId(), -1);
                inst.addProperty(READY + chr.getId(), false);
                for (Char m : membersIn(inst, hub.getId())) {
                    if (m != chr) {
                        m.write(KalingFieldPacket.seasonPick(SeasonPickResult.leave(chr.getId())));
                    }
                }
                inst.removeChar(chr);
            }
            default -> {
            }
        }
    }

    private static void setPick(Instance inst, Field hub, Char chr, int season) {
        inst.addProperty(PICK + chr.getId(), season);
        hub.broadcastPacket(KalingFieldPacket.seasonPick(SeasonPickResult.choice(chr.getId(), season)));
    }

    private static void setReady(Instance inst, Field hub, Char chr, boolean ready) {
        inst.addProperty(READY + chr.getId(), ready);
        hub.broadcastPacket(KalingFieldPacket.seasonPick(SeasonPickResult.ready(chr.getId(), ready)));
        hub.broadcastPacket(KalingFieldPacket.seasonPick(SeasonPickResult.choice(chr.getId(), pick(inst, chr.getId()))));
    }

    /** Everyone in the hub goes to the leader's season (Spring if none); before the deadline only once all are ready. */
    private static void start(Instance inst, int hubId, boolean deadline) {
        List<Char> members = membersIn(inst, hubId);
        if (members.isEmpty()) {
            return;
        }
        synchronized (inst) {
            if (inst.hasProperty(STARTED)) {
                return;
            }
            if (!deadline && members.stream().anyMatch(m -> !ready(inst, m.getId()))) {
                return;
            }
            inst.addProperty(STARTED, true);
        }
        Char leader = members.stream().filter(KalingSeasonPick::isLeader).findFirst().orElse(members.get(0));
        int season = Math.max(0, pick(inst, leader.getId()));
        int scene = hubId + 20 + 40 * season;
        for (Char m : members) {
            m.warp(scene);
        }
    }

    private static List<Char> membersIn(Instance inst, int hubId) {
        List<Char> list = new ArrayList<>();
        for (Char c : new ArrayList<>(inst.getChars())) {
            if (c.getFieldID() == hubId) {
                list.add(c);
            }
        }
        return list;
    }

    private static int pick(Instance inst, int charId) {
        Object p = inst.getProperty(PICK + charId);
        return p instanceof Integer ? (Integer) p : -1;
    }

    private static boolean ready(Instance inst, int charId) {
        return Boolean.TRUE.equals(inst.getProperty(READY + charId));
    }

    private static boolean isLeader(Char chr) {
        Party party = chr.getParty();
        return party == null || party.getPartyLeaderID() == chr.getId();
    }
}
