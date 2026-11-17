package net.swordie.ms.client.social.miniroom;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.client.social.miniroom.gameroom.MiniGameRoom;
import net.swordie.ms.client.social.miniroom.gameroom.memory.MemoryGameRoom;
import net.swordie.ms.client.social.miniroom.gameroom.omok.OmokGameRoom;
import net.swordie.ms.client.social.miniroom.tradingroom.TradingRoom;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.packet.MiniRoomPool;
import net.swordie.ms.connection.packet.UserPacket;
import net.swordie.ms.connection.packet.model.MiniRoomPacket;
import net.swordie.ms.constants.ItemConstants;
import net.swordie.ms.enums.FieldOption;
import net.swordie.ms.enums.InvType;
import net.swordie.ms.enums.MiniRoomAction;
import net.swordie.ms.enums.RoomLeaveType;
import net.swordie.ms.world.AutoSave;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created on 04/02/2022.
 *
 * @author Asura
 */
public class MiniRoomModule {
    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger();
    private static final int OMOK_SET_FIRST = 4080000;
    private static final int OMOK_SET_LAST = 4080011;
    private static final int MATCH_CARDS = 4080100;

    public static void handleMiniGameAction(Char chr, MiniRoomAction mrt, InPacket inPacket) {
        var miniRoom = chr.getMiniRoom();
        if (!(miniRoom instanceof MiniGameRoom)) {
            return;
        }
        var game = (MiniGameRoom) miniRoom;

        switch (mrt) {
            case GameTieReq:
                game.requestTie(chr);
                break;
            case GameTieRes:
                game.tieResponse(chr, inPacket.decodeByte() != 0);
                break;
            case GameGiveUpReq:
                game.giveUp(chr);
                break;
            case GameExitAfterGame:
                game.setExitAfterGame(chr, true);
                break;
            case GameCancelExitAfterGame:
                game.setExitAfterGame(chr, false);
                break;
            case GameUserReady:
                game.chrReady(chr);
                break;
            case GameUserCancelReady:
                game.chrCancelReady(chr);
                break;
            case GameUserBoot:
                game.kickVisitor(chr);
                break;
            case GameUserStart:
                game.startGame(chr);
                break;
            case GameTimeOver:
                game.timeOver(chr);
                break;
            case OmokRetreatReq:
                if (game instanceof OmokGameRoom) {
                    ((OmokGameRoom) game).requestRedo(chr);
                }
                break;
            case OmokRetreatRes:
                if (game instanceof OmokGameRoom) {
                    ((OmokGameRoom) game).redoRequestResponse(chr, inPacket.decodeByte() != 0);
                }
                break;
            case OmokPutStoneChecker:
                if (game instanceof OmokGameRoom) {
                    var xPos = inPacket.decodeInt();
                    var yPos = inPacket.decodeInt();
                    var playerColourValue = inPacket.decodeByte();
                    ((OmokGameRoom) game).putStone(chr, xPos, yPos, playerColourValue);
                }
                break;
            case MemoryTurnUpCard:
                if (game instanceof MemoryGameRoom) {
                    var first = inPacket.decodeByte() != 0;
                    var card = inPacket.decodeByte() & 0xFF;
                    ((MemoryGameRoom) game).turnUpCard(chr, first, card);
                }
                break;
        }
    }

