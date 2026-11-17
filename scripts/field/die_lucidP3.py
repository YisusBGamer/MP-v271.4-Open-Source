# 8880153 (Hard Lucid, phase 3): Hard Lucid is defeated in time. On to her Final Music Box (450004600), with 5
# minutes to collect the rewards.
import time

sm.showFieldEffect("quest/party/clear", 0)
sm.playSound("Party1/Clear", 100)
for c in sm.getField().getChars():
    if c.getInstance() is not None:
        c.getInstance().setTimeout(5 * 60)
        break
if hasattr(sm, "warpFieldLaterIfStillIn"):
    sm.warpFieldLaterIfStillIn(11000, 450004600)  # backup if this script is stopped while it waits
time.sleep(5)
sm.warpField(450004600, 0)
