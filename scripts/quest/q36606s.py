# 36606 The Third Training Session (Atan 1541202): White Sparrow Egg x15 in the Remote Forest Clearing.
execfile("scripts/quest/_mihile_story.py")

me("(Another day of the same old drills. I don't think they're helping me much anymore.)")
me("(Do I need a greater challenge to become the Knight of Light? Or... is there #rsomething else I'm missing#k...?)")
say(ATAN, "Mihile? Are you listening?")
me("Oh! Yes, Father.")
if ask(ATAN, "Go hunt #rGlutinous Rice Sparrows#k in the #bRemote Forest Clearing#k and bring back #r15 White Sparrow Eggs#k."):
    begin(parentID)
    say(ATAN, "Do remember, the #rStray Dogs' territory#k is just beyond the #bClearing#k. I'd rather not see you come back all "
              "covered in bite marks.")
    me("Don't worry.")
sm.dispose()
