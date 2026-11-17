package net.swordie.ms.handlers.user;

import net.swordie.ms.constants.BossConstants;
import net.swordie.ms.client.jobs.sengoku.Hayato;
import net.swordie.ms.client.jobs.resistance.demon.DemonSlayer;
import net.swordie.ms.client.jobs.resistance.Xenon;
import net.swordie.ms.client.jobs.resistance.Blaster;
import net.swordie.ms.client.jobs.legend.Shade;
import net.swordie.ms.client.jobs.legend.Phantom;
import net.swordie.ms.client.jobs.legend.Mercedes;
import net.swordie.ms.client.jobs.legend.Aran;
import net.swordie.ms.client.jobs.flora.Illium;
import net.swordie.ms.client.jobs.anima.Ren;
import net.swordie.ms.client.jobs.adventurer.thief.BladeMaster;
import net.swordie.ms.client.jobs.adventurer.pirate.Corsair;
import net.swordie.ms.client.jobs.Zero;
import net.swordie.ms.Server;
import net.swordie.ms.client.Client;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.runestones.RuneStone;
import net.swordie.ms.client.character.skills.ExtraSkillInfo;
import net.swordie.ms.client.character.skills.ExtraSkillInfoManager;
import net.swordie.ms.client.character.skills.SkillUseSource;
import net.swordie.ms.client.character.skills.UserSkillUseInfo;
import net.swordie.ms.client.character.skills.info.AttackInfo;
import net.swordie.ms.client.character.skills.info.MobAttackInfo;
import net.swordie.ms.client.character.skills.info.SkillInfo;
import net.swordie.ms.client.character.skills.info.SkillUseInfo;
import net.swordie.ms.client.jobs.nova.AngelicBuster;
import net.swordie.ms.client.jobs.Job;
import net.swordie.ms.client.jobs.adventurer.BeastTamer;
import net.swordie.ms.client.jobs.adventurer.archer.Marksman;
import net.swordie.ms.client.jobs.adventurer.archer.Pathfinder;
import net.swordie.ms.client.jobs.adventurer.magician.FirePoison;
import net.swordie.ms.client.jobs.adventurer.pirate.Buccaneer;
import net.swordie.ms.client.jobs.cygnus.BlazeWizard;
import net.swordie.ms.client.jobs.cygnus.DawnWarrior;
import net.swordie.ms.client.jobs.cygnus.NightWalker;
import net.swordie.ms.client.jobs.legend.Evan;
import net.swordie.ms.client.jobs.legend.Luminous;
import net.swordie.ms.client.jobs.nova.Cadena;
import net.swordie.ms.client.jobs.nova.Kaiser;
import net.swordie.ms.client.jobs.resistance.BattleMage;
import net.swordie.ms.client.jobs.resistance.WildHunter;
import net.swordie.ms.client.jobs.sengoku.Kanna;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.packet.Effect;
import net.swordie.ms.connection.packet.Summoned;
import net.swordie.ms.connection.packet.UserPacket;
import net.swordie.ms.connection.packet.CFamiliar;
import net.swordie.ms.connection.packet.UserRemote;
import net.swordie.ms.connection.packet.field.FieldPacket;
import net.swordie.ms.constants.FieldConstants;
import net.swordie.ms.constants.JobConstants;
import net.swordie.ms.constants.MobConstants;
import net.swordie.ms.constants.SkillConstants;
import net.swordie.ms.enums.ChatType;
import net.swordie.ms.enums.FieldOption;
import net.swordie.ms.handlers.Handler;
import net.swordie.ms.handlers.header.InHeader;
import net.swordie.ms.handlers.header.OutHeader;
import net.swordie.ms.life.Familiar;
import net.swordie.ms.life.Life;
import net.swordie.ms.life.Summon;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.loaders.SkillData;
import net.swordie.ms.util.Position;
import net.swordie.ms.util.Rect;
import net.swordie.ms.util.Util;
import net.swordie.ms.world.field.Field;
import net.swordie.ms.world.field.fieldeffect.FieldEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static net.swordie.ms.enums.ChatType.Mob;

public class AttackHandler {

    private static final java.util.Set<Integer> NON_TARGET_FLAME_IDS = java.util.Set.of(BlazeWizard.ORBITAL_FLAME_ATOM,
            BlazeWizard.GREATER_ORBITAL_FLAME_ATOM, BlazeWizard.GRAND_ORBITAL_FLAME_ATOM,
            BlazeWizard.GRAND_ORBITAL_FLAME_ATOM_2, BlazeWizard.FINAL_ORBITAL_FLAME_ATOM,
            BlazeWizard.FINAL_ORBITAL_FLAME_ATOM_2, BlazeWizard.FINAL_ORBITAL_FLAME_ATOM_3,
            BlazeWizard.FINAL_ORBITAL_FLAME_ATOM_4, BlazeWizard.PHOENIX_DRIVE_ATOM, BlazeWizard.PHOENIX_DRIVE_ATOM_2,
            BlazeWizard.HEXA_ORBITAL_FLAME_ATOM, BlazeWizard.HEXA_ORBITAL_FLAME_ATOM_2,
            BlazeWizard.HEXA_ORBITAL_FLAME_ATOM_3, BlazeWizard.HEXA_ORBITAL_FLAME_ATOM_4,
            BlazeWizard.HEXA_PHOENIX_DRIVE_ATOM, BlazeWizard.HEXA_PHOENIX_DRIVE_ATOM_2);
    /** a mob id outside this range means the attack was parsed wrong */
    private static final int MOB_ID_MIN = 1_000_000, MOB_ID_MAX = 100_000_000;
    /** stands in for the summon's object id while the attack is shown to the owner's copy */
    private static final int COPY_SUMMON_OBJECT_ID = 13371337;

    private static final Logger log = LogManager.getLogger();


    // No handler, gets called from other handlers
    /** An attack made by the server itself (the same path a player's attack packet takes). */
    public static void attackAs(Client c, AttackInfo attackInfo) {
        handleAttack(c, attackInfo);
    }