    /**
     * Reads a game room's create request (title, private, password, the game item's ETC slot, the kind) and checks
     * the item: an Omok set makes an Omok room with its stones, the Match Cards make a Match Cards room.
     *
     * @return whether the room may be opened
     */
    private static boolean setUpGameRoom(Char chr, MiniGameRoom room, InPacket inPacket) {
        var roomName = inPacket.decodeString();
        var isPrivate = inPacket.decodeByte() != 0;
        String password = isPrivate ? inPacket.decodeString() : null;
        short slot = inPacket.decodeShort();
        int kind = inPacket.decodeByte();

        if (chr.getField() == null || chr.getField().getInfo() == null
                || (chr.getField().getInfo().getFieldLimit() & FieldOption.MiniGameLimit.getVal()) != 0) {
            chr.write(MiniRoomPool.miniRoomBaseDlg(MiniRoomPacket.enterFailed(12))); // can't open one here
            return false;
        }
        var item = chr.getInventoryByType(InvType.ETC).getItemBySlot(slot);
        var itemId = item == null ? 0 : item.getItemId();
        if (room instanceof OmokGameRoom) {
            if (itemId < OMOK_SET_FIRST || itemId > OMOK_SET_LAST) {
                return false;
            }
            kind = itemId % 100;
        } else {
            if (itemId != MATCH_CARDS) {
                return false;
            }
            kind = Math.max(0, Math.min(2, kind));
        }
        if (roomName == null || roomName.isBlank()) {
            roomName = chr.getName();
        }
        if (roomName.length() > 50) {
            roomName = roomName.substring(0, 50);
        }
        room.setRoomName(roomName);
        room.setRoomPassword(password == null || password.isEmpty() ? null : password);
        room.setGameKind(kind);
        return true;
    }

