# Shinsoo's Refuge (993201063): the Teardrop is returned (36630); then Ereve, where Light Reborn (20810) waits at Lv 30.
execfile("scripts/quest/_mihile_story.py")

if sm.hasQuest(36630):
    finish(36630)
    hide("06", "78", "79", "81")
    try:
        scene_start()
        sm.sendDelay(1000)
        sm.spineScreen(False, False, True, 0, "Effect/Direction29.img/mikhail_2022/spine/1/skeleton", "animation", "mikhailShinsoo")
        sm.sendDelay(5000)
        sm.offSpineScreen("mikhailShinsoo", 1, "", 1000)
        say(SHINSOO, "The Teardrop has come home. Thank you, Knight of Light.")
        me("(Now it's time to be reborn as the true Knight of Light.)")
    finally:
        scene_end()
        sm.chatScript("Talk to Empress Cygnus in Ereve at Lv. 30 to be reborn as the Knight of Light.")
        sm.warpInstanceOut(EREVE, 0)
else:
    sm.warpInstanceOut(EREVE, 0)
sm.dispose()
