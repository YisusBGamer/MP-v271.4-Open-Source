package net.swordie.ms.handlers.user;

import net.swordie.ms.client.jobs.adventurer.pirate.Jett;
import net.swordie.ms.client.jobs.cygnus.ThunderBreaker;
import net.swordie.ms.Server;
import net.swordie.ms.client.Account;
import net.swordie.ms.client.Client;
import net.swordie.ms.client.character.BossUiAccess;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.FirstEnterReward;
import net.swordie.ms.client.character.MonsterCollection;
import net.swordie.ms.client.character.MonsterCollectionExploration;
import net.swordie.ms.client.character.chair.CustomChairType;
import net.swordie.ms.client.character.chair.PortableChair;
import net.swordie.ms.client.character.chair.PortableChairFactory;
import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.client.character.keys.FuncKeyMap;
import net.swordie.ms.client.character.potential.CharacterPotential;
import net.swordie.ms.client.character.potential.CharacterPotentialMan;
import net.swordie.ms.client.character.quest.Quest;
import net.swordie.ms.client.character.quest.QuestManager;
import net.swordie.ms.client.character.runestones.RuneStone;
import net.swordie.ms.client.character.runestones.RuneStoneAckType;
import net.swordie.ms.client.character.runestones.RuneStoneArrows;
import net.swordie.ms.client.character.skills.Option;
import net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat;
import net.swordie.ms.client.character.skills.temp.TemporaryStatManager;
import net.swordie.ms.client.jobs.Job;
import net.swordie.ms.client.jobs.adventurer.BeastTamer;
import net.swordie.ms.client.jobs.adventurer.thief.Shadower;
import net.swordie.ms.client.jobs.adventurer.warrior.DarkKnight;
import net.swordie.ms.client.jobs.flora.Illium;
import net.swordie.ms.client.jobs.legend.Evan;
import net.swordie.ms.client.jobs.legend.Shade;
import net.swordie.ms.client.jobs.nova.AngelicBuster;
import net.swordie.ms.client.jobs.nova.Kaiser;
import net.swordie.ms.client.jobs.resistance.BattleMage;
import net.swordie.ms.client.jobs.resistance.WildHunter;
import net.swordie.ms.client.jobs.resistance.Xenon;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.packet.*;
import net.swordie.ms.connection.packet.field.FieldPacket;
import net.swordie.ms.connection.packet.model.MessagePacket;
import net.swordie.ms.constants.*;
import net.swordie.ms.enums.*;
import net.swordie.ms.handlers.Handler;
import net.swordie.ms.handlers.header.InHeader;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.life.mob.skill.MobSkill;
import net.swordie.ms.life.mob.skill.MobSkillID;
import net.swordie.ms.life.mob.skill.MobSkillStat;
import net.swordie.ms.life.movement.MovementInfo;
import net.swordie.ms.loaders.*;
import net.swordie.ms.loaders.containerclasses.ItemInfo;
import net.swordie.ms.loaders.containerclasses.MobSkillInfo;
import net.swordie.ms.util.FileTime;
import net.swordie.ms.util.Position;
import net.swordie.ms.util.Rect;
import net.swordie.ms.util.Util;
import net.swordie.ms.util.container.Tuple;
import net.swordie.ms.world.field.Field;
import net.swordie.ms.world.field.Portal;
import net.swordie.ms.world.field.modules.RuneStoneFieldModule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static net.swordie.ms.enums.ChatType.SystemNotice;

public class UserHandler {
    private static final int PERFECT_TACTICAL_RELAY_CHAIR_2_0 = 3018464;

    private static final Logger log = LogManager.getLogger();


    @Handler(op = InHeader.USER_MOVE)
    public static void handleUserMove(Char chr, InPacket inPacket) {
        Field field = chr.getField();
        // CVecCtrlUser::EndUpdateActive
        byte fieldKey = inPacket.decodeByte();
        inPacket.decodeInt(); // ? something with field
        inPacket.decodeInt(); // tick
        inPacket.decodeByte(); // ? doesn't get set at all
        // CMovePathCommon::Encode
        MovementInfo movementInfo = new MovementInfo(inPacket);
        movementInfo.applyTo(chr);
        net.swordie.ms.ext.ServerHooks.onUserMove(chr, movementInfo);
        field.checkCharInAffectedAreas(chr);
        field.checkCharsInMobZone();
        field.broadcastPacket(UserRemote.move(chr, movementInfo), chr);

        if (Server.DEBUG_MOVEMENT) {
            chr.chatMessage("[M] Pos: [%d, %d] | Left: [%s]", chr.getPosition().getX(), chr.getPosition().getY(), chr.isLeft());
        }

        Char copy = chr.getCopy();
        if (copy != null) {
            chr.write(UserRemote.move(copy, movementInfo));
        }
        if (chr.getPosition().getY() > 5000) {
            // failsafe when the char falls outside of the map
            Portal portal = field.getInfo().getDefaultPortal();
            Position position = new Position(portal.getX(), portal.getY());
            chr.setPosition(position);
            chr.write(FieldPacket.teleport(position, chr));
        }
        // client has stopped moving. this might not be the best way
        if (chr.getMoveAction() == 4 || chr.getMoveAction() == 5) {
            TemporaryStatManager tsm = chr.getTemporaryStatManager();
            for (int skill : Job.REMOVE_ON_STOP) {
                if (tsm.hasStatBySkillId(skill)) {
                    tsm.removeStatsBySkill(skill);
                }
            }
        }
    }

    @Handler(op = InHeader.USER_HIT)
    public static void handleUserHit(Char chr, InPacket inPacket) {
        int hpBefore = chr.getHP();
        int[] skillHit = mobSkillHit(inPacket);
        chr.getJobHandler().handleHit(inPacket);
        if (skillHit != null) {
            net.swordie.ms.life.mob.skill.MobSkillModule.onMobSkillHit(chr, skillHit[0], skillHit[1], skillHit[2]);
        }
        net.swordie.ms.client.character.achievement.MapleAchievements.onUserHit(chr, hpBefore, chr.getHP(), chr.getMaxHP());
    }

