package net.swordie.ms.connection.packet.model;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.client.social.miniroom.MiniRoom;
import net.swordie.ms.client.social.miniroom.gameroom.MiniGameRoom;
import net.swordie.ms.connection.Encodable;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.enums.MiniRoomAction;
import net.swordie.ms.enums.RoomLeaveType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Created on 03/02/2022.
 *
 * @author Asura
 */
public class MiniRoomPacket implements Encodable {

    private static final Logger log = LogManager.getLogger();

    private MiniRoomAction miniRoomAction;
    private MiniRoom miniRoom;
    private RoomLeaveType leaveType;
    private Char toChr;
    private Char fromChr;
    private int chrPos;
    private int position;
    private Item item;
    private long money;
    private int reasonValue;
    private int chatValue;
    private String msg;
    private int xPos;
    private int yPos;
    private int playerColourValue;
    private boolean accepted;
    private boolean isDraw;
    private int movesBack;
    private int resultValue;
    private boolean firstCard;
    private List<Integer> cards;

    public MiniRoomPacket(MiniRoomAction miniRoomAction) {
        this.miniRoomAction = miniRoomAction;
    }

    @Override
    public void encode(OutPacket outPacket) {
        outPacket.encodeInt(miniRoomAction.getVal());

        switch (miniRoomAction) {
            // Common
            case EnterMiniRoom:
                outPacket.encodeByte(resultValue); // 0 = entered, else the reason it failed
                if (resultValue != 0) {
                    outPacket.encodeByte(0);
                    break;
                }
                outPacket.encodeByte(miniRoom.getMiniRoomType().getVal());
                outPacket.encodeByte(miniRoom.getMaxChars());
                outPacket.encodeByte(miniRoom.getMyPosition(toChr)); // myPosition

                for (var chr : miniRoom.getChrs().values()) {
                    outPacket.encodeByte(miniRoom.getMyPositionByChar(toChr, chr)); // myPosition
                    chr.getAvatarData().getAvatarLook().encode(outPacket);
                    outPacket.encodeString(chr.getName());
                    outPacket.encodeShort(chr.getJob());
                    outPacket.encodeInt(0); // wing item
                }
                outPacket.encodeByte(-1); // end indicator
                outPacket.encodeByte(0); // v271 (0x143b5f00a): a flag after the user list



                // Additional encodes for different miniroom types
                if (miniRoom instanceof MiniGameRoom) {
                    var game = (MiniGameRoom) miniRoom;
                    for (var chr : miniRoom.getChrs().values()) {
                        outPacket.encodeByte(miniRoom.getMyPositionByChar(toChr, chr));
                        chr.getMiniGameRecordByType(miniRoom.getMiniRoomType().getVal()).encode(outPacket);
                    }
                    outPacket.encodeByte(-1); // end indicator
                    outPacket.encodeString(game.getRoomName());
                    outPacket.encodeByte(game.getGameKind());
                    outPacket.encodeByte(false); // game on: nobody enters a started game
                }
                break;
            case Chat:
                outPacket.encodeByte(chatValue);
                if (chatValue == MiniRoomAction.ChatSystem.getVal()) {
                    outPacket.encodeByte(reasonValue);
                    outPacket.encodeString(fromChr.getName());
                } else if (chatValue == MiniRoomAction.ChatUser.getVal()) {
                    outPacket.encodeByte(position);
                    outPacket.encodeString(fromChr.getName());
                    outPacket.encodeString(msg);
                    outPacket.encodeInt(fromChr.getId()); // the client hides lines from blocked senders
                    outPacket.encodeInt(0);
                    fromChr.encodeChatInfo(outPacket, msg);
                }
                break;
            case InviteRequest:
                outPacket.encodeByte(miniRoom.getMiniRoomType().getVal());
                outPacket.encodeInt(fromChr.getId());
                outPacket.encodeString(fromChr.getName());
                outPacket.encodeInt(fromChr.getJob());
                outPacket.encodeInt(fromChr.getId()); // invite id: the client's accept/decline sends it back
                break;
            case Decline:
                outPacket.encodeByte(reasonValue);
                outPacket.encodeByte(0);
                outPacket.encodeString(fromChr == null ? "" : fromChr.getName());
                break;
            case Exit:
                outPacket.encodeByte(position);
                outPacket.encodeByte(leaveType.getVal());
                break;
            case Accept:
                outPacket.encodeByte(miniRoom.getOtherPositionByChar(fromChr)); // position
                fromChr.getAvatarData().getAvatarLook().encode(outPacket);
                outPacket.encodeString(fromChr.getName());
                outPacket.encodeShort(fromChr.getJob());
                outPacket.encodeInt(0); // wing item

                if (miniRoom instanceof MiniGameRoom) {
                    fromChr.getMiniGameRecordByType(miniRoom.getMiniRoomType().getVal()).encode(outPacket);
                }
                break;


                // mini games
            case GameTieReq:
            case GameTieRes:
            case OmokRetreatReq:
            case GameUserReady:
            case GameUserCancelReady:
                break;
            case OmokRetreatRes:
                outPacket.encodeByte(accepted);
                if (accepted) {
                    outPacket.encodeByte(movesBack);
                    outPacket.encodeByte(position); // whose turn it is now
                }
                break;
            case GameUserStart:
                outPacket.encodeByte(position); // the position that moves second
                if (cards != null) {
                    outPacket.encodeByte(cards.size());
                    for (var card : cards) {
                        outPacket.encodeInt(card);
                    }
                }
                break;
            case GameResult:
                outPacket.encodeByte(isDraw ? 1 : 0);
                if (!isDraw) {
                    outPacket.encodeByte(position);
                }
                for (var i = 0; i < 2; i++) {
                    var chr = miniRoom.getChrs().get(i);
                    chr.getMiniGameRecordByType(miniRoom.getMiniRoomType().getVal()).encode(outPacket);
                }
                break;
            case GameTimeOver:
                outPacket.encodeByte(position);
                break;
            case OmokPutStoneChecker:
                outPacket.encodeInt(xPos);
                outPacket.encodeInt(yPos);
                outPacket.encodeByte(playerColourValue);
                break;
            case OmokPutStoneCheckerErr:
                outPacket.encodeByte(reasonValue);
                break;
            case MemoryTurnUpCard:
                outPacket.encodeByte(firstCard);
                outPacket.encodeByte(position);
                if (!firstCard) {
                    outPacket.encodeByte(xPos); // the turn's first card
                    outPacket.encodeByte(resultValue);
                }
                break;


                // TradingRoom
            case PlaceItem:
                outPacket.encodeByte(chrPos);
                outPacket.encodeByte(position);
                item.encode(outPacket);
                break;
            case SetMesos:
                outPacket.encodeByte(chrPos);
                outPacket.encodeLong(money);
                break;
            case ConfirmTrade:
                // empty
                break;
            case TradeState:
                outPacket.encodeByte(resultValue);
                break;
            case TradeStateCheck:
                outPacket.encodeByte(resultValue);
                outPacket.encodeByte(0);
                break;
        }
    }

