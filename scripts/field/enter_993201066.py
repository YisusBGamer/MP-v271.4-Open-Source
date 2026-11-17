# Atan's House (993201066): The Story of Chromile (36619) ends with Atan coming home to Mihile's letter.
execfile("scripts/quest/_mihile_story.py")

ATAN_NPC = 1541205

if sm.hasQuest(36619):
    try:
        scene_start()
        sm.hideUser(True)
        sm.spawnNpc(ATAN_NPC, 500, 150)
        sm.blind(1, 255, 0, 0)
        sm.sendDelay(300)
        unblack(1000)
        say(ATAN, "Mihile, I'm back.")
        say(ATAN, "...")
        say(ATAN, "Mihile? Are you here?")
        say(ATAN, "(Is he out training?)")
        sm.moveNpcByTemplateId(ATAN_NPC, True, 300, 100)
        sm.sendDelay(2000)
        say(ATAN, "(...What's this letter?)")
        sm.blind(1, 255, 0, 500)
        sm.sendDelay(500)
        say(ATAN, "*gasp*")
        say(ATAN, "Ereve?!")
        sm.setQRValue(36619, "dir", "1")
        finish(36619)
    finally:
        sm.hideUser(False)
        scene_end()
sm.warpInstanceOut(EREVE, 0)
sm.dispose()
