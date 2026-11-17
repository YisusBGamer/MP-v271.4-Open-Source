# [Moonbridge] Maple World Right Now (35607): Athena asks you to calm the soldiers in the Outpost. She and the
# soldiers the quest names (3003812-3003815) stand nowhere, so the talk is played here with the Outpost's soldiers.
# Started on arrival in the Outpost too (field/enter_993060020).
ATHENA = 3003812
NOVA, RESISTANCE, CYGNUS = 3003670, 3003669, 3003668

if not sm.hasQuestCompleted(parentID):
    if not sm.hasQuest(parentID):
        sm.setSpeakerID(ATHENA)
        sm.sendNext("The battle is almost upon us. Everyone's putting on a brave face, but I can tell they're anxious.")
        if sm.sendAskAccept("As the time for battle approaches, talk to the anxious soldiers and help calm their "
                            "nerves."):
            sm.startQuest(parentID)
    if sm.hasQuest(parentID):
        sm.setSpeakerID(NOVA)
        sm.sendNext("Nova, the Resistance, the Cygnus Knights... I never thought we'd all fight under one banner.")
        sm.setSpeakerID(RESISTANCE)
        sm.sendNext("You came back from the Arcane River? Then there's still hope for us.")
        sm.setSpeakerID(CYGNUS)
        sm.sendNext("We're ready. Whatever is waiting for us up there, we'll face it together.")
        sm.setPlayerAsSpeaker()
        sm.sendNext("(Despite all the tension and anxiety, everyone took up arms with the newfound determination to "
                    "save the world.)")
        sm.completeQuest(parentID)
sm.dispose()
