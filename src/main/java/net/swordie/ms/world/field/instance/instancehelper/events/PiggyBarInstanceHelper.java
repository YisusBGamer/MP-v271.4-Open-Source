package net.swordie.ms.world.field.instance.instancehelper.events;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.connection.packet.field.FieldPacket;
import net.swordie.ms.connection.packet.ReactorPool;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.enums.ChatType;
import net.swordie.ms.enums.WeatherEffNoticeType;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.handlers.header.OutHeader;
import net.swordie.ms.life.Reactor;
import net.swordie.ms.util.Position;
import net.swordie.ms.world.field.ClockPacket;
import net.swordie.ms.world.field.Field;
import net.swordie.ms.world.field.Portal;
import net.swordie.ms.world.field.instance.Instance;
import net.swordie.ms.world.field.instance.instancehelper.InstanceHelper;

import java.util.*;
import java.util.concurrent.ScheduledFuture;

/**
 * Piggy Bar minigame (fieldType 118, Manager Mush): Red team against Blue team over three rounds (buttons, levers,
 * cannons). Working your own team's machines fills its gauge (FIELD_VALUE redScore{n} / blueScore{n}, 0-100, shown by
 * the client's piggyBarMinigame gauge); the first team to 100, or the higher one when time runs out, takes the round.
 * The collision portals around each team's machines (piggy_redPT / piggy_bluePT) send the other team back to its
 * side. After the third round everyone goes to the reward room, where Manager Mush hands out the reward.
 * A team with nobody in it is run by the house (its gauge rises by itself), so a single player can play.
 * Round time, points, the house's pace and the reward are this server's numbers (GMS rules unknown).
 */
public class PiggyBarInstanceHelper extends InstanceHelper {

    public static final int LOBBY = 910194100;
    public static final int REWARD_ROOM = 910194251;
    private static final int[] ROUNDS = {910194101, 910194151, 910194201};
    private static final String[] ROUND_NAMES = {"Buttons", "Levers", "Cannons"};
    private static final int[] HITS_TO_WORK = {5, 5, 1};
    private static final int[] POINTS = {10, 10, 8};

    private static final int ROUND_SECONDS = 90;
    private static final int WIN_SCORE = 100;
    private static final int HOUSE_POINTS = 5;
    private static final int HOUSE_EVERY_MS = 4500;
    private static final int START_DELAY_MS = 4000;
    private static final int NEXT_ROUND_DELAY_MS = 5000;
    private static final int REWARD_ROOM_SECONDS = 60;

    public static final String WON = "piggyWon";
    private static final String CLAIMED = "piggyClaimed";

    private final Map<Integer, Boolean> red = new HashMap<>(); // char id -> on the Red team
    private final Map<Integer, Integer> reactorHits = new HashMap<>(); // reactor object id -> hits
    private int round = -1;
    private boolean running;
    private boolean finished;
    private int redScore, blueScore;
    private int redRounds, blueRounds;
    private ScheduledFuture roundTimer, houseTimer;

    public static boolean isField(int fieldId) {
        return fieldId == ROUNDS[0];
    }

    public static PiggyBarInstanceHelper of(Char chr) {
        Instance inst = chr == null ? null : chr.getInstance();
        return inst != null && inst.getInstanceHelper() instanceof PiggyBarInstanceHelper h ? h : null;
    }

    @Override
    public void setup(Instance instance) {
        synchronized (this) {
            boolean next = true;
            for (Char chr : instance.getChars()) {
                red.put(chr.getId(), next);
                next = !next;
            }
        }
        for (Char chr : new ArrayList<>(instance.getChars())) {
            placeAtSpawn(chr);
            chr.chatMessage(ChatType.Mob, String.format("You are on the %s team. Work your side's machines to fill your gauge!",
                    isRed(chr) ? "Red" : "Blue"));
        }
        startRoundLater(0);
    }

    @Override
    protected void startStagePreDelay() {
        // rounds are started by setup and endRound
    }

    private synchronized boolean isRed(Char chr) {
        return red.getOrDefault(chr.getId(), true);
    }

    private synchronized boolean hasPlayers(boolean redTeam) {
        for (Char chr : getInstance().getChars()) {
            if (red.getOrDefault(chr.getId(), true) == redTeam) {
                return true;
            }
        }
        return false;
    }

