package net.swordie.ms.handlers.life;

import java.io.File;
import net.swordie.ms.ServerConstants;
import net.swordie.ms.client.Client;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.items.Equip;
import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.client.character.quest.Quest;
import net.swordie.ms.client.character.quest.QuestManager;
import net.swordie.ms.client.trunk.Trunk;
import net.swordie.ms.client.trunk.TrunkDlg;
import net.swordie.ms.client.trunk.TrunkType;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.packet.*;
import net.swordie.ms.connection.packet.field.FieldPacket;
import net.swordie.ms.connection.packet.model.MessagePacket;
import net.swordie.ms.constants.FieldConstants;
import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.enums.ChatType;
import net.swordie.ms.handlers.Handler;
import net.swordie.ms.handlers.header.InHeader;
import net.swordie.ms.handlers.life.modules.NpcHandlerModule;
import net.swordie.ms.handlers.user.MigrationHandler;
import net.swordie.ms.life.Life;
import net.swordie.ms.life.movement.Movement;
import net.swordie.ms.life.movement.MovementInfo;
import net.swordie.ms.life.npc.Npc;
import net.swordie.ms.loaders.ItemData;
import net.swordie.ms.loaders.NpcData;
import net.swordie.ms.loaders.QuestData;
import net.swordie.ms.loaders.containerclasses.QuestInfo;
import net.swordie.ms.scripts.ScriptType;
import net.swordie.ms.util.Position;
import net.swordie.ms.world.gach.GachaponConstants;
import net.swordie.ms.world.gach.result.GachaponDlgType;
import net.swordie.ms.world.gach.result.GachaponResult;
import net.swordie.ms.world.shop.NpcShopDlg;
import net.swordie.ms.world.shop.ShopRequestType;
import net.swordie.orm.dao.ItemDao;
import net.swordie.orm.dao.SworDaoFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class NpcHandler {
    private static final int CENTRAL_CONTROL_SYSTEM_FIELD = 957019000;

    private static final Logger log = LogManager.getLogger();
    private static final ItemDao itemDao = (ItemDao) SworDaoFactory.getByClass(Item.class);
    private static final int MAPLE_GUIDE_MIRROR_NPC = 9010064;
    private static final int MAPLE_GUIDE_REQUEST_MODE = -2;


    /**
     * Contributed by Arixal (from GitLab).
     *
     * The Maple Guide's entries with npc=9010064 (Etc/ContentsGuide.img, e.g. Monster Park) send USER_SELECT_NPC with that
     * template id where a map NPC's object id goes, and -2 where a click on a map NPC has -1. The packet doesn't say which
     * entry was clicked, so the Dimensional Mirror opens (as its quick move entry, with that entry's checks) and the
     * destination is picked there.
     */
    static boolean isMapleGuideMirrorRequest(int npcId, int requestMode) {
        return npcId == MAPLE_GUIDE_MIRROR_NPC && requestMode == MAPLE_GUIDE_REQUEST_MODE;
    }

    private static void openMirrorFromGuide(Char chr) {
        if (FieldConstants.teleportWouldBreak(chr, chr.getField())) {
            chr.dispose();
            return;
        }
        MigrationHandler.useQuickMove(chr, GameConstants.DIMENSIONAL_MIRROR_NPC);
    }

    @Handler(op = InHeader.USER_SELECT_NPC)
    public static void handleUserSelectNpc(Client c, InPacket inPacket) {
        Char chr = c.getChr();
        if(chr == null || !chr.isOnline() || chr.getUser() == null) {
            log.error(String.format("[CharId: %d] tried to open npc while being offline.", chr.getId()));
            c.write(WvsContext.returnToTitle());
            c.close();
            return;
        }
        int npcID = inPacket.decodeInt();
        Position playerPos = inPacket.decodePosition();
        int requestMode = inPacket.getUnreadAmount() >= 4 ? inPacket.decodeInt() : -1;
        if (isMapleGuideMirrorRequest(npcID, requestMode)) {
            openMirrorFromGuide(chr);
            return;
        }
        Life life = chr.getField().getLifeByObjectID(npcID);
        if (!(life instanceof Npc)) {
            chr.chatMessage("Could not find that npc.");
            return;
        }
        Npc npc = (Npc) life;
        int templateID = npc.getTemplateId();
        // storage keepers and shopkeepers a quest has the player talk to: scripts/npc/before_<id>.py runs first and
        // opens the storage or shop itself when the quest doesn't need the talk
        String before = "before_" + templateID;
        if ((npc.isTrunkKeeper() || npc.isShop())
                && new java.io.File(net.swordie.ms.ServerConstants.SCRIPT_DIR + "/npc/" + before + ".py").exists()) {
            if (!chr.isTalkingToNpc()) {
                chr.setTalkingToNpc(true);
                chr.getScriptManager().startScript(templateID, npcID, before, ScriptType.Npc);
            }
            return;
        }
        if (npc.isTrunkKeeper() && !chr.isInTrunk()) {
            chr.setInTrunk(true);
            chr.write(FieldPacket.trunkDlg(TrunkDlg.open(templateID, chr.getAccount().getTrunk())));
            return;
        }
        if (net.swordie.ms.world.HolidayEvents.hasDialog(chr, templateID)) {
            // an NPC with an event quest to start or hand in (the client won't offer quests whose Quest.wz dates are long
            // past): scripts/npc/holidayNpc.py, which can still open the NPC's own shop or script
            if (!chr.isTalkingToNpc()) {
                chr.setTalkingToNpc(true);
                chr.getScriptManager().startScript(templateID, npcID, "holidayNpc", ScriptType.Npc);
            }
            return;
        }
        String script = npc.getScripts().get(0);
        if (npc.isShop()) {
            NpcShopDlg nsd = NpcData.getShopById(templateID);
            if (nsd != null && !nsd.getItems().isEmpty()) {
                chr.getScriptManager().stop(ScriptType.Npc); // reset contents before opening shop?
                if (chr.getShop() == null) {
                    chr.debugMessage("Shop: " + templateID);
                    chr.setShop(nsd);
                    if(templateID == GameConstants.COLLECTOR_NPC) {
                        chr.write(ShopDlg.shopCollectorInit());
                    }

                    chr.write(ShopDlg.openShop(chr, 0, nsd));
                }
                return;
            } else {
                script = GameConstants.DEFAULT_SHOP_SCRIPT;
            }
        }
        if (script == null) {
            script = String.valueOf(npc.getTemplateId());
        }
        if (!chr.isTalkingToNpc()) {
            chr.setTalkingToNpc(true);
            chr.getScriptManager().startScript(npc.getTemplateId(), npcID, script, ScriptType.Npc);
        } else {
            chr.chatMessage("You're already talking to an NPC. Type @stuck if this is not intended.");
        }
    }

    @Handler(op = InHeader.USER_COMPLETE_NPC_SPEECH)
    public static void handleUserCompleteNpcSpeech(Client c, InPacket inPacket) {
        completeNpcSpeech(c, inPacket);
        if (c.getChr() != null) {
            c.getChr().write(net.swordie.ms.connection.packet.UserLocal.npcSpeechDone()); // GMS ends every speech with it
        }
    }

    private static void completeNpcSpeech(Client c, InPacket inPacket) {
        Char chr = c.getChr();
        QuestManager qm = chr.getQuestManager();
        inPacket.decodeInt(); // v271: a changing int (not the field id) comes first, then the quest and the NPC
        int questID = inPacket.decodeInt();
        int npcTemplateID = inPacket.decodeInt();
        int speech = inPacket.decodeByte();

        int objectID = inPacket.decodeInt();
        Life life = chr.getField().getLifeByObjectID(objectID);
        if (!(life instanceof Npc)) {
            chr.chatMessage("Could not find that npc.");
            return;
        }
        npcTemplateID = life.getTemplateId();
        if (qm.hasQuestInProgress(questID)) {
            QuestInfo qi = QuestData.getQuestInfoById(questID);
            var speeches = qi == null ? null : qi.getSpeech();
            if (speeches == null || speeches.isEmpty()) {
                // text speeches: the client shows them itself, but the talk still has to be recorded ("NpcSpeech=<npc><order>")
                // or the quest never counts it (e.g. "A Plan in Crisis", 31381)
                markTextSpeech(chr, qm, questID, npcTemplateID, speech);
                startSpeechScript(chr, questID, npcTemplateID);
                return;
            }
            // the speech list holds, for each npcSpeech entry with a script, the script name followed by the record of the
            // talks so far ("NpcSpeech=<npc><order>/..."). The talk is picked by the NPC spoken to, not the client's index,
            // which doesn't match for quests with more than one scripted talk.
            List<String> scripts = new ArrayList<>();
            List<String> marks = new ArrayList<>();
            for (int i = 0; i < speeches.size(); i++) {
                String s = speeches.get(i);
                if (s == null || s.startsWith("NpcSpeech=")) {
                    continue;
                }
                String mark = "";
                if (i + 1 < speeches.size() && speeches.get(i + 1) != null && speeches.get(i + 1).startsWith("NpcSpeech=")) {
                    String[] done = speeches.get(i + 1).substring("NpcSpeech=".length()).split("/");
                    mark = done.length == 0 ? "" : done[done.length - 1];
                }
                scripts.add(s);
                marks.add(mark);
            }
            int talk = -1;
            String npcId = Integer.toString(npcTemplateID);
            for (int i = 0; i < marks.size(); i++) {
                String m = marks.get(i);
                if (m.startsWith(npcId) && m.length() > npcId.length()) {
                    talk = i;
                    break;
                }
            }
            if (talk < 0) {
                markTextSpeech(chr, qm, questID, npcTemplateID, speech); // a text talk of a quest with scripted ones
                return;
            }
            String scriptName = scripts.get(talk);
            if (new java.io.File(net.swordie.ms.ServerConstants.SCRIPT_DIR + "/quest/" + scriptName + ".py").exists()) {
                chr.getScriptManager().startScript(questID, scriptName, ScriptType.Quest);
            } else if (!marks.get(talk).isEmpty()) {
                // the talk's script isn't on the server: count the talk as done, so the quest can go on
                Quest quest = qm.getQuestById(questID);
                if (quest != null) {
                    String value = quest.getQRValue() == null ? "" : quest.getQRValue();
                    java.util.Set<String> done = new java.util.HashSet<>();
                    if (value.startsWith("NpcSpeech=")) {
                        done.addAll(java.util.Arrays.asList(value.substring("NpcSpeech=".length()).split("/")));
                    }
                    done.add(marks.get(talk));
                    StringBuilder sb = new StringBuilder("NpcSpeech=");
                    String sep = "";
                    for (String m : marks) {
                        if (!m.isEmpty() && done.contains(m)) {
                            sb.append(sep).append(m);
                            sep = "/";
                        }
                    }
                    quest.setQrValue(sb.toString());
                    chr.write(WvsContext.message(MessagePacket.questRecordExMessage(quest)));
                }
            }
        }
    }

    /** GMS answers some text talks with a dialog of the NPC (scripts/quest/speech_<quest>_<npc>.py). */
    private static void startSpeechScript(Char chr, int questID, int npcTemplateID) {
        String name = "speech_" + questID + "_" + npcTemplateID;
        if (new File(ServerConstants.SCRIPT_DIR + "/quest/" + name + ".py").exists()) {
            chr.getScriptManager().startScript(questID, name, ScriptType.Quest);
        }
    }

    /** Records a text-only npcSpeech talk in the quest's record, as the scripted ones are ("<npc><order>"). */
    private static void markTextSpeech(Char chr, QuestManager qm, int questID, int npcTemplateID, int speech) {
        Quest quest = qm.getQuestById(questID);
        if (quest == null) {
            return;
        }
        String mark = npcTemplateID + "" + Math.max(1, speech); // the client sends the talk's order (31381: 1)
        String value = quest.getQRValue() == null ? "" : quest.getQRValue();
        java.util.LinkedHashSet<String> done = new java.util.LinkedHashSet<>();
        if (value.startsWith("NpcSpeech=") && value.length() > "NpcSpeech=".length()) {
            done.addAll(java.util.Arrays.asList(value.substring("NpcSpeech=".length()).split("/")));
        }
        if (!done.add(mark)) {
            return;
        }
        quest.setQrValue("NpcSpeech=" + String.join("/", done));
        chr.write(WvsContext.message(MessagePacket.questRecordExMessage(quest)));
    }

    /** The inventory's Shop button: the Android Shop (the one in @menu > Shops). */
    @Handler(op = InHeader.USER_INVENTORY_SHOP_REQUEST)
    public static void handleUserInventoryShopRequest(Char chr, InPacket inPacket) {
        if (chr.isTalkingToNpc() || chr.getScriptManager().isActive(net.swordie.ms.scripts.ScriptType.Npc)) {
            chr.dispose();
            return;
        }
        chr.getScriptManager().openShop(1012123); // Lauren, Henesys; hardcode-ok
    }

    @Handler(op = InHeader.USER_SHOP_REQUEST)
    public static void handleUserShopRequest(Char chr, InPacket inPacket) {
        byte type = inPacket.decodeByte();
        ShopRequestType shr = ShopRequestType.getByVal(type);
        if (shr == null) {
            log.error(String.format("Unhandled shop request type %d", type));
        }
        NpcShopDlg nsd = chr.getShop();
        if (nsd == null) {
            chr.chatMessage("You are currently not in a shop.");
            return;
        }
        switch (shr) {
            case Buy:
                ShopModule.handleShopBuy(chr, inPacket, nsd);
                break;
            case Recharge:
                ShopModule.handleShopRecharge(chr, inPacket);
                break;
            case Sell:
                ShopModule.handleShopSell(chr, inPacket, nsd);
                break;
            case Close:
                chr.setShop(null);
                break;
            default:
                log.error(String.format("Unhandled shop request type %s", shr));
        }
        chr.dispose();
    }

    @Handler(op = InHeader.USER_TRUNK_REQUEST)
    public static void handleUserTrunkRequest(Client c, InPacket inPacket) {
        Char chr = c.getChr();
        Trunk trunk = chr.getAccount().getTrunk();
        byte req = inPacket.decodeByte();
        TrunkType trunkType = TrunkType.getByVal(req);
        if (trunkType == null) {
            log.error(String.format("Unknown trunk request type %d.", req));
            return;
        }
        if (!chr.isInTrunk() && trunkType != TrunkType.TrunkReq_CloseDialog) {
            // storage packets are only valid while a storage dialog (NPC, script or @storage) is open
            chr.dispose();
            return;
        }
        if (chr.getMiniRoom() != null) {
            chr.write(FieldPacket.trunkDlg(TrunkDlg.message(TrunkType.TrunkRes_GetUnknown)));
            return;
        }
        switch (trunkType) {
            case TrunkReq_Money:
                NpcHandlerModule.handleTrunkGetMoney(inPacket, chr, trunk);
                break;
            case TrunkReq_GetItem:
                NpcHandlerModule.handleTrunkGetItem(inPacket, chr, trunk);
                break;
            case TrunkReq_PutItem:
                NpcHandlerModule.handleTrunkPutItem(inPacket, chr, trunk);
                break;
            case TrunkReq_SortItem:
                trunk.sortItems();
                chr.write(FieldPacket.trunkDlg(TrunkDlg.update(TrunkType.TrunkRes_SortItem, trunk)));
                break;
            case TrunkReq_CloseDialog:
                chr.setInTrunk(false);
                chr.dispose();
                break;
            default:
                log.error(String.format("Unhandled trunk request type %s.", trunkType));
        }
    }

    @Handler(op = InHeader.GACHAPON_REQUEST)
    public static void handleGachaponRequest(Char chr, InPacket inPacket) {
        // TODO: Handle error messages with popup dialog like GMS.
        final int type = inPacket.decodeByte();
        final GachaponResult result = GachaponResult.getByVal(type);

        if (result == null) {
            log.error("[Gachapon] Found unknown gachapon result " + type);
            chr.write(GachaponDlg.gachResult(GachaponResult.ERROR));
            return;
        }
        if (chr == null) {
            chr.write(GachaponDlg.gachResult(GachaponResult.ERROR));
            return;
        }
        switch (result) {
            case SUCCESS:
                final int ticketID = inPacket.decodeInt();
                GachaponDlgType dialog = GachaponConstants.getDlgByTicket(ticketID);
                if (dialog == null || !chr.hasItem(ticketID)) {
                    chr.write(GachaponDlg.gachResult(GachaponResult.ERROR));
                    return;
                }
                // the town gachapon gives from the pool of the town it stands in
                final var pick = GachaponConstants.getRandomReward(dialog, chr.getFieldID());
                if (pick == null) {
                    chr.chatMessage(ChatType.Mob, "This gachapon has no rewards right now.");
                    chr.write(GachaponDlg.gachResult(GachaponResult.ERROR));
                    return;
                }
                final int reward = pick.getItemID();
                final short count = pick.getCount();
                if (!chr.canHoldItem(reward, count)) {
                    chr.chatMessage(ChatType.Mob, "Please make room in your inventory first.");
                    chr.write(GachaponDlg.gachResult(GachaponResult.ERROR));
                    return;
                }
                Equip equip = ItemData.getEquipDeepCopy(reward, true);
                if (equip == null) {
                    Item item = ItemData.getItemDeepCopy(reward, true);
                    if (item == null) {
                        chr.write(GachaponDlg.gachResult(GachaponResult.ERROR));
                        chr.chatMessage(ChatType.Mob, "Item is null" + reward);
                        return;
                    }
                    item.setQuantity(count);
                    chr.addItemToInventory(item);
                    chr.write(GachaponDlg.gachResult(GachaponResult.SUCCESS, item, count));
                    chr.getGachaponManager().addItem(dialog, item, count);
                } else {
                    chr.addItemToInventory(equip);
                    chr.write(GachaponDlg.gachResult(GachaponResult.SUCCESS, equip, (short) 1));
                    chr.getGachaponManager().addItem(dialog, equip, (short) 1);
                }
                chr.consumeItem(chr.getCashInventory().getItemByItemID(ticketID));
                chr.getField().broadcastPacket(UserPacket.setGachaponEffect(chr));
                break;
            case EXIT:
                chr.write(GachaponDlg.gachResult(GachaponResult.EXIT));
                break;
        }
    }

    @Handler(op = InHeader.EVOLVING_REQUEST)
    public static void handleEvolvingRequest(Client c, InPacket inpacket) {
        byte unk = inpacket.decodeByte();
        int unk2 = inpacket.decodeInt();
        int timestamp = inpacket.decodeInt();
        byte unk3 = inpacket.decodeByte();
        Char chr = c.getChr();
            chr.warp(CENTRAL_CONTROL_SYSTEM_FIELD);
    }

    @Handler(op = InHeader.NPC_MOVE)
    public static void handleNpcMove(Char chr, InPacket inPacket) {
        int objectID = inPacket.decodeInt();
        byte oneTimeAction = inPacket.decodeByte();
        byte chatIdx = inPacket.decodeByte();
        int duration = inPacket.decodeInt();
        Life life = chr.getField().getLifeByObjectID(objectID);
        if (life instanceof Npc && ((Npc) life).isMove()) {
            Npc npc = (Npc) chr.getField().getLifeByObjectID(objectID);
            boolean move = npc.isMove();
            MovementInfo movementInfo = new MovementInfo(npc.getPosition(), npc.getVPosition());
            byte keyPadState = 0;
            if (move && inPacket.getUnreadAmount() > 0) {
                movementInfo.decode(inPacket);
                for (Movement m : movementInfo.getMovements()) {
                    Position pos = m.getPosition();
                    Position vPos = m.getVPosition();
                    if (pos != null) {
                        npc.setPosition(pos);
                    }
                    if (vPos != null) {
                        npc.setvPosition(vPos);
                    }
                    npc.setMoveAction(m.getMoveAction());
                    npc.setFh(m.getFh());
                }
                if (inPacket.getUnreadAmount() > 0) {
                    keyPadState = inPacket.decodeByte(); // not always encoded?
                }
            }
            chr.getField().broadcastPacket(NpcPool.npcMove(objectID, oneTimeAction, chatIdx, duration, move,
                    movementInfo, keyPadState));
        }
    }
}