    public static void handleTradingRoomAction(Char chr, MiniRoomAction mrt, InPacket inPacket) {
        var miniRoom = chr.getMiniRoom();
        if (!(miniRoom instanceof TradingRoom)) {
            chr.chatMessage("You are not currently trading.");
            return;
        }

        var tradingRoom = (TradingRoom) chr.getMiniRoom();

        switch (mrt) {
            // TradingRoom
            case PlaceItem:
            case PlaceItem_2:
            case PlaceItem_3:
            case PlaceItem_4:
                // v271's sender runs in protected code, so its layout isn't read. Known candidates, first one that
                // names a real item wins: v265 byte inv, short slot, short quantity, byte trade slot (6 bytes); the
                // same with an int quantity (8); either after a leading int (10 / 12); longer bodies: the 8-byte form
                // with something after it
                byte[] body = inPacket.decodeArr(inPacket.getUnreadAmount());
                int[] put = parsePut(chr, body);
                log.debug(String.format("[Trade] %s puts inv %d slot %d x%d at %d (%d bytes, form %d) | %s",
                        chr.getName(), put[0], put[1], put[2], put[3], body.length, put[4], hex(body)));
                if (put[4] < 0) {
                    return;
                }
                byte invType = (byte) put[0];
                short bagIndex = (short) put[1];
                int quantity = put[2];
                byte tradeSlot = (byte) put[3];

                InvType placeInvType = InvType.getInvTypeByVal(invType);
                if (tradingRoom.isLocked(chr) || placeInvType == null || placeInvType == InvType.EQUIPPED) {
                    // no changes once someone has confirmed; equipped items can't be traded without unequipping
                    return;
                }
                Item item = chr.getInventoryByType(placeInvType).getItemBySlot(bagIndex);
                if (item == null) {
                    return;
                }
                if (quantity <= 0 || item.getQuantity() < quantity) {
                    chr.getOffenseManager().addOffense(String.format("Character {%d} tried to trade an item {%d} with a higher quantity {%s} than the item has {%d}.", chr.getId(), item.getItemId(), quantity, item.getQuantity()));
                    return;
                }

                if (!item.isTradable()) {
                    chr.getOffenseManager().addOffense(String.format("Character {%d} tried to trade an item {%d} whilst it was trade blocked.", chr.getId(), item.getItemId()));
                    return;
                }
                if (ItemConstants.isEquip(item.getItemId()) && (quantity != 1 || item.getQuantity() != 1)) {
                    // an equip is never a stack: splitting one would copy it (stats, stars, potentials)
                    chr.getOffenseManager().addOffense(String.format("Character {%d} tried to trade equip {%d} with quantity {%d} (has {%d}).", chr.getId(), item.getItemId(), quantity, item.getQuantity()));
                    return;
                }

                quantity = offerQuantity(item, quantity);
                Item offer = item.deepCopy();
                offer.setQuantity(quantity);

                if (tradingRoom.canAddItem(chr, tradeSlot, offer)) {
                    takeFromInventory(chr, item, quantity);
                    tradingRoom.addItem(chr, tradeSlot, offer);
                }
                break;
            case SetMesos:
            case SetMesos_2:
            case SetMesos_3:
            case SetMesos_4:
                // protected sender (see put item): 8 = long (v265), 4 = int, 12 = leading int + long
                byte[] mb = inPacket.decodeArr(inPacket.getUnreadAmount());
                long money = mb.length >= 12 ? le(mb, 4, 8) : mb.length >= 8 ? le(mb, 0, 8) : mb.length >= 4 ? le(mb, 0, 4) : -1;
                if (mb.length >= 12 && (money < 0 || money > chr.getMoney()) && le(mb, 0, 8) >= 0 && le(mb, 0, 8) <= chr.getMoney()) {
                    money = le(mb, 0, 8);
                }
                log.debug(String.format("[Trade] %s mesos %d (%d bytes) | %s", chr.getName(), money, mb.length, hex(mb)));
                if (mb.length < 4) {
                    return;
                }

                if (money > chr.getMoney() || money < 0) {
                    chr.getOffenseManager().addOffense(String.format("Character %d tried to add an invalid amount of mesos (%d, own money: %d)",
                            chr.getId(), money, chr.getMoney()));
                    return;
                }
                if (tradingRoom.isLocked(chr)) {
                    return;
                }
                // takes the mesos only if they fit in the offer (they used to be taken first, and lost when the offer
                // would go over the cap)
                tradingRoom.addMoney(chr, money);
                break;
            case TradeState:
                // v271's Trade button (after its yes/no): the old confirm, answered with the confirm states
                if (inPacket.getUnreadAmount() > 0) {
                    inPacket.decodeByte();
                }
            case ConfirmTrade:
                if (mrt == MiniRoomAction.ConfirmTrade) {
                    byte[] cb = inPacket.decodeArr(inPacket.getUnreadAmount());
                    log.debug(String.format("[Trade] %s confirm (%d bytes) | %s", chr.getName(), cb.length, hex(cb)));
                }
            case TradeConfirm:
            case TradeConfirm2:
            case TradeConfirm3:
                var other = tradingRoom.getOther(chr);
                if (other == null) {
                    if (mrt == MiniRoomAction.TradeState) {
                        chr.write(MiniRoomPool.miniRoomBaseDlg(MiniRoomPacket.tradeState(1))); // nobody to trade with
                    }
                    break;
                }
                if (mrt == MiniRoomAction.TradeState) {
                    boolean both = tradingRoom.hasConfirmed(other);
                    chr.write(MiniRoomPool.miniRoomBaseDlg(MiniRoomPacket.tradeState(both ? 3 : 1)));
                    other.write(MiniRoomPool.miniRoomBaseDlg(MiniRoomPacket.tradeState(both ? 3 : 2)));
                } else {
                    other.write(MiniRoomPool.miniRoomBaseDlg(MiniRoomPacket.confirmTrade()));
                }
                // one step under the trading room's lock: both traders confirming at the same time can't both
                // (or neither) complete the trade
                boolean traded = false;
                synchronized (tradingRoom) {
                    if (tradingRoom.isFinished()) {
                        break;
                    }
                    if (tradingRoom.hasConfirmed(other)) {
                        traded = tradingRoom.completeTrade();
                        if (traded) {
                            tradingRoom.completeMiniRoom();
                        } else {
                            tradingRoom.cancelMiniRoom(chr, RoomLeaveType.Error);
                        }
                    } else {
                        tradingRoom.addConfirmedChar(chr);
                    }
                }
                if (traded) {
                    AutoSave.saveNow(chr);
                    AutoSave.saveNow(other);
                }
                break;
            case TradeConfirmRemoteResponse:
                // just an ack by the client?
                break;
            case TradeStateCheck:
                // the answer to an Out TradeStateCheck (never sent by the server)
                break;
        }
    }