    private Field roundField() {
        return round >= 0 && round < ROUNDS.length ? getInstance().getField(ROUNDS[round]) : null;
    }

    private void placeAtSpawn(Char chr) {
        Field field = chr.getField();
        if (field == null) {
            return;
        }
        Portal p = field.getInfo().getPortalByName(isRed(chr) ? "redTeam" : "blueTeam");
        if (p != null) {
            Position pos = new Position(p.getX(), p.getY());
            chr.setPosition(pos.deepCopy());
            chr.write(FieldPacket.teleport(pos, chr));
        }
    }

    private void startRoundLater(int r) {
        addTimer(EventManager.addEvent(() -> startRound(r), START_DELAY_MS));
    }

    private void startRound(int r) {
        Field field;
        synchronized (this) {
            if (finished) {
                return;
            }
            round = r;
            redScore = 0;
            blueScore = 0;
            reactorHits.clear();
            running = true;
            field = roundField();
        }
        if (field == null) {
            return;
        }
        field.broadcastPacket(FieldPacket.fieldValue("redScore" + (r + 1), "0"));
        field.broadcastPacket(FieldPacket.fieldValue("blueScore" + (r + 1), "0"));
        field.broadcastPacket(FieldPacket.clock(ClockPacket.secondsClock(ROUND_SECONDS)));
        field.broadcastPacket(WvsContext.weatherEffectNotice(WeatherEffNoticeType.SprinkledChocolate,
                String.format("Round %d: %s! First team to fill its gauge wins.", r + 1, ROUND_NAMES[r]), 5000));
        roundTimer = addTimer(EventManager.addEvent(this::timeOver, ROUND_SECONDS * 1000L));
        for (boolean team : new boolean[]{true, false}) {
            if (!hasPlayers(team)) {
                houseTimer = addTimer(EventManager.addFixedRateEvent(() -> addPoints(team, HOUSE_POINTS),
                        HOUSE_EVERY_MS, HOUSE_EVERY_MS));
            }
        }
    }

    @Override
    public void onWarp(Char chr, Field oldField, Field newField) {
        if (newField == null || newField.getInstance() != getInstance()) {
            return;
        }
        int idx = -1;
        for (int i = 0; i < ROUNDS.length; i++) {
            if (ROUNDS[i] == newField.getId()) {
                idx = i;
            }
        }
        if (idx > 0) {
            addTimer(EventManager.addEvent(() -> placeAtSpawn(chr), 500));
        }
    }

    /** A hit on one of the round's machines (reactor scripts piggybar{round}step{n}). */
    public void hitMachine(Char chr, Reactor reactor) {
        boolean team = isRed(chr);
        boolean machineRed = reactor.getPosition().getX() < 0;
        int points;
        synchronized (this) {
            if (!running || chr.getField() != roundField()) {
                return;
            }
            if (team != machineRed) {
                chr.chatMessage(ChatType.Mob, "That's the other team's machine!");
                return;
            }
            int hits = reactorHits.merge(reactor.getObjectId(), 1, Integer::sum);
            if (hits < HITS_TO_WORK[round]) {
                return;
            }
            reactorHits.put(reactor.getObjectId(), 0);
            points = POINTS[round];
        }
        reactor.setState((byte) 1);
        chr.getField().broadcastPacket(ReactorPool.reactorChangeState(reactor, (short) 0, (byte) 0));
        addTimer(EventManager.addEvent(() -> {
            reactor.setState((byte) 0);
            Field f = reactor.getField();
            if (f != null) {
                f.broadcastPacket(ReactorPool.reactorChangeState(reactor, (short) 0, (byte) 0));
            }
        }, 600));
        addPoints(team, points);
    }

    private void addPoints(boolean team, int points) {
        Field field;
        int score;
        synchronized (this) {
            if (!running) {
                return;
            }
            if (team) {
                redScore = Math.min(WIN_SCORE, redScore + points);
                score = redScore;
            } else {
                blueScore = Math.min(WIN_SCORE, blueScore + points);
                score = blueScore;
            }
            field = roundField();
        }
        if (field != null) {
            field.broadcastPacket(FieldPacket.fieldValue((team ? "redScore" : "blueScore") + (round + 1), String.valueOf(score)));
        }
        if (score >= WIN_SCORE) {
            endRound(team ? 1 : 2);
        }
    }

