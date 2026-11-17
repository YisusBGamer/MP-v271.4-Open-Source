package net.swordie.ms.client.social.miniroom.gameroom.memory;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.social.miniroom.MiniRoomType;
import net.swordie.ms.client.social.miniroom.gameroom.GameKindType;
import net.swordie.ms.client.social.miniroom.gameroom.MiniGameRoom;
import net.swordie.ms.connection.packet.MiniRoomPool;
import net.swordie.ms.connection.packet.model.MiniRoomPacket;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Match Cards: turn up two cards a turn; a pair is kept and the same player goes again, otherwise the turn passes.
 * The one with more pairs once the board is empty wins.
 */
public class MemoryGameRoom extends MiniGameRoom {
    // the client's timers: the start shows every card for a while, a miss waits for the cards to turn back
    private static final long TURN_TIME = 10000;
    private static final long MISS_TURN_TIME = 11600;
    private static final int[] CARD_COUNTS = {12, 20, 30};

    private final List<Integer> cards = new ArrayList<>();
    private final boolean[] taken = new boolean[CARD_COUNTS[CARD_COUNTS.length - 1]];
    private final int[] pairs = new int[2];
    private int firstCard = -1;

    public static int getCardCount(int gameKind) {
        return CARD_COUNTS[Math.max(0, Math.min(CARD_COUNTS.length - 1, gameKind))];
    }

    public List<Integer> getCards() {
        return cards;
    }

    @Override
    protected void onStart(int secondMover) {
        var count = getCardCount(getGameKind());
        cards.clear();
        for (var i = 0; i < count / 2; i++) {
            cards.add(i);
            cards.add(i);
        }
        Collections.shuffle(cards);
        Arrays.fill(taken, false);
        pairs[0] = pairs[1] = 0;
        firstCard = -1;
        broadcast(MiniRoomPacket.memoryStart(secondMover, cards));
    }

    @Override
    protected void onTimeOver() {
        firstCard = -1;
    }

    @Override
    protected void onFinish() {
        firstCard = -1;
    }

    private boolean isOpen(int card) {
        return card >= 0 && card < cards.size() && !taken[card];
    }

    public synchronized void turnUpCard(Char chr, boolean first, int card) {
        if (!isStarted() || chr != getTurnChar() || !isOpen(card)) {
            return;
        }
        var pos = getTurn();
        var other = getOther(chr);
        if (first) {
            if (firstCard >= 0) {
                return;
            }
            firstCard = card;
            // the flipper's client already shows it
            other.write(MiniRoomPool.miniRoomBaseDlg(MiniRoomPacket.memoryTurnUpFirst(card)));
            return;
        }
        if (firstCard < 0 || card == firstCard) {
            return;
        }
        var prev = firstCard;
        firstCard = -1;
        var match = cards.get(prev).equals(cards.get(card));
        // the result byte: the flipper's position, plus 2 for a pair
        broadcast(MiniRoomPacket.memoryTurnUpSecond(card, prev, match ? 2 + pos : pos));
        if (!match) {
            nextTurn(MISS_TURN_TIME);
            return;
        }
        taken[prev] = taken[card] = true;
        pairs[pos]++;
        if (pairs[0] + pairs[1] < cards.size() / 2) {
            setTurn(pos, TURN_TIME);
            return;
        }
        if (pairs[0] == pairs[1]) {
            finishGame(null);
        } else {
            finishGame(getChrs().get(pairs[0] > pairs[1] ? 0 : 1));
        }
    }

    @Override
    protected long getFirstTurnTime() {
        // every card is shown first, 200 ms each on top of the client's base time
        return getCardCount(getGameKind()) * 200L + 11500;
    }

    @Override
    protected long getTurnTime() {
        return TURN_TIME;
    }

    @Override
    public MiniRoomType getMiniRoomType() {
        return MiniRoomType.MemoryGame;
    }

    @Override
    public GameKindType getMiniGameKind() {
        return GameKindType.Memory;
    }
}