    private static void handleAttack(Client c, AttackInfo attackInfo) {
        Char chr = c.getChr();
        int skillID = attackInfo.skillId;
        int slv = attackInfo.slv;
        Field field = chr.getField();
        if (chr.isInTrunk()) {
            // nobody attacks with the storage window open: the storage was closed without the close packet
            chr.setInTrunk(false);
        }

        if (skillID != 0 && (!FieldConstants.isBypassFieldLimitCheckField(field.getId()) && (field.getInfo().getFieldLimit() & FieldOption.SkillLimit.getVal()) > 0
                || (field.getInfo().getFieldLimit() & FieldOption.MoveSkillOnly.getVal()) > 0)
                || attackInfo.skillId == SkillConstants.LEVEL_UP_DAMAGE_SKILL_ID) {
            // don't allow skills on maps where it's not allowed
            // ignore the damage from the level up skill, instakills buffed mobs
            return;
        }

        if (skillID == RuneStone.LIBERATE_THE_DESTRUCTIVE_RUNE
                && (!chr.hasSkillOnCooldown(RuneStone.SEALED_RUNE_POWER)
                || chr.getRemainingCoolTime(RuneStone.SEALED_RUNE_POWER) < 12 * 60 * 1000)
        ) {
            // destro attack only 3 min after rune
            return;
        }

        // Fill in TotalDamageDealt in AttackInfo & Mob info
        for (var mai : attackInfo.mobAttackInfo) {
            mai.mob = field.getLifeByObjectID(Mob.class, mai.mobId); // Find mob
            if (mai.mob != null) {
                mai.totalDamage = Arrays.stream(mai.damages).sum(); // total damage per mob
                mai.mobDies = mai.mob.getHp() <= mai.totalDamage; // check if mob would die

                attackInfo.totalDamageDealt += mai.totalDamage; // add total dmg per mob to attackInfo to get total damage dealt
            }
        }

        if (chr.checkAndSetSkillCooltime(skillID, attackInfo, attackInfo.sui, SkillUseSource.AttackUseRequest) // Check Cooldown and Set Cooldown
                && chr.applyMpCon(skillID, slv, attackInfo, attackInfo.sui, SkillUseSource.AttackUseRequest) // Check MP and Apply MP Consumption
        ) {
            if (chr.isDebugMode()) {
                chr.chatMessage(Mob, "SkillID: " + skillID);
            }
            Job sourceJobHandler = chr.getJobHandler();
            SkillInfo si = SkillData.getSkillInfoById(skillID);
            if (!SkillConstants.isNoExtraSkill(skillID) && si != null && si.getExtraSkillInfo().size() > 0) {
                List<Integer> extraSkillList = new ArrayList<>();
                si.getExtraSkillInfo().forEach(map -> extraSkillList.addAll(map.keySet()));
                List<ExtraSkillInfo> esi = ExtraSkillInfoManager.alterExtraSkillInfo(chr, attackInfo, extraSkillList);
                if (esi.size() > 0) {
                    chr.write(FieldPacket.registerExtraSkill(skillID, esi));
                }
            }
            if (si != null && SkillConstants.isMassSpellSkill(skillID) && chr.getParty() != null) {
                Rect r = si.getFirstRect();
                if (r != null) {
                    Rect rectAround = chr.getRectAround(r);
                    for (Char ptChr : chr.getParty().getPartyMembersInField(chr)) {
                        if (rectAround.hasPositionInside(ptChr.getPosition())) {
                            Effect effect = Effect.skillAffected(skillID, slv, 0);
                            if (ptChr != chr) {  // Caster shouldn't get the Affected Skill Effect
                                chr.getField().broadcastPacket(UserRemote.effect(ptChr.getId(), effect), ptChr);
                                ptChr.write(UserPacket.effect(effect));
                                if (!si.isFinalAttack()) {
                                    sourceJobHandler.handleAttack(c, attackInfo);
                                }
                            }

                        }
                    }
                }
            }
            if (SkillConstants.isBroadcastSkillUseRequest(skillID)) {
                Effect effect = Effect.skillUse(skillID, chr.getLevel(), slv, attackInfo.bySummonedID);
                effect.setSkillUseInfo(attackInfo.sui);
                chr.getField().broadcastPacket(UserRemote.effect(chr.getId(), effect), chr);
            }
            if (si != null && !si.isFinalAttack()) {
                sourceJobHandler.handleAttack(c, attackInfo);
                // the stun/freeze/seal of the attack's data, on mobs the handler gave none
                net.swordie.ms.client.jobs.SkillMobStatus.applyFromData(chr, attackInfo, si);
            }

            // Final Attack Request
            int faSkill = sourceJobHandler.getFinalAttackSkill();
            if (faSkill > 0) {
                List<Integer> mobsHit = attackInfo.mobAttackInfo.stream().map(mai -> mai.mobId).collect(Collectors.toList());
                chr.write(FieldPacket.finalAttackRequest(chr, skillID, faSkill, mobsHit));
            }

            broadcastDamage(attackInfo, chr); // broadcast damage before applying damage
            int multiKillMessage = 0;
            long mobexp = 0;
            for (MobAttackInfo mai : attackInfo.mobAttackInfo) {
                var mob = mai.mob; // setting done before within this method.
                if (mob == null) {
                    if (mai.mobId < MOB_ID_MIN || mai.mobId > MOB_ID_MAX) {
                        chr.chatMessage(ChatType.Expedition, String.format("Wrong attack info parse (probably)! SkillID = %d, Mob ID = %d", skillID, mai.mobId));
                    }
                    continue;
                } else if (mob.getHp() > 0) {
                    if (mai.totalDamage > 0) {
                        net.swordie.ms.client.character.quest.StarForceQuestModule.onAttack(chr);
                    }
                    mob.damage(chr, mai.totalDamage);
                    if (attackInfo.isReflectType()) {
                        var isMagicAttack = attackInfo.attackHeader != null && attackInfo.attackHeader.getValue() == OutHeader.REMOTE_MAGIC_ATTACK.getValue();
                        mob.handleDamageReflect(chr, skillID, mai.totalDamage, isMagicAttack);
                    }
                    // Horntail sponge damage is done by Mob.damage (damageCorrespondingSponge), not here,
                    // or every hit would count twice
                }
                if (!MobConstants.isUnionRaidBoss(mob.getTemplateId()) && mob.getHp() <= 0) {
                    mob.killedByChar(chr, attackInfo);
                    // MultiKill +1,  per killed mob
                    multiKillMessage++;
                    mobexp = mob.getForcedMobStat().getExp();
                }
            }

            // damage check
            if (Server.DEBUG_MOVEMENT) {
                chr.chatMessage("[A] Pos: [%d, %d] | Left: [%s]", attackInfo.chrPos.getX(), attackInfo.chrPos.getY(), attackInfo.left);
            }

            if (chr.getOffenseManager().doSampleTest()) {
                var damageCalc = chr.getDamageCalc();
                damageCalc.checkDamage(attackInfo);
                damageCalc.checkSkillRange(attackInfo);
                // TODO: checkSkillInterval (against nodelay)
            }

            // MultiKill Message Popup & Exp
            if (multiKillMessage > 2) {
                chr.multiKillMessage(multiKillMessage, mobexp);
            }

        }
        Summon summon = attackInfo.summon;
        if (summon != null && attackInfo.attackHeader == OutHeader.SUMMONED_ATTACK && attackInfo.attackActionType == 25) {
            summon.getField().removeSummon(summon);
        }
    }

    private static void reflectDamageToSponge(Char chr, Field field, Mob mob, long totalDamage) {
        if ((mob.getTemplateId() >= BossConstants.EASY_HORNTAIL_HEAD_A && mob.getTemplateId() <= BossConstants.EASY_HORNTAIL_TAIL)) {
            Life life = field.getLifeByTemplateId(BossConstants.HORNTAIL_EASY);
            if (life != null) {
                Mob mob2 = (Mob) life;
                mob2.damage(chr, totalDamage);
                field.broadcastPacket(FieldPacket.fieldEffect(FieldEffect.mobHPTagFieldEffect(mob2)));
            }
        }
        if ((mob.getTemplateId() >= BossConstants.NORMAL_HORNTAIL_HEAD_A && mob.getTemplateId() <= BossConstants.NORMAL_HORNTAIL_TAIL)) {
            Life life2 = field.getLifeByTemplateId(BossConstants.HORNTAIL_NORMAL);
            if (life2 != null) {
                Mob mob2 = (Mob) life2;
                mob2.damage(chr, totalDamage);
                field.broadcastPacket(FieldPacket.fieldEffect(FieldEffect.mobHPTagFieldEffect(mob2)));
            }
        }
        if ((mob.getTemplateId() >= BossConstants.CHAOS_HORNTAIL_HEAD_A && mob.getTemplateId() <= BossConstants.CHAOS_HORNTAIL_TAIL)) {
            Life life3 = field.getLifeByTemplateId(BossConstants.CHAOS_HORNTAIL);
            if (life3 == null) {
                life3 = field.getLifeByTemplateId(BossConstants.HORNTAIL_HARD);
            }
            if (life3 != null) {
                Mob mob3 = (Mob) life3;
                mob3.damage(chr, totalDamage);
                field.broadcastPacket(FieldPacket.fieldEffect(FieldEffect.mobHPTagFieldEffect(mob3)));
            }
        }
    }