    /**
     * A hit by a monster skill the client draws (v265 0x144ee38a0: attack index -8, damage from the skill's fixDamR):
     * int, int, byte -8, byte, byte, int damage, byte, byte, int mob object id, the 47-byte block (0x141073610), then
     * int skill id, int level. Read from a copy so the job's hit handling gets the packet untouched.
     *
     * @return {mob object id, skill id, level}, or null for any other hit
     */
    private static int[] mobSkillHit(InPacket inPacket) {
        if (inPacket.getUnreadAmount() < 21 + 47 + 8) {
            return null;
        }
        InPacket p = inPacket.clone();
        p.decodeInt();
        p.decodeInt();
        if (p.decodeByte() != net.swordie.ms.enums.AttackIndex.MobSkill.getVal()) {
            return null;
        }
        p.decodeByte();
        p.decodeByte();
        int damage = p.decodeInt();
        p.decodeByte();
        p.decodeByte();
        int mobObjectId = p.decodeInt();
        p.decodeArr(47);
        int skillId = p.decodeInt();
        int slv = p.decodeInt();
        return damage > 0 && skillId > 0 && slv > 0 ? new int[]{mobObjectId, skillId, slv} : null;
    }


    @Handler(op = InHeader.USER_OPEN_QUICK_MOVE_REQUEST)
    public static void handleUserOpenQuickMoveRequest(Char chr, InPacket inPacket) {
        // the short echoes the flag of the last SET_QUICK_MOVE_INFO, not open/close; the client opens its list
        // whenever the reply leaves it non-empty, so always send the current list (as GMS)
        inPacket.decodeShort();
        chr.write(FieldPacket.userOpenQuickMove(true, chr.getAvailableQuickMoves()));
    }

    @Handler(op = InHeader.USER_GROWTH_HELPER_REQUEST)
    public static void handleUserGrowthRequestHelper(Client c, InPacket inPacket) {
        Char chr = c.getChr();
        Field field = chr.getField();
        if (net.swordie.ms.constants.FieldConstants.teleportWouldBreak(chr, field)) {
            chr.dispose();
            return;
        }
        // v265 (reference server): byte status; 0 = int field id, byte portal
        short status = inPacket.decodeByte();
        if (status == 0) {
            int mapleGuideMapId = inPacket.decodeInt();

            if (!EtcData.getGrowthHelp().containsKey(mapleGuideMapId) || EtcData.getGrowthHelp().get(mapleGuideMapId) > chr.getLevel()) {
                chr.getOffenseManager().addOffense(String.format("Tried going to %d, requires lv %d. Current lv: %d",
                        mapleGuideMapId, EtcData.getGrowthHelp().get(mapleGuideMapId), chr.getLevel()));
                return;
            }

            Field toField = chr.getClient().getChannelInstance().getField(mapleGuideMapId);
            if (toField == null || net.swordie.ms.constants.FieldConstants.teleportWouldBreak(null, toField)) {
                chr.dispose();
                return;
            }
            chr.warp(toField);
        }
    }

    @Handler(op = InHeader.FUNC_KEY_MAPPED_MODIFIED)
    public static void handleFuncKeyMappedModified(Char chr, InPacket inPacket) {
        int mode = 0;
        if (JobConstants.isBeastTamer(chr.getJob())) {
            var tsm = chr.getTemporaryStatManager();
            var option = tsm.getOption(CharacterTemporaryStat.BeastMode);

            if (option != null) {
                int reason = option.rOption;

                switch (reason) {
                    case BeastTamer.BEAR_MODE:
                        mode = 1;
                        break;
                    case BeastTamer.SNOW_LEOPARD_MODE:
                        mode = 2;
                        break;
                    case BeastTamer.HAWK_MODE:
                        mode = 3;
                        break;
                    case BeastTamer.CAT_MODE:
                        mode = 4;
                        break;
                }
            }
        }

        while (chr.getFuncKeyMap().size() < mode + 1) {
            chr.getFuncKeyMap().add(FuncKeyMap.getDefaultMapping(0));
        }

        // v265: byte type; 0 = key list (byte preset, int -1, byte count, then byte key, byte type, int id),
        // 1/2/6 = pet HP/MP/cure potion (int item), 3/4 = preset switches (byte)
        int updateType = inPacket.decodeByte();
        switch (updateType) {
            case 0 -> {
                int preset = inPacket.decodeByte();
                inPacket.decodeInt(); // -1
                int size = inPacket.decodeByte() & 0xFF;
                // one key map is kept (sent as every preset): the client's own defaults for its other presets,
                // sent after login, must not overwrite it
                if (preset != 0 && !JobConstants.isBeastTamer(chr.getJob())) {
                    break;
                }
                var fkm = chr.getFuncKeyMap().get(JobConstants.isBeastTamer(chr.getJob()) ? mode : 0);
                for (int i = 0; i < size; i++) {
                    int index = inPacket.decodeByte() & 0xFF;
                    byte type = inPacket.decodeByte();
                    int value = inPacket.decodeInt();
                    if (!net.swordie.ms.client.character.keys.FuncKeyRules.isAllowed(type, value)) {
                        type = 0;
                        value = 0;
                    }
                    fkm.putKeyBinding(index, type, value);
                }
            }
            case 1 -> chr.setPetHpPotion(inPacket.decodeInt());
            case 2 -> chr.setPetMpPotion(inPacket.decodeInt());
            case 6 -> chr.setPetAcPotion(inPacket.decodeInt());
            default -> {
                // 3, 4: key preset switches, not kept
            }
        }
    }


    @Handler(op = InHeader.USER_CHARACTER_INFO_REQUEST)
    public static void handleUserCharacterInfoRequest(Client c, InPacket inPacket) {
        Char chr = c.getChr();
        Field field = chr.getField();
        inPacket.decodeInt(); // tick
        // v265 asks by name (reference server), not by character id
        String name = inPacket.decodeString();
        Char requestChar = chr.getName().equals(name) ? chr : field.getCharByName(name);
        if (requestChar == null) {
            chr.chatMessage(SystemNotice, "The character you tried to find could not be found.");
            chr.dispose();
        } else {
            c.write(FieldPacket.characterInfo(requestChar));
        }
    }


