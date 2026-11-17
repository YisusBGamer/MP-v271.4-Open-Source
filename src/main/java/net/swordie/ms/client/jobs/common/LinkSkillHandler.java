package net.swordie.ms.client.jobs.common;

import net.swordie.ms.client.Client;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.info.HitInfo;
import net.swordie.ms.client.character.skills.Option;
import net.swordie.ms.client.character.skills.info.AttackInfo;
import net.swordie.ms.client.character.skills.info.SkillUseInfo;
import net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat;
import net.swordie.ms.client.character.skills.temp.TemporaryStatManager;
import net.swordie.ms.client.jobs.anima.Lara;
import net.swordie.ms.client.jobs.nova.Kain;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.loaders.SkillData;

import static net.swordie.ms.client.character.skills.SkillStat.*;
import static net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat.IndieNBDR;
import static net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat.NatureFriend;
import static net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat.IndieDamR;
import static net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat.PriorPreparation;

/**
 * Created on 28/12/2021.
 *
 * @author Asura
 */
public class LinkSkillHandler implements ICommonSkillHandler {


    private Char chr;

    public LinkSkillHandler(Char chr) {
        this.chr = chr;
    }

    @Override
    public void handleSkill(Char chr, TemporaryStatManager tsm, int skillId, int slv, InPacket inPacket, SkillUseInfo skillUseInfo) {

    }

    @Override
    public void handleAttack(Client c, AttackInfo attackInfo) {
        //
        // Lara : Nature's Friend
        // Upon mob death, increment count. Upon defeating {x} monsters. give {w} NBDR buff for {time} and place skill on cooldown for {cooltime}
        //
        if (chr.hasSkill(Lara.NATURE_FRIEND_ORIGIN) || chr.hasSkill(Lara.NATURE_FRIEND_LINKED)) {
            natureFriend(attackInfo);
        }
        //
        // Kain : Time to Prepare
        // Upon defeating 8 enemies or hitting boss 5 times, increment count. Upon reaching 5 counts, give {w} Damage buff for {time} and place skill on cooldown for {cooltime}
        //
        if (chr.hasSkill(Kain.PRIOR_PREPARATION_ORIGIN) || chr.hasSkill(Kain.PRIOR_PREPARATION_LINKED)) {
            priorPreparation(attackInfo);
        }
        if (!attackInfo.mobAttackInfo.isEmpty()) {
            innateGift();
            qiCultivation(attackInfo);
        }
    }

    // Khali's Innate Gift: on attack, prop% to recover x% of max HP/MP every second for time (cooldown)
    private static final int INNATE_GIFT_ORIGIN = 150030241;
    private static final int INNATE_GIFT_LINKED = 80003224;
    // Mo Xuan's Qi Cultivation: each boss hit (once per cooldown) adds a stack of +y% boss damage, up to x, for time
    private static final int QI_CULTIVATION_ORIGIN = 170000241;
    private static final int QI_CULTIVATION_LINKED = 80011964;

    private int linkLevel(int origin, int linked) {
        return Math.max(chr.getSkillLevel(origin), chr.getSkillLevel(linked));
    }

    private void innateGift() {
        int slv = linkLevel(INNATE_GIFT_ORIGIN, INNATE_GIFT_LINKED);
        var si = SkillData.getSkillInfoById(INNATE_GIFT_ORIGIN);
        if (slv <= 0 || si == null || chr.hasSkillOnCooldown(INNATE_GIFT_ORIGIN)
                || !net.swordie.ms.util.Util.succeedProp(si.getValue(prop, slv))) {
            return;
        }
        int pct = si.getValue(x, slv);
        int seconds = Math.max(1, si.getValue(time, slv));
        chr.setSkillCooldown(INNATE_GIFT_ORIGIN, slv); // hardcode-ok
        net.swordie.ms.handlers.executors.EventManager.addFixedRateEvent(() -> {
            if (chr.getField() != null && chr.getHP() > 0) {
                chr.heal(chr.getHPPerc(pct));
                chr.healMP(chr.getMaxMP() * pct / 100);
            }
        }, 1000, 1000, seconds);
    }

    private void qiCultivation(AttackInfo attackInfo) {
        int slv = linkLevel(QI_CULTIVATION_ORIGIN, QI_CULTIVATION_LINKED);
        var si = SkillData.getSkillInfoById(QI_CULTIVATION_ORIGIN);
        if (slv <= 0 || si == null || chr.hasSkillOnCooldown(QI_CULTIVATION_ORIGIN)
                || attackInfo.mobAttackInfo.stream().noneMatch(mai -> mai.isBoss)) {
            return;
        }
        var tsm = chr.getTemporaryStatManager();
        var cur = tsm.getOptByCTSAndSkill(CharacterTemporaryStat.IndieBDR, QI_CULTIVATION_LINKED);
        int perStack = Math.max(1, si.getValue(y, slv));
        int stacks = cur == null ? 1 : Math.min(si.getValue(x, slv), cur.nOption / perStack + 1);
        tsm.removeStatsBySkill(QI_CULTIVATION_LINKED);
        tsm.putCharacterStatValue(CharacterTemporaryStat.IndieBDR,
                new Option(stacks * perStack, QI_CULTIVATION_LINKED, si.getValue(time, slv)), true);
        tsm.sendSetStatPacket();
        chr.setSkillCooldown(QI_CULTIVATION_ORIGIN, slv); // hardcode-ok
    }