    private static long le(byte[] b, int off, int n) {
        long v = 0;
        for (int i = n - 1; i >= 0; i--) {
            v = (v << 8) | (b[off + i] & 0xFF);
        }
        return n == 4 ? (int) v : v;
    }

    private static String hex(byte[] b) {
        StringBuilder sb = new StringBuilder();
        for (byte x : b) {
            sb.append(String.format("%02X ", x));
        }
        return sb.toString().trim();
    }

    /**
     * Contributed by Arixal (from GitLab).
     *
     * The quantity a put moves. A throwing-star or bullet set always moves whole, whatever the client sends (it sends
     * 1 for them, as storage, the shop and the Auction House assume): part of one would become a second set.
     */
    static int offerQuantity(Item item, int requested) {
        return ItemConstants.isRechargable(item.getItemId()) ? item.getQuantity() : requested;
    }

    // Contributed by Arixal (from GitLab).
    /** Takes the quantity put in a trade window out of the inventory (consumeItem(Item) never removes a set). */
    static void takeFromInventory(Char chr, Item item, int quantity) {
        chr.consumeItem(item, quantity);
    }

    /** {inv, slot, quantity, trade slot, form}; form -1 = nothing matched (the v265 reading is returned). */
    private static int[] parsePut(Char chr, byte[] b) {
        int[][] forms = {{0, 4}, {0, 2}, {4, 4}, {4, 2}};   // offset of the inv byte, quantity width
        int[] first = null;
        for (int f = 0; f < forms.length; f++) {
            int off = forms[f][0], qw = forms[f][1];
            if (b.length < off + 4 + qw) {
                continue;
            }
            int inv = b[off];
            int slot = (short) le(b, off + 1, 2);
            int qty = (int) le(b, off + 3, qw);
            int ts = b[off + 3 + qw];
            int[] r = {inv, slot, qty, ts, f};
            if (first == null) {
                first = new int[]{inv, slot, qty, ts, -1};
            }
            InvType t = InvType.getInvTypeByVal(inv);
            if (t == null || t == InvType.EQUIPPED || ts < 0 || ts >= net.swordie.ms.constants.GameConstants.MAX_TRADE_ITEMS) {
                continue;
            }
            Item it = chr.getInventoryByType(t) == null ? null : chr.getInventoryByType(t).getItemBySlot(slot);
            if (it != null && qty > 0 && qty <= it.getQuantity()) {
                return r;
            }
        }
        return first != null ? first : new int[]{0, 0, 0, 0, -1};
    }

