package net.swordie.ms.handlers.script;

import net.swordie.ms.client.Client;
import net.swordie.ms.client.character.AutoAbilityModule;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.union.ChampionRaid;
import net.swordie.ms.client.character.MonsterParkExtreme;
import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.world.field.instance.instancehelper.epic.EpicDungeon;
import net.swordie.ms.connection.packet.InGameDirectionEvent;
import net.swordie.ms.connection.packet.UserLocal;
import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.constants.FieldConstants;
import net.swordie.ms.constants.ItemConstants;
import net.swordie.ms.constants.QuestConstants;
import net.swordie.ms.enums.*;
import net.swordie.ms.handlers.Handler;
import net.swordie.ms.handlers.header.InHeader;
import net.swordie.ms.life.Life;
import net.swordie.ms.life.RandomPortal;
import net.swordie.ms.life.npc.NpcMessageType;
import net.swordie.ms.loaders.EtcData;
import net.swordie.ms.loaders.ItemData;
import net.swordie.ms.scripts.ScriptManagerImpl;
import net.swordie.ms.scripts.ScriptType;
import net.swordie.ms.world.field.Field;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class ScriptHandler {
    private static final int MYSTIC_FACE_ACCESSORY = 1012331;
    private static final int MYSTIC_EYE_ACCESSORY = 1022148;
    private static final int MYSTIC_EARRINGS = 1032156;
    private static final int VETERAN_CRUSADER_PENDANT = 1122157;
    private static final int MYSTIC_PENDANT = 1122208;
    private static final int VETERAN_CRUSADER_BELT = 1132111;
    private static final int MYSTIC_BELT = 1132182;
    private static final int VETERAN_CRUSADER_SHOULDER = 1152069;
    private static final int SILENT_CRUSADE_TRANSTICKET = 2030026;
    private static final int SILENT_CRUSADE_MASTERY_BOOK = 2430668;
    private static final int APPRENTICE_HUNTER = 3700031;
    private static final int CAPABLE_HUNTER = 3700032;
    private static final int VETERAN_HUNTER = 3700033;
    private static final int SUPERIOR_HUNTER = 3700034;
    private static final int CRUSADER_COINS = 4310029;
    private static final int MYSTERIOUS_AIDE_NPC = 9133402;

    private static final Logger log = LogManager.getLogger();


    @Handler(op = InHeader.USER_SCRIPT_MESSAGE_ANSWER)
    public static void handleUserScriptMessageAnswer(Client c, InPacket inPacket) {
        Char chr = c.getChr();
        ScriptManagerImpl smi = chr.getScriptManager();
        var serverType = inPacket.decodeInt(); // given in type 0 scriptmessage, unused for now
        byte lastType = (byte) NpcMessageType.fromV271(inPacket.decodeByte()); // the client's v271 number
        NpcMessageType nmt = smi.getNpcScriptInfo().getMessageType();
        // whether the server itself sent this dialog type (otherwise it's taken from the client)
        boolean serverSentType = nmt != null && smi.isActive(smi.getLastActiveScriptType());
        if (nmt == null) {
            nmt = lastType < NpcMessageType.values().length
                    ? Arrays.stream(NpcMessageType.values()).filter(n -> n.getVal() == lastType).findAny().orElse(NpcMessageType.None)
                    : NpcMessageType.None;
        }

        // v265 per-type response decoding
        byte action = 0;
        int answer = 0;
        String ans = null;
        switch (nmt) {
            case Say:
            case SayOk:
            case SayNext:
            case SayPrev:
                inPacket.decodeInt();
                inPacket.decodeString();
                action = inPacket.decodeByte();
                break;
            case AskAccept:
            case AskAccept2:
            case AskYesNo:
                action = inPacket.decodeByte();
                break;
            case AskMenu:
            case AskSlideMenu:
            case AskStoryUI:
                action = (byte) (inPacket.decodeByte() > 0 ? 1 : -1);
                if (action > 0) {
                    answer = Math.max(inPacket.decodeInt(), 0);
                }
                break;
            case AskText:
            case AskBoxtext:
                action = (byte) (inPacket.decodeByte() > 0 ? 1 : -1);
                if (action > 0) {
                    ans = inPacket.decodeString();
                }
                break;
            case AskNumber:
            case OnAskNumberUseKeyPad:
                action = (byte) (inPacket.decodeByte() > 0 ? 1 : -1);
                if (action > 0) {
                    answer = (int) Math.max(inPacket.decodeLong(), 0L);
                }
                break;
            case AskAvatar:
            case AskAvatar2:
                action = (byte) (inPacket.decodeByte() > 0 ? 1 : -1);
                if (action > 0) {
                    inPacket.decodeByte(); // Angelic Buster flag (echoed)
                    inPacket.decodeByte();
                    answer = inPacket.decodeByte() & 0xFF; // option index, up to 255 options
                }
                break;
            case AskAvatarZero:
                inPacket.decodeByte();
                action = (byte) (inPacket.decodeByte() > 0 ? 1 : -1);
                if (action > 0) {
                    answer = inPacket.decodeByte();
                }
                break;
            case AskAndroid:
            case AskAndroid2:
                action = (byte) (inPacket.decodeByte() > 0 ? 1 : -1);
                if (action > 0) {
                    answer = inPacket.decodeByte() & 0xFF;
                }
                break;
            case AskPet:
            case AskPetAll:
            case AskActionPetEvolution:
                action = (byte) (inPacket.decodeByte() > 0 ? 1 : -1);
                if (action > 0) {
                    answer = (int) inPacket.decodeLong();
                }
                break;
            case AskAngelicBuster:
                action = inPacket.decodeByte();
                answer = action;
                break;
            case AskSelectMenu:
                action = inPacket.decodeByte();
                answer = inPacket.decodeByte();
                break;
            case PlayMovieClip:
                answer = inPacket.decodeByte();
                action = (byte) (inPacket.decodeByte() > 0 ? 1 : -1);
                break;
            case AskIngameDirection:
                action = inPacket.decodeByte();
                InGameDirectionAsk answerType = InGameDirectionAsk.getByVal(action);
                if (answerType == null || answerType == InGameDirectionAsk.NOT) {
                    return;
                }
                if (inPacket.getUnreadAmount() >= 2) {
                    inPacket.decodeByte(); // v271 0x1438626a0: context flag before success
                }
                boolean success = inPacket.decodeByte() == 1;
                if (answerType == InGameDirectionAsk.CAMERA_MOVE_TIME && success) {
                    int answerVal = inPacket.decodeInt();
                    chr.write(UserLocal.inGameDirectionEvent(InGameDirectionEvent.delay(answerVal)));
                    return;
                }
                chr.getScriptManager().handleAction(nmt, (byte) 1, answerType.getVal());
                return;
            case Monologue:
                action = 1;
                break;
            case SayIllustration:
            case SayDualIllustration:
                // same as Say in v265
                inPacket.decodeInt();
                inPacket.decodeString();
                action = inPacket.decodeByte();
                break;
            case AskYesNoIllustration:
            case AskAcceptIllustration:
            case AskYesNoDualIllustration:
            case AskAcceptDualIllustration:
                action = inPacket.decodeByte();
                break;
            case AskMenuIllustration:
            case AskMenuDualIllustration:
                action = (byte) (inPacket.decodeByte() > 0 ? 1 : -1);
                if (action > 0) {
                    answer = Math.max(inPacket.decodeInt(), 0);
                }
                break;
            default:
                // unknown type: try reading action + optional int
                action = inPacket.decodeByte();
                if (inPacket.getUnreadAmount() >= 4) {
                    answer = inPacket.decodeInt();
                }
                break;
        }
        // route the answer to the script engine
        if (action == -1 && nmt == NpcMessageType.AskStoryUI && serverSentType) {
            smi.handleAction(nmt, (byte) 1, -1); // closed without a pick: the script asks again
            return;
        }
        if (action == -1) {
            smi.dispose(false);
            return;
        }
        if (nmt == NpcMessageType.AskSlideMenu) {
            // only while a Dimensional Mirror dialog is really open
            var town = DimensionalPortalTownType.getByVal(answer);
            if (!serverSentType || town == null || chr.getHP() <= 0) {
                smi.dispose(false);
                return;
            }
            chr.warp(town.getMapID());
            chr.dispose();
        }
        if (ans != null) {
            chr.getScriptManager().handleAction(nmt, action, ans);
        } else {
            chr.getScriptManager().handleAction(nmt, action, answer);
        }
    }

    @Handler(op = InHeader.DIRECTION_NODE_COLLISION)
    public static void handleDirectionNodeCollision(Client c, InPacket inPacket) {
        Char chr = c.getChr();
        if (chr == null || chr.getField() == null) {
            return;
        }
        Field field = chr.getField();

        int node = inPacket.decodeInt();
        List<String> directionNode = field.getInfo().getDirectionNode(node);
        if (directionNode == null) {
            return;
        }
        String script = directionNode.get(chr.getCurrentDirectionNode(node));
        if (script == null) {
            return;
        }
        chr.increaseCurrentDirectionNode(node);
        chr.getScriptManager().setCurNodeEventEnd(false);
        chr.getScriptManager().startScript(field.getId(), script, ScriptType.Field);
    }

    @Handler(op = InHeader.USER_CONTENTS_MAP_REQUEST)
    public static void handleUserContentsMapRequest(Char chr, InPacket inPacket) {
        // v265: all senders (0x142660420/640/860) write only the field id
        if (inPacket.getUnreadAmount() < 4) {
            return;
        }
        int fieldId = inPacket.decodeInt();

        if (!EtcData.getGrowthHelp().containsKey(fieldId) || EtcData.getGrowthHelp().get(fieldId) > chr.getLevel()) {
            chr.getOffenseManager().addOffense(String.format("Tried going to %d, requires lv %d. Current lv: %d",
                    fieldId, EtcData.getGrowthHelp().get(fieldId), chr.getLevel()));
            return;
        }

        Field field = chr.getOrCreateFieldByCurrentInstanceType(fieldId);
        if (field == null || net.swordie.ms.constants.FieldConstants.teleportWouldBreak(null, field)) {
            chr.chatMessage("You may not warp to that map.");
            chr.dispose();
            return;
        }

        chr.warp(field);
    }

    private static final short RUN_SCRIPT_STORAGE_ROOM = 111;
    private static final short RUN_SCRIPT_UNION_RAID = 17;
    private static final short RUN_SCRIPT_UNION_CHAMPION = 18;
    private static final short RUN_SCRIPT_UNION_COIN = 19;

    @Handler(op = InHeader.USER_RUN_SCRIPT)
    public static void handleUserRunScript(Char chr, InPacket inPacket) {
        // v271 (client 0x140e3bd30): int seed + (mt19937(seed) % 10) + 1 random bytes before the type.
        inPacket.skipClientSalt(2, 0x200);
        short type = inPacket.decodeShort();
        ScriptManagerImpl sm = chr.getScriptManager();
        switch (type) {
            case 10:
                sm.startScript(chr, 9010000, "MonsterCollection_FAQ", ScriptType.Npc);
                break;
            case 11:
                int fieldID = inPacket.decodeInt();
                if (chr.getField().getId() == fieldID && GameConstants.getMaplerunnerField(fieldID) > 0) {
                    chr.warp(chr.getOrCreateFieldByCurrentInstanceType(FieldConstants.FOREST_OF_TENACITY));
                }
                break;
            case 12:
                sm.startScript(chr, 9010000, "DamageSkinSave_FAQ", ScriptType.Npc);
                break;
            case 18:
                sm.startScript(chr, 9010106, "union_raid", ScriptType.Npc);
                break;
            case 19:
                sm.startScript(chr, 9010106, "union_coin", ScriptType.Npc);
                break;
            case 23:
                sm.startScript(chr, 9010000, "MapleGuide_FAQ", ScriptType.Npc);
                break;
            case 35:
                sm.startScript(chr, 9010000, "Emoticon_FAQ", ScriptType.Npc);
                break;
            case 111:
            case 112:
            case 113:
            case 114:
            case 115:
                // v271: reporte de hash de integridad del cliente (Encode4(hash)), no es una peticion de UI.
                inPacket.skip(inPacket.getUnreadAmount());
                break;
            default:
                //Tipo sin implementar: se drena el frame y se deja constancia en consola, sin responder.
                //94 esta documentado por el log: 5E 00 | 50 53 42 06 | 6D 00 00 00 (03:01:14) y la misma
                //cabecera con 76 00 00 00 a las 14:21:57. Es short tipo + int mapa + int indice (varia
                //109 -> 118), y 105000000 es Sleepywood: el mismo int que USER_SAVE_NAVIGATION_REQUEST
                //(1908) y QUICK_MOVE_NAVIGATION_REQUEST (842), o sea la ventanilla de navegacion.
                //No hay opcode de respuesta verificado en el dump, y responder un script equivocado
                //"fabrica" protocolo, asi que solo se consume.
                int unread = inPacket.getUnreadAmount();
                if (unread > 0) {
                    inPacket.skip(unread);
                }
                System.out.printf("[USER_RUN_SCRIPT] type %d sin implementar | resto=%d bytes%n", type, unread);
                break;
        }
    }

    @Handler(op = InHeader.ENTER_RANDOM_PORTAL_REQUEST)
    public static void handleEnterRandomPortalRequest(Char chr, InPacket inPacket) {
        int portalObjID = inPacket.decodeInt();
        Life life = chr.getField().getLifeByObjectID(portalObjID);
        if (!(life instanceof RandomPortal)) {
            chr.chatMessage("Could not find that portal.");
            return;
        }
        RandomPortal randomPortal = (RandomPortal) life;
        if (randomPortal.getCharID() != chr.getId()) {
            chr.chatMessage("A mysterious force is blocking your way.");
            chr.dispose();
            return;
        }
        RandomPortal.Type type = randomPortal.getAppearType();
        String script = type.getScript();
        chr.dispose();
        chr.getScriptManager().startScript(randomPortal.getAppearType().getVal(), randomPortal.getObjectId(),
                script, ScriptType.Portal);
    }

    @Handler(op = InHeader.LIBRARY_START_SCRIPT)
    public static void handleLibraryStartScript(Char chr, InPacket inPacket) {
        int bookId = inPacket.decodeByte();
        if (chr.getQuestManager().hasQuestCompleted(32662)) {
            int questID = QuestConstants.DIMENSION_LIBRARY + bookId;
            chr.getScriptManager().startScript(questID, "q" + questID + "s", ScriptType.Quest);
        }
    }

    @Handler(op = InHeader.CROSS_HUNTER_COMPLETE_REQUEST)
    public static void handleCrossHunterCompleteRequest(Char chr, InPacket inPacket) {
        short tab = inPacket.decodeShort();
        HashMap<Item, Integer> itemMap = new HashMap<>();

        if (chr.getScriptManager().getQRValue(QuestConstants.SILENT_CRUSADE_WANTED_TAB_1 + tab).contains("r=1")) {
            chr.getOffenseManager().addOffense(String.format("Character %d tried to complete Silent Crusade Chapter %d, whilst already having claimed the reward.", chr.getId(), tab + 1));
            chr.dispose();
            return;
        }

        switch (tab) {
            case 0: // Chapter 1
                itemMap.put(ItemData.getItemDeepCopy(APPRENTICE_HUNTER), 1);  // Apprentice Hunter
                itemMap.put(ItemData.getItemDeepCopy(CRUSADER_COINS), 10); // Crusader Coins  x10
                break;
            case 1: // Chapter 2
                itemMap.put(ItemData.getItemDeepCopy(CAPABLE_HUNTER), 1);  // Capable Hunter
                itemMap.put(ItemData.getItemDeepCopy(ItemConstants.SPELL_TRACE_ID), 100);// Spell Traces  x100
                itemMap.put(ItemData.getItemDeepCopy(CRUSADER_COINS), 15); // Crusader Coins  x15
                break;
            case 2: // Chapter 3
                itemMap.put(ItemData.getItemDeepCopy(VETERAN_HUNTER), 1);  // Veteran Hunter
                itemMap.put(ItemData.getItemDeepCopy(SILENT_CRUSADE_MASTERY_BOOK), 1);  // Silent Crusade Mastery Book
                itemMap.put(ItemData.getItemDeepCopy(CRUSADER_COINS), 20); // Crusader Coins  x20
                break;
            case 3: // Chapter 4
                itemMap.put(ItemData.getItemDeepCopy(SUPERIOR_HUNTER), 1);  // Superior Hunter
                itemMap.put(ItemData.getItemDeepCopy(ItemConstants.SPELL_TRACE_ID), 500);// Spell Traces  x500
                itemMap.put(ItemData.getItemDeepCopy(CRUSADER_COINS), 30); // Crusader Coins  x30
                break;
        }

        if (!chr.canHold(new ArrayList<>(itemMap.keySet()))) {
            chr.chatMessage("Please make some space in your inventory.");
            chr.dispose();
            return;
        }

        chr.getScriptManager().setQRValue(QuestConstants.SILENT_CRUSADE_WANTED_TAB_1 + tab, "r=1");
        for (Map.Entry<Item, Integer> entry : itemMap.entrySet()) {
            Item item = entry.getKey();
            item.setQuantity(entry.getValue());
            chr.addItemToInventory(item);
        }
        chr.dispose();
    }

    @Handler(op = InHeader.CROSS_HUNTER_SHOP_REQUEST)
    public static void handleCrossHunterShopRequest(Char chr, InPacket inPacket) {
        if (inPacket.getUnreadAmount() == 0) {
            // shop showing
            return;
        }

        short itemIndexInShop = inPacket.decodeShort();
        int itemId = inPacket.decodeInt();
        short itemQuantity = inPacket.decodeShort();

        int crusaderCoin = CRUSADER_COINS;
        List<Integer> coinCostList = Arrays.asList(50, 40, 60, 255, 170, 85, 170, 85, 10);
        List<Integer> itemList = Arrays.asList(VETERAN_CRUSADER_BELT, VETERAN_CRUSADER_SHOULDER, VETERAN_CRUSADER_PENDANT, MYSTIC_FACE_ACCESSORY, MYSTIC_EYE_ACCESSORY, MYSTIC_EARRINGS, MYSTIC_PENDANT, MYSTIC_BELT, SILENT_CRUSADE_TRANSTICKET);

        if (!itemList.contains(itemId)) {
            chr.getOffenseManager().addOffense(String.format("Character %d tried to trade an item {%d} that is not in the shop list.", chr.getId(), itemId));

        } else if (itemList.get(itemIndexInShop) != itemId) {
            chr.getOffenseManager().addOffense(String.format("Character %d tried to trade an item {%d} that is not in the given position {%d}.", chr.getId(), itemId, itemIndexInShop));

        } else if (ItemConstants.isEquip(itemId) && itemQuantity > 1) {
            chr.getOffenseManager().addOffense(String.format("Character %d tried to get a quantity {%d} that is more than 1 Silent Crusade equip {%d}.", chr.getId(), itemQuantity, itemId));

        } else if (itemIndexInShop >= itemList.size()) {
            chr.getOffenseManager().addOffense(String.format("Character %d tried to get an item from a shopIndex {%d} that is more than or equal to the amount of items in the shop {%d}.", chr.getId(), itemIndexInShop, itemList.size()));

        } else if (!chr.hasItemCount(crusaderCoin, (coinCostList.get(itemIndexInShop)) * itemQuantity)) {
            chr.chatMessage("You don't have enough Crusader Coins.");

        } else if (!chr.canHoldItem(itemId, 1)) {
            chr.chatMessage("You don't have any inventory space.");

        } else {
            chr.consumeItem(crusaderCoin, coinCostList.get(itemIndexInShop));
            chr.addItemToInventory(itemList.get(itemIndexInShop), 1);
        }
        chr.dispose();
    }

    @Handler(op = InHeader.UI_SCRIPT_REQUEST)
    public static void handleUiScriptRequest(Char chr, InPacket inPacket) {
        short typeVal = inPacket.decodeShort();
        int fieldID = inPacket.decodeInt();
        // No normal script: use a self-made one
        UiScript us = UiScript.getByVal(typeVal);
        if (us == null) {
            log.error("Unknown UiScript " + typeVal);
            return;
        }
        switch (us) {
            case UnionRaidStart:
                //chr.getScriptManager().startScript(9010106, "unionRaid_IN", ScriptType.Npc);
                chr.getScriptManager().createQuestWithQRValue(QuestConstants.UNION_MAP, "map=" + chr.getFieldID());
                if (chr.getUnionRaid() != null) {
                    chr.getUnionRaid().enter(chr);
                }
                break;
            case FamiliarHelp:

                chr.getScriptManager().startScript(MYSTERIOUS_AIDE_NPC, -1, "familiar_help", ScriptType.Npc);
                break;
            default:
                log.error("Unhandled UiScript " + us);
        }
    }

    @Handler(op = InHeader.GOLLUX_MINI_MAP_LEAVE_REQUEST)
    public static void handleGolluxMiniMapLeaveRequest(Char chr, InPacket inPacket) {
        // the mini map's Leave button: solo players too (a solo player in a Gollux map whose portals are still
        // closed has no other way out)
        if (chr.isTalkingToNpc()) {
            return;
        }
        chr.getScriptManager().startScript(0, "GiantBossQuit1", ScriptType.Portal);
    }
}
