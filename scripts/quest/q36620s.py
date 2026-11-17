# 36620 The Keepsake (Neinheart 1101002, auto start): Empress Aria's Keepsake from the Ghost Stumps of Gusty Peak, to
# 10 Boogies in Perion. Accepting sends Mihile to Gusty Peak.
execfile("scripts/quest/_mihile_story.py")

say(NEINHEART, "How goes your training? You seem a bit stronger now.")
say(NEINHEART, "I have a mission for you. I believe it will help you hone your skills.")
if ask(NEINHEART, "Go to Perion and recover the #blost keepsake#k.\r\n\r\n#r(* When you accept, you will move to Gusty Peak "
                  "automatically.)#k"):
    begin(parentID)
    say(NEINHEART, "The keepsake can be found among the #bGhost Stumps#k of #bGusty Peak#k. Take the recovered keepsake to "
                   "#b10 Boogies#k.")
    sm.warp(GUSTY_PEAK, 0)
sm.dispose()