    // Common
    public static MiniRoomPacket enterMiniRoom(Char toChr, MiniRoom miniRoom) {
        var packet = new MiniRoomPacket(MiniRoomAction.EnterMiniRoom);
        packet.miniRoom = miniRoom;
        packet.toChr = toChr;
        return packet;
    }

    public static MiniRoomPacket inviteRequest(Char fromChr, MiniRoom miniRoom) {
        var packet = new MiniRoomPacket(MiniRoomAction.InviteRequest);
        packet.fromChr = fromChr;
        packet.miniRoom = miniRoom;
        return packet;
    }

    public static MiniRoomPacket exit(int position, RoomLeaveType leaveType) {
        var packet = new MiniRoomPacket(MiniRoomAction.Exit);
        packet.position = position;
        packet.leaveType = leaveType;
        return packet;
    }

    /** An invite result: 1 not found, 2 busy, 3 refused, 4 not accepting invites ("%s" = the name) */
    public static MiniRoomPacket inviteResult(int reason, Char invitee) {
        var packet = new MiniRoomPacket(MiniRoomAction.Decline);
        packet.reasonValue = reason;
        packet.fromChr = invitee;
        return packet;
    }

    public static MiniRoomPacket chat(int chatValue, Char fromChr, int position, int reasonValue, String msg) {
        var packet = new MiniRoomPacket(MiniRoomAction.Chat);
        packet.chatValue = chatValue; // MiniRoomAction.ChatSystem or ChatUser
        packet.fromChr = fromChr;
        packet.position = position;
        packet.reasonValue = reasonValue;
        packet.msg = msg;
        return packet;
    }

