# 993060020 Outpost: Maple World Right Now (35607) right after Neinheart's Briefing (35605). Athena, who gives it,
# isn't placed here; the talk is the same as quest/q35607s.
ATHENA = 3003812
NOVA, RESISTANCE, CYGNUS = 3003670, 3003669, 3003668

if sm.hasQuestCompleted(35605) and not sm.hasQuestCompleted(35607):
    if not sm.hasQuest(35607):
        sm.setSpeakerID(ATHENA)
        sm.sendNext("The battle is almost upon us. Everyone's putting on a brave face, but I can tell they're anxious.")
        if sm.sendAskAccept("As the time for battle approaches, talk to the anxious soldiers and help calm their "
                            "nerves."):
            sm.startQuest(35607)
    if sm.hasQuest(35607):
        sm.setSpeakerID(NOVA)
        sm.sendNext("Nova, the Resistance, the Cygnus Knights... I never thought we'd all fight under one banner.")
        sm.setSpeakerID(RESISTANCE)
        sm.sendNext("You came back from the Arcane River? Then there's still hope for us.")
        sm.setSpeakerID(CYGNUS)
        sm.sendNext("We're ready. Whatever is waiting for us up there, we'll face it together.")
        sm.setPlayerAsSpeaker()
        sm.sendNext("(Despite all the tension and anxiety, everyone took up arms with the newfound determination to "
                    "save the world.)")
        sm.completeQuest(35607)
