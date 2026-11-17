package net.swordie.ms.client.character;

import net.swordie.ms.client.jobs.adventurer.pirate.Corsair;
import net.swordie.ms.client.jobs.adventurer.thief.BladeMaster;
import net.swordie.ms.client.jobs.resistance.WildHunter;
import net.swordie.ms.client.Account;
import net.swordie.ms.client.LinkSkill;
import net.swordie.ms.client.character.avatar.AvatarLook;
import net.swordie.ms.client.character.damage.DamageSkinSaveData;
import net.swordie.ms.client.character.emoticons.Emoticon;
import net.swordie.ms.client.character.emoticons.EmoticonModule;
import net.swordie.ms.client.character.emoticons.EmoticonShortcut;
import net.swordie.ms.client.character.familiar.FamiliarCodexManager;
import net.swordie.ms.client.character.familiar.FamiliarModifiedMask;
import net.swordie.ms.client.character.info.FarmUserInfo;
import net.swordie.ms.client.character.info.ZeroInfoFlag;
import net.swordie.ms.client.character.items.*;
import net.swordie.ms.client.character.modules.BagItemModule;
import net.swordie.ms.client.character.modules.LinkSkillsModule;
import net.swordie.ms.client.character.potential.CharacterPotential;
import net.swordie.ms.client.character.quest.Quest;
import net.swordie.ms.client.character.quest.QuestManager;
import net.swordie.ms.client.character.skills.ChosenSkill;
import net.swordie.ms.client.character.skills.CoreAura;
import net.swordie.ms.client.character.skills.Skill;
import net.swordie.ms.client.character.skills.StolenSkill;
import net.swordie.ms.client.character.skills.vmatrix.MatrixRecord;
import net.swordie.ms.client.character.hexa.HexaModule;
import net.swordie.ms.client.character.union.UnionArtifactModule;
import net.swordie.ms.client.character.skills.vmatrix.MatrixSlot;
import net.swordie.ms.client.friend.FriendRecord;
import net.swordie.ms.client.jobs.resistance.WildHunterInfo;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.constants.JobConstants;
import net.swordie.ms.constants.QuestConstants;
import net.swordie.ms.constants.SkillConstants;
import net.swordie.ms.enums.DBChar;
import net.swordie.ms.enums.InvType;
import net.swordie.ms.life.android.Android;
import net.swordie.ms.life.Familiar;
import net.swordie.ms.connection.packet.UserLocal;
import net.swordie.ms.client.character.familiar.FamiliarPresets;
import net.swordie.ms.loaders.QuestData;
import net.swordie.ms.util.FileTime;
import net.swordie.ms.util.Util;

import java.util.*;
import java.util.stream.Collectors;

public class CharEncoder {

    /**
     * Encodes this Char's info inside a given {@link OutPacket}, with given info (CharacterData::Decode, v265 order).
     *
     * @param outPacket The OutPacket this method should encode to.
     * @param mask      Which info should be encoded.
     */
    /** v271 CharacterData selector bytes (mask[n] = 1 selects Fn; client decoder 0x1407c9950). */
    private static final int V271_F_CHARACTER = 0;
    private static final int V271_F_MONEY = 1;
    private static final int V271_F_EQUIP = 2;
    private static final int V271_F_CONSUME = 3;
    private static final int V271_F_INSTALL = 4;
    private static final int V271_F_ETC = 5;
    private static final int V271_F_CASH = 6;
    private static final int V271_F_INVENTORY_SIZE = 7;
    private static final int V271_F_DECORATION = 44;
    private static final int V271_F_INVENTORY_STATE = 64;
    private static final int V271_F_SKILL = 8;
    private static final int V271_F_QUEST = 9;
    private static final int V271_F_QUEST_COMPLETE = 14;
    private static final int V271_F_SKILL_COOLTIME = 15;
    private static final int V271_F_QUEST_EX = 16;
    private static final int V271_F_WORLD_SHARE = 19;
    private static final int V271_F_KEYED_RECORDS = 37; // client selector 0x25: keyed-record list -> CUser+0x125F
    private static final int V271_F_LINK_OWNER = 73;
    private static final int V271_F_LINK_SOURCES = 71;
    private static final int V271_F_LINK_LEVELS = 74;
    private static final int V271_F_LINK_PRESETS = 72;
    private static final int V271_F_VMATRIX = 50;
    private static final int V271_F_HEXA_SKILLS = 59;
    private static final int V271_F_HEXA_STATS = 60;
    private static final int V271_F_UNION_ARTIFACT = 63;
    private static final int V271_F_F0_TIME = 62;
    private static final int V271_F_DAMAGE_SKIN_SLOTS = 66;
    private static final int V271_F_DAMAGE_SKINS = 67;
    private static final int V271_F_FAMILIAR = 85;
    /** Hair, face, skin containers; client selectors 0x1489c0900 / 0x1489c0970 / 0x1489c09e0. */
    private static final int[] V271_MANNEQUIN_MASKS = {54, 55, 57};
    private static final int V271_F_EMOTICON = 56; // selector 0x1489c0a50, read after the mannequin sections
    /** Salon slot counts; selector 0x1489c0890 (init 0x14003e353 sets flag 0x58), reader 0x1407a6ff0. */
    private static final int V271_F_SALON = 88;
    private static final int V271_F_ABILITY = 28; // selector 0x1489bf2b0: Inner Ability presets (0x140788ce0)
    private static final int V271_F_HONOR = 29; // selector 0x1489c0040: honor level, honor EXP