    @Handler(op = InHeader.EVENT_UI_REQ)
    public static void handleEventUiReq(Client c, InPacket inPacket) {
        //TODO: get opcodes for CUIContext::OnPacket
    }


    @Handler(op = InHeader.BATTLE_RECORD_ON_OFF_REQUEST)
    public static void handleBattleRecordOnOffRequest(Client c, InPacket inPacket) {
        // CBattleRecordMan::RequestOnCalc
        boolean on = inPacket.decodeByte() != 0;
        boolean isNew = inPacket.decodeByte() != 0;
        boolean clear = inPacket.decodeByte() != 0;
        c.getChr().setBattleRecordOn(on);
        c.write(BattleRecordMan.serverOnCalcRequestResult(on));
    }

    @Handler(op = InHeader.BATTLE_RECORD_MAX_DAMAGE)
    public static void handleBattleRecordMaxDamage(Client c, InPacket inPacket) {
        boolean hasMore = inPacket.decodeByte() != 0;
        inPacket.decodeInt(); // max damage, 999999999 when the record was reset
        inPacket.decodeInt();
        if (hasMore) {
            inPacket.decodeInt();
        }
    }

    @Handler(op = InHeader.USER_SIT_REQUEST)
    public static void handleUserSitRequest(Char chr, InPacket inPacket) {
        int fieldSeatId = inPacket.decodeShort();

        if (fieldSeatId == -1) {
            chr.getUp();
        }
    }

    @Handler(op = InHeader.USER_PORTABLE_CHAIR_SIT_REQUEST)
    public static void handleUserPortableChairSitRequest(Char chr, InPacket inpacket) {
        Field field = chr.getField();
        int fieldId = inpacket.decodeInt(); // fieldId
        int itemId = inpacket.decodeInt(); // item id
        int bagIndex = inpacket.decodeInt(); // bag Index
        byte chairBag = inpacket.decodeByte(); // is PortableChair in a bag
        inpacket.decodeInt(); // v265: -1 before the position
        Position position = inpacket.decodePositionInt(); // Position

        ItemInfo itemInfo = ItemData.getItemInfoByID(itemId);
        if (itemInfo == null) {
            chr.dispose();
            return;
        }

        if (itemId == PERFECT_TACTICAL_RELAY_CHAIR_2_0) { // Perfect Tactical Relay Chair 2.0
            chr.chatMessage("This chair has been temporarily disabled.");
            chr.dispose();
            return;
        }

        CustomChairType type = itemInfo.getCustomChairInfo() != null ? itemInfo.getCustomChairInfo().getChairType() : null;
        PortableChair chair;
        try {
            chair = PortableChairFactory.getBy(chr, type, itemId, inpacket);
        } catch (RuntimeException e) {
            // the client is already seated: an unread chair layout must not leave it with no chair to get up from
            log.warn(String.format("Chair %d (%s): data not read (%s)", itemId, type, e));
            chair = new PortableChair(chr, itemId);
        }

        chr.sit(chair);
    }

    @Handler(op = InHeader.USER_TOWER_CHAIR_SETTING)
    public static void handleUserTowerChairSetting(Char chr, InPacket inPacket) {
        Field field = chr.getField();
        int unk = inPacket.decodeInt();

        QuestManager qm = chr.getQuestManager();
        Quest q = qm.getQuestById(QuestConstants.TOWER_CHAIR);
        if (q == null) {
            q = QuestData.createQuestFromId(QuestConstants.TOWER_CHAIR);
            qm.addQuest(q);
        }

        for (int i = 0; i < 6; i++) {
            q.setProperty(String.format("%d", i), inPacket.decodeInt());
        }

        chr.write(WvsContext.message(MessagePacket.questRecordExMessage(q)));

        chr.write(WvsContext.towerChairSettingResult());
        chr.getUp();

    }

    @Handler(op = InHeader.USER_EMOTION)
    public static void handleUserEmotion(Client c, InPacket inPacket) {
        Char chr = c.getChr();
        int emotion = inPacket.decodeInt();
        int duration = inPacket.decodeInt();
        boolean byItemOption = inPacket.decodeByte() != 0;
        if (GameConstants.isValidEmotion(emotion)) {
            chr.getField().broadcastPacket(UserRemote.emotion(chr.getId(), emotion, duration, byItemOption), chr);
        }
    }

    @Handler(op = InHeader.USER_ACTIVATE_EFFECT_ITEM)
    public static void handleUserActivateEffectItem(Client c, InPacket inPacket) {
        Char chr = c.getChr();
        int itemId = inPacket.decodeInt();
        if (chr.hasItem(itemId)) {
            chr.setActiveEffectItemID(itemId);
        }
    }

    @Handler(op = InHeader.USER_SOUL_EFFECT_REQUEST)
    public static void handleUserSoulEffectRequest(Client c, InPacket inPacket) {
        Char chr = c.getChr();
        boolean set = inPacket.decodeByte() != 0;

        QuestManager qm = chr.getQuestManager();
        Quest q = qm.getQuestById(QuestConstants.SOUL_EFFECT);
        if (q == null) {
            q = QuestData.createQuestFromId(QuestConstants.SOUL_EFFECT);
            qm.addQuest(q);
        }
        q.setProperty("effect", set ? 1 : 0);
        chr.showUserSoulEffect();
    }

