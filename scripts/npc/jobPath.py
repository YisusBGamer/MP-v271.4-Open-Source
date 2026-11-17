# Job path selection, opened with @job. The work is done by JobPathModule (sm.chooseJobPath()).
sm.setSpeakerID(9010000)  # Maple Administrator
from net.swordie.ms.constants import GameConstants
from net.swordie.ms.constants import SkillConstants


def refresh_job_blessings():
    # Repair the current running build too; permanent refresh is in setJob.
    fairy = SkillConstants.getFairyBlessingByJob(chr.getJob())
    empress = SkillConstants.getEmpressBlessingByJob(chr.getJob())
    for skill in list(chr.getSkills()):
        skill_id = skill.getSkillId()
        if SkillConstants.isBlessingSkill(skill_id) and skill_id not in (fairy, empress):
            sm.removeSkill(skill_id)
    chr.initBlessingSkills()
    chr.initBaseStats()


refresh_job_blessings()


def refresh_rebirth_hyper_points():
    from net.swordie.ms.client.character import RebirthModule
    from net.swordie.ms.client.character.hyperstats import HyperStatsModule
    if not RebirthModule.isReborn(chr):
        return
    HyperStatsModule.reapplyCurrentPreset(chr)
    HyperStatsModule.refreshPointDisplay(chr)
    if chr.getLevel() >= 140:
        total = HyperStatsModule.getTotalHyperStatSp(chr)
        allocated = chr.getSpentHyperSp()
        chr.chatMessage("Hyper Stats refreshed: %d available (%d total, %d allocated)."
                        % (max(0, total - allocated), total, allocated))


refresh_rebirth_hyper_points()

WARNING = ("#r[Warning]#k " + GameConstants.JOB_PATH_WARNING + " Without @job, you advance through your class's "
           "official job advancement quests.\r\n\r\n")

choices = list(sm.getJobPathChoices())


def pick(items, sel):
    # menu selections come from the client: ignore anything out of range
    if sel is None or sel < 0 or sel >= len(items):
        return None
    return items[sel]


if len(choices) == 0:
    sm.sendSayOkay(sm.getJobPathStatus())
else:
    chosen = None
    if sm.isBeginnerJob():
        prev = sm.getPreviousClass()
        groups = [g for g in sm.getJobPathGroups() if any(c.getGroup() == g for c in choices)]
        text = (WARNING + "You are level #b" + str(chr.getLevel()) + "#k, so you can choose #bany job path#k.\r\n"
                "You become that class right away, at the job that fits your level and with the SP for your "
                "level, and then advance automatically at the job advancement levels. You also get a #bstarter kit#k: "
                "a weapon for your class and the secondary weapon it needs.\r\n")
        offset = 0
        if prev is not None:
            text += "#L0##bReturn to your previous class: " + prev.getName() + "#k#l\r\n"
            offset = 1
        for i in range(len(groups)):
            text += "#L" + str(i + offset) + "#" + groups[i] + "#l\r\n"
        sel = sm.sendNext(text)
        if prev is not None and sel == 0:
            chosen = prev
        else:
            group = pick(groups, sel - offset)
            if group is not None:
                inGroup = [c for c in choices if c.getGroup() == group]
                text = "#b" + group + "#k: which class do you want to become?\r\n"
                for i in range(len(inGroup)):
                    text += "#L" + str(i) + "#" + inGroup[i].getName() + "#l\r\n"
                chosen = pick(inGroup, sm.sendNext(text))
    else:
        text = WARNING + "Choosing a path makes you advance automatically from now on. Which path do you follow?\r\n"
        for i in range(len(choices)):
            text += "#L" + str(i) + "#" + choices[i].getName() + "#l\r\n"
        chosen = pick(choices, sm.sendNext(text))

    if chosen is not None and sm.sendAskYesNo("Follow the path of the #b" + chosen.getName() + "#k?\r\n\r\n"
                       + WARNING + "#rYour job and path can't be changed until you Rebirth.#k"):
        result = sm.chooseJobPath(chosen.getFinalJob())
        refresh_job_blessings()
        sm.sendSayOkay(result)
