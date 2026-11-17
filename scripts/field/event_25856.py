# id 940010020 (Hidden Street : Near South Sanctum), field 940010020
sm.lockInGameUI(True, True)
sm.forcedInput(1)
sm.sendDelay(30)
sm.forcedInput(0)
sm.spawnNpc(3000110, -2500, 0)
sm.showNpcSpecialActionByTemplateId(3000110, "summon", 0)
sm.spawnNpc(3000111, -2400, 0)
sm.showNpcSpecialActionByTemplateId(3000111, "summon", 0)
sm.spawnNpc(3000114, -2300, 0)
sm.showNpcSpecialActionByTemplateId(3000114, "summon", 0)
sm.spawnNpc(3000115, -2200, 0)
sm.showNpcSpecialActionByTemplateId(3000115, "summon", 0)
sm.spawnNpc(3000116, -2100, 0)
sm.showNpcSpecialActionByTemplateId(3000116, "summon", 0)
sm.spawnNpc(3000119, -2000, 0)
sm.showNpcSpecialActionByTemplateId(3000119, "summon", 0)
sm.spawnNpc(3000120, -1900, 0)
sm.showNpcSpecialActionByTemplateId(3000120, "summon", 0)
sm.setSpeakerType(3)
sm.setParam(17)
sm.sendNext("Oh!")
sm.setSpeakerID(3000120) # Nefarious Priest
sm.setParam(1)
sm.sendSay("Wh-who are you?!")
sm.setParam(17)
sm.sendSay("I am the lovely sentinel of justice, Angeli...")
sm.setSpeakerID(3000114) # Nefarious Priest
sm.setParam(1)
sm.sendSay("Get her!")
sm.setParam(17)
sm.sendSay("The other bad guys usually wait for me to finish my catchphrase...")
sm.setSpeakerID(3000110) # Nefarious Priest
sm.setParam(1)
sm.sendSay("Oh, uh, sorry, I'm new...")
sm.setParam(17)
sm.sendSay("It's okay... but I'll never, ever forgive you for being a jerk to the South Sanctum!")
sm.lockInGameUI(False, True)

# The recording ends as the fight starts ("I'll never, ever forgive you for being a jerk to the South Sanctum!"): the 7
# priests attack. The exit (portal/check_25856.py) opens once they're beaten.
if sm.hasQuest(25856):
    for npc, mob, x in ((3000110, 9300553, -2500), (3000111, 9300553, -2400), (3000114, 9300554, -2300),
                        (3000115, 9300554, -2200), (3000116, 9300555, -2100), (3000119, 9300555, -2000),
                        (3000120, 9300553, -1900)):
        sm.removeNpc(npc)
        sm.spawnMob(mob, x, 29, False)