    /** The Ability window's Honor EXP reset of one preset; locked lines come as whole lines. */
    @Handler(op = InHeader.USER_REQUEST_CHARACTER_POTENTIAL_SKILL_RAND_VALUE_SET)
    public static void handleUserRequestCharacterPotentialSkillRandValueSet(Char chr, InPacket inPacket) {
        CharacterPotentialMan cpm = chr.getPotentialMan();
        int preset = inPacket.decodeByte();
        int size = inPacket.decodeInt();
        Set<Integer> lockedLines = new HashSet<>();
        for (int i = 0; i < size && i < 3; i++) {
            lockedLines.add((int) inPacket.decodeByte()); // key
            inPacket.decodeInt(); // skill
            inPacket.decodeByte(); // slv
            inPacket.decodeByte(); // grade
        }
        if (!CharacterPotentialMan.isValidPreset(preset)) {
            chr.dispose();
            return;
        }
        int cost = cpm.getRerollCost(preset, lockedLines.size());
        if (cost > chr.getHonorExp()) {
            chr.chatMessage("You do not have enough honor exp for that action.");
            chr.getOffenseManager().addOffense(String.format("Character %d tried to reset honor without having enough exp (required %d, has %d)",
                    chr.getId(), cost, chr.getHonorExp()));
            chr.dispose();
            return;
        }

        // the same reroll as @menu's (CharacterPotentialMan#rerollWithHonor)
        String error = cpm.rerollWithHonor(preset, lockedLines);
        if (error != null) {
            chr.chatMessage(error);
            chr.dispose();
        }
    }

    @Handler(op = InHeader.USER_REQUEST_CHARACTER_POTENTIAL_SKILL_CHANGE_PRESET)
    public static void handleUserRequestCharacterPotentialSkillChangePreset(Char chr, InPacket inPacket) {
        if (!chr.getPotentialMan().changePreset(inPacket.decodeByte())) {
            chr.dispose();
        }
    }

    @Handler(op = InHeader.RUNE_STONE_USE_REQ)
    public static void handleRuneStoneUseRequest(Client c, InPacket inPacket) {
        Char chr = c.getChr();
        int runestoneSkillId = RuneStone.SEALED_RUNE_POWER;

        int runestoneId = inPacket.decodeInt(); // unknown
        RuneType runeType = RuneType.getByVal((byte) inPacket.decodeInt());
        RuneStone runeStone = chr.getField().getRuneStone();

        // No Rune
        if (runeStone == null) {
            chr.dispose();
            return;
        }

        // Chr on Rune Cooltime
        if (chr.hasSkillOnCooldown(runestoneSkillId)) {
            var cdTime = chr.getRemainingCoolTime(runestoneSkillId);
            chr.write(FieldPacket.runeStoneUseAck(chr.getFieldID(), RuneStoneAckType.CantUseAnotherRuneYet, runestoneId, null, (int) cdTime));
            return;
        }

        // Rune is too strong for chr
        int minLevel = chr.getField().getMobs().stream().mapToInt(m -> m.getForcedMobStat().getLevel()).min().orElse(0);
        if (minLevel - GameConstants.RUNE_LEVEL_RANGE > chr.getLevel()) {
            chr.write(FieldPacket.runeStoneUseAck(chr.getFieldID(), RuneStoneAckType.TooStrongForYouToHandle_Rune));
            return;
        }

        // Rune is too far away
        Rect rect = runeStone.getPosition().getRectAround(GameConstants.RUNE_USE_RECT);
        if (!rect.hasPositionInside(chr.getPosition())) {
            chr.write(FieldPacket.runeStoneUseAck(chr.getFieldID(), RuneStoneAckType.TooFarAway));
            return;
        }


        // Send Arrow Message
        var arrows = RuneStoneArrows.random();
        chr.getOffenseManager().resetRunestoneInfo();
        chr.getOffenseManager().setRandArrows(arrows.getArrows());
        chr.write(FieldPacket.runeStoneShowArrows(arrows));
    }

    /** Sent by the client once the arrows are done: byte 1 = all arrows right, 0 = failed or cancelled. */
    @Handler(op = InHeader.RUNE_STONE_END_NOTICE)
    public static void handleRuneStoneEndNotice(Char chr, InPacket inPacket) {
        var om = chr.getOffenseManager();
        boolean success = inPacket.decodeByte() == 1;
        Field field = chr.getField();
        RuneStone runeStone = field.getRuneStone();
        boolean started = om.getRandArrows() != null;
        om.resetRunestoneInfo();
        if (!success || !started || runeStone == null || chr.hasSkillOnCooldown(RuneStone.SEALED_RUNE_POWER)) {
            return;
        }
        RuneStoneFieldModule.useRuneStone(chr, field, runeStone);
        runeStone.activateRuneStoneEffect(chr);
        chr.addSkillCoolTime(RuneStone.SEALED_RUNE_POWER, GameConstants.RUNE_COOLDOWN_TIME * 60 * 1000);
    }

    @Handler(op = InHeader.RUNE_STONE_ARROW_INPUT)
    public static void handleRuneStoneArrowInput(Char chr, InPacket inPacket) {
        Field field = chr.getField();
        var om = chr.getOffenseManager();

        RuneStone runeStone = field.getRuneStone();
        if (runeStone == null) {
            return;
        }

        var idx = inPacket.decodeInt();
        var arrowSide = inPacket.decodeInt();

        // the client judges the arrows and reports in RUNE_STONE_END_NOTICE; key numbering not confirmed yet, so only log
        var expected = om.getRandArrows();
        if (expected != null && idx >= 0 && idx < expected.length && arrowSide != expected[idx]) {
            log.debug("Rune arrow {} of {}: input {} but the set has {}", idx, chr.getName(), arrowSide, expected[idx]);
        }
        om.setCorrectArrows(om.getCorrectArrows() + 1);

    }

    @Handler(op = InHeader.MONSTER_COLLECTION_EXPLORE_REQ)
    public static void handleMonsterCollectionExploreReq(Char chr, InPacket inPacket) {
        int region = inPacket.decodeInt();
        int session = inPacket.decodeInt();
        int group = inPacket.decodeInt();
        int key = region * 10000 + session * 100 + group;
        Account account = chr.getAccount();
        MonsterCollection mc = account.getMonsterCollection();
        MonsterCollectionExploration mce = mc.getExploration(region, session, group);
        boolean complete = mc.isComplete(region, session, group);
        if (complete && mce == null) {
            // starting an exploration
            if (mc.getOpenExplorationSlots() <= 0) {
                chr.write(WvsContext.monsterCollectionResult(MonsterCollectionResultType.NotEnoughExplorationSlots, null, 0));
                return;
            }
            mce = mc.createExploration(region, session, group);
            mc.addExploration(mce);
            chr.write(WvsContext.message(MessagePacket.collectionRecordMessage(mce.getPosition(), mce.getValue(true))));
            chr.write(WvsContext.monsterCollectionResult(MonsterCollectionResultType.ExploreBegin, null, 0));
        } else {
            // trying to start an incomplete/already exploring group
            chr.write(WvsContext.monsterCollectionResult(MonsterCollectionResultType.NoMonstersForExploring, null, 0));
        }
        chr.dispose(); // still required even if you send a collection result
    }

