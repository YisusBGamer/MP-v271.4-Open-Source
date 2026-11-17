# 36612 Going Out 2 (self start, Lv 18): days after Father left, hunting Field Mice; a voice calls for help (993201007).
execfile("scripts/quest/_mihile_story.py")

me("(All I have to do is not wander off too far.)")
if ask(MIHILE, "Should I hunt Field Mice?\r\n\r\n#r(* When you accept, you will be moved to the Remote Forest Field.)#k"):
    begin(parentID)
    sm.warpInstanceIn(FIELD_SCENE, 0)
sm.dispose()
