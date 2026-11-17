# 36617 To Ereve, the Sky Island (Neinheart 1541203): off to Ereve with the Empress (arrival scene 993201011).
execfile("scripts/quest/_mihile_story.py")

if ask(NEINHEART, "#bEreve#k, Her Majesty's home, is #ba floating island in the sky#k. By the grace of Shinsoo, it stays "
                  "aloft and well protected. Are you ready to go?\r\n\r\n#r(* When you accept, you will move to Ereve "
                  "automatically.)#k"):
    begin(parentID)
    me("(Father will see the letter. He'll understand.)")
    say(NEINHEART, "Very good. Come along.")
    sm.warpInstanceIn(ARRIVAL_SCENE, 0)
sm.dispose()