    // v265 sends the boss window's teleport as CHECK_BOSS_PARTY_BY_SCRIPT: boss, difficulty, map
    @Handler(ops = {InHeader.BOSS_TELEPORT_REQUEST, InHeader.CHECK_BOSS_PARTY_BY_SCRIPT})
    public static void handleBossTeleportRequest(Char chr, InPacket inPacket) {
        var idx = inPacket.decodeInt();
        var difficulty = inPacket.decodeInt(); // same teleport anyway
        var mapId = inPacket.decodeInt();
        // BOSS_TELEPORT_REQUEST has one more int; take whichever of the two is a boss window map
        if (!BossUiAccess.isTeleportMap(mapId) && inPacket.getUnreadAmount() >= 4) {
            int next = inPacket.decodeInt();
            if (BossUiAccess.isTeleportMap(next)) {
                mapId = next;
            }
        }
        log.info(String.format("[Boss] %s boss window teleport: boss %d, difficulty %d, map %d", chr.getName(), idx,
                difficulty, mapId));
        // only the boss window's destinations: the map id comes from the client, which could ask for any map
        if (!BossUiAccess.isTeleportMap(mapId)) {
            chr.getOffenseManager().addOffense(String.format("Tried to use the boss teleport to map %d.", mapId));
            chr.dispose();
            return;
        }
        bossTeleport(chr, mapId);
    }

    /** The boss window's Enter button: boss (window order), practice mode, byte, difficulty, 3 bytes */
    @Handler(op = InHeader.BOSS_UI_REQUEST)
    public static void handleBossUiRequest(Char chr, InPacket inPacket) {
        int boss = inPacket.decodeInt();
        boolean practice = inPacket.decodeByte() != 0;
        inPacket.decodeByte();
        int difficulty = inPacket.decodeInt();
        int[] entry = BossUiAccess.getEntry(boss, difficulty);
        log.info(String.format("[Boss] %s boss window Enter: boss %d, difficulty %d -> %s", chr.getName(), boss,
                difficulty, entry == null ? "no entry" : "map " + entry[0] + ", level " + entry[1]));
        if (entry == null || !BossUiAccess.isTeleportMap(entry[0])) {
            chr.chatMessage("That boss can't be entered from the boss window yet.");
            chr.dispose();
            return;
        }
        if (chr.getLevel() < entry[1]) {
            chr.chatMessage(String.format("You need to be level %d to enter.", entry[1]));
            chr.dispose();
            return;
        }
        chr.setBossPractice(practice);
        if (practice) {
            chr.chatMessage("Practice Mode: this run gives no rewards, no clear and uses no entry.");
        }
        bossTeleport(chr, entry[0]);
    }

    @Handler(op = InHeader.DAILY_GIFT_REQUEST)
    public static void handleDailyGiftRequest(Char chr, InPacket inPacket) {
        net.swordie.ms.client.character.DailyGiftModule.claim(chr);
    }

    private static void bossTeleport(Char chr, int mapId) {
        if (chr.getInstance() == null && chr.getHP() > 0 && !chr.isTalkingToNpc() && chr.getMiniRoom() == null) {
            chr.getScriptManager().setReturnField(chr.getFieldID());
            chr.warp(mapId);
        } else {
            chr.dispose();
        }
    }

    @Handler(op = InHeader.MONSTER_COLLECTION_COMPLETE_REWARD_REQ)
    public static void handleMonsterCollectionCompleteRewardReq(Char chr, InPacket inPacket) {
        int reqType = inPacket.decodeInt(); // 0 = group
        int region = inPacket.decodeInt();
        int session = inPacket.decodeInt();
        int group = inPacket.decodeInt();
        int exploreIndex = inPacket.decodeInt();
        MonsterCollection mc = chr.getAccount().getMonsterCollection();
        switch (reqType) {
            case 0: // group
                MonsterCollectionGroup mcs = mc.getGroup(region, session, group);
                if (mcs != null && !mcs.isRewardClaimed() && mc.isComplete(region, session, group)) {
                    Tuple<Integer, Integer> rewardInfo = MonsterCollectionData.getReward(region, session, group);
                    Item item = ItemData.getItemDeepCopy(rewardInfo.getLeft());
                    int quantity = rewardInfo.getRight();
                    if (quantity == 0) {
                        quantity = 1;
                    }
                    item.setQuantity(quantity);
                    chr.addItemToInventory(item);
                    mcs.setRewardClaimed(true);
                    chr.write(WvsContext.monsterCollectionResult(MonsterCollectionResultType.CollectionCompletionRewardSuccess, null, 0));
                } else if (mcs != null && mcs.isRewardClaimed()) {
                    chr.write(WvsContext.monsterCollectionResult(MonsterCollectionResultType.AlreadyClaimedReward, null, 0));
                } else {
                    chr.write(WvsContext.monsterCollectionResult(MonsterCollectionResultType.CompleteCollectionBeforeClaim, null, 0));
                }
                break;
            case 4: // exploration
                MonsterCollectionExploration mce = mc.getExploration(region, session, group);
                if (mce != null && mce.getEndDate().isExpired()) {
                    mc.removeExploration(mce);
                    net.swordie.ms.client.character.achievement.MapleAchievements.onExplorationDone(chr,
                            net.swordie.ms.loaders.MonsterCollectionData.getExplorationMinutes(region, session, group));
                    chr.write(WvsContext.message(MessagePacket.collectionRecordMessage(mce.getPosition(), mce.getValue(false))));
                    chr.write(WvsContext.monsterCollectionResult(MonsterCollectionResultType.CollectionCompletionRewardSuccess, null, 0));
                } else {
                    chr.write(WvsContext.monsterCollectionResult(MonsterCollectionResultType.TryAgainInAMoment, null, 0));
                }
                break;
            default:
                log.warn("Unhandled MonsterCollectionCompleteRewardReq type " + reqType);
                chr.write(WvsContext.monsterCollectionResult(MonsterCollectionResultType.TryAgainInAMoment, null, 0));

        }
        chr.dispose(); // still required even if you send a collection result
    }

