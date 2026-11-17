# id 3 (south00), field 101080000
# The way into Elodin. The first time ("Onward to Ellinia's South Forest!") it goes through the scene in the deep
# forest; after that it leads to Slumbering Forest, at the portal that leads back here (west00)
ONWARD_TO_SOUTH_FOREST = 37155
SLUMBERING_FOREST = 101080900
WEST_PORTAL = 1  # west00
if sm.hasQuest(ONWARD_TO_SOUTH_FOREST):
    sm.lockInGameUI(True, False)
    sm.removeAdditionalEffect()
    sm.setSpeakerType(8)
    sm.setParam(3)
    sm.sendNext("I'm tough. What's the worst that could happen?")
    sm.sendDelay(500)
    sm.sendDelay(900)
    sm.hideUser(True)
    sm.blind(True, 255, 0, 0, 0, 500)
    sm.sendDelay(500)
    sm.showFadeTransition(0, 1000, 3000)
    sm.zoomCamera(0, 1000, 2147483647, 2147483647, 2147483647)
    sm.moveCamera(True, 0, 0, 0)
    sm.sendDelay(300)
    sm.removeOverlapScreen(1000)
    sm.moveCamera(True, 0, 0, 0)
    sm.lockInGameUI(False, True)
    sm.warp(910143001)
else:
    sm.warp(SLUMBERING_FOREST, WEST_PORTAL)