    /** The collision portals around a team's machines: the other team is sent back to its side. */
    public void touchTeamWall(Char chr, boolean redWall) {
        if (isRed(chr) != redWall) {
            placeAtSpawn(chr);
        }
    }

    private void timeOver() {
        int winner;
        synchronized (this) {
            winner = redScore > blueScore ? 1 : blueScore > redScore ? 2 : 0;
        }
        endRound(winner);
    }

    /** @param winner 1 Red, 2 Blue, 0 a draw */
    private void endRound(int winner) {
        Field field;
        int r;
        synchronized (this) {
            if (!running) {
                return;
            }
            running = false;
            EventManager.stopTimer(roundTimer);
            EventManager.stopTimer(houseTimer);
            if (winner == 1) {
                redRounds++;
            } else if (winner == 2) {
                blueRounds++;
            }
            field = roundField();
            r = round;
        }
        if (field != null) {
            field.broadcastPacket(FieldPacket.clock(ClockPacket.removeClock()));
            String text = winner == 0 ? "Time's up! The round is a draw."
                    : String.format("%s team wins round %d!", winner == 1 ? "Red" : "Blue", r + 1);
            field.broadcastPacket(WvsContext.weatherEffectNotice(WeatherEffNoticeType.SprinkledChocolate, text, 4000));
        }
        addTimer(EventManager.addEvent(() -> {
            if (r + 1 < ROUNDS.length) {
                getInstance().warp(ROUNDS[r + 1]);
                startRoundLater(r + 1);
            } else {
                finish();
            }
        }, NEXT_ROUND_DELAY_MS));
    }

    private void finish() {
        synchronized (this) {
            if (finished) {
                return;
            }
            finished = true;
        }
        boolean redWins = redRounds > blueRounds;
        boolean draw = redRounds == blueRounds;
        for (Char chr : new ArrayList<>(getInstance().getChars())) {
            boolean won = !draw && isRed(chr) == redWins;
            getInstance().addProperty(WON + chr.getId(), won);
        }
        getInstance().warp(REWARD_ROOM);
        getInstance().setForcedReturn(LOBBY);
        getInstance().setTimeout(REWARD_ROOM_SECONDS);
        getInstance().chatMessage(ChatType.Mob, draw ? String.format("Final score %d to %d: a draw!", redRounds, blueRounds)
                : String.format("Final score %d to %d: the %s team wins!", Math.max(redRounds, blueRounds),
                Math.min(redRounds, blueRounds), redWins ? "Red" : "Blue"));
    }

    /** Manager Mush in the reward room: the reward, once. @return the line he says */
    public String claimReward(Char chr) {
        if (!finished) {
            return "The game isn't over yet!";
        }
        String key = CLAIMED + chr.getId();
        synchronized (this) {
            if (getInstance().hasProperty(key)) {
                return "You already have your reward. Thanks for playing!";
            }
            getInstance().addProperty(key, true);
        }
        boolean won = Boolean.TRUE.equals(getInstance().getProperty(WON + chr.getId()));
        int level = Math.min(chr.getLevel(), GameConstants.charExp.length - 1);
        long exp = (long) (GameConstants.charExp[level] * (won ? GameConstants.PIGGY_BAR_WIN_EXP_RATE : GameConstants.PIGGY_BAR_LOSE_EXP_RATE));
        if (exp > 0) {
            chr.addExp(exp);
        }
        return won ? "Sweet, sweet victory! Here's your reward." : "Good effort! Here's a little something for playing.";
    }

    /** For Manager Mush during a round. */
    public synchronized String status(Char chr) {
        if (finished) {
            return "The game is over. See you in the reward room!";
        }
        if (round < 0) {
            return "The first round is about to start!";
        }
        return String.format("Round %d (%s). Red %d - Blue %d. Rounds won: Red %d, Blue %d. You are on the %s team.",
                round + 1, ROUND_NAMES[round], redScore, blueScore, redRounds, blueRounds, isRed(chr) ? "Red" : "Blue");
    }

    @Override
    public void clearInstance(boolean clearInstance) {
        synchronized (this) {
            running = false;
            finished = true;
        }
        super.clearInstance(clearInstance);
    }
}
