# Attacked Ereve (993201070): Father (Atan) is the intruder; Shinsoo's Teardrop (36623-36627).
execfile("scripts/quest/_mihile_story.py")

if sm.hasQuestCompleted(36621) and not sm.hasQuestCompleted(36627):
    show("05", "73", "76", "77", "80")
    if not sm.hasQuestCompleted(36623):
        me("(The intruder... Father?!)")
sm.dispose()