    private static void broadcastDamage(AttackInfo attackInfo, Char chr) {
        // TODO fix all remote 38 attacks
        if (SkillConstants.noEncodeAttack(attackInfo.skillId)) {
            return;
        }
        if (net.swordie.ms.ext.ServerHooks.isAutomated(chr)
                && net.swordie.ms.ext.ServerHooks.isUnwatched(chr.getField())) {
            return; // a bot's attack with only bots around: nobody to draw it for
        }
        if (attackInfo.attackHeader != null) {
            switch (attackInfo.attackHeader) {
                case SUMMONED_ATTACK:
                    if (attackInfo.summon == null) {
                        return;
                    }
                    chr.getField().broadcastPacket(Summoned.summonedAttack(chr.getId(), attackInfo, false), chr);
                    if (chr.getCopy() != null) {
                        Summon oldSummon = attackInfo.summon;
                        int oldOID = oldSummon.getObjectId();
                        oldSummon.setObjectId(COPY_SUMMON_OBJECT_ID);
                        Char oldChr = oldSummon.getChr();
                        oldSummon.setChr(chr.getCopy());
                        chr.write(Summoned.summonedAttack(chr.getCopy().getId(), attackInfo, false));
                        oldSummon.setChr(oldChr);
                        oldSummon.setObjectId(oldOID);
                    }
                    break;
                case FAMILIAR_INFO:
                    if (attackInfo.familiar != null) {
                        chr.getField().broadcastPacket(CFamiliar.familiarAttack(attackInfo), chr);
                    }
                    break;
                default:
                    chr.getField().broadcastPacket(UserRemote.attack(chr, attackInfo), chr);
                    Char copy = chr.getCopy();
                    if (copy != null) {
                        chr.write(UserRemote.attack(copy, attackInfo));
                    }
            }
        }
    }


    @Handler(op = InHeader.USER_BODY_ATTACK)
    public static void handleBodyAttack(Client c, InPacket inPacket) {
        AttackInfo ai = new AttackInfo();
        ai.sui = new SkillUseInfo();
        ai.attackHeader = OutHeader.REMOTE_BODY_ATTACK;
        ai.inHeader = InHeader.USER_BODY_ATTACK;
        inPacket.decodeInt();
        inPacket.decodeInt();
        ai.fieldKey = inPacket.decodeByte();
        byte mask = inPacket.decodeByte();
        ai.hits = (byte) (mask & 0xF);
        ai.mobCount = (mask >>> 4) & 0xF;
        inPacket.decodeInt(); // v271: new int after the mask, as the melee writers (no body-attack sender to check)
        ai.skillId = inPacket.decodeInt();
        ai.slv = inPacket.decodeInt();
        inPacket.decodeInt(); // crc
        inPacket.decodeInt();
        inPacket.decodeInt();

        decodeUnknownAttackPacketData(inPacket, ai);

        ai.sui.skillID = ai.skillId;
        ai.sui.slv = ai.slv;
        new UserSkillUseInfo(ai.sui).decode(inPacket);

        if (SkillConstants.isZeroSkill(ai.skillId)) {
            ai.zeroTag = inPacket.decodeByte();
        }
        ai.areaPAD = inPacket.decodeByte() >>> 3;
        inPacket.decodeByte(); // encoded as 0
        short actionMask = inPacket.decodeShort();
        ai.left = ((actionMask >>> 15) & 1) != 0;
        ai.attackAction = (short) (actionMask & 0x7FFF);
        ai.attackCount = inPacket.decodeInt();
        ai.attackSpeed = inPacket.decodeByte();
        inPacket.decodeByte();
        ai.wt = inPacket.decodeInt();
        ai.ar01Mad = inPacket.decodeInt(); // only done if mage skill

        if (ai.skillId > 0) {
            for (int i = 0; i < ai.mobCount; i++) {
                MobAttackInfo mai = new MobAttackInfo();
                mai.mobId = inPacket.decodeInt();
                mai.hitAction = inPacket.decodeByte();
                mai.left = inPacket.decodeByte();
                mai.idk3 = inPacket.decodeByte();
                mai.forceActionAndLeft = inPacket.decodeByte();
                mai.frameIdx = inPacket.decodeByte();
                mai.templateID = inPacket.decodeInt();
                mai.calcDamageStatIndexAndDoomed = inPacket.decodeByte(); // 1st bit for bDoomed, rest for calcDamageStatIndex
                mai.hitX = inPacket.decodeShort();
                mai.hitY = inPacket.decodeShort();
                mai.oldPosX = inPacket.decodeShort(); // ?
                mai.oldPosY = inPacket.decodeShort(); // ?
                mai.idk6 = inPacket.decodeShort();
                inPacket.decodeByte();
                mai.idk7 = inPacket.decodeInt();
                mai.idk8 = inPacket.decodeInt();
                inPacket.decodeInt(); // v271: new int before the damage lines (assumed as melee)
                long[] damages = new long[ai.hits];
                for (int j = 0; j < ai.hits; j++) {
                    damages[j] = inPacket.decodeLong();
                }
                mai.damages = damages;
                mai.mobUpDownYRange = inPacket.decodeInt();
                inPacket.decodeInt(); // crc
                inPacket.decodeInt(); // crc
                parseAttackInfoPacket(inPacket, mai);
                ai.mobAttackInfo.add(mai);
            }
        }
        ai.pos = inPacket.decodePosition();
        handleAttack(c, ai);
    }


    @Handler(op = InHeader.SUMMONED_ATTACK)
    public static void handleSummonedAttack(Client c, InPacket inPacket) {
        Char chr = c.getChr();
        Field field = chr.getField();
        AttackInfo ai = new AttackInfo();
        ai.sui = new SkillUseInfo();
        int summonedID = inPacket.decodeInt();
        inPacket.decodeInt(); // v271: new int (-1 or an id; client 0x14413eb40)
        ai.attackHeader = OutHeader.SUMMONED_ATTACK;
        ai.inHeader = InHeader.SUMMONED_ATTACK;
        ai.summon = (Summon) field.getLifeByObjectID(summonedID);
        ai.updateTime = inPacket.decodeInt();
        int skillId = inPacket.decodeInt();
        ai.skillId = skillId;
        ai.sui.skillID = skillId;
        ai.summonSpecialSkillId = inPacket.decodeInt();
        inPacket.decodeByte();
        inPacket.decodeByte();
        inPacket.decodeByte();
        int bonusCount = inPacket.decodeInt();
        for (int i = 0; i < bonusCount; i++) {
            inPacket.decodeInt();
            inPacket.decodeInt();
        }
        // v271 (client 0x1411d34d0): the int before the string is gone; an int list follows the byte
        inPacket.decodeString();
        inPacket.decodeInt();
        inPacket.decodeByte();
        int extraCount = inPacket.decodeInt();
        for (int i = 0; i < extraCount; i++) {
            inPacket.decodeInt();
        }


        byte leftAndAction = inPacket.decodeByte();
        ai.attackActionType = (byte) (leftAndAction & 0x7F); // attackActionType 25 = ExplosionRemove
        ai.left = (byte) (leftAndAction >>> 7) != 0;
        byte mask = inPacket.decodeByte();
        ai.hits = (byte) (mask & 0xF);
        ai.mobCount = (mask >>> 4) & 0xF;
        inPacket.decodeByte(); // hardcoded 0

        // ~~~ START CSummoned__TryDoingTeslaCoilAttack ADDITION ~~~
        if (SkillConstants.isSummonedTeslaCoilAttack(skillId)) {
            for (int i = 0; i < 3; i++) {
                inPacket.decodeInt(); // rockNshock Obj Id
            }
        }
        // ~~~ END CSummoned__TryDoingTeslaCoilAttack ADDITION ~~~

        ai.pos = inPacket.decodePosition();
        ai.pos = inPacket.decodePosition();
        inPacket.decodeByte();


        if (SkillConstants.isSummonedBox2DAttack(skillId) && ai.attackActionType != 12) {
            inPacket.decodePosition();
        }

        inPacket.decodeInt(); // hardcoded -1
        short idk3 = inPacket.decodeShort();
        int idk4 = inPacket.decodeInt();
        ai.bulletID = inPacket.decodeInt();
        if (skillId == AngelicBuster.MIGHTY_MASCOT || skillId == Corsair.BROADSIDE_SUMMON || skillId == Corsair.HEXA_BROADSIDE_SUMMON) { // Mighty Mascot, Broadside
            inPacket.decodeInt();
        }
        if (skillId == Corsair.BROADSIDE_2 || skillId == Corsair.HEXA_OCTO_CANNON_2) {
            inPacket.decodePositionInt();
        }
        for (int i = 0; i < ai.mobCount; i++) {
            MobAttackInfo mai = new MobAttackInfo();
            mai.mobId = inPacket.decodeInt();
            mai.templateID = inPacket.decodeInt();
            mai.hitAction = inPacket.decodeByte();
            mai.left = inPacket.decodeByte();
            mai.idk3 = inPacket.decodeByte();
            mai.forceActionAndLeft = inPacket.decodeByte();
            mai.frameIdx = inPacket.decodeByte();
            int idk5 = inPacket.decodeInt(); // another template id, same as the one above
            mai.calcDamageStatIndexAndDoomed = inPacket.decodeByte(); // 1st bit for bDoomed, rest for calcDamageStatIndex
            mai.hitX = inPacket.decodeShort();
            mai.hitY = inPacket.decodeShort();
            mai.oldPosX = inPacket.decodeShort(); // ?
            mai.oldPosY = inPacket.decodeShort(); // ?

            int idk7 = inPacket.decodeInt();
            short idk6 = inPacket.decodeShort();
            int idk8 = inPacket.decodeInt();
            int idk9 = inPacket.decodeInt();

            inPacket.decodeByte(); // new 220
            inPacket.decodeInt(); // v271: new int before the damage lines

            long[] damages = new long[ai.hits];
            for (int j = 0; j < ai.hits; j++) {
                damages[j] = inPacket.decodeLong();
            }
            mai.damages = damages;
            mai.mobUpDownYRange = inPacket.decodeInt();
            parseAttackInfoPacket(inPacket, mai);
            ai.mobAttackInfo.add(mai);
        }
        // if TeslaCoil || JaguarSkill
        // decodeInt
        handleAttack(c, ai);
    }


