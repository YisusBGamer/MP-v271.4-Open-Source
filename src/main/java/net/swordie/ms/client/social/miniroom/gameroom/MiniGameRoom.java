package net.swordie.ms.client.social.miniroom.gameroom;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.social.miniroom.MiniRoom;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.connection.packet.MiniRoomPool;
import net.swordie.ms.connection.packet.UserPacket;
import net.swordie.ms.connection.packet.model.MiniRoomPacket;
import net.swordie.ms.enums.MiniRoomAction;
import net.swordie.ms.enums.RoomLeaveType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * A two-player game room (Omok, Match Cards): the owner sits at position 0, the visitor at 1.
 * State changes go through the room's lock: both players' packets are handled at the same time.
 *
 * Created on 04/02/2022.
 *
 * @author Asura
 */
public abstract class MiniGameRoom extends MiniRoom {

    private static final int MAX_CHARS = 2;
    // a client's turn timer starts when the packet arrives, so it runs out a little after the server's
    private static final long TIME_OVER_GRACE = 1500;

    private String roomName;
    private String roomPassword;
    private int gameKind;
    private boolean visitorReady;
    private boolean started;
    private int firstMover; // position that moves first next game: the last game's loser
    private int turn;
    private long turnDeadline;
    private Char tieRequester;
    private final Set<Char> exitAfterGame = new HashSet<>();

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomPassword() {
        return roomPassword;
    }

    public void setRoomPassword(String roomPassword) {
        this.roomPassword = roomPassword;
    }

    public boolean isPrivate() {
        return getRoomPassword() != null;
    }

    /** Omok: the stone set (item id % 100); Match Cards: the board (0 = 12, 1 = 20, 2 = 30 cards). */
    public int getGameKind() {
        return gameKind;
    }

    public void setGameKind(int gameKind) {
        this.gameKind = gameKind;
    }

    public synchronized boolean isStarted() {
        return started;
    }

    protected int getTurn() {
        return turn;
    }

    protected Char getTurnChar() {
        return getChrs().get(turn);
    }

    protected void setTurn(int position, long timeMillis) {
        this.turn = position;
        this.turnDeadline = System.currentTimeMillis() + timeMillis;
    }

    protected void nextTurn(long timeMillis) {
        setTurn(1 - turn, timeMillis);
    }

    public Char getVisitor() {
        return getChrs().getOrDefault(1, null);
    }

    @Override
    public Char getRoomOwner() {
        return getChrs().getOrDefault(0, null);
    }

    public Char getOther(Char chr) {
        return chr == getRoomOwner() ? getVisitor() : getRoomOwner();
    }

    protected boolean isPlayer(Char chr) {
        return chr != null && getPositionByChar(chr) >= 0;
    }

    protected void broadcast(MiniRoomPacket packet) {
        for (var chr : new ArrayList<>(getChrs().values())) {
            chr.write(MiniRoomPool.miniRoomBaseDlg(packet));
        }
    }

    protected void updateBalloon() {
        var owner = getRoomOwner();
        if (owner != null && owner.getField() != null) {
            owner.getField().broadcastPacket(UserPacket.miniRoomBalloon(owner, this));
        }
    }

    /** The sign others see above the owner (MINI_ROOM_BALLOON and the remote user's enter-field). */
    public void encodeBalloon(OutPacket outPacket) {
        var owner = getRoomOwner();
        outPacket.encodeByte(getMiniRoomType().getVal());
        outPacket.encodeInt(owner.getId()); // room SN: the client sends it back to enter
        outPacket.encodeString(getRoomName());
        outPacket.encodeByte(isPrivate());
        outPacket.encodeByte(getGameKind());
        outPacket.encodeByte(getChrs().size());
        outPacket.encodeByte(getMaxChars());
        outPacket.encodeByte(isStarted());
        owner.encodeChatInfo(outPacket, getRoomName());
    }

    /** @return the client's enter-result code when chr can't come in, 0 when it can */
    public synchronized int canEnter(Char chr, String password) {
        if (getRoomOwner() == null) {
            return 1; // already closed
        }
        if (getChrs().size() >= getMaxChars()) {
            return 2; // full
        }
        if (started) {
            return 16; // a game is in progress
        }
        if (getRoomPassword() != null && !getRoomPassword().equals(password)) {
            return 18; // wrong password
        }
        return 0;
    }

    @Override
    public synchronized void charEnter(Char chr) {
        super.charEnter(chr);
        visitorReady = false;
        updateBalloon();
    }

    @Override
    public synchronized void charLeave(Char chr, RoomLeaveType leaveType) {
        if (!isPlayer(chr)) {
            chr.setMiniRoom(null);
            return;
        }
        if (started) {
            // leaving mid-game is a loss
            finishGame(getOther(chr));
        }
        if (chr == getRoomOwner()) {
            cancelMiniRoom(chr, leaveType);
            return;
        }
        var type = leaveType == RoomLeaveType.MRLeave_Kicked || leaveType == RoomLeaveType.MRLeave_Booked
                ? leaveType : RoomLeaveType.MRLeave_UserRequest;
        super.charLeave(chr, type);
        visitorReady = false;
        exitAfterGame.remove(chr);
        tieRequester = null;
        updateBalloon();
    }

    @Override
    public synchronized void createMiniRoom(Char... chrs) {
        super.createMiniRoom(chrs);
        updateBalloon();
    }

