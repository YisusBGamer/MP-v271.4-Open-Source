# Beast Tamer tutorial. The client has only a few of its lines (Say.img, marked 'official'); the rest follow the
# quests' Quest.wz summaries. The Express skip (field/q59000_tuto.py) still works.
# Like, Grrr and Stuff (59015) - Fort the bear lends you his power (Bear mode); the kobolds are in the next part
# of town (field/onUserEnter_866129000.py adds the Smelly Kobolds).
if not sm.hasQuest(parentID) and not sm.hasQuestCompleted(parentID):
    sm.setSpeakerID(9390301)
    sm.sendNext("Grrr... Kobolds in OUR town? Like, no way. Let's give 'em a taste of bear, yo.")
    if sm.sendAskAccept("Ready to try out my might?"):
        sm.startQuest(parentID)
        sm.giveSkill(110001501, 1, 1)  # Bear mode
        sm.sendNext("Use my power to defeat those monsters!")  # official (Say.img)
if sm.hasQuest(parentID) and sm.getFieldID() != 866129000:
    sm.warp(866129000, 0)
sm.dispose()
