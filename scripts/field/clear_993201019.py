# Somewhere in Ereve (993201019): Atan is down; A Young Man's Loss (36627) ends at Ereve's End.
execfile("scripts/quest/_mihile_story.py")

if sm.hasQuest(36627):
    try:
        scene_start()
        me("Father! Don't...!")
        say(ATAN, "Liar that I was, murderer that I was... you were still my son.")
        me("(He was my only family. I couldn't let him die. But he chose to anyway.)")
        finish(36627)
    finally:
        scene_end()
    hide("05")
    show("78", "79", "81")
sm.warpInstanceOut(EREVES_END, 0)
sm.dispose()
