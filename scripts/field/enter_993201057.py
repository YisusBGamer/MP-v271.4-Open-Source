# Remote House (993201057): Atan's home, where the Mihile story's training quests start.
execfile("scripts/quest/_mihile_story.py")

if sm.hasQuestCompleted(36601) and not sm.hasQuestCompleted(36611):
    show("02")
if sm.hasQuestCompleted(36601) and not sm.hasQuest(20036) and not sm.hasQuestCompleted(20036):
    sm.chatScript("Talk to Father.")
sm.dispose()
