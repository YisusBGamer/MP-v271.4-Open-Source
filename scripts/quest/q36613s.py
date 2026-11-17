# 36613 A Girl in Danger (self start, Lv 18): the scream came from the Wildland (993201010).
execfile("scripts/quest/_mihile_story.py")

me("Was that a scream?!")
me("Oh no, what's happening...?")
if ask(MIHILE, "I need to help before it gets more dangerous!\r\n\r\n#r(* When you accept, you will move to where the "
               "sound came from.)#k"):
    begin(parentID)
    me("I should hurry before someone gets hurt!")
    sm.warpInstanceIn(WILDLAND_SCENE, 0)
sm.dispose()