    // Trading Room
    public static MiniRoomPacket accept(Char chr, MiniRoom miniRoom) {
        var packet = new MiniRoomPacket(MiniRoomAction.Accept);
        packet.fromChr = chr;
        packet.miniRoom = miniRoom;
        return packet;
    }

    public static MiniRoomPacket confirmTrade() {
        var packet = new MiniRoomPacket(MiniRoomAction.ConfirmTrade);
        return packet;
    }

    /** v271: 1 my side confirmed, 2 the other side confirmed, 3 both, 4 neither */
    public static MiniRoomPacket tradeState(int state) {
        var packet = new MiniRoomPacket(MiniRoomAction.TradeState);
        packet.resultValue = state;
        return packet;
    }

    /** v271: 0 = the client answers TradeStateCheck 0; else it asks its yes/no and sends TradeState 0 */
    public static MiniRoomPacket tradeStateCheck(int value) {
        var packet = new MiniRoomPacket(MiniRoomAction.TradeStateCheck);
        packet.resultValue = value;
        return packet;
    }

    public static MiniRoomPacket placeItem(int chrPos, int position, Item item) {
        var packet = new MiniRoomPacket(MiniRoomAction.PlaceItem);
        packet.chrPos = chrPos;
        packet.position = position;
        packet.item = item;
        return packet;
    }

    public static MiniRoomPacket setMoney(int chrPos, long money) {
        var packet = new MiniRoomPacket(MiniRoomAction.SetMesos);
        packet.chrPos = chrPos;
        packet.money = money;
        return packet;
    }

    public static MiniRoomPacket enterFailed(int reason) {
        var packet = new MiniRoomPacket(MiniRoomAction.EnterMiniRoom);
        packet.resultValue = reason;
        return packet;
    }

    // mini games
    /** An action without a body: tie request/refusal, retreat request, ready, cancel ready. */
    public static MiniRoomPacket gameAction(MiniRoomAction action) {
        return new MiniRoomPacket(action);
    }

    public static MiniRoomPacket retreatResult(boolean accepted, int movesBack, int position) {
        var packet = new MiniRoomPacket(MiniRoomAction.OmokRetreatRes);
        packet.accepted = accepted;
        packet.movesBack = movesBack;
        packet.position = position;
        return packet;
    }

    public static MiniRoomPacket gameStart(int secondMover) {
        var packet = new MiniRoomPacket(MiniRoomAction.GameUserStart);
        packet.position = secondMover;
        return packet;
    }

    public static MiniRoomPacket memoryStart(int secondMover, List<Integer> cards) {
        var packet = gameStart(secondMover);
        packet.cards = cards;
        return packet;
    }

    public static MiniRoomPacket gameResult(MiniRoom miniRoom, boolean isDraw, int winnerPosition) {
        var packet = new MiniRoomPacket(MiniRoomAction.GameResult);
        packet.miniRoom = miniRoom;
        packet.isDraw = isDraw;
        packet.position = winnerPosition;
        return packet;
    }

    public static MiniRoomPacket gameTimeOver(int turnPosition) {
        var packet = new MiniRoomPacket(MiniRoomAction.GameTimeOver);
        packet.position = turnPosition;
        return packet;
    }

    public static MiniRoomPacket putStoneChecker(int xPos, int yPos, int playerColourValue) {
        var packet = new MiniRoomPacket(MiniRoomAction.OmokPutStoneChecker);
        packet.xPos = xPos;
        packet.yPos = yPos;
        packet.playerColourValue = playerColourValue;
        return packet;
    }

    public static MiniRoomPacket putStoneCheckerErr(int reasonValue) {
        var packet = new MiniRoomPacket(MiniRoomAction.OmokPutStoneCheckerErr);
        packet.reasonValue = reasonValue;
        return packet;
    }

    public static MiniRoomPacket memoryTurnUpFirst(int card) {
        var packet = new MiniRoomPacket(MiniRoomAction.MemoryTurnUpCard);
        packet.firstCard = true;
        packet.position = card;
        return packet;
    }

    /** @param result the flipper's position, plus 2 when the cards are a pair */
    public static MiniRoomPacket memoryTurnUpSecond(int card, int firstCard, int result) {
        var packet = new MiniRoomPacket(MiniRoomAction.MemoryTurnUpCard);
        packet.position = card;
        packet.xPos = firstCard;
        packet.resultValue = result;
        return packet;
    }
}
