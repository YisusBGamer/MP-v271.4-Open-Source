# Somewhere in a Field (993201007): the Stray Dog of a Lost Property (36607) begun in this map is down.
execfile("scripts/quest/_mihile_story.py")

if sm.hasQuest(36607):
    me("(All right, I've got the picture. Homeward bound.)")
    finish(36607)
sm.warpInstanceOut(REMOTE_HOUSE, 0)
sm.dispose()
