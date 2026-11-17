package net.swordie.ms.handlers.user;

import net.swordie.ms.client.jobs.adventurer.Kinesis;
import net.swordie.ms.client.jobs.resistance.WildHunter;
import net.swordie.ms.client.jobs.anima.HoYoung;
import net.swordie.ms.client.jobs.flora.Adele;
import net.swordie.ms.client.jobs.flora.Ark;
import net.swordie.ms.client.jobs.flora.Illium;
import net.swordie.ms.client.jobs.resistance.demon.DemonAvenger;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.quest.Quest;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.packet.UserPool;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.connection.packet.model.MessagePacket;
import net.swordie.ms.constants.QuestConstants;
import net.swordie.ms.handlers.Handler;
import net.swordie.ms.handlers.header.InHeader;

import java.util.Set;

/**
 * Right-click on a skill whose data has skillIconOnOff (e.g. Akatsuki Samurai: lock its activation effect). The skill
 * window sends In 1151 with the skill id; the state is a quest-ex 1544 key like the other right-click toggles, shown
 * with SKILL_ON_OFF_EFFECT (remoteSync: to the whole map).
 */
public class SkillIconToggleHandler {

    /** v271 Skill.wz skills with skillIconOnOff=1. */
    private static final Set<Integer> ICON_ON_OFF = Set.of(37120002, 41100005, 42111005, 80004201, 151100006,
            Adele.GATHERING, Adele.HEXA_CREATION, Adele.HEXA_GATHERING, Illium.WINGS_OF_GLORY, Ark.BLISSFUL_RESTRAINT_ATTACK, Ark.HEXA_BLISSFUL_RESTRAINT_ATTACK, HoYoung.GROUND_WAVE_REAL_1, HoYoung.WIND_WAVE_REAL_1, HoYoung.HEXA_EARTH_GROUND_SHATTERING_WAVE, HoYoung.HEXA_HEAVEN_IRON_FAN_GALE,
            DemonAvenger.DIMENSIONAL_SWORD_SUMMON, Kinesis.PSYCHIC_CASTLE, WildHunter.COMMAND_PREDATORS_EYE, WildHunter.PRIMAL_BLOOM);

    @Handler(op = InHeader.SKILL_ICON_ON_OFF_REQUEST)
    public static void handleSkillIconOnOff(Char chr, InPacket inPacket) {
        if (inPacket.getUnreadAmount() < 4) {
            return;
        }
        int skillId = inPacket.decodeInt();
        if (!ICON_ON_OFF.contains(skillId) || chr.getSkillLevel(skillId) <= 0) {
            return;
        }
        Quest quest = chr.getQuestManager().getOrCreateQuestById(QuestConstants.SKILL_COMMAND_LOCK_QUEST_ID_2);
        quest.convertQRValueToProperties();
        String key = String.valueOf(skillId);
        boolean on = !quest.hasProperty(key);
        if (on) {
            quest.setProperty(key, 1);
        } else {
            quest.getProperties().remove(key);
            quest.setQRValueToProperties();
        }
        chr.write(WvsContext.message(MessagePacket.questRecordExMessage(quest)));
        chr.getField().broadcastPacket(UserPool.skillOnOffEffectOf(chr.getId(), skillId, on));
    }
}