    /**
     * v265 (0x144e94ea0 / 0x1450764a0 / 0x1450ac1b0): its own compact layout, not the shared attack one.
     * short n1, short n2 (patched counts), int hits, int, int skill, byte, int, int tick, short x, short y, int, int,
     * n1 x (int mob, short x, short y, int), n2 x (int mob, short x, short y, int, hits x long damage).
     */
    @Handler(op = InHeader.USER_AFFECTED_AREA_FOR_SCREEN_ATTACK)
    public static void handleAffectedAreaForScreenAttack(Char chr, InPacket inPacket) {
        if (inPacket.getUnreadAmount() < 38) {
            return;
        }
        int n1 = inPacket.decodeShort();
        int n2 = inPacket.decodeShort();
        // GMS: 2 n1, 2 n2, 4 hits, 4 skill, 4 slv, 1 left, 4, 4 tick, 2 2 pos, 4, 4, n1 x 12, n2 x mob
        int hits = inPacket.decodeInt();
        int skillId = inPacket.decodeInt();
        inPacket.decodeInt();
        boolean left = inPacket.decodeByte() != 0;
        inPacket.decodeInt();
        int tick = inPacket.decodeInt();
        short x = inPacket.decodeShort();
        short y = inPacket.decodeShort();
        inPacket.decodeInt();
        inPacket.decodeInt();
        if (SkillData.getSkillInfoById(skillId) == null || hits < 0 || hits > 15 || n1 < 0 || n2 < 0 || n2 > 15) {
            return;
        }
        for (int i = 0; i < n1 && inPacket.getUnreadAmount() >= 12; i++) {
            inPacket.decodeArr(12);
        }
        AttackInfo ai = new AttackInfo();
        ai.sui = new SkillUseInfo();
        ai.inHeader = InHeader.USER_AFFECTED_AREA_FOR_SCREEN_ATTACK;
        ai.attackHeader = OutHeader.REMOTE_MELEE_ATTACK;
        ai.skillId = skillId;
        ai.slv = chr.getSkillLevel(skillId);
        ai.sui.skillID = skillId;
        ai.sui.slv = ai.slv;
        ai.hits = (byte) hits;
        ai.left = left;
        ai.tick = tick;
        ai.pos.setX(x);
        ai.pos.setY(y);
        for (int i = 0; i < n2 && inPacket.getUnreadAmount() >= 12 + 8 * hits; i++) {
            MobAttackInfo mai = new MobAttackInfo();
            mai.mobId = inPacket.decodeInt();
            mai.hitX = inPacket.decodeShort();
            mai.hitY = inPacket.decodeShort();
            inPacket.decodeInt();
            mai.damages = new long[hits];
            for (int j = 0; j < hits; j++) {
                mai.damages[j] = inPacket.decodeLong();
            }
            Life life = chr.getField().getLifeByObjectID(mai.mobId);
            mai.templateID = life instanceof Mob ? ((Mob) life).getTemplateId() : 0;
            ai.mobAttackInfo.add(mai);
        }
        ai.mobCount = ai.mobAttackInfo.size();
        handleAttack(chr.getClient(), ai);
    }

    @Handler(op = InHeader.ADD_ATTACK_RESET)
    public static void handleAddAttackReset(Char chr, InPacket inPacket) {
        int parentSkillID = inPacket.decodeInt();
        if (JobConstants.isZero(chr.getJob())) {
            return;
        }
        chr.resetSkillCoolTime(parentSkillID);
    }