    @Override
    public synchronized void cancelMiniRoom(Char reasonChr, RoomLeaveType reasonType) {
        var owner = getRoomOwner();
        if (owner != null && owner.getField() != null) {
            owner.getField().broadcastPacket(UserPacket.miniRoomBalloon(owner, null));
        }
        for (var entry : getChrs().entrySet()) {
            var chr = entry.getValue();
            var leaveReason = chr == owner ? RoomLeaveType.MRLeave_UserRequest : RoomLeaveType.MRLeave_HostOut;
            if (chr == owner && reasonType == RoomLeaveType.MRLeave_Booked) {
                leaveReason = RoomLeaveType.MRLeave_Booked;
            }
            chr.write(MiniRoomPool.miniRoomBaseDlg(MiniRoomPacket.exit(entry.getKey(), leaveReason)));
        }
        started = false;
        visitorReady = false;
        tieRequester = null;
        exitAfterGame.clear();
        super.cancelMiniRoom(reasonChr, reasonType);
    }

    @Override
    public synchronized void chat(Char chr, String msg) {
        var senderPos = getPositionByChar(chr);
        if (senderPos < 0) {
            return;
        }
        broadcast(MiniRoomPacket.chat(MiniRoomAction.ChatUser.getVal(), chr, senderPos, 0, msg));
    }

    public synchronized void chrReady(Char chr) {
        if (chr != getVisitor() || started) {
            return;
        }
        visitorReady = true;
        broadcast(MiniRoomPacket.gameAction(MiniRoomAction.GameUserReady));
    }

    public synchronized void chrCancelReady(Char chr) {
        if (chr != getVisitor() || started) {
            return;
        }
        visitorReady = false;
        broadcast(MiniRoomPacket.gameAction(MiniRoomAction.GameUserCancelReady));
    }

    public synchronized void kickVisitor(Char chr) {
        var visitor = getVisitor();
        if (chr != getRoomOwner() || started || visitor == null) {
            return;
        }
        charKicked(visitor);
    }

    public synchronized void startGame(Char chr) {
        if (chr != getRoomOwner() || started || getChrs().size() < MAX_CHARS || !visitorReady) {
            return;
        }
        started = true;
        tieRequester = null;
        setTurn(firstMover, getFirstTurnTime());
        onStart(1 - firstMover);
        updateBalloon();
    }

    public synchronized void giveUp(Char chr) {
        if (!started || !isPlayer(chr)) {
            return;
        }
        finishGame(getOther(chr));
    }

    public synchronized void requestTie(Char chr) {
        if (!started || !isPlayer(chr) || tieRequester != null) {
            return;
        }
        tieRequester = chr;
        getOther(chr).write(MiniRoomPool.miniRoomBaseDlg(MiniRoomPacket.gameAction(MiniRoomAction.GameTieReq)));
    }

    public synchronized void tieResponse(Char chr, boolean accepted) {
        var requester = tieRequester;
        if (!started || requester == null || requester != getOther(chr)) {
            return;
        }
        tieRequester = null;
        if (accepted) {
            finishGame(null);
        } else {
            requester.write(MiniRoomPool.miniRoomBaseDlg(MiniRoomPacket.gameAction(MiniRoomAction.GameTieRes)));
        }
    }

    /** Both clients send it when the turn timer runs out: the first one past the server's deadline counts. */
    public synchronized void timeOver(Char chr) {
        if (!started || !isPlayer(chr) || System.currentTimeMillis() < turnDeadline - TIME_OVER_GRACE) {
            return;
        }
        onTimeOver();
        nextTurn(getTurnTime());
        broadcast(MiniRoomPacket.gameTimeOver(getTurn()));
    }

    public synchronized void setExitAfterGame(Char chr, boolean exit) {
        if (!isPlayer(chr)) {
            return;
        }
        if (exit) {
            exitAfterGame.add(chr);
        } else {
            exitAfterGame.remove(chr);
        }
    }

    /**
     * Ends the game, records the result and sends it.
     *
     * @param winner the winner, null for a tie
     */
    protected void finishGame(Char winner) {
        if (!started) {
            return;
        }
        started = false;
        visitorReady = false;
        tieRequester = null;
        var owner = getRoomOwner();
        var visitor = getVisitor();
        if (owner == null || visitor == null) {
            return;
        }
        var type = getMiniRoomType().getVal();
        if (winner == null) {
            owner.getMiniGameRecordByType(type).incTieCount();
            visitor.getMiniGameRecordByType(type).incTieCount();
        } else {
            var loser = getOther(winner);
            winner.getMiniGameRecordByType(type).incWinCount();
            loser.getMiniGameRecordByType(type).incLossCount();
            firstMover = getPositionByChar(loser);
        }
        broadcast(MiniRoomPacket.gameResult(this, winner == null, winner == null ? 0 : getPositionByChar(winner)));
        onFinish();
        updateBalloon();
        if (!exitAfterGame.isEmpty()) {
            var leaving = new ArrayList<>(exitAfterGame);
            exitAfterGame.clear();
            // the visitor first: the owner leaving closes the room
            leaving.sort((a, b) -> Integer.compare(getPositionByChar(b), getPositionByChar(a)));
            for (var chr : leaving) {
                if (isPlayer(chr)) {
                    charLeave(chr, RoomLeaveType.MRLeave_Booked);
                }
            }
        }
    }

    /** Sends the game's start packet. */
    protected abstract void onStart(int secondMover);

    protected void onTimeOver() {
    }

    protected void onFinish() {
    }

    protected abstract long getFirstTurnTime();

    protected abstract long getTurnTime();

    @Override
    public int getMaxChars() {
        return MAX_CHARS;
    }

    @Override
    public int getMyPositionByChar(Char toChr, Char chr) {
        return getPositionByChar(chr);
    }

    @Override
    public int getOtherPositionByChar(Char chr) {
        return getPositionByChar(chr);
    }

    @Override
    public int getMyPosition(Char chr) {
        return getPositionByChar(chr);
    }

    public abstract GameKindType getMiniGameKind();

}
