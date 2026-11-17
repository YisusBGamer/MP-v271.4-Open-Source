package net.swordie.ms.client.social.miniroom.gameroom.omok;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.social.miniroom.MiniRoomType;
import net.swordie.ms.client.social.miniroom.gameroom.GameKindType;
import net.swordie.ms.client.social.miniroom.gameroom.MiniGameRoom;
import net.swordie.ms.connection.packet.MiniRoomPool;
import net.swordie.ms.connection.packet.model.MiniRoomPacket;
import net.swordie.ms.enums.MiniRoomAction;

/**
 * Created on 04/02/2022.
 *
 * @author Asura
 */
public class OmokGameRoom extends MiniGameRoom {
    private static final long TURN_TIME = 30000;

    private OmokGame omokGame = new OmokGame();

    @Override
    protected void onStart(int secondMover) {
        omokGame = new OmokGame();
        omokGame.setTurn(getTurnChar());
        broadcast(MiniRoomPacket.gameStart(secondMover));
    }

    @Override
    protected void onTimeOver() {
        omokGame.setRedoRequest(null);
        omokGame.setTurn(getOther(getTurnChar()));
    }

    public synchronized void putStone(Char moveMaker, int xPos, int yPos, int playerColourValue) {
        if (!isStarted() || !isPlayer(moveMaker) || omokGame.getRedoRequest() != null) {
            return;
        }
        if (!omokGame.putStone(moveMaker, xPos, yPos, playerColourValue)) {
            var onBoard = xPos >= 0 && yPos >= 0 && xPos < omokGame.getBoard().length && yPos < omokGame.getBoard().length;
            var doubleThree = onBoard && moveMaker == omokGame.getTurn() && omokGame.getBoard()[xPos][yPos] == 0
                    && omokGame.violatesThreeAndThreeRule(xPos, yPos, playerColourValue);
            // 81: "You have double-3's", anything else: "You can't put it there"
            moveMaker.write(MiniRoomPool.miniRoomBaseDlg(MiniRoomPacket.putStoneCheckerErr(doubleThree ? 81 : 0)));
            return;
        }
        broadcast(MiniRoomPacket.putStoneChecker(xPos, yPos, playerColourValue));
        if (omokGame.won(xPos, yPos, playerColourValue)) {
            finishGame(moveMaker);
        } else if (omokGame.isTie()) {
            finishGame(null);
        } else {
            nextTurn(TURN_TIME);
            omokGame.setTurn(getTurnChar());
        }
    }

    public synchronized void requestRedo(Char requestor) {
        if (!isStarted() || !isPlayer(requestor) || omokGame.getRedoRequest() != null
                || omokGame.getLog().stream().noneMatch(s -> s.getPlacer() == requestor)) {
            return;
        }
        omokGame.setRedoRequest(requestor);
        getOther(requestor).write(MiniRoomPool.miniRoomBaseDlg(MiniRoomPacket.gameAction(MiniRoomAction.OmokRetreatReq)));
    }

    public synchronized void redoRequestResponse(Char chr, boolean isAccepted) {
        var requestor = omokGame.getRedoRequest();
        if (!isStarted() || requestor == null || requestor != getOther(chr)) {
            return;
        }
        omokGame.setRedoRequest(null);
        var moveBack = 0;
        if (isAccepted) {
            moveBack = omokGame.redo(requestor);
            setTurn(getPositionByChar(requestor), TURN_TIME);
        }
        broadcast(MiniRoomPacket.retreatResult(isAccepted, moveBack, getPositionByChar(requestor)));
    }

    @Override
    protected long getFirstTurnTime() {
        return TURN_TIME;
    }

    @Override
    protected long getTurnTime() {
        return TURN_TIME;
    }

    @Override
    public MiniRoomType getMiniRoomType() {
        return MiniRoomType.OmokGame;
    }

    @Override
    public GameKindType getMiniGameKind() {
        return GameKindType.Omok;
    }
}
