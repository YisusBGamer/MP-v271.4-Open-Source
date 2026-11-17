# 36630 Loose Ends (Cygnus 1541279, Ereve's End): Shinsoo's Teardrop back to Shinsoo's Refuge (993201063).
execfile("scripts/quest/_mihile_story.py")

me("Empress. I know what I have to do as the Knight of Light.")
if ask(CYGNUS, "Then be the shield that protects Ereve. First, restore #rShinsoo's Teardrop#k to its rightful place."):
    begin(parentID)
    sm.warpInstanceIn(SHINSOO_REFUGE, 0)
sm.dispose()