    public static void handleCommonMiniRoomAction(Char chr, MiniRoomAction mrt, InPacket inPacket) {

        var miniRoom = chr.getMiniRoom();

        switch (mrt) {
            // Common
            case CreateMiniRoom:
                if (miniRoom != null) {
                    // already in a room (e.g. a trade with items offered): a second one would strand the first
                    chr.dispose();
                    return;
                }
                var miniRoomTypeVal = inPacket.decodeByte();
                var miniRoomType = MiniRoomType.getByVal(miniRoomTypeVal);
                if (miniRoomType == null) {
                    return;
                }

                var miniRoomCreated = MiniRoomFactory.getByType(miniRoomType);
                if (miniRoomCreated == null) {
                    return;
                }

                if (miniRoomCreated instanceof MiniGameRoom) {
                    if (!setUpGameRoom(chr, (MiniGameRoom) miniRoomCreated, inPacket)) {
                        return;
                    }
                } else if (miniRoomType == MiniRoomType.TradingRoom) {
                    inPacket.decodeByte(); // 0
                }
                miniRoomCreated.createMiniRoom(chr);
                break;

            case Balloon:
                // the owner's dialog opened: the sign went up with the room already
                if (miniRoom instanceof MiniGameRoom && miniRoom.getRoomOwner() == chr) {
                    chr.getField().broadcastPacket(UserPacket.miniRoomBalloon(chr, (MiniGameRoom) miniRoom));
                }
                break;
            case Exit:
                if (miniRoom != null) {
                    miniRoom.charLeave(chr, RoomLeaveType.LeftRoom);
                }
                break;
            case Chat:
                inPacket.decodeInt(); // tick
                String msg = inPacket.decodeString();

                if (miniRoom == null) {
                    chr.chatMessage("You are currently not in a room.");
                    return;
                }

                miniRoom.chat(chr, msg);
                break;
            case Accept:
                var inviterChrId = inPacket.decodeInt();
                var isPrivateRoom = inPacket.decodeByte() != 0;
                String enteredPassword = null;
                if (isPrivateRoom) {
                    enteredPassword = inPacket.decodeString();
                }
                inPacket.decodeByte();
                var inviter = chr.getField().getCharByID(inviterChrId);

                if (inviter == null) {
                    chr.write(MiniRoomPool.miniRoomBaseDlg(MiniRoomPacket.enterFailed(1)));
                    return;
                }
                if (inviter.isInInterface()) {
                    chr.chatMessage("That player is currently doing something else");
                    return;
                }
                if (chr.getMiniRoom() != null || chr.isInInterface()) {
                    // Accepter is already in a mini room or in an interface
                    return;
                }

                var inviterMiniRoom = inviter.getMiniRoom();

                if (inviterMiniRoom == null) {
                    // the room is closed
                    chr.write(MiniRoomPool.miniRoomBaseDlg(MiniRoomPacket.enterFailed(1)));
                    return;
                }
                if (inviterMiniRoom instanceof MiniGameRoom) {
                    var gameRoom = (MiniGameRoom) inviterMiniRoom;
                    synchronized (gameRoom) {
                        int result = gameRoom.canEnter(chr, enteredPassword);
                        if (result != 0) {
                            chr.write(MiniRoomPool.miniRoomBaseDlg(MiniRoomPacket.enterFailed(result)));
                            return;
                        }
                        gameRoom.charEnter(chr);
                    }
                    return;
                }
                if (inviterMiniRoom.getChrs().size() >= inviterMiniRoom.getMaxChars()) {
                    chr.write(MiniRoomPool.miniRoomBaseDlg(MiniRoomPacket.enterFailed(2)));
                    return;
                }
                if (inviterMiniRoom instanceof TradingRoom && !(inviterMiniRoom.isInvited(chr))) {
                    // Person accepts but has not been invited to the trade
                    return;
                }
                inviterMiniRoom.charEnter(chr);
                break;
            case InviteRequest:
                var chrId = inPacket.decodeInt();
                var invitee = chr.getField().getCharByID(chrId);
                if (miniRoom == null) {
                    return;
                }
                if (invitee == null) {
                    chr.write(MiniRoomPool.miniRoomBaseDlg(MiniRoomPacket.inviteResult(1, null)));
                    miniRoom.cancelMiniRoom(invitee, RoomLeaveType.Error);
                    return;
                }
                if (invitee.getMiniRoom() != null || invitee.isInInterface()) {
                    chr.write(MiniRoomPool.miniRoomBaseDlg(MiniRoomPacket.inviteResult(2, invitee)));
                    miniRoom.cancelMiniRoom(invitee, RoomLeaveType.Busy);
                    return;
                }

                miniRoom.invite(chr, invitee);
                break;
            case Decline:
                inviterChrId = inPacket.decodeInt();
                // the result the inviter's client shows: the client sends 11 (busy) by itself
                int reason = inPacket.getUnreadAmount() > 0 ? inPacket.decodeByte() : 3;
                inviter = chr.getField().getCharByID(inviterChrId);

                if (inviter == null || inviter.getMiniRoom() == null || !inviter.getMiniRoom().isInvited(chr)) {
                    return;
                }

                inviter.write(MiniRoomPool.miniRoomBaseDlg(MiniRoomPacket.inviteResult(reason, chr)));
                inviter.getMiniRoom().declineInvitation(chr);
                break;
        }
    }
}
