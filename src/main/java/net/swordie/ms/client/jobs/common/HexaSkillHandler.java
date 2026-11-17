package net.swordie.ms.client.jobs.common;

import net.swordie.ms.client.Client;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.info.HitInfo;
import net.swordie.ms.client.character.quest.Quest;
import net.swordie.ms.client.character.skills.Option;
import net.swordie.ms.client.character.skills.atom.secondatom.SecondAtom;
import net.swordie.ms.client.character.skills.info.AttackInfo;
import net.swordie.ms.client.character.skills.info.SecondAtomInfo;
import net.swordie.ms.client.character.skills.info.SkillInfo;
import net.swordie.ms.client.character.skills.info.SkillUseInfo;
import net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat;
import net.swordie.ms.client.character.skills.temp.TemporaryStatManager;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.packet.UserPool;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.connection.packet.model.MessagePacket;
import net.swordie.ms.constants.QuestConstants;
import net.swordie.ms.enums.AssistType;
import net.swordie.ms.enums.MoveAbility;
import net.swordie.ms.life.Summon;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.loaders.SkillData;
import net.swordie.ms.util.Rect;
import net.swordie.ms.util.Util;
import net.swordie.ms.world.field.Field;

import java.util.ArrayList;
import java.util.List;

import static net.swordie.ms.client.character.skills.SkillStat.*;

/**
 * HEXA common skills: Sol Janus (the common node every 6th job has) and Phantom's Fate Shuffle switch.
 * <p>
 * Sol Janus is set to Dusk or Dawn from its skill window (HEXA_SOL_JANUS_REQUEST). Dusk: attacks now and then send
 * a volley of dusk orbs at monsters around the player, for a little HP. Dawn: when hit, the client casts a dawn
 * spirit that stays and attacks; up to three by level, the oldest goes when another comes.
 */
public class HexaSkillHandler implements ICommonSkillHandler {

    public static final int SOL_JANUS = 500001000;
    public static final int SOL_JANUS_DUSK = 500001001;
    public static final int SOL_JANUS_DAWN = 500001002;
    public static final int SOL_JANUS_DAWN_2 = 500001003;
    public static final int SOL_JANUS_DAWN_3 = 500001004;
    public static final int SOL_HECATE = 500001005;
    public static final int SOL_HECATE_STYX = 500001006;
    public static final int SOL_HECATE_PACTUM = 500001009;

    public static final int PHANTOM_FATE_SHUFFLE = 24141003;
    public static final int PHANTOM_HEXA_MILLE_AIGUILLES = 24141001;
    public static final int PHANTOM_HEXA_MILLE_AIGUILLES_FORTUNE = 24141002;

    // the client keeps the chosen mode in these quest records, under the skill's id
    private static final int HECATE_STYX_MS = 3000; // hardcode-ok: data dummyStr6=3 (Death Seed growth) matches, string stat
    private static final String SOL_JANUS_KEY = String.valueOf(SOL_JANUS);
    private static final String FATE_SHUFFLE_KEY = String.valueOf(PHANTOM_FATE_SHUFFLE);

    private static final int DUSK_RANGE = 500;
    private static final int DAWN_ICON_SEC = 60;
    private static final int AFTERIMAGE = 400001065;
    private static final int HEXA_AFTERIMAGE = 500001106;

    private final Char chr;

    public HexaSkillHandler(Char chr) {
        this.chr = chr;
    }

    private static boolean isDawn(int skillId) {
        return skillId == SOL_JANUS_DAWN || skillId == SOL_JANUS_DAWN_2 || skillId == SOL_JANUS_DAWN_3;
    }

    // ------------------------------------------------------------------------------------------------- requests

    private static Quest getRecord(Char chr, int questId, String key) {
        var qm = chr.getQuestManager();
        Quest q = qm.getQuestById(questId);
        if (q == null) {
            qm.addQuestWithQr(chr, questId, key + "=0");
            q = qm.getQuestById(questId);
        }
        return q;
    }