    /**
     * The v271 CharacterData with F0 (character), F1 (money), F7 (inventory sizes) and, unless
     * test.fieldItems=false, the item sections (F2-F6, F44, F64). No skills, quests or other sections. Every read the
     * v271 full decoder (0x1407c9950, mode 0) does for this selection, in order:
     * B100 mask; the unconditional prefix (1, 4 4 4, 1, 1 n, 4 n, 1 lists: unchanged from v265);
     * F0: CharacterStat (0x1407cf030), three (1 has, Str) gates (v265's buddy byte before them is gone);
     * F1: 1 (new), 8 money;
     * F3: 4 n {4 4 4 8} (exp consume items), 4 n {4 4 4 4 4 B8 B8} (potion pot);
     * F7: one 4 per inventory (equip, use, setup, etc, cash, decoration), read inside the category loop;
     * F64: 1 1 1; F2: equips (encodeEquips: 1, slot lists of 0x1407d0a70 items, 12 range lists); F4: 2 lists;
     * F44: 1, cash equipped, decoration, 6 range lists (range table 0x14729aaf0, same as v265);
     * F3/F4/F5/F6: slot lists (2 slot, item; 0 ends); F3/F4/F5: bag data (4 n, same as v265);
     * F0 again: 0x140799590 = 6 x 4, 6 x 1, 6 x 4 (skill alarms), then a 4 (new in v271);
     * unconditional: 1 (gates F78's list), 1 (gates F37's list; the client stores it at CUser+0x125B) + 4 n,
     * n x (4, Str) read only when the mask selects F37 (sent empty; record 15 arrives as a MESSAGE type 31 into
     * CUser+0x125F, the map the Daily Gift gate reads); F28/F29 (ability); F19 world-share records = 2 n, n x (4, Str);
     * then unconditional 0x14077da40 = 4 n, n x (4, Str);
     * F5: 0x140798d60 = 4 n (records changed from v265; sent empty).
     * Mannequin containers F54/F55/F57 (0x140781980 = 1 shown, 1 limit, 48 x (1 has, [1 flag, 4 style, 4 extra])),
     * then F56 emoticons (0x1407d0e30). The client opens the beauty salon list from these three containers.
     * Skills (test.fieldSkills), after the bag data: F8 skill records + hyper stats, F73/F71/F74/F72 link skills
     * (LinkSkillsModule.encode, in that order), F15 cooltimes. Quests (test.fieldQuests): F9 records and F14
     * completed after the skill alarms, F16 quest ex before the F78 gate. Same reads as v265 except the master level
     * condition (see needsMasterLevelV271).
     */
    public static void encodeV271Minimal(Char chr, OutPacket outPacket) {
        boolean items = net.swordie.ms.ServerSettings.getBoolean("test.fieldItems", true);
        boolean skills = net.swordie.ms.ServerSettings.getBoolean("test.fieldSkills", true);
        boolean quests = net.swordie.ms.ServerSettings.getBoolean("test.fieldQuests", true);
        boolean endgame = net.swordie.ms.ServerSettings.getBoolean("test.fieldEndgame", true);
        boolean damageSkins = net.swordie.ms.ServerSettings.getBoolean("test.fieldDamageSkins", true);
        boolean familiars = net.swordie.ms.ServerSettings.getBoolean("test.fieldFamiliars", true);
        boolean ability = net.swordie.ms.ServerSettings.getBoolean("test.fieldAbility", true);
        int start = outPacket.getLength();
        byte[] mask = new byte[100];
        mask[V271_F_CHARACTER] = 1;
        mask[V271_F_MONEY] = 1;
        mask[V271_F_INVENTORY_SIZE] = 1;
        mask[V271_F_WORLD_SHARE] = 1;
        mask[V271_F_KEYED_RECORDS] = 1;
        mask[V271_F_EMOTICON] = 1;
        mask[V271_F_SALON] = 1;
        for (int f : V271_MANNEQUIN_MASKS) {
            mask[f] = 1;
        }
        if (items) {
            for (int f : new int[]{V271_F_EQUIP, V271_F_CONSUME, V271_F_INSTALL, V271_F_ETC, V271_F_CASH,
                    V271_F_DECORATION, V271_F_INVENTORY_STATE}) {
                mask[f] = 1;
            }
        }
        if (skills) {
            for (int f : new int[]{V271_F_SKILL, V271_F_LINK_OWNER, V271_F_LINK_SOURCES, V271_F_LINK_LEVELS,
                    V271_F_LINK_PRESETS, V271_F_SKILL_COOLTIME}) {
                mask[f] = 1;
            }
        }
        if (quests) {
            mask[V271_F_QUEST] = 1;
            mask[V271_F_QUEST_COMPLETE] = 1;
            mask[V271_F_QUEST_EX] = 1;
        }
        if (endgame) {
            for (int f : new int[]{V271_F_VMATRIX, V271_F_HEXA_SKILLS, V271_F_HEXA_STATS, V271_F_UNION_ARTIFACT}) {
                mask[f] = 1;
            }
        }
        if (damageSkins) {
            mask[V271_F_F0_TIME] = 1;
            mask[V271_F_DAMAGE_SKIN_SLOTS] = 1;
            mask[V271_F_DAMAGE_SKINS] = 1;
        }
        if (familiars) {
            mask[V271_F_FAMILIAR] = 1;
        }
        if (ability) {
            mask[V271_F_ABILITY] = 1;
            mask[V271_F_HONOR] = 1;
        }
        outPacket.encodeArr(mask);

        outPacket.encodeByte(chr.getCombatOrders());
        for (int i = 0; i < GameConstants.MAX_PET_AMOUNT; i++) {
            outPacket.encodeInt(-2); // pet skill cool times (stored as deadlines)
        }
        outPacket.encodeByte(0);
        outPacket.encodeByte(0); // int list
        outPacket.encodeInt(0); // (int, 8 bytes) list
        outPacket.encodeByte(0); // no (8 bytes) lists

        // F0
        int statStart = outPacket.getLength();
        chr.getAvatarData().getCharacterStat().encode(outPacket, chr.getAvatarData().getAvatarLook());
        int statEnd = outPacket.getLength();
        String fairy = chr.getBlessingOfFairy();
        outPacket.encodeByte(fairy != null);
        if (fairy != null) {
            outPacket.encodeString(fairy);
        }
        String empress = chr.getBlessingOfEmpress();
        outPacket.encodeByte(empress != null);
        if (empress != null) {
            outPacket.encodeString(empress);
        }
        outPacket.encodeByte(false);

        if (damageSkins) {
            // F62 0x1407cf5f0: 2, B8 (v265 sent these after F0 unconditionally); F66 0x1407891a0: slot count;
            // F67 0x140789250: saved skins (4 n, n x (4 4 B8)), then the active / premium / third record
            outPacket.encodeShort(0);
            outPacket.encodeFT(FileTime.MIN_TIME());
            encodeDamageSkins(chr, outPacket);
        }

        // F1
        outPacket.encodeByte(0); // new in v271, read as a bool before the money; meaning unknown
        outPacket.encodeLong(chr.getMoney());

        if (items) {
            outPacket.encodeInt(chr.getExpConsumeItems().size());
            for (ExpConsumeItem eci : chr.getExpConsumeItems()) {
                eci.encode(outPacket);
            }
            PotionPot.encodeList(outPacket, chr);
        }

        // F7
        outPacket.encodeInt(chr.getEquipInventory().getSlots());
        outPacket.encodeInt(chr.getConsumeInventory().getSlots());
        outPacket.encodeInt(chr.getInstallInventory().getSlots());
        outPacket.encodeInt(chr.getEtcInventory().getSlots());
        outPacket.encodeInt(chr.getCashInventory().getSlots());
        outPacket.encodeInt(chr.getDecInventory().getSlots());

        if (items) {
            // F64: applied equipment preset, 0, applied link skill preset
            outPacket.encodeByte(EquipPresetModule.appliedForEncode(chr));
            outPacket.encodeByte(0);
            outPacket.encodeByte(LinkSkillsModule.appliedPreset(chr));
            encodeEquips(chr, outPacket); // F2, F4, F44
            encodeBundleItems(outPacket, chr.getConsumeInventory().getItems());
            encodeBundleItems(outPacket, chr.getInstallInventory().getItems());
            encodeBundleItems(outPacket, chr.getEtcInventory().getItems());
            for (Item item : chr.getCashInventory().getItems()) {
                outPacket.encodeShort(item.getBagIndex());
                item.encode(outPacket);
            }
            outPacket.encodeShort(0);
            for (int i = 2; i <= 4; i++) {
                encodeBagData(chr, outPacket, InvType.getInvTypeByVal(i));
            }
        }

        if (endgame) {
            // V points + one-time core levels from the node system, before the skill records are written
            net.swordie.ms.client.character.skills.vmatrix.V271Matrix.prepare(chr);
        }
        if (skills) {
            encodeSkillsV271(chr, outPacket);
        }

        // F0, second part: skill alarms (6 skill ids, 6 enabled flags, 6 keys), then the new v271 int
        for (int i = 0; i < 6; i++) {
            outPacket.encodeInt(0);
        }
        for (int i = 0; i < 6; i++) {
            outPacket.encodeByte(0);
        }
        for (int i = 0; i < 6; i++) {
            outPacket.encodeInt(-1);
        }
        outPacket.encodeInt(-1); // client +0x1554: restricted skill set id, -1 = none (0 hides every skill level)

        if (quests && endgame) {
            net.swordie.ms.client.character.skills.vmatrix.V271Matrix.ensurePoints(chr); // quest ex 1477 in F16
        }
        if (quests) {
            encodeQuestsV271(chr, outPacket, familiars);
        } else if (familiars) {
            encodeFamiliarsV271(chr, outPacket); // F85 (between F14 and F16)
        }

        outPacket.encodeByte(0); // F78: (int, Str) list follows only when set
        // F37 (0x1407cd5bc): the client stores this byte at CUser+0x125B and returns 0 out of the Daily Gift gate
        // (0x141E52F10) while it is 0. The F37 list that follows fills CUser+0x125F; it is sent empty, record 15
        // arrives as a MESSAGE NX_RECORD_MESSAGE (type 31) right after login.
        outPacket.encodeByte(1);
        outPacket.encodeInt(0);
        if (ability) {
            // F28 then F29 (F45 soul collection between them is unset); the client keeps the Ability button
            // disabled without these
            chr.getPotentialMan().fillPresets();
            net.swordie.ms.connection.packet.WvsContext.encodePotentialPresets(outPacket, chr);
            outPacket.encodeInt(1); // honor level
            outPacket.encodeInt(chr.getHonorExp());
        }
        encodeWorldShareRecords(chr, outPacket); // F19, read after F29
        outPacket.encodeInt(0); // (int, Str) list, always read

        if (endgame) {
            // after F49 (unset): F50 V matrix cores, F59 HEXA skills, F60 HEXA stats (both read as v265), F87 Erda
            // Link (unset), F63 Union Artifacts + int list (as v265)
            net.swordie.ms.client.character.skills.vmatrix.V271Matrix.encode(chr, outPacket);
            HexaModule.encodeSkills(chr, outPacket);
            HexaModule.encodeStats(chr, outPacket);
            UnionArtifactModule.encode(chr, outPacket);
            outPacket.encodeInt(0);
        }

        if (items) {
            outPacket.encodeInt(0); // F5 list
        }

        // F88 (selector 0x1489c0890): hair, face, skin slot counts the Beauty Room pages through
        for (int i = 0; i < MannequinModule.TYPES; i++) {
            MannequinModule.encodeSalonData(outPacket, i);
        }

        // F54/F55/F57, in that order (hair, face, skin): two bytes + 48 records, see MannequinModule
        for (int i = 0; i < MannequinModule.TYPES; i++) {
            MannequinModule.encode(chr, outPacket, i);
        }

        // F56: emoticons, tabs, slots
        net.swordie.ms.client.character.emoticons.EmoticonModule.encode(chr, outPacket);

        if (net.swordie.ms.ServerSettings.getBoolean("test.fieldGate", false)) {
            dumpV271(chr, outPacket.getData(), start, statStart - start, statEnd - statStart);
        }
    }