    private void natureFriend(AttackInfo attackInfo) {
        if (chr.hasSkillOnCooldown(Lara.NATURE_FRIEND_ORIGIN)) {
            return;
        }

        var si = SkillData.getSkillInfoById(Lara.NATURE_FRIEND_ORIGIN);
        var slv = chr.getSkillLevel(Lara.NATURE_FRIEND_LINKED);
        if (slv <= 0) {
            slv = chr.getSkillLevel(Lara.NATURE_FRIEND_ORIGIN);
        }

        var tsm = chr.getTemporaryStatManager();

        var deadMobCount = (int) (attackInfo.mobAttackInfo.stream().filter(mai -> mai.mobDies && !mai.isBoss).count());

        if (deadMobCount <= 0) {
            return;
        }

        var currentStack = tsm.getOption(NatureFriend).nOption;
        var newStack = currentStack + deadMobCount;

        var threshold = si.getValue(x, slv);
        var buffNBDRValue = si.getValue(w, slv);
        var tOpt = si.getValue(time, slv);

        var giveBuff = newStack >= threshold;

        if (giveBuff) {
            tsm.removeStatsBySkill(Lara.NATURE_FRIEND_LINKED_BUFF);
            tsm.putCharacterStatValue(IndieNBDR, new Option(buffNBDRValue, Lara.NATURE_FRIEND_ORIGIN, tOpt), true);
            tsm.sendSetStatPacket();
            chr.setSkillCooldown(Lara.NATURE_FRIEND_ORIGIN, slv); // hardcode-ok
        } else {
            tsm.putCharacterStatValue(NatureFriend, new Option(Math.max(0, Math.min(newStack, threshold)), Lara.NATURE_FRIEND_LINKED_BUFF, 0), true);
            tsm.sendSetStatPacket();
        }
    }

    private void priorPreparation(AttackInfo attackInfo) {
        if (chr.hasSkillOnCooldown(Kain.PRIOR_PREPARATION_ORIGIN)) {
            return;
        }

        int slv = Math.max(
                chr.getSkillLevel(Kain.PRIOR_PREPARATION_LINKED),
                chr.getSkillLevel(Kain.PRIOR_PREPARATION_ORIGIN)
        );
        if (slv <= 0) {
            return;
        }

        var si = SkillData.getSkillInfoById(Kain.PRIOR_PREPARATION_ORIGIN);
        if (si == null) {
            return;
        }

        var tsm = chr.getTemporaryStatManager();

        int prepStacks = 0;
        int mobKills = 0;
        int bossHits = 0;

        if (tsm.hasStat(PriorPreparation)) {
            var o = tsm.getOption(PriorPreparation);
            prepStacks = o.nOption;
            mobKills = o.xOption;
            bossHits = o.yOption;
        }

        int mobThreshold = si.getValue(x, slv);
        int bossThreshold = si.getValue(w, slv);
        int maxPrep = si.getValue(u, slv);

        boolean changed = false;

        for (var mai : attackInfo.mobAttackInfo) {
            if (mai.isBoss) {
                bossHits++;
                changed = true;

                if (bossHits >= bossThreshold) {
                    prepStacks++;
                    bossHits = 0;
                    mobKills = 0;
                    break;
                }
            } else if (mai.mobDies) {
                mobKills++;
                changed = true;

                if (mobKills >= mobThreshold) {
                    prepStacks++;
                    bossHits = 0;
                    mobKills = 0;
                    break;
                }
            }
        }

        if (!changed) {
            return;
        }

        prepStacks = Math.min(prepStacks, maxPrep);

        if (prepStacks >= maxPrep) {
            tsm.removeStatsBySkill(Kain.PRIOR_PREPARATION_LINKED_BUFF);

            int dmgInc = si.getValue(y, slv);
            int dur = si.getValue(time, slv);

            tsm.putCharacterStatValue(
                    IndieDamR,
                    new Option(dmgInc, Kain.PRIOR_PREPARATION_ORIGIN, dur),
                    true
            );
            tsm.sendSetStatPacket();

            chr.setSkillCooldown(Kain.PRIOR_PREPARATION_ORIGIN, slv); // hardcode-ok
        } else {
            var o = new Option(prepStacks, Kain.PRIOR_PREPARATION_LINKED_BUFF, 0);
            o.xOption = mobKills;
            o.yOption = bossHits;

            tsm.putCharacterStatValue(PriorPreparation, o, true);
            tsm.sendSetStatPacket();
        }
    }

    @Override
    public void handleHit(Client c, HitInfo hitInfo) {

    }

    @Override
    public void handleRemoveCTS(CharacterTemporaryStat cts, Option o) {

    }

    @Override
    public void handleChangeHP(int curHP, int newHP) {

    }

    @Override
    public void handleChangeMP(int curMP, int newMP) {

    }
}
