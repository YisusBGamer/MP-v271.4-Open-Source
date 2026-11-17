# Limbert's General Store (913070000)
def skip_tutorial():
    MAPLE_ADMINISTARTOR = 2007

    quests_to_complete = [
        20031,
        20032,
        20033,
        20034,
        20035,
        20036,
        29976,
    ]
    # the v265 Mihile story (36601-36630) plays in story instances this server has no scripts for;
    # Light Reborn (20810, 2nd job) needs 36630
    quests_to_complete += [36601] + [q for q in range(36603, 36631) if q not in (36622, 36626)]
    skills_to_add = [

    ]

    map_to_warp = 913070071 # Ereve
    target_level = 10

    sm.setSpeakerID(MAPLE_ADMINISTARTOR)
    sm.removeEscapeButton()
    sm.lockInGameUI(True)

    if sm.askTutorialSkip(130000000):
        if sm.getChr().getLevel() < target_level:
            sm.addLevel(target_level - sm.getChr().getLevel())

        for quest in quests_to_complete:
            sm.completeQuestNoRewards(quest)

        # Skipping gives what "A Knight of Light" (q20036s) gives at the end of the tutorial. Played through,
        # the character stays a Nameless Warden (5000) until then, as on the official server.
        sm.giveItem(1142399) # Newborn Light (Medal)
        sm.giveItem(1052444) # Apprentice Knight of Light Robe
        sm.giveItem(1302077) # Beginner Warrior's Sword
        sm.giveNewSecondary(1098000) # Soul Shield of Protection
        sm.setJob(5100) # gives the job's SP
        sm.setSTR(4)
        sm.setINT(4)
        sm.setDEX(4)
        sm.setLUK(4)
        sm.setAP(4 + chr.getLevel() * 5)

    for skillID in skills_to_add:
        sm.giveSkill(skillID)
    sm.lockInGameUI(False)
    if sm.getChr().getLevel() > 1:
        sm.warp(map_to_warp)
    elif chr.getJob() == 5000:
        # played: the v265 story opens with Cygnus and Neinheart at the bell, then Mihile's home (36601).
        # No dispose after this warp: it would stop the prologue's own field script (enter_993201000).
        sm.warpInstanceIn(993201000, 0)
        return True
    return False

if not skip_tutorial():
    sm.dispose()