    /**
     * F8: 1 (1 = replace), 2 n {4 id, 4 level, B8 expire, [4 master level]}, 1 hyper stat preset, 3 x (4 n, n x 4 4 4);
     * F73/F71/F74/F72: link skills; F15: 2 n {4 id, 4 seconds left}.
     */
    private static void encodeSkillsV271(Char chr, OutPacket outPacket) {
        outPacket.encodeByte(true);
        List<Skill> skills = chr.getSkills().stream().filter(s -> !SkillConstants.isUnionSkill(s.getSkillId()))
                .sorted(Comparator.comparingInt(Skill::getSkillId)).collect(Collectors.toList());
        outPacket.encodeShort(skills.size());
        for (Skill skill : skills) {
            outPacket.encodeInt(skill.getSkillId());
            outPacket.encodeInt(skill.getCurrentLevel());
            outPacket.encodeFT(FileTime.fromType(FileTime.Type.MAX_TIME));
            if (needsMasterLevelV271(skill.getSkillId())) {
                outPacket.encodeInt(skill.getMasterLevel());
            }
        }
        var hyperStats = chr.getHyperStatsManager();
        outPacket.encodeByte(hyperStats.getCurrentPreset());
        hyperStats.encode(outPacket);

        LinkSkillsModule.encode(chr, outPacket);

        long now = System.currentTimeMillis();
        Map<Integer, Long> cooltimes = new HashMap<>();
        chr.getSkillCoolTimes().forEach((skillId, time) -> {
            if (time - now > 0) {
                cooltimes.put(skillId, time);
            }
        });
        outPacket.encodeShort(cooltimes.size());
        cooltimes.forEach((skillId, time) -> {
            outPacket.encodeInt(skillId);
            outPacket.encodeInt((int) ((time - now) / 1000));
        });
    }

    /**
     * The v271 client's master-level test (0x1407c53c0), as the v265 one (SkillConstants.isSkillNeedMasterLevel) plus
     * its changes: every Beast Tamer 4th job skill (11212xxxx), 4320005 and 33120010 have one; 33120012 and 5221029
     * don't. Checked by running both clients' functions over every Skill.wz id.
     */
    static boolean needsMasterLevelV271(int skillId) {
        switch (skillId) {
            case BladeMaster.VENOM_DB:
            case WildHunter.WILD_INSTINCT:
                return true;
            case WildHunter.WILD_INSTINCT_2:
            case Corsair.FIRING_ORDERS:
                return false;
        }
        if (skillId / 10000 == 11212) {
            return true;
        }
        return SkillConstants.isSkillNeedMasterLevel(skillId);
    }

    /**
     * F9: 1 (1 = replace), 2 n {4 id, Str value}, 2 n {4, 8-byte value} (sent empty), 2 n {Str, Str} (sent empty);
     * F14: 1 (1 = replace), 2 n {4 id, B8 time}; F16: 2 n {4 id, Str}. Same as v265.
     */
    private static void encodeQuestsV271(Char chr, OutPacket outPacket, boolean familiars) {
        QuestManager qm = chr.getQuestManager();
        outPacket.encodeByte(true);
        var inProgress = qm.getQuestsInProgress();
        outPacket.encodeShort(inProgress.size());
        for (Quest quest : inProgress) {
            outPacket.encodeInt(quest.getQRKey());
            outPacket.encodeString(quest.getQRValue());
        }
        outPacket.encodeShort(0);
        outPacket.encodeShort(0);

        outPacket.encodeByte(true);
        Set<Quest> completedQuests = qm.getCompletedQuests();
        List<Integer> bossUiQuests = BossUiAccess.getQuestsToShowCompleted(chr);
        outPacket.encodeShort(completedQuests.size() + bossUiQuests.size());
        for (Quest quest : completedQuests) {
            outPacket.encodeInt(quest.getQRKey());
            outPacket.encodeFT(quest.getCompletedTime());
        }
        for (int questId : bossUiQuests) {
            outPacket.encodeInt(questId);
            outPacket.encodeFT(FileTime.currentTime());
        }

        if (familiars) {
            encodeFamiliarsV271(chr, outPacket); // F85 is read after F14 (F10-F12 unselected) and before F16
        }

        List<Integer> missingBossRecords = BossUiAccess.getMissingRecords(chr);
        // the boss window only updates a clear record it got here, so the ones without a clear go out empty
        List<Integer> missingClearRecords = BossClearRecords.getMissingRecords(chr);
        var exRecords = qm.getEx();
        outPacket.encodeShort(exRecords.size() + missingBossRecords.size() + missingClearRecords.size());
        for (Quest quest : exRecords) {
            outPacket.encodeInt(quest.getQRKey());
            String value = QuestConstants.is2QrQuest(quest.getQRKey()) ? quest.getQRExValue() : quest.getQRValue();
            outPacket.encodeString(BossUiAccess.withRequiredRecord(chr, quest.getQRKey(), value));
        }
        for (int questId : missingBossRecords) {
            outPacket.encodeInt(questId);
            outPacket.encodeString(BossUiAccess.withRequiredRecord(chr, questId, ""));
        }
        for (int recordId : missingClearRecords) {
            outPacket.encodeInt(recordId);
            outPacket.encodeString("");
        }
    }

