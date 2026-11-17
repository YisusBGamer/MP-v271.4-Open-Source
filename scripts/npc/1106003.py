# Neinheart (1106003), General Store Yard (913070050): Mihile's "The Final Test" (20035).
# v265 marks it blocked (no lightbulb), so Neinheart starts and ends it here as q20035s/q20035e do.
NEINHEART = 1106003
ASSAILANT = 9001050
QUEST = 20035

sm.setSpeakerID(NEINHEART)


def spawn_assailants():
    for x in (-200, -50, 100, 250, 400):
        sm.spawnMob(ASSAILANT, x, 65, False)
        sm.spawnMob(ASSAILANT, x + 60, 65, False)


if sm.getChr().getJob() != 5000 or not sm.hasQuestCompleted(20034):
    sm.sendSayOkay("...")
elif sm.hasQuestCompleted(QUEST):
    sm.sendSayOkay("The Empress has something to tell you. Go on.")
elif not sm.hasQuest(QUEST):
    sm.sendNext("The Empress believes you're the one we came for. I'll believe it when I see it.")
    if sm.sendAskAccept("Let's call this a test. Defeat what I send at you, and I'll listen to whatever she has to say."):
        sm.startQuestNoCheck(QUEST)
        spawn_assailants()
        sm.sendNext("Don't let them touch you. Go!")
    else:
        sm.sendNext("Afraid already? Talk to me when you're ready.")
elif sm.isComplete(QUEST):
    sm.sendNext("Not bad, for someone who usually fights with a broom.")
    if sm.tryCompleteQuest(QUEST):
        sm.sendSay("Empress, I take it back. Go on, talk to her. She has something to tell you.")
else:
    if not sm.hasMobsInField():
        spawn_assailants()
    sm.sendSayOkay("You're not done. Defeat the Assailants.")
sm.dispose()