    @Handler(ops = {InHeader.USER_MELEE_ATTACK,
            InHeader.USER_SHOOT_ATTACK,
            InHeader.USER_MAGIC_ATTACK,
            InHeader.USER_NON_TARGET_FORCE_ATOM_ATTACK,
            InHeader.USER_AREA_DOT_ATTACK,
            InHeader.USER_AREA_DOT_ATTACK_NO_AREA})
    public static void handleAttack(Char chr, InPacket inPacket, InHeader header) {
        // the Angelic Buster variant (275): the area-dot attack without its area object ids (the int before the mobs and
        // the one after the position at the end)
        boolean noArea = header == InHeader.USER_AREA_DOT_ATTACK_NO_AREA;
        if (noArea) {
            header = InHeader.USER_AREA_DOT_ATTACK;
        }
        AttackInfo ai = new AttackInfo();
        SkillUseInfo skillUseInfo = new SkillUseInfo();
        int unread = inPacket.getUnreadAmount();
        if (unread >= 9) {
            InPacket tail = inPacket.clone();
            tail.decodeArr(unread - 9);
            ai.clientTail = tail.decodeArr(9);
        }
        ai.sui = skillUseInfo;
        ai.inHeader = header;
        if (header != InHeader.USER_AFFECTED_AREA_FOR_SCREEN_ATTACK) {
            inPacket.decodeInt();
            inPacket.decodeInt();
        }
        switch (header) {
            case USER_MELEE_ATTACK:
            case USER_AFFECTED_AREA_FOR_SCREEN_ATTACK:
                ai.attackHeader = OutHeader.REMOTE_MELEE_ATTACK;
                break;
            case USER_SHOOT_ATTACK:
                ai.boxAttack = inPacket.decodeByte() != 0; // hardcoded in shootAttack (0) and box2d (1)
                ai.attackHeader = OutHeader.REMOTE_SHOOT_ATTACK;
                break;
            case USER_NON_TARGET_FORCE_ATOM_ATTACK:
                inPacket.decodeArr(20); // id/crc/something else
            case USER_MAGIC_ATTACK:
                ai.attackHeader = OutHeader.REMOTE_MAGIC_ATTACK;
                break;
        }
        ai.bulletID = chr.getBulletIDForAttack();
        ai.fieldKey = inPacket.decodeByte();
        byte mask = inPacket.decodeByte();
        ai.hits = (byte) (mask & 0xF);
        ai.mobCount = (mask >>> 4) & 0xF;
        inPacket.decodeInt(); // v271: new int before the skill id (melee/shoot/magic writers)
        ai.skillId = inPacket.decodeInt();
        ai.slv = inPacket.decodeInt();

        // MeleeAttack || ShootAttack || ForceAtom_NonTarget
        if (header == InHeader.USER_MELEE_ATTACK || header == InHeader.USER_SHOOT_ATTACK
                || header == InHeader.USER_NON_TARGET_FORCE_ATOM_ATTACK) {
            ai.addAttackProc = inPacket.decodeByte();
        }

        inPacket.decodeInt(); // crc
        inPacket.decodeInt();
        inPacket.decodeInt();


        // START UNKNOWNATTACKPACKETDATA
        decodeUnknownAttackPacketData(inPacket, ai);
        // END UNKNOWNATTACKPACKETDATA


        // START USER SKILL ENCODE FUNC
        skillUseInfo.skillID = ai.skillId;
        skillUseInfo.slv = ai.slv;
        new UserSkillUseInfo(skillUseInfo).decode(inPacket);
        // END USER SKILL ENCODE FUNC


        var skillID = ai.skillId;

        // MeleeAttack || ShootAttack || MagicAttack || Box2DAttack
        // the area-dot and non-target force atom writers have no key-down int
        if (header != InHeader.USER_AREA_DOT_ATTACK && header != InHeader.USER_NON_TARGET_FORCE_ATOM_ATTACK
                && (SkillConstants.isAttackKeyDownSkill(skillID) || SkillConstants.isSuperNovaSkill(skillID))) {
            ai.keyDown = inPacket.decodeInt();
        }
        if (skillID == Illium.HEXA_WINGED_JAVELIN_SPLIT || skillID == Pathfinder.HEXA_BOUNTIFUL_BURST) {
            inPacket.decodeInt();
            inPacket.decodeInt();
        }
        if (skillID == AngelicBuster.CHEERING_BALLOONS_2) { // Grand Finale: Cheering Balloons
            inPacket.decodeInt();
        }

        // ForceAtom_NonTarget (client 0x144e1ca10): Phoenix Drive and its HEXA version carry two ints
        if (header == InHeader.USER_NON_TARGET_FORCE_ATOM_ATTACK && (skillID == BlazeWizard.PHOENIX_DRIVE_ATOM
                || skillID == BlazeWizard.HEXA_PHOENIX_DRIVE_ATOM || skillID == BlazeWizard.PHOENIX_DRIVE_ATOM_2
                || skillID == BlazeWizard.HEXA_PHOENIX_DRIVE_ATOM_2)) {
            inPacket.decodeInt();
            inPacket.decodeInt();
        }

        // MeleeAttack || MagicAttack: only those writers have the grenade id
        if ((header == InHeader.USER_MELEE_ATTACK || header == InHeader.USER_MAGIC_ATTACK) && SkillConstants.isRushBombSkill(skillID)) {
            ai.grenadeId = inPacket.decodeInt();
        }

        // MeleeAttack || ShootAttack || ForceAtom_NonTarget || AreaDotAttack
        if (SkillConstants.isZeroSkill(skillID)) {
            ai.zeroTag = inPacket.decodeByte();
        }

        // MeleeAttack || ShootAttack || TryDoingShootAttackObject
        if (SkillConstants.isUsercloneSummonedAbleSkill(skillID)) {
            ai.bySummonedID = inPacket.decodeInt();
        }

        // MeleeAttack
        if (skillID == Marksman.PERFECT_SHOT_HIT || skillID == SkillConstants.NEROTAS_POWER_PROTECTION) {
            inPacket.decodePositionInt();
        }

        // MeleeAttack
        if (skillID == NightWalker.SHADOW_SPEAR_AA_LARGE) {
            ai.shadowSpear1 = inPacket.decodeInt();
            ai.shadowSpear2 = inPacket.decodeInt();
        }

        ai.someMask = inPacket.decodeByte();
        ai.buckShot = inPacket.decodeByte();
        // ShootAttack
        if (header == InHeader.USER_SHOOT_ATTACK) {
            int idk3 = inPacket.decodeInt();
            ai.isJablin = inPacket.decodeByte() != 0;
            if (ai.boxAttack) {
                int boxIdk1 = inPacket.decodeInt();
                short boxIdk2 = inPacket.decodeShort();
                short boxIdk3 = inPacket.decodeShort();
            }
        }

        // ForceAtom_NonTarget (client 0x144e1ca10): one int for the Orbital Flame / Phoenix family, a second one for
        // the HEXA Orbital Flames 12141000-12141005
        boolean nonTarget = header == InHeader.USER_NON_TARGET_FORCE_ATOM_ATTACK;
        if (SkillConstants.isOrbitalFlameSkill(skillID) || (nonTarget && NON_TARGET_FLAME_IDS.contains(skillID))) {
            inPacket.decodeInt();
        }
        if (nonTarget && skillID >= BlazeWizard.HEXA_ORBITAL_FLAME && skillID <= BlazeWizard.HEXA_ORBITAL_FLAME_ATOM_4) {
            inPacket.decodeInt();
        }

        short maskie = inPacket.decodeShort();
        ai.left = ((maskie >>> 15) & 1) != 0;
        ai.attackAction = (short) (maskie & 0x7FFF);
        ai.requestTime = inPacket.decodeInt();
        ai.attackActionType = inPacket.decodeByte();

        // ShootAttack (v265's Leaf Tornado 23111001 sends no such ints)
        if (skillID == SkillConstants.BOMB_PUNCH_2 || skillID == Xenon.COMBAT_SWITCH_FISSION) {
            int idk5 = inPacket.decodeInt();
            int x = inPacket.decodeInt();
            int y = inPacket.decodeInt();
        }

        // FlameBall || MagicAttack || RandAreaAttack || PsychicLockAttack || TryDoingShootAttackObject
        if (header == InHeader.USER_MAGIC_ATTACK && SkillConstants.isEvanForceSkill(skillID)) {
            boolean mirAttack = inPacket.decodeByte() != 0;
        }

        ai.attackSpeed = inPacket.decodeByte();
        ai.tick = inPacket.decodeInt();
        // TODO TryDoingRandAreaAttack shows 1more int after tick. Check In-Game

        // follow-up attack writer (client 0x1454015b0): one int after the tick for these ids, melee and magic
        if (skillID == Blaster.AFTERIMAGE_SHOCK_3 || skillID == BladeMaster.KARMA_BLADE_BUFF || skillID == DemonSlayer.NIGHTMARE_FLAMES || skillID == Ren.THOUSAND_BLOSSOM_FLURRY_EX) {
            inPacket.decodeInt();
        }


        if (SkillConstants.isRandomAttackSkill(skillID)) {
            inPacket.decodeInt(); // mobId
        }

        if (skillID == WildHunter.ANOTHER_BITE) {
            inPacket.decodeInt(); // ?
        }

        if (header == InHeader.USER_AREA_DOT_ATTACK && !noArea) {
            ai.affectedAreaObjId = inPacket.decodeInt();
        }

        // FlameBall || LightningUnionAttack || ForceAtom_NonTarget
        if (header == InHeader.USER_MAGIC_ATTACK || header == InHeader.USER_NON_TARGET_FORCE_ATOM_ATTACK) {
            int idk = inPacket.decodeInt();
        }

        // MeleeAttack || ShootAttack
        if (header == InHeader.USER_MELEE_ATTACK || header == InHeader.USER_SHOOT_ATTACK) {
            int bulletSlot = inPacket.decodeInt();
        }

        if (header == InHeader.USER_AREA_DOT_ATTACK || header == InHeader.USER_AFFECTED_AREA_FOR_SCREEN_ATTACK) {
            inPacket.decodeInt();
        }

        // MeleeAttack || ShootAttack || ForceAtom_NonTarget
        if (header == InHeader.USER_MELEE_ATTACK || header == InHeader.USER_SHOOT_ATTACK || header == InHeader.USER_NON_TARGET_FORCE_ATOM_ATTACK) {
            ai.finalAttackLastSkillID = inPacket.decodeInt();
            if (ai.finalAttackLastSkillID > 0) {
                ai.finalAttackByte = inPacket.decodeByte();
            }
        }

        // ShootAttack
        if (header == InHeader.USER_SHOOT_ATTACK) {
            int bulletSlot = inPacket.decodeShort();
            byte idk = inPacket.decodeByte();
            ai.rect = inPacket.decodeShortRect();
        }


        // MeleeAttack
        if (skillID == Buccaneer.SPIRAL_ASSAULT) { // Spiral Assault
            ai.ignorePCounter = inPacket.decodeByte() != 0;
        }
        if (skillID == Shade.SPIRIT_FRENZY) {
            ai.spiritCoreEnhance = inPacket.decodeInt();
        }

        // MeleeAttack
        if ((skillID == Mercedes.ROLLING_MOONSAULT || skillID == SkillConstants.BOMB_PUNCH) && ai.addAttackProc != 0) {
            ai.addAttackProcRemote = inPacket.decodeByte(); // boolean
        }



        // Mob Loop
        for (int i = 0; i < ai.mobCount; i++) {
            MobAttackInfo mai = new MobAttackInfo();
            mai.mobId = inPacket.decodeInt();
            mai.hitAction = inPacket.decodeByte();
            mai.left = inPacket.decodeByte();
            mai.idk3 = inPacket.decodeByte();
            mai.forceActionAndLeft = inPacket.decodeByte();
            mai.frameIdx = inPacket.decodeByte();
            mai.templateID = inPacket.decodeInt();
            mai.calcDamageStatIndexAndDoomed = inPacket.decodeByte(); // 1st bit for bDoomed, rest for calcDamageStatIndex
            mai.hitX = inPacket.decodeShort();
            mai.hitY = inPacket.decodeShort();

            mai.oldPosX = inPacket.decodeShort(); // ?
            mai.oldPosY = inPacket.decodeShort(); // ?

            // FlameBall || MagicAttack
            if (header == InHeader.USER_MAGIC_ATTACK) {
                mai.hpPerc = inPacket.decodeByte();
                // v271: 42111002 (v265 Soul Shear, now Tengu's Piercing Shot) left this list
                if (skillID == SkillConstants.SOUL_SHEAR || skillID == SkillConstants.ETHER_PULSE) {
                    mai.magicInfo = (short) inPacket.decodeByte();
                } else {
                    mai.magicInfo = inPacket.decodeShort();
                }
            } else {
                mai.idk6 = inPacket.decodeShort();
            }

            mai.idk8 = inPacket.decodeInt(); // new 199
            mai.idk7 = inPacket.decodeInt();

            inPacket.decodeByte(); // new 220
            inPacket.decodeInt(); // v271: new int before the damage lines

            mai.damages = new long[ai.hits];
            for (int j = 0; j < ai.hits; j++) {
                mai.damages[j] = inPacket.decodeLong();
            }
            mai.mobUpDownYRange = inPacket.decodeInt();
            inPacket.decodeInt(); // crc
            inPacket.decodeInt(); // crc

            // MeleeAttack
            if (skillID == Blaster.ROCKET_RUSH) {
                mai.isResWarriorLiftPress = inPacket.decodeByte() != 0;
            }

            // MeleeAttack
            if (skillID == FirePoison.POISON_NOVA_EXPLOSION) {
                inPacket.decodeByte();
            }

            // PsychicLockAttack
            if (SkillConstants.isKinesisPsychicLockSkill(skillID)) {
                mai.psychicLockInfo1 = inPacket.decodeInt();
                mai.psychicLockInfo2 = inPacket.decodeInt();
            }

            parseAttackInfoPacket(inPacket, mai);
            ai.mobAttackInfo.add(mai);
        }



        if (header == InHeader.USER_MAGIC_ATTACK) { // Extra Encodes for USER_MAGIC_ATTACK
            short forcedX = inPacket.decodeShort();
            short forcedY = inPacket.decodeShort();
            if (SkillConstants.isKinesisPsychicLockSkillExtraEncode(skillID)) {
                inPacket.decodeByte();
            }

            if (SkillConstants.isKinesisPsychicAreaSkill(skillID)) {
                inPacket.decodeInt(); // psychic Area Key (int)
                inPacket.decodeShort(); // psychic Area Key (short)
            }

            // ShootAttackObject
            if (SkillConstants.isSomeAA(skillID) || SkillConstants.isShootObj(skillID)) {
                ai.shootObjId = inPacket.decodeInt(); // ShootObj Id
                ai.pos3 = inPacket.decodePosition(); // end point of ShootObj
                ai.idk5 = inPacket.decodeByte(); // true for isSomePathFinderSkill skills, 0 for others
            }

            if (skillID == BattleMage.DARK_SHOCK) {
                ai.pos3 = inPacket.decodePosition(); // old position
            }

            // RandAreaAttack
            ai.dragon = inPacket.decodeByte() != 0;
            ai.forcedX = forcedX;
            ai.forcedY = forcedY;
            if (!ai.dragon && inPacket.getUnreadAmount() == 6) {
                // GMS (writer 0x143391400): two more bytes (0 0) before the send's trailing int
                inPacket.decodeByte();
                inPacket.decodeByte();
            }
            if (ai.dragon) {
                short rcDstRight = inPacket.decodeShort();
                short rectRight = inPacket.decodeShort();
                short x = inPacket.decodeShort();
                short y = inPacket.decodeShort();
                ai.dragonAttackStart = inPacket.decodeByte();
                ai.dragonAttackActionType = inPacket.decodeByte();
                ai.dragonAttackProgess = inPacket.decodeByte();
                ai.rcDstRight = rcDstRight;
                ai.rectRight = rectRight;
                ai.x = x;
                ai.y = y;
            }

            if (skillID == BlazeWizard.IGNITION_EXPLOSION) {
                ai.option = inPacket.decodeInt();
            }

            if (skillID == FirePoison.MIST_ERUPTION || skillID == FirePoison.HEXA_MIST_ERUPTION) {
                int size = inPacket.decodeByte() & 0xFF;
                // every id is read so the rest of the packet stays aligned; only the first few are acted on
                int[] mists = new int[Math.min(size, 32)];
                for (int i = 0; i < size; i++) {
                    int id = inPacket.decodeInt();
                    if (i < mists.length) {
                        mists[i] = id;
                    }
                }
                ai.mists = mists;
            }

            if (skillID == FirePoison.POISON_MIST) {
                byte force = inPacket.decodeByte();
                short forcedXSh = inPacket.decodeShort();
                short forcedYSh = inPacket.decodeShort();
                ai.force = force;
                ai.forcedXSh = forcedXSh;
                ai.forcedYSh = forcedYSh;
            }

            if (skillID == SkillConstants.SOUL_SHEAR) {
                byte sizeB = inPacket.decodeByte();
                int[] idkArr2 = new int[sizeB];
                short[] shortArr2 = new short[sizeB];
                for (int i = 0; i < sizeB; i++) {
                    idkArr2[i] = inPacket.decodeInt();
                    shortArr2[i] = inPacket.decodeShort();
                }
                short delay = inPacket.decodeShort();
                ai.mists = idkArr2;
                ai.shortArr = shortArr2;
                ai.delay = delay;
            }


        } else if (header == InHeader.USER_MELEE_ATTACK) { // Extra Encodes for USER_MELEE_ATTACK

            if (skillID == Kaiser.ANCESTRAL_PROMINENCE || skillID == Xenon.ORBITAL_CATACLYSM || skillID == BeastTamer.TABLE_FLIP || skillID == BeastTamer.TABLE_FLIP_2
                    || SkillConstants.isScreenCenterAttackSkill(skillID)) {
                ai.ptTarget.setX(inPacket.decodeShort());
                ai.ptTarget.setY(inPacket.decodeShort());

            } else if (SkillConstants.isSuperNovaSkill(skillID)) {
                ai.ptTarget.setX(inPacket.decodeShort());
                ai.ptTarget.setY(inPacket.decodeShort());

            } else if (skillID == Zero.AIR_RIOT_2) {
                ai.chrPos = inPacket.decodePosition();

            } else if (skillID == Marksman.SPLIT_SHOT_FINAL_ATTACK || skillID == Phantom.ACE_IN_THE_HOLE_FINISHER || skillID == SkillConstants.AURORA_DOLPHIN_MOUNT || SkillConstants.isWingedJavelinOrAbyssalCast(skillID)) {
                ai.chrPos = inPacket.decodePosition();

            } else if (skillID == Hayato.SUMMER_RAIN) {
                ai.chrPos = inPacket.decodePosition();

            } else if (skillID == Hayato.HITOKIRI_HUNDRED_STRIKE) {
                ai.chrPos = inPacket.decodePosition();

            } else if (skillID == Hayato.REACTIVE_FOCUS || skillID == Hayato.SWEEPING_SWORD) {
                ai.chrPos = inPacket.decodePosition();
            } else if (skillID == Hayato.BATTOU_FULL_MOONS_RAGE) {
                ai.chrPos = inPacket.decodePosition();
            } else {
                if (inPacket.getUnreadAmount() == 10) {
                    inPacket.decodeShort(); // GMS: a short (0) before the position, then the send's trailing int
                }
                ai.chrPos = inPacket.decodePosition();
            }

            // MeleeAttack || ShootObjAttack
            if (SkillConstants.isSomeAA(skillID) || SkillConstants.isShootObj(skillID)) {
                ai.shootObjId = inPacket.decodeInt(); // ShootObj Id
                ai.pos3 = inPacket.decodePosition(); // end point of ShootObj
                ai.idk5 = inPacket.decodeByte(); // true for isSomePathFinderSkill skills, 0 for others
            }

            if (skillID == Aran.MAHAS_BLESSING) {
                ai.x = inPacket.decodeShort();
                ai.y = inPacket.decodeShort();
            }

            if (SkillConstants.isDivineEchoMimicSkills(skillID) && ai.attackAction == 0) {
                ai.isMimickedBy = inPacket.decodeInt(); // Mimic Chr Id
                inPacket.decodePosition(); // Mimic Chr Position
            }

            if (SkillConstants.isAranFallingStopSkill(skillID) ||
                    skillID == SkillConstants.SMASH_SWING || skillID == SkillConstants.BOMB_PUNCH_3
                    || skillID == SkillConstants.BOMB_PUNCH_4 || skillID == SkillConstants.SMASH_SWING_2
                    || skillID == SkillConstants.SMASH_SWING_3 || skillID == SkillConstants.SMASH_SWING_4 ||

                    skillID == Blaster.BOBBING_ATTACK || skillID == Blaster.HAMMER_SMASH_CHARGE || skillID == Blaster.WEAVING_ATTACK) {
                inPacket.decodeByte();
            }

            if (skillID == Aran.FINISHER_HUNTER_PREY_2 || skillID == Blaster.HYPER_MAGNUM_PUNCH || SkillConstants.isShadowAssault(skillID)
                    || skillID == DawnWarrior.EQUINOX_SLASH || skillID == Buccaneer.CORKSCREW_BLOW) {
                ai.teleportLeft = inPacket.decodeByte() != 0;
                ai.teleportPt = inPacket.decodePositionInt(); // to Position
            }

            if (skillID != Kaiser.INFERNO_BREATH && skillID != Kaiser.INFERNO_BREATH_FINAL_FORM && skillID != Phantom.CARTE_ROSE_FINALE) {

                if (skillID == Zero.ADV_EARTH_BREAK) {
                    var size = inPacket.decodeShort();
                    for (int i = 0; i < size; i++) {
                        // Advanced Earth Break Positions
                        ai.positions.add(inPacket.decodePosition());
                    }
                }

            } else {
                //inPacket.decodeInt(); // unk
                ai.Vx = inPacket.decodeShort();
                for (int i = 0; i < ai.Vx; i++) {
                    // Inferno Breath Affected Area Positions
                    ai.positions.add(inPacket.decodePosition());
                }
            }
            if (skillID == NightWalker.POISON_BOMB && ai.grenadeId != 0) {
                ai.grenadePos.setX(inPacket.decodeShort());
                ai.grenadePos.setY(inPacket.decodeShort());
            }
            if (skillID == SkillConstants.ERDA_EXPLOSION) {
                inPacket.decodeInt();
                // while ( X ) {
                //     inPacket.decodeInt();
            }

            // Shit for Evan
            if ((skillID == 0 || skillID == Evan.MAGIC_DEBRIS || skillID == Evan.ENHANCED_MAGIC_DEBRIS) && JobConstants.isEvan(chr.getJob())) {
                if (skillID == 0) {
                    ai.dragon = true;
                    ai.dragonAttackActionType = -1;
                } else {
                    ai.attackActionType = -1;
                }
            }


        } else if (header == InHeader.USER_SHOOT_ATTACK) { // Extra Encodes for USER_SHOOT_ATTACK

            if (SkillConstants.isScreenCenterAttackSkill(skillID)) {
                ai.chrPos = inPacket.decodePosition();
            }

            if (SkillConstants.isUserCloneSummon(skillID) && SkillConstants.isUsercloneSummonedAbleSkill(skillID)) {
                ai.chrPos = inPacket.decodePosition();
            } else {
                ai.chrPos = inPacket.decodePosition();

                // Box2DAttack
                if (ai.boxAttack && false) { // new 227, don't decode pos? At least for Broadside
                    inPacket.decodePosition();
                }

            }

            if (SkillConstants.isAranFallingStopSkill(skillID) ||
                    skillID == SkillConstants.SMASH_SWING || skillID == SkillConstants.BOMB_PUNCH_3
                    || skillID == SkillConstants.BOMB_PUNCH_4 || skillID == SkillConstants.SMASH_SWING_2
                    || skillID == SkillConstants.SMASH_SWING_3 || skillID == SkillConstants.SMASH_SWING_4 ||

                    skillID == Blaster.BOBBING_ATTACK || skillID == Blaster.HAMMER_SMASH_CHARGE || skillID == Blaster.WEAVING_ATTACK) {
                inPacket.decodeByte();
            }

            if (JobConstants.isWildHunter(skillID / 10000)) {
                ai.bodyRelMove = inPacket.decodePosition();
            }

            if (SkillConstants.isKeydownSkillRectMoveXY(skillID)) {
                ai.keyDownRectMoveXY = inPacket.decodePosition();
            }

            if (skillID >= Cadena.CHAIN_ARTS_PURSUIT_HORIZONTAL && skillID <= Cadena.CHAIN_ARTS_PURSUIT_DOWN) { // Cadena : Chain Pursuit
                inPacket.decodeByte(); // unk
                ai.ptTarget = inPacket.decodePosition(); // pursuit hook end position
            }

            if (skillID == Mercedes.SPIKES_ROYALE || skillID == SkillConstants.SPIKES_ROYALE) {
                ai.fh = inPacket.decodeByte();
            }

            // v271: 42121002 (v265 Falling Sakura, now Heart-Wreck Talisman) has no extra int
            if (skillID == SkillConstants.SURGING_BLADE && ai.mobCount > 0) {
                inPacket.decodeInt();
            }

            if (SkillConstants.isDragonAction(skillID)) {
                ai.dragon = true;
                ai.dragonAttackStart = inPacket.decodeByte();
                ai.dragonAttackActionType = inPacket.decodeByte();
            }


        } else if (header == InHeader.USER_AREA_DOT_ATTACK || header == InHeader.USER_AFFECTED_AREA_FOR_SCREEN_ATTACK) {
            if (header == InHeader.USER_AREA_DOT_ATTACK && !noArea && inPacket.getUnreadAmount() == 10) {
                inPacket.decodeShort(); // GMS: a short (0) before the position
            }
            ai.pos.setX(inPacket.decodeShort());
            ai.pos.setY(inPacket.decodeShort());

            if (header == InHeader.USER_AREA_DOT_ATTACK && !noArea) {
                ai.affectedAreaObjId = inPacket.decodeInt();
            }
            if (noArea && inPacket.getUnreadAmount() >= 4) {
                // 0x1455ee5a0: for some skills a second position (another object's) follows the user's
                inPacket.decodeShort();
                inPacket.decodeShort();
            }


        } else if (header == InHeader.USER_NON_TARGET_FORCE_ATOM_ATTACK) {
            ai.pos.setX(inPacket.decodeShort());
            ai.pos.setY(inPacket.decodeShort());
        }

        handleAttack(chr.getClient(), ai);
    }