    /**
     * F85, the familiar block (v271 0x1407c9950): 4 flags, then 1: B23 codex record; 8: 5 x (B12 familiar SNs,
     * B8 badges) per preset; 2: 1 clear, 4 n, n x B56 GW_Familiar; 4: 1 clear, 4 n, n x B21 badge page.
     */
    private static void encodeFamiliarsV271(Char chr, OutPacket outPacket) {
        var fcm = chr.getFamiliarCodexManager();
        outPacket.encodeInt(1 | 2 | 4 | 8);
        UserLocal.encodeCodexRecordV271(outPacket, fcm);
        for (int p = 0; p < FamiliarPresets.MAX_PRESETS; p++) {
            UserLocal.encodeCodexFamiliarsV271(outPacket, FamiliarPresets.getFamiliars(chr, p));
            UserLocal.encodeCodexBadgesV271(outPacket, FamiliarPresets.getBadges(chr, p));
        }
        var familiars = new java.util.ArrayList<>(fcm.getFamiliars());
        outPacket.encodeByte(1);
        outPacket.encodeInt(familiars.size());
        for (Familiar familiar : familiars) {
            familiar.encodeRecord(outPacket);
        }
        var pages = fcm.getBadgePageInfo();
        outPacket.encodeByte(1);
        outPacket.encodeInt(pages.size());
        for (var entry : pages.entrySet()) {
            UserLocal.encodeBadgePageV271(outPacket, chr.getId(), entry.getKey(), entry.getValue().getCollectedMask());
        }
    }

