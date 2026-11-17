# Little Room (993201056): Mihile's room. The Shield starts 36601, the Weapon Box ends it.
execfile("scripts/quest/_mihile_story.py")

if not sm.hasQuest(36601) and not sm.hasQuestCompleted(36601):
    show("00")
    sm.chatScript("Talk to the Shield.")
elif sm.hasQuest(36601):
    sm.chatScript("Take the Beginner Warrior's Sword out of the Weapon Box.")
sm.dispose()