    private static void setRecord(Char chr, int questId, String key, int value) {
        Quest q = getRecord(chr, questId, key);
        if (q == null) {
            return;
        }
        q.setProperty(key, value);
        if (questId == QuestConstants.SKILL_COMMAND_LOCK_QUEST_ID) {
            q.setProperty("pr0", 0);
        }
        chr.write(WvsContext.message(MessagePacket.questRecordExMessage(q)));
    }

    /** Sol Janus set to Dusk (500001001) or Dawn (500001002); Dusk sends the dawn spirits away */
    public static void handleSolJanusRequest(Char chr, InPacket inPacket) {
        int skillId = inPacket.decodeInt();
        if (!chr.hasSkill(SOL_JANUS) || (skillId != SOL_JANUS_DUSK && skillId != SOL_JANUS_DAWN)) {
            chr.dispose();
            return;
        }
        if (skillId == SOL_JANUS_DUSK && chr.getField() != null) {
            Field field = chr.getField();
            for (Summon s : new ArrayList<>(field.getSummons())) {
                if (s.getChr() == chr && isDawn(s.getSkillID())) {
                    field.removeSummon(s);
                }
            }
        }
        setRecord(chr, QuestConstants.SKILL_COMMAND_LOCK_QUEST_ID, SOL_JANUS_KEY, skillId);
        chr.dispose();
    }

    /**
     * Sol Hecate: Pactum window: int value, int slot. The client reads the settings back from the skill command
     * record (21770) under "500001009_<slot>" (0x145a173d0).
     */
    public static void handleSolHecatePactumRequest(Char chr, InPacket inPacket) {
        int value = inPacket.decodeInt();
        int slot = inPacket.decodeInt();
        if (!chr.hasSkill(SOL_HECATE) || slot < 0 || slot > 2
                || (slot > 0 && value != 0 && !chr.hasSkill(value))) {
            chr.dispose();
            return;
        }
        setRecord(chr, QuestConstants.SKILL_COMMAND_LOCK_QUEST_ID, SOL_HECATE_PACTUM + "_" + slot, value);
        chr.dispose();
    }

    private static boolean isDuskMode(Char chr) {
        Quest q = chr.getQuestManager().getQuestById(QuestConstants.SKILL_COMMAND_LOCK_QUEST_ID);
        return q != null && q.hasProperty(SOL_JANUS_KEY) && q.getIntProperty(SOL_JANUS_KEY) == SOL_JANUS_DUSK;
    }

    /** Phantom's HEXA Mille Aiguilles card switch: kept for the client, its two skills show the change */
    public static void handleFateShuffleRequest(Char chr, InPacket inPacket) {
        int skillId = inPacket.decodeInt();
        if (!chr.hasSkill(PHANTOM_FATE_SHUFFLE) && !chr.hasSkill(PHANTOM_HEXA_MILLE_AIGUILLES)) {
            chr.dispose();
            return;
        }
        setRecord(chr, QuestConstants.SKILL_COMMAND_LOCK_QUEST_ID_2, FATE_SHUFFLE_KEY, skillId);
        chr.write(UserPool.skillOnOffEffectOf(chr.getId(), PHANTOM_HEXA_MILLE_AIGUILLES));
        chr.write(UserPool.skillOnOffEffectOf(chr.getId(), PHANTOM_HEXA_MILLE_AIGUILLES_FORTUNE));
        chr.dispose();
    }

    // ------------------------------------------------------------------------------------------------- skills

    @Override
    public void handleSkill(Char chr, TemporaryStatManager tsm, int skillId, int slv, InPacket inPacket,
                            SkillUseInfo skillUseInfo) {
        if (isDawn(skillId)) {
            spawnDawn(chr);
        }
        castInvincibility(chr, skillId, slv);
        solHecate(chr, skillId);
    }