    private static void decodeUnknownAttackPacketData(InPacket inPacket, AttackInfo ai) {
        inPacket.decodeByte();
        inPacket.decodeShort(); // bullet item position
        inPacket.decodeInt(); // bullet item id
        ai.byUnreliableMemory = inPacket.decodeByte() != 0;
        inPacket.decodeByte();
        inPacket.decodeByte(); // v271: three bytes here (v265 four; client 0x1410203f0)
        inPacket.decodeInt();
        inPacket.decodeLong(); // character position
        inPacket.decodeInt();
        inPacket.decodeInt();
        int count = inPacket.decodeInt(); // bonus attacks (Dark Shock)
        for (int i = 0; i < count; i++) {
            inPacket.decodeInt();
        }
        inPacket.decodeByte();
        inPacket.decodeByte();
        inPacket.decodeInt();
        inPacket.decodeInt();
        inPacket.decodeLong();
        inPacket.decodeLong();
        inPacket.decodeLong();
        inPacket.decodeByte();
        count = inPacket.decodeInt();
        for (int i = 0; i < count; i++) {
            inPacket.decodeInt();
        }
        for (int i = 0; i < 4; i++) { // v271: four (the fifth moved after the pair list)
            inPacket.decodeInt();
        }
        // "User Melee Skill System" attacks come with a 1452-byte first crypto block
        if (inPacket.decodeString().endsWith("Skill System")) {
            inPacket.useShortFirstBlock();
        }
        inPacket.decodeInt();
        inPacket.decodeInt(); // tick
        inPacket.decodeByte();
        count = inPacket.decodeInt();
        for (int i = 0; i < count; i++) {
            inPacket.decodeInt();
            inPacket.decodeInt();
        }
        inPacket.decodeInt(); // v271: moved here
        count = inPacket.decodeInt(); // v271: new int list
        for (int i = 0; i < count; i++) {
            inPacket.decodeInt();
        }
        inPacket.decodeInt();
    }

