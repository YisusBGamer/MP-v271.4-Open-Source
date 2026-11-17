package net.swordie.ms.handlers.life;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.familiar.FamiliarCodexUpdateMask;
import net.swordie.ms.client.character.familiar.FamiliarModifiedMask;
import net.swordie.ms.client.character.familiar.FamiliarPresets;
import net.swordie.ms.client.character.familiar.FamiliarModifiedType;
import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.packet.CFamiliar;
import net.swordie.ms.connection.packet.ShopDlg;
import net.swordie.ms.connection.packet.UserLocal;
import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.constants.ItemConstants;
import net.swordie.ms.enums.SpecStat;
import net.swordie.ms.handlers.Handler;
import net.swordie.ms.handlers.header.InHeader;
import net.swordie.ms.handlers.life.modules.FamiliarHandlerModule;
import net.swordie.ms.life.Familiar;
import net.swordie.ms.life.movement.MovementInfo;
import net.swordie.ms.loaders.ItemData;
import net.swordie.ms.loaders.NpcData;
import net.swordie.ms.loaders.containerclasses.ItemInfo;
import net.swordie.ms.scripts.ScriptType;
import net.swordie.ms.util.Position;
import net.swordie.ms.util.Rect;
import net.swordie.ms.util.Util;
import net.swordie.ms.world.shop.NpcShopDlg;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class FamiliarHandler {
    private static final int RORO_NPC = 9133404;

    private static final Logger log = LogManager.getLogger();


    @Handler(op = InHeader.FAMILIAR_ADD_REQUEST)
    public static void handleFamiliarAddRequest(Char chr, InPacket inPacket) {
        inPacket.decodeInt(); // tick
        short slot = inPacket.decodeShort();
        int itemID = inPacket.decodeInt();
        int amountConsumed = inPacket.decodeInt(); // Amount of Familiars used
        Item item = chr.getConsumeInventory().getItemBySlot(slot);

        if (item == null || item.getItemId() != itemID || !ItemConstants.isFamiliar(itemID) || !chr.hasItemCount(itemID, amountConsumed)) {
            chr.chatMessage("Could not find that item.");
            chr.getOffenseManager().addOffense(String.format("Character %d tried to add a familiar it doesn't have. (item id %d)", chr.getId(), itemID));
            chr.dispose();
            return;
        }

        var cardInfo = ItemData.getItemInfoByID(item.getItemId());
        if (cardInfo == null) {
            chr.dispose();
            return;
        }
        var familiarId = cardInfo.getFamiliarId();
        var grade = cardInfo.getGrade();
        // the codex only has room for its familiar slots (5 more per Familiar Slot Expansion Coupon)
        var fcm0 = chr.getFamiliarCodexManager();
        int room = fcm0.getFamiliarSlots() - fcm0.getFamiliars().size();
        if (room <= 0) {
            chr.chatMessage("Your Familiar slots are full. Extract familiars or use a Familiar Slot Expansion Coupon.");
            chr.dispose();
            return;
        }
        amountConsumed = Math.min(amountConsumed, room);
        if (familiarId == 0 && !ItemConstants.isFauxmiliarCard(itemID)) {
            chr.chatMessage("That card has no familiar.");
            chr.dispose();
            return;
        }

        List<Familiar> familiars = new ArrayList<>();
        for (int i = 0; i < amountConsumed; i++) {
            if (ItemConstants.isFauxmiliarCard(itemID)) {
                if (itemID == ItemConstants.SUSPICIOUS_FAUXMILIAR_FAMILIAR) {
                    familiarId = 9963000; // hardcode-ok
                } else {
                    familiarId = 9963001; // hardcode-ok
                }
            }

            if (familiarId == 0) {
                continue;
            }
            var familiar = new Familiar(familiarId, "", chr, grade);
            chr.addFamiliar(familiar);
            familiars.add(familiar);
        }
        chr.consumeItem(item, amountConsumed);
        fcm0.ensureSummonSkill(); // GMS: the Summon Familiars skill appears with the first familiar
        fcm0.updateForClient(FamiliarCodexUpdateMask.FamiliarCount.getVal());
        chr.write(UserLocal.familiarAddResult(familiars));
        chr.write(UserLocal.familiarBadgeAddResult(chr));
    }

    @Handler(op = InHeader.FAMILIAR_SUMMON_REQUEST)
    public static void handleFamiliarSummonRequest(Char chr, InPacket inPacket) {
        boolean summon = inPacket.decodeByte() != 0; // summon | remove

        if (summon) {
            chr.summonFamiliars();
            // GMS starts the skill's 1 s cooldown (Skill.wz) on every summon
            chr.setSkillCooldown(GameConstants.SUMMON_FAMILIARS_SKILL, 1);
        } else {
            chr.banishFamiliars();
        }
    }

    @Handler(op = InHeader.FAMILIAR_CODEX_MODIFY_REQUEST)
    public static void handleFamiliarCodexModifyRequest(Char chr, InPacket inPacket) {
        var fcm = chr.getFamiliarCodexManager();
        var mask = inPacket.decodeInt();

        if ((mask & FamiliarCodexUpdateMask.SummonGauge.getVal()) != 0) {
            // keep the Summon Gauge full: top up whatever the client drained
            inPacket.decodeShort();
            fcm.setSummonGauge(net.swordie.ms.constants.GameConstants.MAX_SUMMON_GAUGE);
            fcm.updateForClient(FamiliarCodexUpdateMask.SummonGauge.getVal());
        }
        if ((mask & FamiliarCodexUpdateMask.FamiliarSlots.getVal()) != 0) {
            inPacket.decodeShort(); // familiar slots
        }
        if ((mask & FamiliarCodexUpdateMask.FamiliarCount.getVal()) != 0) {
            inPacket.decodeInt(); // familiar count
        }
        if ((mask & FamiliarCodexUpdateMask.Unk8.getVal()) != 0) {
            inPacket.decodeShort(); // unk
        }
        if ((mask & FamiliarCodexUpdateMask.Unk10.getVal()) != 0) {
            inPacket.decodeLong(); // decode Buffer8  not sure about structure
        }
        if ((mask & FamiliarCodexUpdateMask.ModifySetup.getVal()) != 0) {
            if (fcm.isFamiliarsSummoned()) {
                fcm.banishFamiliars();
            }

            int[] activatedFamiliars = new int[GameConstants.ACTIVE_FAMILIARS_SLOTS];
            for (int i = 0; i < GameConstants.ACTIVE_FAMILIARS_SLOTS; i++) {
                activatedFamiliars[i] = inPacket.decodeInt(); // Familiar Unique Id
            }
            fcm.setActiveFamiliars(activatedFamiliars);
            FamiliarPresets.saveFamiliars(chr, activatedFamiliars);
            // v265: the badges come only with their own mask bit
            fcm.updateForClient(FamiliarCodexUpdateMask.ModifySetup.getVal());
        }
        if ((mask & FamiliarCodexUpdateMask.ModifyEquippedBadges.getVal()) != 0) {
            int[] equippedBadges = new int[GameConstants.BADGE_SLOTS];
            for (int i = 0; i < GameConstants.BADGE_SLOTS; i++) {
                equippedBadges[i] = inPacket.decodeByte(); // -1  =  empty slot
            }
            equippedBadges = fcm.filterBadges(equippedBadges);
            fcm.setActiveBadges(equippedBadges);
            FamiliarPresets.saveBadges(chr, equippedBadges);
            fcm.initQuestsForSlots();
            fcm.updateForClient(FamiliarCodexUpdateMask.ModifyEquippedBadges.getVal());
        }
        // v265 (encoder 0x140661d70): 0x80 = familiar preset byte, 0x100 = badge preset byte (sender 0x145bb68b0);
        // 0x400 = after the record, byte badge preset + 8 badge slots (0x145b81bd0 / 0x145bbf230)
        if ((mask & FamiliarCodexUpdateMask.ModifyFamiliarPreset.getVal()) != 0 && inPacket.getUnreadAmount() >= 1) {
            if (FamiliarPresets.selectFamiliarPreset(chr, inPacket.decodeByte())) {
                fcm.updateForClient(FamiliarCodexUpdateMask.ModifySetup.getVal()
                        | FamiliarCodexUpdateMask.ModifyFamiliarPreset.getVal());
            }
        }
        if ((mask & FamiliarCodexUpdateMask.ModifyBadgePreset.getVal()) != 0 && inPacket.getUnreadAmount() >= 1) {
            if (FamiliarPresets.selectBadgePreset(chr, inPacket.decodeByte())) {
                fcm.setActiveBadges(fcm.filterBadges(fcm.getActiveBadges()));
                fcm.updateForClient(FamiliarCodexUpdateMask.ModifyEquippedBadges.getVal()
                        | FamiliarCodexUpdateMask.ModifyBadgePreset.getVal());
            }
        }
        if ((mask & 0x400) != 0 && inPacket.getUnreadAmount() >= 1 + GameConstants.BADGE_SLOTS) {
            int preset = inPacket.decodeByte();
            int[] badges = new int[GameConstants.BADGE_SLOTS];
            for (int i = 0; i < GameConstants.BADGE_SLOTS; i++) {
                badges[i] = inPacket.decodeByte();
            }
            badges = fcm.filterBadges(badges);
            FamiliarPresets.setBadgePreset(chr, preset, badges);
            if (preset == FamiliarPresets.getBadgePreset(chr)) {
                fcm.initQuestsForSlots();
                fcm.updateForClient(FamiliarCodexUpdateMask.ModifyEquippedBadges.getVal());
            }
        }
    }

    @Handler(op = InHeader.FAMILIAR_EDIT_REQUEST)
    public static void handleFamiliarEditRequest(Char chr, InPacket inPacket) {
        var type = inPacket.decodeByte();
        var famModifiedRequest = FamiliarModifiedType.byVal(type);

        if (type == 12 || type == 13 || type == 14) {
            handlePresetEdit(chr, type, inPacket);
            return;
        }
        if (type == 15) {
            // Mystic Frontier (the familiar expedition board: dice, Action Points, weekly expeditions), 17 sub-types
            // logged so the board's flow can be learned; answered as failed (MODIFY_RESULT type 15: sub, success 0)
            // so the window doesn't wait
            int sub = inPacket.getUnreadAmount() > 0 ? inPacket.decodeByte() : -1;
            // sub 13 (int n, n x SN) reveals potentials like type 5; GMS answers it with the familiars and 5 / 1
            final int revealSub = 13;
            if (sub == revealSub && inPacket.getUnreadAmount() >= 4) {
                handleRelease(chr, inPacket.decodeInt(), inPacket);
                return;
            }
            byte[] body = inPacket.decodeArr(inPacket.getUnreadAmount());
            StringBuilder hex = new StringBuilder();
            for (byte b : body) {
                hex.append(String.format("%02X ", b));
            }
            log.info(String.format("[MysticFrontier] %s sub %d body %s", chr.getName(), sub, hex.toString().trim()));
            net.swordie.ms.connection.OutPacket res = new net.swordie.ms.connection.OutPacket(
                    net.swordie.ms.handlers.header.OutHeader.FAMILIAR_MODIFY_RESULT);
            res.encodeByte(15);
            res.encodeByte(sub);
            res.encodeByte(0);
            chr.write(res);
            chr.chatMessage("Mystic Frontier isn't available yet.");
            chr.dispose();
            return;
        }
        if (famModifiedRequest == null) {
            chr.chatMessage("Unknown familiar request.");
            log.debug("Unknown familiar request: " + type);
            return;
        }

        int familiarId = inPacket.decodeInt();

        switch (famModifiedRequest) {
            case Extract:
                FamiliarHandlerModule.handleFamiliarExtract(chr, familiarId, inPacket);
                break;
            case Fuse:
                FamiliarHandlerModule.handleFamiliarFuse(chr, familiarId, inPacket);
                break;
            case Upgrade:
                FamiliarHandlerModule.handleFamiliarUpgrade(chr, familiarId, inPacket);
                break;
            case ToggleLock:
                FamiliarHandlerModule.handleFamiliarToggleLock(chr, familiarId, inPacket);
                break;
            case Rename:
                FamiliarHandlerModule.handleFamiliarRename(chr, familiarId, inPacket);
                break;
            case ReleaseOption:
                // senders 0x145b5e0c0 / 0x145b62ae0: byte 5, int count, count x int SN; the client asks
                // count x 35,000 mesos (string 0x457a) to reveal hidden lines
                handleRelease(chr, familiarId, inPacket);
                break;
            default:
                chr.chatMessage("Unhandled familiar request.");
                log.debug("Unhandled familiar request: " + famModifiedRequest);
                break;
        }
    }

    /**
     * v271 preset edits (they replace v265's codex modify request; one preset index for familiars and badges):
     * 12 = 4 preset (select), 13 = 4 preset, 3 x 4 familiar SN (that preset's familiars), 14 = 1 slot, 4 badge
     * (255 = none) in the current preset. Answered with CODEX_UPDATE.
     */
    private static void handlePresetEdit(Char chr, int type, InPacket inPacket) {
        var fcm = chr.getFamiliarCodexManager();
        int len = inPacket.getUnreadAmount();
        switch (type) {
            case 12 -> {
                int preset = inPacket.decodeInt();
                FamiliarPresets.selectFamiliarPreset(chr, preset);
                if (FamiliarPresets.selectBadgePreset(chr, preset)) {
                    fcm.setActiveBadges(fcm.filterBadges(fcm.getActiveBadges()));
                }
            }
            case 13 -> {
                int preset = inPacket.decodeInt();
                int[] fams = new int[GameConstants.ACTIVE_FAMILIARS_SLOTS];
                for (int i = 0; i < fams.length && inPacket.getUnreadAmount() >= 4; i++) {
                    int sn = inPacket.decodeInt();
                    fams[i] = sn != 0 && fcm.getFamiliarByID(sn) != null ? sn : 0;
                }
                if (preset == FamiliarPresets.getFamiliarPreset(chr)) {
                    if (fcm.isFamiliarsSummoned()) {
                        fcm.banishFamiliars();
                    }
                    fcm.setActiveFamiliars(fams);
                    FamiliarPresets.saveFamiliars(chr, fams);
                } else {
                    FamiliarPresets.setFamiliarPreset(chr, preset, fams);
                }
            }
            case 14 -> {
                int slot = inPacket.decodeByte();
                int badge = inPacket.decodeInt();
                int[] badges = fcm.getActiveBadges().clone();
                if (slot >= 0 && slot < badges.length) {
                    badges[slot] = badge == 255 || badge < 0 ? -1 : badge;
                }
                badges = fcm.filterBadges(badges);
                fcm.setActiveBadges(badges);
                FamiliarPresets.saveBadges(chr, badges);
                fcm.initQuestsForSlots();
            }
        }
        log.debug(String.format("[Familiar] %s preset edit %d (%d bytes)", chr.getName(), type, len));
        fcm.updateForClient(FamiliarCodexUpdateMask.All.getVal());
    }

    private static final long RELEASE_COST = 35_000;

    private static void handleRelease(Char chr, int count, InPacket inPacket) {
        List<Familiar> asked = new ArrayList<>();
        List<Familiar> release = new ArrayList<>();
        for (int i = 0; i < count && inPacket.getUnreadAmount() >= 4; i++) {
            Familiar f = chr.getFamiliarByID(inPacket.decodeInt());
            if (f == null || asked.contains(f)) {
                continue;
            }
            asked.add(f);
            if (!f.hasReleasedPotential()) {
                release.add(f);
            }
        }
        long cost = RELEASE_COST * release.size();
        if (release.isEmpty() && !asked.isEmpty()) {
            // all already revealed: GMS still answers with the familiars and a success
            chr.write(UserLocal.familiarAddResult(asked,
                    FamiliarModifiedMask.PotLines0.getVal() | FamiliarModifiedMask.PotLines1.getVal()));
            chr.write(UserLocal.familiarModifyResult(FamiliarModifiedType.ReleaseOption, true));
            return;
        }
        if (release.isEmpty() || chr.getMoney() < cost) {
            if (!release.isEmpty()) {
                chr.chatMessage(String.format("Revealing the potential costs %,d mesos.", cost));
            }
            chr.write(UserLocal.familiarModifyResult(FamiliarModifiedType.ReleaseOption, false));
            chr.dispose();
            return;
        }
        chr.deductMoney(cost);
        for (Familiar f : release) {
            f.rollPotentialLines();
        }
        chr.write(UserLocal.familiarAddResult(release,
                FamiliarModifiedMask.PotLines0.getVal() | FamiliarModifiedMask.PotLines1.getVal()));
        chr.write(UserLocal.familiarModifyResult(FamiliarModifiedType.ReleaseOption, true));
    }

    @Handler(op = InHeader.FAMILIAR_SHOP_OPEN_REQUEST)
    public static void handleFamiliarShopOpenRequest(Char chr, InPacket inPacket) {
        if(chr == null || !chr.isOnline()  || chr.getUser() == null) {
            log.error(String.format("[CharId: %d] tried to open familiar shop while being offline.", chr.getId()));
            return;
        }
        var roro = RORO_NPC;

        NpcShopDlg nsd = NpcData.getShopById(roro);
        chr.getScriptManager().stop(ScriptType.Npc); // reset contents before opening shop?
        if (chr.getShop() == null) {
            chr.setShop(nsd);
            chr.write(ShopDlg.openShop(chr, 0, nsd));
        }
    }


    @Handler(op = InHeader.FAMILIAR_MOVE)
    public static void handleFamiliarMove(Char chr, InPacket inPacket) {
        byte idx = inPacket.decodeByte(); // ?
        var familiarTemplateId = inPacket.decodeInt(); // familiar id
        var fcm = chr.getFamiliarCodexManager();
        if (fcm == null || fcm.getCurrentActiveFamiliars() == null || idx >= fcm.getCurrentActiveFamiliars().size()) {
            return;
        }
        Familiar f = chr.getFamiliarCodexManager().getFamiliarByIdx(idx);
        if (f != null && Util.arrayContains(chr.getFamiliarCodexManager().getActiveFamiliars(), (int) f.getId()) && chr.getFamiliarCodexManager().isFamiliarsSummoned()) {
            MovementInfo movementInfo = new MovementInfo(inPacket);
            movementInfo.applyTo(f);
            chr.getField().broadcastPacket(CFamiliar.familiarMove(chr.getId(), f.getIdx(), movementInfo), chr);
        }
    }


    @Handler(op = InHeader.FAMILIAR_SKILL)
    public static void handleFamiliarSkill(Char chr, InPacket inPacket) {
        inPacket.decodeByte();
        int familiarID = inPacket.decodeInt();
        inPacket.decodeByte(); // unk
        inPacket.decodeInt(); // tick

        int familiarBuffItemId = inPacket.decodeInt();

        Familiar f = chr.getFamiliarCodexManager().getFamiliarByTemplateId(familiarID);
        if (f == null) {
            return;
        }

        Item item = ItemData.getItemDeepCopy(familiarBuffItemId);
        if (item == null) {
            return;
        }

        ItemInfo ii = ItemData.getItemInfoByID(familiarBuffItemId);
        if (ii == null) {
            return;
        }

        if (!f.checkFamiliarCon(ii)) {
            return;
        }

        var targetType = ii.getSpecStats().getOrDefault(SpecStat.familiarPassiveSkillTarget, 0);
        switch (targetType) {
            case 0: // Self
                chr.useStatChangeItem(item, false);
                break;

            case 1: // Party Members
                var p = chr.getParty();
                if (p != null) {
                    for (Char pChr : p.getPartyMembersInSameField(chr)) { // Excl chr
                        pChr.useStatChangeItem(item, false);
                    }
                }
                chr.useStatChangeItem(item, false);
                break;

            case 2: // Nearby Players
                Position position = f.getPosition();
                Rect rect = position.getRectAround(GameConstants.FAMILIAR_AFFECT_NEARBY_ALLIES_RECT);
                for (Char fChr : chr.getField().getCharsInRect(rect)) {
                    fChr.useStatChangeItem(item, false);
                }
                break;
        }
    }
}
