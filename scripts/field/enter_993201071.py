# Ereve's End (993201071): the shield awakens; Chromile's spirit (36628-36630).
execfile("scripts/quest/_mihile_story.py")

if sm.hasQuestCompleted(36627) and not sm.hasQuestCompleted(36630):
    show("78", "79", "81")
    if sm.hasQuestCompleted(36628):
        show("06")
sm.dispose()
