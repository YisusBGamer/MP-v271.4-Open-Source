package net.swordie.ms.enums;

import java.util.Arrays;

/**
 * Created by Asura on 10-8-2018.
 */
public enum MiniRoomAction {
    PlaceItem(0),
    PlaceItem_2(1),
    PlaceItem_3(2),
    PlaceItem_4(3),

    SetMesos(4),
    SetMesos_2(5),
    SetMesos_3(6),
    SetMesos_4(7),
    ConfirmTrade(8),
    TradeConfirm(9),
    TradeConfirm2(10),
    TradeConfirm3(11), // 3...?
    TradeConfirmRemoteResponse(14), // the client's answer to ConfirmTrade: byte n, n x (int item, int)

    TradeRestraintItem(15),

    // v271 trade: in: byte (1 = the client already warned at put time, so no yes/no first); out: byte state
    // 1 my side confirmed, 2 the other side confirmed, 3 both, 4 neither
    TradeState(16),
    // v271 trade: out: byte, byte (0 = the client answers with this type and 0; else it asks yes/no and sends
    // TradeState 0 again); in: byte
    TradeStateCheck(17),

    // v271: every type from 16 on is two higher than v265 (the two trade types above were inserted), both ways
    CreateMiniRoom(18),

    Accept(21), // in: int invite id, byte, byte; out: a visitor's avatar
    EnterMiniRoom(23),
    InviteRequest(24),
    Decline(25), // in: int invite id, byte reason; out: the invite result
    ChatSystem(26), // Chat's sub type: byte, str
    Chat(27),
    ChatUser(28), // Chat's sub type: a user's line

    Avatar(30),
    Exit(31), // in: byte 0

    CheckSSN2(34),
    // personal and entrusted shops: the client has no dialog for them (mini room types 5 and 6)

    Balloon(29), // in: byte 1 (the owner's dialog opened)

    // mini games (Omok, Match Cards): the same numbers both ways
    GameTieReq(66),
    GameTieRes(67), // in: byte accepted; out: refused
    GameGiveUpReq(68),

    OmokRetreatReq(70),
    OmokRetreatRes(71), // in: byte accepted; out: byte accepted [byte count, byte turn position]
    GameExitAfterGame(72),
    GameCancelExitAfterGame(73),
    GameUserReady(74),
    GameUserCancelReady(75),
    GameUserBoot(76),
    GameUserStart(77), // out: byte position that moves second [Match Cards: byte count, int x count]
    GameResult(78), // byte 1 = tie, else byte winner position; then both records
    GameTimeOver(79), // out: byte position whose turn it is
    OmokPutStoneChecker(80), // int x, int y, byte colour
    OmokPutStoneCheckerErr(81),

    MemoryTurnUpCard(84), // in: byte first, byte card; out: first: byte 1, byte card; second: byte 0, byte card, byte first card, byte result
    ;

    private byte val;

    MiniRoomAction(int val) {this.val = (byte) val;}
    public byte getVal() {return val;}

    public static MiniRoomAction getByVal(byte val) {
        return Arrays.stream(values()).filter(mrt -> mrt.getVal() == val).findAny().orElse(null);
    }

    public boolean isCreateMiniRoomAction() {
        return CreateMiniRoom.equals(this);
    }

    public boolean isCommonAction() {
        return isCreateMiniRoomAction()
                || Accept.equals(this)
                || EnterMiniRoom.equals(this)
                || InviteRequest.equals(this)
                || Decline.equals(this)
                || Chat.equals(this)
                || Avatar.equals(this)
                || Exit.equals(this);
    }

    public boolean isMiniGameAction() {
        return getVal() >= GameTieReq.getVal() && getVal() <= MemoryTurnUpCard.getVal();
    }

    public boolean isTradingRoomAction() {
        return getVal() >= PlaceItem.getVal() && getVal() <= TradeStateCheck.getVal();
    }
}
