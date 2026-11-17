# Shiba (1057000), Training Center (103050910): a new Dual Blade's basic training, 2600 (turn-in) and 2601-2603.
# v265 marks these blocked, so the client shows no lightbulb: Shiba hands them out and takes them in here.
# Training Spots 1-3 (103050911-103050913) hold the Snails, Blue Snails and Red Snails.
SHIBA = 1057000
TRAINING = [
    (2601, "#bSnails#k at #bTraining Spot 1#k", 10),
    (2602, "#bBlue Snails#k at #bTraining Spot 2#k", 15),
    (2603, "#bRed Snails#k at #bTraining Spot 3#k", 20),
]
MAPLE_ISLAND = (36301, 36302, 36303, 36304, 36306, 36307)

sm.setSpeakerID(SHIBA)


def finish_training():
    # as q2603e: Ryden's test comes next
    sm.sendNext("Slow. Much, much too slow. Like a wilting leaf... Are you sure you have what it takes to learn to dual-wield?")
    sm.sendSay("My insults slide like rain off your back... Hey, Ryden! What's with the new recruit?")
    sm.completeQuest(2603)
    sm.giveExp(50)
    sm.startQuestNoCheck(2604)
    for q in MAPLE_ISLAND:
        if not sm.hasQuestCompleted(q):
            sm.completeQuestNoRewards(q)
    sm.warp(103050101)


def run():
    if sm.getChr().getJob() != 0:
        sm.sendSayOkay("Faster. Always faster.")
        return
    if sm.hasQuest(2600):
        sm.sendNext("Ryden sent you? Hmph. Another recruit who looks like a wilting leaf.")
        sm.completeQuest(2600)
    elif not sm.hasQuestCompleted(2600):
        sm.sendSayOkay("Talk to Ryden in the Garden first.")
        return
    for quest, target, count in TRAINING:
        if sm.hasQuestCompleted(quest):
            continue
        if sm.hasQuest(quest):
            if not sm.isComplete(quest):
                sm.sendSayOkay("Not done yet. Defeat " + str(count) + " " + target + ". The doors are behind me.")
                return
            if quest == 2603:
                finish_training()
                return
            sm.sendNext("Done already? Not bad... for a beginner.")
            sm.completeQuest(quest)
            continue
        if sm.sendAskAccept("Your next lesson: defeat " + str(count) + " " + target + ". Can you handle that?"):
            sm.startQuestNoCheck(quest)
            sm.sendNext("Go. And don't come back until it's done.")
        else:
            sm.sendNext("Scared of a few snails?")
        return


run()
sm.dispose()
