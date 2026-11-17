# Portal script pt_hakhak.
# Mr. Do's Herb Field (Grand Athenaeum, Episode II): the field for the ingredient you're gathering - Glowing Antlers
# (Medicine Maker 1, 32620), Golden Peaches (32621) or Ginseng Concentrate (32622). The farmer there brings you back.
if sm.hasQuest(32621):
    sm.warp(303030300, 0)
elif sm.hasQuest(32622):
    sm.warp(303030200, 0)
else:
    sm.warp(303030100, 0)
sm.dispose()