    @Handler(op = InHeader.USER_EFFECT_LOCAL)
    public static void handleUserEffectLocal(Char chr, InPacket inPacket) {
        if (chr == null) {
            return;
        }

        int skillId = inPacket.decodeInt();
        int slv = inPacket.decodeByte();
        boolean sendLocal = false;
        if (inPacket.getUnreadAmount() > 0) {
            sendLocal = inPacket.decodeByte() != 0;
        }

        int chrId = chr.getId();
        Field field = chr.getField();
        Effect effect = null;

        if (!chr.hasSkill(skillId)) {
            chr.getOffenseManager().addOffense(String.format("Character {%d} tried to use a skill {%d} they do not have.", chrId, skillId));
            return;
        }

        if (skillId == Evan.DRAGON_FURY) {
            effect = Effect.showDragonFuryEffect(skillId, slv, 0, true);

        } else if (skillId == DarkKnight.FINAL_PACT) {
            effect = Effect.showFinalPactEffect(skillId, slv, 0, true);

        } else if (skillId == WildHunter.CALL_OF_THE_HUNTER) {
            effect = Effect.showCallOfTheHunterEffect(skillId, slv, 0, chr.isLeft(), chr.getPosition().getX(), chr.getPosition().getY());

        } else if (skillId == Kaiser.VERTICAL_GRAPPLE || skillId == AngelicBuster.GRAPPLING_HEART || skillId == Job.ROPE_LIFT) { // 'Grappling Hook' Skills
            int chrPositionY = inPacket.decodeInt();
            Position ropeConnectDest = inPacket.decodePositionInt();
            effect = Effect.showVerticalGrappleEffect(skillId, chr.getLevel(), slv, 0, chrPositionY, ropeConnectDest.getX(), ropeConnectDest.getY());

        } else if (skillId == ThunderBreaker.FLASH/*TB  Flash*/ || skillId == Shade.FOX_TROT
                || skillId == Jett.VORTEX_JUMP/*Jett Flash*/ || skillId == Shadower.TRICKBLADE_FINISHER
                || skillId == Shadower.INTO_DARKNESS || skillId == Illium.CRYSTALLINE_WINGS) { // 'Flash' Skills
            Position origin = inPacket.decodePositionInt();
            Position dest = inPacket.decodePositionInt();
            effect = Effect.showFlashBlinkEffect(skillId, slv, 0, origin.getX(), origin.getY(), dest.getX(), dest.getY());

        } else if (SkillConstants.isSuperNovaSkill(skillId)) { // 'SuperNova' Skills
            Position chrPosition = inPacket.decodePositionInt();
            effect = Effect.showSuperNovaEffect(skillId, slv, 0, chrPosition.getX(), chrPosition.getY());

        } else if (SkillConstants.isUnregisteredSkill(skillId)) { // 'Unregistered' Skills
            effect = Effect.showUnregisteredSkill(skillId, slv, 0, chr.isLeft());

        } else if (SkillConstants.isHomeTeleportSkill(skillId)) {
            effect = Effect.skillUse(skillId, chr.getLevel(), slv, 0);

        } else if (skillId == BattleMage.DARK_SHOCK) {
            Position origin = inPacket.decodePositionInt();
            Position dest = inPacket.decodePositionInt();
            effect = Effect.showDarkShockSkill(skillId, slv, origin, dest);
        } else if (skillId == Xenon.TRIANGULATION) {
            
        } else {
            if (Server.DEBUG) {
                log.error(String.format("Unhandled Remote Effect Skill id %d", skillId));
                chr.debugMessage(String.format("Unhandled Remote Effect Skill:  id = %d", skillId));
            }
        }

        if (effect != null) {
            if (sendLocal) {
                chr.write(UserPacket.effect(effect));
            }
            if (chr.getCopy() != null) {
                chr.write(UserRemote.effect(1337, effect));
            }
            field.broadcastPacket(UserRemote.effect(chr.getId(), effect));
        }
    }

    @Handler(op = InHeader.REQUEST_EVENT_LIST)
    public static void handleEventListRequest(Client c, InPacket inPacket) {
        boolean show = true;
        var eventList = EventListData.getActiveEvents();
        c.write(WvsContext.requestEventList(c.getChr().getLevel(), show, eventList));
        c.write(WvsContext.eventListResult(eventList));
    }

    @Handler(op = InHeader.ACHIEVEMENT_REQUEST)
    public static void handleAchievementRequest(Char chr, InPacket inPacket) {
        net.swordie.ms.client.character.achievement.MapleAchievements.handleRequest(chr, inPacket.decodeInt(), inPacket);
    }

    @Handler(op = InHeader.USER_FOLLOW_CHARACTER_REQUEST)
    public static void handleUserFollowCharacterRequest(Char chr, InPacket inPacket) {
        Field field = chr.getField();
        int driverChrId = inPacket.decodeInt();
        short unk = inPacket.decodeShort();

        Char driverChr = field.getCharByID(driverChrId);
        if (driverChr == null) {
            return;
        }
        driverChr.write(WvsContext.setPassenserRequest(chr.getId()));
    }

    @Handler(op = InHeader.USER_FOLLOW_CHARACTER_WITHDRAW)
    public static void handleUserFollowCharacterWithdraw(Char chr, InPacket inPacket) {
        byte isWithdrawable = inPacket.decodeByte();
        if (isWithdrawable == 0) {
            chr.write(UserPacket.followCharacter(chr.getId(), 0, false, null));
        }
    }

