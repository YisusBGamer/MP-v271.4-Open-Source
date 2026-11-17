# Weird Room -> Mask Room: only opens while the story needs it.
if sm.hasQuest(15129) or sm.hasQuest(15136):
    sm.warp(922900700)
else:
    sm.chatScript("The door won't budge.")
sm.dispose()