    private static void parseAttackInfoPacket(InPacket inPacket, MobAttackInfo mai) {
        // PACKETMAKER::MakeAttackInfoPacket
        mai.type = inPacket.decodeByte();
        mai.currentAnimationName = "";
        mai.unkStr = "";
        if (mai.type == 1) {
            mai.currentAnimationName = inPacket.decodeString();
            mai.unkStr = inPacket.decodeString();
            mai.animationDeltaL = inPacket.decodeInt();
            inPacket.decodeByte();
            // v265: the part-name list is its own count + strings, whatever the byte before it says
            mai.hitPartRunTimesSize = inPacket.decodeInt();
            mai.hitPartRunTimes = new String[Math.max(0, mai.hitPartRunTimesSize)];
            for (int j = 0; j < mai.hitPartRunTimes.length; j++) {
                mai.hitPartRunTimes[j] = inPacket.decodeString();
            }
        } else if (mai.type == 2) {
            mai.currentAnimationName = inPacket.decodeString();
            mai.unkStr = inPacket.decodeString();
            mai.animationDeltaL = inPacket.decodeInt();
            inPacket.decodeByte();
        }
        mai.packetMakerUnk1 = inPacket.decodeByte();
        inPacket.decodePosition();
        inPacket.decodePosition();
        inPacket.decodePosition();
        inPacket.decodeByte();
        inPacket.decodeByte();
        mai.unk214 = inPacket.decodeByte();
        inPacket.decodeInt();
        inPacket.decodeShort(); // v271: new short (client 0x14552b580)
        if (inPacket.decodeByte() != 0) {
            inPacket.decodeInt();
            inPacket.decodeInt();
            int size = inPacket.decodeInt();
            for (int i = 0; i < size; i++) {
                inPacket.decodeInt();
                inPacket.decodeLong();
                inPacket.decodeString();
                inPacket.decodeLong();
            }
            for (int i = 0; i < 4; i++) {
                inPacket.decodeInt();
            }
            inPacket.decodeLong();
            inPacket.decodeLong();
            inPacket.decodeInt();
            inPacket.decodeInt();
            inPacket.decodeInt();
        }
        inPacket.decodeInt(); // mob template id
        inPacket.decodeInt();
        int size = inPacket.decodeInt();
        for (int i = 0; i < size; i++) {
            inPacket.decodeInt();
            inPacket.decodeInt();
        }
    }

