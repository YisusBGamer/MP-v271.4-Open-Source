# 36611 Father's Loss (Atan 1541202, Lv 18): before he leaves, Father tells how Mihile's brother died (993201006).
execfile("scripts/quest/_mihile_story.py")

say(ATAN, "...Before I go, we should talk about something.")
me("What is it?")
if ask(ATAN, "It'll be a long talk. Do you have time for it now?\r\n\r\n#r(* You will begin the talk when you accept.)#k"):
    begin(parentID)
    say(ATAN, "Very good.")
    sm.warpInstanceIn(HOUSE_SCENE, 0)
sm.dispose()