    /** Test aid: writes the CharacterData bytes to logs/v271-chardata-NAME.txt for the offline read-order check. */
    private static void dumpV271(Char chr, byte[] data, int start, int statOffset, int statLength) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("stat ").append(statOffset).append(' ').append(statLength).append("\n");
            for (int i = start; i < data.length; i++) {
                sb.append(String.format("%02X", data[i]));
            }
            sb.append("\n");
            java.nio.file.Path dir = java.nio.file.Paths.get("logs");
            java.nio.file.Files.createDirectories(dir);
            java.nio.file.Files.writeString(dir.resolve("v271-chardata-" + chr.getName() + ".txt"), sb.toString());
        } catch (Exception e) {
            // test aid only
        }
    }

    /** Bag owners of one inventory and their contents (client 0x140791bd0 / v265 0x14064f440, same reads). */
    private static void encodeBagData(Char chr, OutPacket outPacket, InvType type) {
        var itemBagOwners = BagItemModule.getBagItemOwners(chr, type);
        outPacket.encodeInt(itemBagOwners.size());
        for (Item itemBagOwner : itemBagOwners) {
            var itemBagIndex = itemBagOwner.getBagItemIndex();
            outPacket.encodeInt(itemBagIndex);
            if (itemBagIndex < GameConstants.getMaxItemPerBagByType(type) && itemBagIndex >= 0) {
                outPacket.encodeInt(itemBagOwner.getItemId());
                for (Item itemBagItem : BagItemModule.getItemsByBagItemIndex(chr, type, itemBagIndex)) {
                    outPacket.encodeInt(itemBagItem.getBagIndexInBagItem());
                    itemBagItem.encode(outPacket);
                }
                outPacket.encodeInt(-1);
            }
        }
    }

    public static void encode(Char chr, OutPacket outPacket, DBChar mask) {
        // v265 starts with one byte per section flag instead of the v232 long mask. The reference tree always sends
        // 100 ones (it only ever encodes DBChar.All); a partial mask sends its low 64 bits this way (unconfirmed).
        for (int i = 0; i < 100; i++) {
            outPacket.encodeByte(i >= 64 || (mask.get() & (1L << i)) != 0);
        }

        outPacket.encodeByte(chr.getCombatOrders());
        for (int i = 0; i < GameConstants.MAX_PET_AMOUNT; i++) {
            if (chr.getPets() != null && i < chr.getPets().size()) {
                outPacket.encodeInt(chr.getPets().get(i).getActiveSkillCoolTime());
            } else {
                outPacket.encodeInt(-2);
            }
        }
        outPacket.encodeByte(0); // unk, not in kmst
        outPacket.encodeByte(0); // size of an int list
        outPacket.encodeInt(0); // size of an (int, long) list
        outPacket.encodeByte(0);

        if (mask.isInMask(DBChar.Character)) {
            chr.getAvatarData().getCharacterStat().encode(outPacket, chr.getAvatarData().getAvatarLook());
            outPacket.encodeByte(chr.getBuddyCapacity());

            boolean hasBlessingOfFairy = chr.getBlessingOfFairy() != null;
            outPacket.encodeByte(hasBlessingOfFairy);
            if (hasBlessingOfFairy) {
                outPacket.encodeString(chr.getBlessingOfFairy());
            }

            boolean hasBlessingOfEmpress = chr.getBlessingOfEmpress() != null;
            outPacket.encodeByte(hasBlessingOfEmpress);
            if (hasBlessingOfEmpress) {
                outPacket.encodeString(chr.getBlessingOfEmpress());
            }

            outPacket.encodeByte(false); // ultimate explorer, deprecated
        }

        outPacket.encodeShort(0);
        outPacket.encodeFT(FileTime.MIN_TIME());

        encodeDamageSkins(chr, outPacket);

        if (mask.isInMask(DBChar.Money)) {
            outPacket.encodeLong(chr.getMoney());
        }

        if (mask.isInMask(DBChar.ItemSlotConsume) || mask.isInMask(DBChar.ExpConsumeItem)) {
            outPacket.encodeInt(chr.getExpConsumeItems().size());
            for (ExpConsumeItem eci : chr.getExpConsumeItems()) {
                eci.encode(outPacket);
            }
        }

        if (mask.isInMask(DBChar.ItemSlotConsume) || mask.isInMask(DBChar.ShopBuyLimit)) {
            // Potion Pots (client reader at 0x14068154a: int n, n x (4 item, 4 char, 4 max, 4 hp, 4 mp, FT, FT))
            net.swordie.ms.client.character.items.PotionPot.encodeList(outPacket, chr);
        }

        // Items ------------------------------------------------

        if (mask.isInMask(DBChar.InventorySize)) {
            outPacket.encodeInt(chr.getEquipInventory().getSlots());
            outPacket.encodeInt(chr.getConsumeInventory().getSlots());
            outPacket.encodeInt(chr.getInstallInventory().getSlots());
            outPacket.encodeInt(chr.getEtcInventory().getSlots());
            outPacket.encodeInt(chr.getCashInventory().getSlots());
            outPacket.encodeInt(chr.getDecInventory().getSlots());
        }

        // client 0x140680fe0 stores these at inventory+0x30: [0] applied equipment preset, [1], [2] applied link
        // skill preset
        outPacket.encodeByte(EquipPresetModule.appliedForEncode(chr));
        outPacket.encodeByte(0);
        outPacket.encodeByte(LinkSkillsModule.appliedPreset(chr));

        if (mask.isInMask(DBChar.ItemSlotEquip)) {
            encodeEquips(chr, outPacket);
        }

        if (mask.isInMask(DBChar.ItemSlotConsume)) {
            encodeBundleItems(outPacket, chr.getConsumeInventory().getItems());
        }
        if (mask.isInMask(DBChar.ItemSlotInstall)) {
            encodeBundleItems(outPacket, chr.getInstallInventory().getItems());
        }
        if (mask.isInMask(DBChar.ItemSlotEtc)) {
            encodeBundleItems(outPacket, chr.getEtcInventory().getItems());
        }
        if (mask.isInMask(DBChar.ItemSlotCash)) {
            for (Item item : chr.getCashInventory().getItems()) {
                outPacket.encodeShort(item.getBagIndex());
                item.encode(outPacket);
            }
            outPacket.encodeShort(0);
        }

        // BagDatas (Consume, Install, Etc); the reference tree only ever sends empty lists here
        for (int i = 2; i <= 4; i++) {
            var type = InvType.getInvTypeByVal(i);
            if (!mask.isInMask(i == 2 ? DBChar.ItemSlotConsume : i == 3 ? DBChar.ItemSlotInstall : DBChar.ItemSlotEtc)) {
                continue;
            }
            var itemBagOwners = BagItemModule.getBagItemOwners(chr, type);
            outPacket.encodeInt(itemBagOwners.size());
            for (Item itemBagOwner : itemBagOwners) {
                var itemBagIndex = itemBagOwner.getBagItemIndex();
                outPacket.encodeInt(itemBagIndex);
                if (itemBagIndex < GameConstants.getMaxItemPerBagByType(type) && itemBagIndex >= 0) {
                    outPacket.encodeInt(itemBagOwner.getItemId());
                    for (Item itemBagItem : BagItemModule.getItemsByBagItemIndex(chr, type, itemBagIndex)) {
                        outPacket.encodeInt(itemBagItem.getBagIndexInBagItem());
                        itemBagItem.encode(outPacket);
                    }
                    outPacket.encodeInt(-1); // mark end of list
                }
            }
        }
        for (int i = 0; i < 5; i++) {
            outPacket.encodeInt(0); // more item lists
        }
        // End bagdatas

        if (mask.isInMask(DBChar.ItemSlotEtc)) {
            outPacket.encodeInt(0); // boss reward crystals
        }
        if (mask.isInMask(DBChar.CoreAura)) {
            outPacket.encodeInt(0);
        }
        if (mask.isInMask(DBChar.Unk40000000)) {
            outPacket.encodeInt(0); // (long, FileTime) list
        }

        if (mask.isInMask(DBChar.SkillRecord)) {
            outPacket.encodeByte(true);
            List<Skill> skills = chr.getSkills().stream().filter(s -> !SkillConstants.isUnionSkill(s.getSkillId()))
                    .sorted(Comparator.comparingInt(Skill::getSkillId)).collect(Collectors.toList());
            outPacket.encodeShort(skills.size());
            for (Skill skill : skills) {
                outPacket.encodeInt(skill.getSkillId());
                outPacket.encodeInt(skill.getCurrentLevel());
                outPacket.encodeFT(FileTime.fromType(FileTime.Type.MAX_TIME));
                if (SkillConstants.isSkillNeedMasterLevel(skill.getSkillId())) {
                    outPacket.encodeInt(skill.getMasterLevel());
                }
            }

            // Hyper Stats
            var hyperStats = chr.getHyperStatsManager();
            outPacket.encodeByte(hyperStats.getCurrentPreset());
            hyperStats.encode(outPacket);

            LinkSkillsModule.encode(chr, outPacket);
        }

        if (mask.isInMask(DBChar.SkillCooltime)) {
            long curTime = System.currentTimeMillis();
            Map<Integer, Long> cooltimes = new HashMap<>();
            chr.getSkillCoolTimes().forEach((skillId, time) -> {
                if (time - curTime > 0) {
                    cooltimes.put(skillId, time);
                }
            });
            outPacket.encodeShort(cooltimes.size());
            cooltimes.forEach((skillId, time) -> {
                outPacket.encodeInt(skillId); // nSkillId
                outPacket.encodeInt((int) ((time - curTime) / 1000)); // nSkillCooltime
            });
        }

        // skill alarms: 6 skill ids, 6 enabled flags, 6 keys (-1: no key, as the reference server sends)
        for (int i = 0; i < 6; i++) {
            outPacket.encodeInt(0);
        }
        for (int i = 0; i < 6; i++) {
            outPacket.encodeByte(0);
        }
        for (int i = 0; i < 6; i++) {
            outPacket.encodeInt(-1);
        }

        if (mask.isInMask(DBChar.QuestRecord)) {
            outPacket.encodeByte(true); // remove all old entries
            var inProgress = chr.getQuestManager().getQuestsInProgress();
            outPacket.encodeShort(inProgress.size());
            for (Quest quest : inProgress) {
                outPacket.encodeInt(quest.getQRKey());
                outPacket.encodeString(quest.getQRValue());
            }
            outPacket.encodeShort(0);
            outPacket.encodeShort(0); // (string, string) list
        }
        if (mask.isInMask(DBChar.QuestComplete)) {
            outPacket.encodeByte(true); // remove all old entries
            Set<Quest> completedQuests = chr.getQuestManager().getCompletedQuests();
            // boss window prerequisites shown as completed to the client only (see BossUiAccess)
            List<Integer> bossUiQuests = BossUiAccess.getQuestsToShowCompleted(chr);
            outPacket.encodeShort(completedQuests.size() + bossUiQuests.size());
            for (Quest quest : completedQuests) {
                outPacket.encodeInt(quest.getQRKey());
                outPacket.encodeFT(quest.getCompletedTime()); // Timestamp of completion
            }
            for (int questId : bossUiQuests) {
                outPacket.encodeInt(questId);
                outPacket.encodeFT(FileTime.currentTime());
            }
        }

        if (mask.isInMask(DBChar.MinigameRecord)) {
            outPacket.encodeShort(0);
        }

        if (mask.isInMask(DBChar.CoupleRecord)) {
            outPacket.encodeShort(0); // couple
            outPacket.encodeShort(0); // friend
            outPacket.encodeShort(0); // marriage
        }

        if (mask.isInMask(DBChar.MapTransfer)) {
            for (int i = 0; i < 5; i++) {
                outPacket.encodeInt(GameConstants.NO_MAP_ID);
            }
            for (int i = 0; i < 10; i++) {
                outPacket.encodeInt(GameConstants.NO_MAP_ID);
            }
            for (int i = 0; i < 13; i++) {
                outPacket.encodeInt(GameConstants.NO_MAP_ID);
            }
            var hyperRockFields = chr.getHyperRockFields();
            for (int i = 0; i < 13; i++) {
                if (hyperRockFields != null && i < hyperRockFields.size()) {
                    outPacket.encodeInt(hyperRockFields.get(i));
                } else {
                    outPacket.encodeInt(GameConstants.NO_MAP_ID);
                }
            }
        }

        if (mask.isInMask(DBChar.FamiliarCodex)) { // 44 bytes, then presets
            FamiliarCodexManager fcm = chr.getFamiliarCodexManager();

            // zeros, as the reference tree and GMS send; v232 put char id, gauge, slots, active familiars
            // and badges here
            outPacket.encodeArr(new byte[44]);

            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 3; j++) {
                    outPacket.encodeInt(i == 0 ? fcm.getActiveFamiliars()[j] : 0);
                }
            }
            for (int i = 0; i < 5; i++) {
                if (i == 0) {
                    for (int activeBadge : fcm.getActiveBadges()) {
                        outPacket.encodeByte(activeBadge);
                    }
                } else {
                    for (int j = 0; j < 8; j++) {
                        outPacket.encodeByte(-1);
                    }
                }
            }
        }

        if (mask.isInMask(DBChar.Familiar)) {
            outPacket.encodeByte(false); // init?
            outPacket.encodeInt(chr.getFamiliars().size());
            for (var familiar : chr.getFamiliars()) {
                outPacket.encodeInt((int) familiar.getId());
                familiar.encode(outPacket, FamiliarModifiedMask.All.getVal());
            }
        }

        if (mask.isInMask(DBChar.FamiliarCodex)) { // badge collection
            var badgePageInfo = chr.getFamiliarCodexManager().getBadgePageInfo();
            outPacket.encodeByte(false); // already init
            outPacket.encodeInt(badgePageInfo.size());
            for (var entry : badgePageInfo.entrySet()) {
                var page = entry.getKey();
                var info = entry.getValue();
                outPacket.encodeInt(page);
                outPacket.encodeInt(chr.getId());
                outPacket.encodeShort(page);
                for (int j = 0; j < 4; j++) {
                    outPacket.encodeInt(info.getCollectedMask()[j]);
                }
            }
        }

        if (mask.isInMask(DBChar.QuestRecordEx)) {
            List<Integer> missingBossRecords = BossUiAccess.getMissingRecords(chr);
            var exRecords = chr.getQuestManager().getEx(); // one snapshot: the count must match what's written
            outPacket.encodeShort(exRecords.size() + missingBossRecords.size());
            for (Quest quest : exRecords) {
                outPacket.encodeInt(quest.getQRKey());
                String value = QuestConstants.is2QrQuest(quest.getQRKey()) ? quest.getQRExValue() : quest.getQRValue();
                outPacket.encodeString(BossUiAccess.withRequiredRecord(chr, quest.getQRKey(), value));
            }
            for (int questId : missingBossRecords) {
                outPacket.encodeInt(questId);
                outPacket.encodeString(BossUiAccess.withRequiredRecord(chr, questId, ""));
            }
        }

        if (mask.isInMask(DBChar.AvatarLook)) { // NewYearCard in the reference tree
            outPacket.encodeShort(0); // (int, short) list
        }

        outPacket.encodeByte(true); // bNxRecordAccessAuth
        if (mask.isInMask(DBChar.Unk10000000000)) {
            outPacket.encodeInt(0); // (int, string) list
        }
        if (mask.isInMask(DBChar.Unk100000000000)) {
            outPacket.encodeInt(0); // (int, int) list
        }

        if (mask.isInMask(DBChar.WildHunterInfo)) {
            if (JobConstants.isWildHunter(chr.getAvatarData().getCharacterStat().getJob())) {
                QuestManager qm = chr.getQuestManager();
                WildHunterInfo whi = chr.getWildHunterInfo();
                Quest chosenQuest = qm.getQuestById(QuestConstants.WILD_HUNTER_JAGUAR_CHOSEN_ID);
                int toID = -1;
                if (chosenQuest == null) {
                    chosenQuest = QuestData.createQuestFromId(QuestConstants.WILD_HUNTER_JAGUAR_CHOSEN_ID);
                    qm.addQuest(chosenQuest);
                } else if (Util.isNumber(chosenQuest.getQRValue())) {
                    toID = Integer.parseInt(chosenQuest.getQRValue());
                }
                whi.setIdx((byte) toID);
                whi.setRidingType((byte) toID);
                chosenQuest.setQrValue("" + toID);
                chr.getWildHunterInfo().encode(outPacket); // GW_WildHunterInfo::Decode
            }
        }

        if (mask.isInMask(DBChar.ZeroInfo)) {
            if (JobConstants.isZero(chr.getAvatarData().getCharacterStat().getJob())) {
                if (chr.getZeroInfo() == null) {
                    chr.initZeroInfo();
                }
                chr.getZeroInfo().encode(ZeroInfoFlag.all(), outPacket); // ZeroInfo::Decode
            }
        }

        if (mask.isInMask(DBChar.ShopBuyLimit)) {
            outPacket.encodeShort(0);
            outPacket.encodeShort(0);
            outPacket.encodeShort(0);
        }

        outPacket.encodeInt(0); // (int x4) list
        outPacket.encodeInt(0);

        if (mask.isInMask(DBChar.StolenSkills)) {
            boolean phantom = JobConstants.isPhantom(chr.getAvatarData().getCharacterStat().getJob());
            for (int i = 0; i < 16; i++) {
                StolenSkill stolenSkill = phantom ? chr.getStolenSkillByPosition(i) : null;
                outPacket.encodeInt(stolenSkill == null ? 0 : stolenSkill.getSkillId());
            }
        }

        if (mask.isInMask(DBChar.ChosenSkills)) {
            if (JobConstants.isPhantom(chr.getAvatarData().getCharacterStat().getJob())) {
                for (int i = 1; i <= 5; i++) { //Shifted by +1 to accomodate the Skill Management Tabs
                    ChosenSkill chosenSkill = chr.getChosenSkillByPosition(i);
                    outPacket.encodeInt(chosenSkill == null
                            ? 0
                            : chr.isChosenSkillInStolenSkillList(chosenSkill.getSkillId())
                            ? chosenSkill.getSkillId()
                            : 0
                    );
                }
            } else {
                for (int i = 0; i < 5; i++) {
                    outPacket.encodeInt(0);
                }
            }
        }

        if (mask.isInMask(DBChar.CharacterPotential)) { // 3 presets
            chr.getPotentialMan().fillPresets();
            net.swordie.ms.connection.packet.WvsContext.encodePotentialPresets(outPacket, chr);
        }

        if (mask.isInMask(DBChar.SoulCollection)) {
            var entries = chr.getAccount().getSoulCollectionEntries();
            outPacket.encodeShort(entries.size());
            entries.forEach(outPacket::encode);
        }

        if (mask.isInMask(DBChar.Character)) {
            outPacket.encodeInt(1); // honor level, deprecated
            outPacket.encodeInt(chr.getHonorExp()); // honor exp
        }

        if (mask.isInMask(DBChar.Unk200000000)) {
            outPacket.encodeByte(true);
            outPacket.encodeShort(0); // (short category, (int item, int count) list) list
        }

        if (mask.isInMask(DBChar.ReturnEffectInfo)) {
            outPacket.encodeByte(0);
        }
        if (mask.isInMask(DBChar.DressUpInfo)) {
            BeautyModule.dressUpInfo(chr).encode(outPacket); // GW_DressUpInfo::Decode
        }

        if (mask.isInMask(DBChar.ActiveDamageSkin)) {
            outPacket.encodeInt(activeSkinId(chr.getDamageSkin()));
            outPacket.encodeInt(chr.getPremiumDamageSkin().getDamageSkinID());
            outPacket.encodeFT(activeSkinTime());
            outPacket.encodeString(chr.getActiveDamageSkin().getDescription());
            outPacket.encodeInt(activeSkinId(chr.getActiveDamageSkin()));
        }

        if (mask.isInMask(DBChar.MemorialCubeInfo)) {
            // MemorialCubeInfo.encode is the v232 layout; v265's differs, so a held cube result isn't sent
            outPacket.encodeByte(false);
        }

        if (mask.isInMask(DBChar.MemorialFlameInfo)) {
            outPacket.encodeByte(false);
        }

        if (mask.isInMask(DBChar.LikePoint)) {
            new LikePoint().encode(outPacket);
        }

        if (mask.isInMask(DBChar.RunnerGameRecord)) {
            new RunnerGameRecord().encode(outPacket); // RunnerGameRecord::Decode
        }

        if (mask.isInMask(DBChar.Unk8000000000000)) {
            outPacket.encodeInt(0); // (int, byte x3) list
            outPacket.encodeInt(0);
            outPacket.encodeLong(0);
            outPacket.encodeByte(0);
            outPacket.encodeByte(0);
        }

        encodeWorldShareRecords(chr, outPacket);

        if (mask.isInMask(DBChar.MonsterCollection)) {
            Set<MonsterCollectionExploration> mces = chr.getAccount().getMonsterCollection().getMonsterCollectionExplorations();
            outPacket.encodeShort(mces.size());
            for (MonsterCollectionExploration mce : mces) {
                outPacket.encodeInt(mce.getPosition());
                outPacket.encodeString(mce.getValue(true));
            }
        }

        outPacket.encodeByte(1);
        outPacket.encodeShort(0);
        outPacket.encodeInt(0); // CharacterData::DecodeTextEquipInfo

        if (mask.isInMask(DBChar.Unk10000000000000)) {
            outPacket.encodeShort(0); // (int, int) list
        }

        if (mask.isInMask(DBChar.VMatrix)) {
            List<MatrixRecord> matrixRecords = chr.getSortedMatrixRecords();
            outPacket.encodeInt(matrixRecords.size());
            for (MatrixRecord mr : matrixRecords) {
                outPacket.encode(mr);
            }
            outPacket.encodeInt(GameConstants.MAX_NODE_SLOTS);
            for (int i = 0; i < GameConstants.MAX_NODE_SLOTS; i++) {
                MatrixRecord mr = chr.getMatrixRecordByPosition(i);
                MatrixSlot ms = chr.getMatrixSlotByPosition(i);
                outPacket.encodeInt(mr != null ? matrixRecords.indexOf(mr) : -1); // nodeID
                outPacket.encodeInt(i); // matrix Slot position
                outPacket.encodeInt(ms.getEnhancementLv()); // slot enhancement level
                outPacket.encodeByte(ms.isUnlockedByPurchase()); // bShow Unlocked Symbol
            }
        }

        HexaModule.encodeSkills(chr, outPacket);
        HexaModule.encodeStats(chr, outPacket);
        net.swordie.ms.client.character.erdalink.ErdaLinkModule.encode(chr, outPacket); // Erda Link (0x1406525d0)
        UnionArtifactModule.encode(chr, outPacket);
        outPacket.encodeInt(0); // int list

        if (mask.isInMask(DBChar.Achievement)) {
            // client 0x140655090: account, character, two ints (MESSAGE 47), records, insignias
            net.swordie.ms.client.character.achievement.MapleAchievements.encodeCharData(chr, outPacket);
        }

        if (mask.isInMask(DBChar.ItemSlotEtc)) {
            outPacket.encodeInt(0); // boss reward crystals
        }

        for (int i = 0; i < 3; i++) {
            MannequinModule.encode(chr, outPacket, i); // salon mannequins: hair, face, skin
        }

        if (mask.isInMask(DBChar.Familiar)) {
            // chat emoticons (client reader 0x1407D0E30)
            net.swordie.ms.client.character.emoticons.EmoticonModule.encode(chr, outPacket);
        }

        outPacket.encodeInt(0);

        if (mask.isInMask(DBChar.Commerce)) {
            var vessel = chr.getVessel();
            outPacket.encodeByte(vessel != null);
            if (vessel != null) {
                vessel.encode(outPacket);
            }

            var q = chr.getQuestManager().getOrCreateQuestById(QuestConstants.VOYAGES);
            q.convertQRValueToProperties();
            outPacket.encodeShort(q.getProperties().size()); // Routes
            for (var entry : q.getProperties().entrySet()) {
                outPacket.encodeByte(Integer.parseInt(entry.getKey()));
                outPacket.encodeInt(Integer.parseInt(entry.getValue()));
                outPacket.encodeInt(0);
            }
            outPacket.encodeShort(0); // Cargo
        }

        if (mask.isInMask(DBChar.AvatarLook)) { // NewYearCard in the reference tree
            outPacket.encodeByte(0);
            outPacket.encodeInt(0); // (short, short) list
            outPacket.encodeInt(0); // (short, int) list
        }

        if (mask.isInMask(DBChar.ActiveDamageSkin)) { // Unk20000000000 in the reference tree
            outPacket.encodeShort(0);
        }

        if (mask.isInMask(DBChar.RedLeafInfo)) {
            outPacket.encodeInt(chr.getUserId());
            outPacket.encodeInt(chr.getId());
            outPacket.encodeInt(4);
            outPacket.encodeInt(0);
            outPacket.encodeArr(new byte[32]);
        }
    }

    /**
     * The reference server sends 0 for the default skin in the active slots; ours keeps -1 for "none".
     */
    public static int activeSkinId(DamageSkinSaveData skin) {
        return skin == null ? 0 : Math.max(0, skin.getDamageSkinID());
    }

    /**
     * When the active skin was set: the reference tree sends a real date (the default skin's creation) where ours had
     * the 1900 zero time or 0. We don't store it, so this is now.
     */
    private static FileTime activeSkinTime() {
        return FileTime.currentTime();
    }

    public static final int DAMAGE_SKIN_SLOTS = 35;

    /** The collection as the client lists it (its requests name a skin by index in this list): no default skin, by id. */
    public static List<DamageSkinSaveData> savedDamageSkins(Char chr) {
        if (chr.getAccount() == null) {
            return List.of();
        }
        return chr.getAccount().getDamageSkins().stream().filter(skin -> skin.getDamageSkinID() != 0)
                .sorted(java.util.Comparator.comparingInt(DamageSkinSaveData::getDamageSkinID)
                        .thenComparingInt(DamageSkinSaveData::getItemID)).toList();
    }

    /** v265 damage skin block: slot count, saved skins, active, premium and one more entry */
    public static void encodeDamageSkins(Char chr, OutPacket outPacket) {
        outPacket.encodeInt(DAMAGE_SKIN_SLOTS);
        var saved = savedDamageSkins(chr);
        outPacket.encodeInt(saved.size());
        for (var skin : saved) {
            outPacket.encodeInt(skin.getDamageSkinID());
            outPacket.encodeInt(skin.getItemID());
            outPacket.encodeFT(FileTime.MIN_TIME());
        }
        var active = chr.getDamageSkin();
        outPacket.encodeInt(activeSkinId(active));
        outPacket.encodeInt(active == null ? 0 : active.getItemID());
        outPacket.encodeFT(activeSkinTime());
        for (var skin : new DamageSkinSaveData[]{chr.getPremiumDamageSkin(), null}) {
            outPacket.encodeInt(skin == null ? -1 : skin.getDamageSkinID());
            outPacket.encodeInt(skin == null ? 0 : skin.getItemID());
            outPacket.encodeFT(FileTime.MIN_TIME());
        }
    }

    private static void encodeBundleItems(OutPacket outPacket, List<Item> items) {
        for (Item item : items) {
            if (!item.isInBag()) {
                outPacket.encodeShort(item.getBagIndex());
                item.encode(outPacket);
            }
        }
        outPacket.encodeShort(0);
    }

    private static void encodeEquipRange(OutPacket outPacket, List<Item> equipped, BodyPart from, BodyPart to) {
        encodeEquipRange(outPacket, equipped, from.getVal(), to.getVal());
    }

    private static void encodeEquipRange(OutPacket outPacket, List<Item> equipped, int from, int to) {
        for (Item item : equipped) {
            if (item instanceof Equip && item.getBagIndex() >= from && item.getBagIndex() < to) {
                outPacket.encodeShort(item.getBagIndex());
                item.encode(outPacket);
            }
        }
        outPacket.encodeShort(0);
    }

    /**
     * v265 equip block (order from the reference tree): equipped, equip inventory, 12 special ranges, 2 empty
     * lists, then cash equipped, decorations and 6 more ranges.
     */
    private static void encodeEquips(Char chr, OutPacket outPacket) {
        List<Item> equipped = new ArrayList<>(chr.getEquippedInventory().getItems());
        equipped.sort(Comparator.comparingInt(Item::getBagIndex));

        outPacket.encodeByte(false); // only equipped
        for (Item item : equipped) {
            if (item instanceof Equip && !item.isCash() && item.getBagIndex() > BodyPart.BPBase.getVal()
                    && item.getBagIndex() < BodyPart.BPEnd.getVal()) {
                outPacket.encodeShort(item.getBagIndex());
                item.encode(outPacket);
            }
        }
        outPacket.encodeShort(0);
        for (Item item : chr.getEquipInventory().getItems()) {
            if (!item.isCash()) {
                outPacket.encodeShort(item.getBagIndex());
                item.encode(outPacket);
            }
        }
        outPacket.encodeShort(0);

        encodeEquipRange(outPacket, equipped, BodyPart.EvanBase, BodyPart.EvanEnd);
        encodeEquipRange(outPacket, equipped, BodyPart.MechBase, BodyPart.MechEnd);
        encodeEquipRange(outPacket, equipped, BodyPart.BitsBase, BodyPart.BitsEnd);
        encodeEquipRange(outPacket, equipped, BodyPart.MBPBase, BodyPart.MBPEnd);
        encodeEquipRange(outPacket, equipped, BodyPart.AFBase, BodyPart.AFEnd);
        encodeEquipRange(outPacket, equipped, BodyPart.AUSBase, BodyPart.AUSEnd);
        // client range table 0x146861be0: 1750 (Grand Sacred Symbols), the 3 equipment presets (3000/3100/3200), totems, Haku
        encodeEquipRange(outPacket, equipped, BodyPart.GSSBase, BodyPart.GSSEnd);
        List<Item> presets = new ArrayList<>(chr.getEquippedInventory().getPresetItems());
        presets.sort(Comparator.comparingInt(Item::getBagIndex));
        for (int p = 0; p < EquipPresetModule.PRESETS; p++) {
            encodeEquipRange(outPacket, presets, EquipPresetModule.base(p) + 1, EquipPresetModule.base(p) + 66);
        }
        encodeEquipRange(outPacket, equipped, BodyPart.TotemBase, BodyPart.TotemEnd);
        encodeEquipRange(outPacket, equipped, BodyPart.HakuStart, BodyPart.HakuEnd);

        outPacket.encodeShort(0); // 20001~20048
        outPacket.encodeShort(0); // 20049~20051

        outPacket.encodeByte(false); // only equipped
        for (Item item : equipped) {
            if (item instanceof Equip && item.getBagIndex() >= BodyPart.CBPBase.getVal()
                    && item.getBagIndex() <= BodyPart.CBPEnd.getVal()) {
                outPacket.encodeShort(item.getBagIndex() - 100);
                item.encode(outPacket);
            }
        }
        outPacket.encodeShort(0);
        for (Item item : chr.getDecInventory().getItems()) {
            if (item instanceof Equip) {
                outPacket.encodeShort(item.getBagIndex());
                item.encode(outPacket);
            }
        }
        outPacket.encodeShort(0);

        encodeEquipRange(outPacket, equipped, BodyPart.APBase, BodyPart.APEnd);
        encodeEquipRange(outPacket, equipped, BodyPart.DUBase, BodyPart.DUEnd);
        encodeEquipRange(outPacket, equipped, BodyPart.ZeroBase, BodyPart.ZeroEnd);
        outPacket.encodeShort(0);
        outPacket.encodeShort(0);
        outPacket.encodeShort(0);
    }


    /**
     * World-shared records: GMS sets 131 "uiOpen=1" with quest 16370 (the menu keeps Maple Achievements grey without
     * it); the boss window and Contents Guide checks read their clears from here too.
     */
    private static void encodeWorldShareRecords(Char chr, OutPacket outPacket) {
        boolean achievementsOpen = isAchievementsOpen(chr);
        List<Quest> bossClears = BossClearRecords.getWorldShareRecords(chr);
        outPacket.encodeShort((achievementsOpen ? 1 : 0) + bossClears.size());
        if (achievementsOpen) {
            outPacket.encodeInt(LevelUnlockModule.ACHIEVEMENTS_WSR);
            outPacket.encodeString(LevelUnlockModule.ACHIEVEMENTS_WSR_VALUE);
        }
        for (Quest record : bossClears) {
            outPacket.encodeInt(record.getQRKey());
            outPacket.encodeString(record.getQRValue());
        }
    }

    private static boolean isAchievementsOpen(Char chr) {
        try {
            return chr.getAccount() != null && chr.getQuestManager() != null
                    && chr.getQuestManager().hasQuestCompleted(LevelUnlockModule.ACHIEVEMENTS_QUEST)
                    && net.swordie.ms.client.character.achievement.MapleAchievements.isComplete(chr, 1);
        } catch (Exception e) {
            return false;
        }
    }
}
