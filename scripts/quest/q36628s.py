# 36628 A Light in the Darkness (Cygnus 1541279, Ereve's End): the sundered shield glows; Chromile appears.
execfile("scripts/quest/_mihile_story.py")

say(CYGNUS, "Mihile...")
me("(There's nothing left for me now. What should I do...?)")
if ask(CYGNUS, "Look, Mihile. Your shield..."):
    begin(parentID)
    try:
        scene_start()
        sm.spineScreen(False, False, True, 0, "Effect/Direction29.img/mikhail_2022/spine/0/skeleton", "animation", "mikhailShield")
        sm.sendDelay(5000)
        sm.offSpineScreen("mikhailShield", 1, "", 1000)
        me("(The shield is glowing... And something is appearing in the light...)")
        finish(parentID)
        show("06")
    finally:
        scene_end()
sm.dispose()
