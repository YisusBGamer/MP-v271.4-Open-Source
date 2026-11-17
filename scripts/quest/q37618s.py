# 37618 [Reverse City] Upward and Downward - start: Barr (3004619), M Tower 1
# Official summary: go to M Tower Top Floor. Done by reaching it (field script enter_450014240, which also brings
# Barr there).
sm.setSpeakerID(3004619) # Barr
sm.sendNext("Ugh... everyone in one piece? That crash would've finished a lesser survivor.")
sm.sendSay("The #bSingularity Maker#k is at the top of this tower. We have to reach it before it's fully charged.")
if not sm.sendAskAccept("Go on ahead. I'll catch up."):
    sm.dispose()
sm.startQuest(parentID)
sm.sendSayOkay("Use the portal on the bottom right and go to M Tower Top Floor.\r\nI will follow you soon.")  # official line