    @Handler(op = InHeader.FAMILIAR_ATTACK)
    public static void handleFamiliarAttack(Char chr, InPacket inPacket) {
        AttackInfo ai = new AttackInfo();
        ai.sui = new SkillUseInfo();
        ai.attackHeader = OutHeader.FAMILIAR_INFO;
        ai.fieldKey = inPacket.decodeByte(); // ?
        ai.familiarId = inPacket.decodeInt();

        Familiar f = chr.getFamiliarCodexManager().getFamiliarByTemplateId(ai.familiarId);
        if (f == null || !Util.arrayContains(chr.getFamiliarCodexManager().getActiveFamiliars(), (int) f.getId()) || !chr.getFamiliarCodexManager().isFamiliarsSummoned()) {
            return;
        }
        ai.familiar = f;

        ai.mobCount = inPacket.decodeByte();
        ai.mobCount = 1;
        //ai.hits = inPacket.decodeByte();
        //ai.skillId = inPacket.decodeInt();
        ai.idk = inPacket.decodeByte();
        ai.slv = 1;
        for (int i = 0; i < ai.mobCount; i++) {
            MobAttackInfo mai = new MobAttackInfo();
            // client 0x142e56f20: byte, int familiar, byte, byte, int mob object id, int (from the mob, likely template), int, long damage
            mai.mobId = inPacket.decodeInt();
            mai.templateID = inPacket.decodeInt();
            mai.idkInt = inPacket.decodeInt(); // delay, echoed to bystanders
            ai.hits = 1;
            long[] damages = new long[ai.hits];
            for (int j = 0; j < ai.hits; j++) {
                damages[j] = inPacket.decodeLong(); // still an int :(      Not anymore :pog:
            }
            mai.damages = damages;
            ai.mobAttackInfo.add(mai);
        }
        handleAttack(chr.getClient(), ai);
        // 4 more bytes after this, not sure what it is
    }

}