    /**
     * Sol Hecate (toggle): GMS sends the spirit (walks with the player, sequence attacks, no end time) and Hekate n 1;
     * the cancel request ends both. Styx: HekateStyx n 1 for 3 s.
     */
    private static void solHecate(Char chr, int skillId) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        if (skillId == SOL_HECATE) {
            Field field = chr.getField();
            int slv = Math.max(1, chr.getSkillLevel(SOL_HECATE));
            if (field != null) {
                Summon summon = Summon.getSummonByNoCTS(chr, SOL_HECATE, slv);
                summon.setSummonTerm(0);
                summon.setMoveAbility(MoveAbility.Walk);
                summon.setAssistType(AssistType.SequenceAttack);
                summon.setFlyMob(false);
                field.spawnSummonAndRemoveOld(summon);
            }
            tsm.putCharacterStatValue(CharacterTemporaryStat.Hekate, new Option(1, skillId, 0));
            tsm.sendSetStatPacket();
        } else if (skillId == SOL_HECATE_STYX) {
            Option o = new Option(1, skillId, HECATE_STYX_MS);
            o.setInMillis(true);
            tsm.putCharacterStatValue(CharacterTemporaryStat.HekateStyx, o);
            tsm.sendSetStatPacket();
        }
    }

    /**
     * 6th job Origin/Ascent skills: invincible while casting, for the level's ndTime (ms). The client loads ndTime
     * too; the Indie stat only makes sure, and isn't put again while it runs.
     */
    private static void castInvincibility(Char chr, int skillId, int slv) {
        if (!SixthJobSkills.isOriginOrAscent(skillId)) {
            return;
        }
        SkillInfo si = SkillData.getSkillInfoById(skillId);
        int lv = slv > 0 ? slv : chr.getSkillLevel(skillId);
        if (si == null || lv <= 0) {
            return;
        }
        int nd = si.getValue(ndTime, lv);
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        if (nd <= 0 || tsm.hasAOptByCTSAndSkill(CharacterTemporaryStat.IndieNotDamaged, skillId)) {
            return;
        }
        Option o = new Option(1, skillId, nd);
        o.setInMillis(true);
        tsm.putCharacterStatValue(CharacterTemporaryStat.IndieNotDamaged, o, true);
        tsm.sendSetStatPacket();
    }

    private void spawnDawn(Char chr) {
        Field field = chr.getField();
        int slv = chr.getSkillLevel(SOL_JANUS);
        SkillInfo si = SkillData.getSkillInfoById(SOL_JANUS_DAWN);
        // no form of Sol Janus works on boss maps
        if (field == null || slv <= 0 || si == null || isDuskMode(chr) || field.isBossSpawned()) {
            return;
        }
        int max = Math.max(1, Math.min(3, si.getValue(w, slv)));
        List<Summon> dawns = new ArrayList<>();
        for (Summon s : field.getSummons()) {
            if (s.getChr() == chr && isDawn(s.getSkillID())) {
                dawns.add(s);
            }
        }
        if (dawns.size() >= max) {
            field.removeSummon(dawns.get(0)); // the oldest
            dawns.remove(0);
        }
        int summonId = SOL_JANUS_DAWN_3;
        for (int id : new int[]{SOL_JANUS_DAWN, SOL_JANUS_DAWN_2}) { // the first one not out
            if (dawns.stream().noneMatch(d -> d.getSkillID() == id)) {
                summonId = id;
                break;
            }
        }
        Summon summon = Summon.getSummonBy(chr, summonId, slv);
        summon.setMoveAction((byte) 4);
        summon.setAssistType(AssistType.Attack);
        summon.setMoveAbility(MoveAbility.Stop);
        summon.setFlyMob(false);
        field.spawnSummon(summon);
        // GMS: an icon per spirit, from that spirit's id; the data has no time for it, GMS uses 60 s
        int dur = si.getValue(time, slv) > 0 ? si.getValue(time, slv) : DAWN_ICON_SEC;
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        tsm.putCharacterStatValue(CharacterTemporaryStat.IndieBuffIcon, new Option(1, summonId, dur), true);
        tsm.sendSetStatPacket();
    }

    @Override
    public void handleAttack(Client c, AttackInfo attackInfo) {
        if (attackInfo != null && attackInfo.skillId > 0) {
            castInvincibility(chr, attackInfo.skillId, attackInfo.slv);
        }
        afterimageRestore(attackInfo);
        if (attackInfo == null || attackInfo.skillId == SOL_JANUS_DUSK || isDawn(attackInfo.skillId)
                || attackInfo.mobAttackInfo == null || attackInfo.mobAttackInfo.isEmpty()) {
            return;
        }
        duskVolley();
    }

    /** Afterimage of the Otherworld (V and HEXA): while it stands, prop% per attack to restore u% Max HP, w% Max MP. */
    private void afterimageRestore(AttackInfo attackInfo) {
        if (attackInfo == null || attackInfo.mobAttackInfo == null || attackInfo.mobAttackInfo.isEmpty()) {
            return;
        }
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        int skillId = tsm.hasAOptByCTSAndSkill(CharacterTemporaryStat.IndieCrDmg, HEXA_AFTERIMAGE) ? HEXA_AFTERIMAGE
                : tsm.hasAOptByCTSAndSkill(CharacterTemporaryStat.IndieCrDmg, AFTERIMAGE) ? AFTERIMAGE : 0;
        int slv = skillId == 0 ? 0 : chr.getSkillLevel(skillId);
        SkillInfo si = skillId == 0 ? null : SkillData.getSkillInfoById(skillId);
        if (si == null || slv <= 0 || !Util.succeedProp(si.getValue(prop, slv))) {
            return;
        }
        int hp = (int) ((long) chr.getMaxHP() * si.getValue(u, slv) / 100);
        int mp = (int) ((long) chr.getMaxMP() * si.getValue(w, slv) / 100);
        if (hp > 0) {
            chr.heal(hp);
        }
        if (mp > 0) {
            chr.healMP(mp);
        }
    }

    private void duskVolley() {
        if (!chr.hasSkill(SOL_JANUS) || chr.hasSkillOnCooldown(SOL_JANUS_DUSK) || !isDuskMode(chr)
                || chr.getField() == null || chr.getField().isBossSpawned()) {
            return;
        }
        SkillInfo si = SkillData.getSkillInfoById(SOL_JANUS_DUSK);
        if (si == null || si.getSecondAtomInfos().isEmpty()) {
            return;
        }
        int slv = chr.getSkillLevel(SOL_JANUS);
        Rect rect = chr.getRectAround(new Rect(-DUSK_RANGE, -DUSK_RANGE, DUSK_RANGE, DUSK_RANGE));
        if (!chr.isLeft()) {
            rect = rect.horizontalFlipAround(chr.getPosition().getX());
        }
        List<Mob> mobs = chr.getField().getMobsInRect(rect);
        if (mobs.isEmpty()) {
            return;
        }
        SecondAtomInfo sai = si.getSecondAtomInfos().get(0);
        int bullets = Math.max(1, si.getValue(bulletCount, slv));
        List<SecondAtom> atoms = new ArrayList<>();
        for (int i = 0; i < bullets; i++) {
            Mob mob = Util.getRandomFromCollection(mobs);
            atoms.add(new SecondAtom(chr.getNewSecondAtomKey(), sai.getDataIndex(), i, chr.getId(), mob.getObjectId(),
                    SOL_JANUS_DUSK, sai.getCreateDelay(), sai.getEnableDelay(), sai.getRotate(), sai.getExpire(), 360,
                    sai.getAttackableCount(), chr.getPosition(), sai.getCustomList()));
        }
        chr.spawnSecondAtoms(atoms);
        int hpCost = (int) ((long) si.getValue(hpRCon, slv) * chr.getMaxHP() / 100);
        if (hpCost > 0 && chr.getHP() > hpCost) {
            chr.heal(-hpCost);
        }
        chr.addSkillCoolTime(SOL_JANUS_DUSK, Math.max(1, si.getValue(cooltime, slv)) * 1000);
    }

    @Override
    public void handleHit(Client c, HitInfo hitInfo) {

    }

    @Override
    public void handleRemoveCTS(CharacterTemporaryStat cts, Option o) {
        if (cts == CharacterTemporaryStat.Hekate && chr.getField() != null) {
            for (Summon s : new ArrayList<>(chr.getField().getSummons())) {
                if (s.getChr() == chr && s.getSkillID() == SOL_HECATE) {
                    chr.getField().removeSummon(s);
                }
            }
        }
    }

    @Override
    public void handleChangeHP(int curHP, int newHP) {

    }

    @Override
    public void handleChangeMP(int curMP, int newMP) {

    }
}