    @Handler(op = InHeader.SET_PASSENGER_RESULT)
    public static void handleSetPassengerResult(Char chr, InPacket inPacket) {
        Field field = chr.getField();
        int requestorChrId = inPacket.decodeInt();
        boolean accepted = inPacket.decodeByte() != 0;
        Char requestorChr = field.getCharByID(requestorChrId);

        if (!accepted) {

            int errorType = inPacket.decodeInt();
            switch (errorType) {

            }

        } else {
            requestorChr.write(UserPacket.followCharacter(requestorChr.getId(), chr.getId(), false, new Position()));

        }
    }

    @Handler(op = InHeader.QUICKSLOT_KEY_MAPPED_MODIFIED)
    public static void handleQuickslotKeyMappedModified(Char chr, InPacket inPacket) {
        final int length = GameConstants.QUICKSLOT_LENGTH;
        List<Integer> quickslotKeys = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            quickslotKeys.add(inPacket.decodeInt());
        }
        chr.setQuickslotKeys(quickslotKeys);
    }

    @Handler(op = InHeader.USER_CATCH_DEBUFF_COLLISION)
    public static void handleUserCatchDebuffCollision(Char chr, InPacket inPacket) {
        if(FieldConstants.isPierreField(chr.getFieldID()) && !chr.isDead()){
            MobSkillInfo msi = SkillData.getMobSkillInfoByIdAndLevel(MobSkillID.Lapidification.getVal(), 3);
            Option o = new Option(MobSkillID.Lapidification.getVal());
            o.slv = 3;
            o.nOption = 1;
            o.tOption = msi.getSkillStatIntValue(MobSkillStat.time);
            TemporaryStatManager tsm = chr.getTemporaryStatManager();
            tsm.putCharacterStatValueFromMobSkill(CharacterTemporaryStat.Lapidification, o);
            tsm.sendSetStatPacket();
        } else if (FieldConstants.isVellumField(chr.getFieldID()) && !chr.isDead()) {
            MobSkillInfo msi = SkillData.getMobSkillInfoByIdAndLevel(MobSkillID.Stun.getVal(), 43);
            Option o = new Option(MobSkillID.Stun.getVal());
            o.slv = 43;
            o.nOption = 1;
            o.tOption = msi.getSkillStatIntValue(MobSkillStat.time);
            TemporaryStatManager tsm = chr.getTemporaryStatManager();
            tsm.putCharacterStatValueFromMobSkill(CharacterTemporaryStat.Stun, o);
            tsm.sendSetStatPacket();
        }
        else {
            int hpR = inPacket.decodeInt();
            chr.damage(chr.getHPPerc(hpR), true);
        }
    }

    @Handler(op = InHeader.USER_DAZZLE_HIT)
    public static void handleUserDazzleHit(Char chr, InPacket inPacket){
        if(chr.isDead()) return;

        // v265 0x143625820: sent when a player charmed by Dazzle (Attract from mob skill 188) touches the Alluring
        // Mirror or Gollux's head / abdomen; int template id
        int templateID = inPacket.decodeInt();
        Mob mob = MobData.getMobDeepCopyById(templateID);
        if (mob == null || !chr.getTemporaryStatManager().hasStat(CharacterTemporaryStat.Attract)) {
            return;
        }
        // the mirror has 188 as a skill, Gollux as an attack's status
        MobSkill ms = Util.findWithPred(mob.getSkills(), it -> it.getSkillID() == MobSkillID.Dazzle.getVal());
        int level = ms != null ? ms.getLevel() : 0;
        if (ms == null && mob.getMobInfo() != null) {
            MobSkill attack = Util.findWithPred(mob.getMobInfo().getAttacks(), it -> it.getDisease() == MobSkillID.Dazzle.getVal());
            level = attack == null ? 0 : attack.getLevel();
        }
        MobSkillInfo msi = level <= 0 ? null : SkillData.getMobSkillInfoByIdAndLevel(MobSkillID.Dazzle.getVal(), level);
        if (msi == null) {
            return;
        }
        chr.getTemporaryStatManager().removeStat(CharacterTemporaryStat.Attract);
        chr.getTemporaryStatManager().sendResetStatPacket();
        // x: the share of max HP it takes (50 for the mirror and Gollux, 100 for Alang); bossHeal: the boss gets back
        int share = msi.getSkillStatIntValue(MobSkillStat.x);
        chr.damage(share > 0 ? chr.getHPPerc(Math.min(100, share)) : chr.getMaxHP(), true);
        int bossHeal = msi.getSkillStatIntValue(MobSkillStat.bossHeal);
        if (bossHeal > 0) {
            for (Mob m : chr.getField().getMobs()) {
                if (m.isBoss() && m.isAlive()) {
                    net.swordie.ms.life.mob.skill.MobSkillModule.healMobPercent(m, bossHeal);
                }
            }
        }
    }

    @Handler(op = InHeader.USER_RECIPE_COOLTIME_REQUEST)
    public static void handleUserRecipeInfoRequest(Char chr, InPacket inPacket) {
        int recipe = inPacket.decodeInt();
        chr.write(UserLocal.recipeCooltimeResult(recipe, 0));
    }

    @Handler(op = InHeader.REQUEST_FIRST_ENTER_REWARD)
    public static void handleFirstEnterRewardClaimRequest(Char chr, InPacket inPacket){
        int id = inPacket.decodeInt();
        int Type = inPacket.decodeInt();
        int itemId = inPacket.decodeInt();
        int quantity = inPacket.decodeInt();
        int responseType = inPacket.decodeInt();
        FileTime ft = inPacket.decodeFT();
        int unk1 = inPacket.decodeInt();
        int unk2 = inPacket.decodeInt();
        int unk3 = inPacket.decodeInt();
        int unk4 = inPacket.decodeInt();
        int unk5 = inPacket.decodeInt();
        int unk6 = inPacket.decodeInt();
        int unk7 = inPacket.decodeInt();
        String unk8  = inPacket.decodeString();
        String unk9  = inPacket.decodeString();
        String unk10  = inPacket.decodeString();
        int unk11 = inPacket.decodeInt();
        int unk12 = inPacket.decodeInt();
        boolean refused =  inPacket.decodeByte() == 2; // 2 = refused, 1 is accepted

        FirstEnterReward claimTarget = null;
        for(FirstEnterReward firstEnterReward : chr.getFirstEnterRewards()){
            if(firstEnterReward.getId() == id){
                claimTarget = firstEnterReward;
                continue;
            }
        }
        if(claimTarget == null){
            return;
        }
        if (FirstEnterReward.isRemovedSource(claimTarget.getDescription())) {
            chr.removeFirstEnterReward(claimTarget);
            chr.write(WvsContext.firstEnterReward(chr.getFirstEnterRewards(), FirstEnterRewardPacketType.Load_Items, 0));
            return;
        }

        if(refused){ //Refused reward
            chr.removeFirstEnterReward(claimTarget);
            chr.write(WvsContext.firstEnterReward(chr.getFirstEnterRewards(), FirstEnterRewardPacketType.Load_Items, 0));
            return;
        }

        if(claimTarget.getExpireTime().isExpired()) {
            chr.write(WvsContext.firstEnterReward(chr.getFirstEnterRewards(), FirstEnterRewardPacketType.Time_Expired, 0));
            chr.removeFirstEnterReward(claimTarget);
            chr.write(WvsContext.firstEnterReward(chr.getFirstEnterRewards(), FirstEnterRewardPacketType.Load_Items, 0));
            return;
        }

        boolean claimed = false;
        switch(claimTarget.getRewardType())
        {
            case GameItem:
            case CashItem:
                if (net.swordie.ms.client.character.FirstEnterReward.claimsToLocker(claimTarget.getItemId())) {
                    // e.g. a Surprise Style Box only opens from the Cash Shop storage
                    net.swordie.ms.client.character.FirstEnterReward.claimToLocker(chr, claimTarget.getItemId(),
                            claimTarget.getQuantity());
                    chr.write(WvsContext.firstEnterReward(chr.getFirstEnterRewards(), FirstEnterRewardPacketType.Item_Claimed, claimTarget.getQuantity()));
                    claimed = true;
                } else if(chr.canHold(claimTarget.getItemId(), claimTarget.getQuantity())){
                    chr.addItemToInventory(claimTarget.getItemId(), claimTarget.getQuantity());
                    chr.write(WvsContext.firstEnterReward(chr.getFirstEnterRewards(), FirstEnterRewardPacketType.Item_Claimed, claimTarget.getQuantity()));
                    claimed = true;
                }
                break;
            case MaplePoints:
                chr.addNx(claimTarget.getQuantity());
                chr.write(WvsContext.firstEnterReward(chr.getFirstEnterRewards(), FirstEnterRewardPacketType.Nx_Claimed, claimTarget.getQuantity()));
                claimed = true;
                break;
            case Meso:
                if(chr.canAddMoney(claimTarget.getQuantity())){
                    chr.addMoney(claimTarget.getQuantity());
                    chr.write(WvsContext.firstEnterReward(chr.getFirstEnterRewards(), FirstEnterRewardPacketType.Meso_Claimed, claimTarget.getQuantity()));
                    claimed = true;
                }
                break;
            case Exp:
                chr.addExp(claimTarget.getQuantity());
                chr.write(WvsContext.firstEnterReward(chr.getFirstEnterRewards(), FirstEnterRewardPacketType.Exp_Claimed, claimTarget.getQuantity()));
                claimed = true;
                break;
            default:
                chr.chatMessage("Unknown reward type");
                break;
        }
        if(claimed){
            chr.removeFirstEnterReward(claimTarget);
            chr.write(WvsContext.firstEnterReward(chr.getFirstEnterRewards(), FirstEnterRewardPacketType.Load_Items, 0));
        }
    }

    @Handler(op = InHeader.REWARD_MOB_LIST_REQUEST)
    public static void rewardMobListRequest(Char chr, InPacket inPacket) {
        short reqCount = inPacket.decodeShort();
        var groups = new ArrayList<List<Integer>>(reqCount);

        for (int i = 0; i < reqCount; i++) {
            int itemId = inPacket.decodeInt();
            var raw = DropData.getDropInfoByItemId(itemId);
            var filtered = new ArrayList<Integer>();
            var seen = new HashSet<Integer>(); // Each group has its own seen set

            for (var dropInfo : raw) {
                int mobId = dropInfo.getMobId();
                if (mobId > 0 && seen.add(mobId)) { // Only add valid mobIds and avoid duplicates within group
                    filtered.add(mobId);
                }
            }
            groups.add(filtered);
        }

        // Validation checks
        short groupCount = (short) groups.size();
        if (groupCount < 0) groupCount = 0;
        if (groupCount > 100) groupCount = 100; // client guard

        chr.write(WvsContext.rewardMobListResult(groupCount, groups));
    }

    @Handler(op = InHeader.USER_MEDAL_MODIFIED)
    public static void handleUserMedalModified(Char chr, InPacket inPacket) {
        int questID = QuestConstants.TITLE_MEDAL_SHOW;
        byte isOn = inPacket.decodeByte();
        inPacket.decodeInt();
        QuestManager qm = chr.getQuestManager();
        Quest q = qm.getQuestById(questID);
        if (q == null) {
            qm.addQuestWithQr(chr, questID, "1007=" + isOn + ";1009=1");
        } else {
            q.setProperty("1007", isOn + "");
            chr.write(WvsContext.message(MessagePacket.questRecordExMessage(q)));
        }
        chr.dispose();
    }

    @Handler(op = InHeader.USER_TITLE_MODIFIED)
    public static void handleUserTitleModified(Char chr, InPacket inPacket) {
        int questID = QuestConstants.TITLE_MEDAL_SHOW;
        byte isOn = inPacket.decodeByte();
        inPacket.decodeInt();
        QuestManager qm = chr.getQuestManager();
        Quest q = qm.getQuestById(questID);
        if (q == null) {
            qm.addQuestWithQr(chr, questID, "1007=1;1009=" + isOn);
        } else {
            q.setProperty("1009", isOn + "");
            chr.write(WvsContext.message(MessagePacket.questRecordExMessage(q)));
        }
        chr.dispose();
    }
}
