# 8880150 / 8880151 (Lucid phase 2, Normal / Hard).
# Normal: cleared, on to the Final Music Box. Hard: Lucid's third phase - she has 45 seconds
# (plus a few to settle in); if she isn't defeated in time she goes back to phase 2 with 16% HP.
import time
from net.swordie.ms.constants import BossConstants

field_id = sm.getFieldID()
if BossConstants.isLucidHardField(field_id):
    time.sleep(3)
    try:
        # a failed phase 3 sends her back to phase 2 with 16% HP (GMS), instead of ending the run
        from net.swordie.ms.life.mob.boss.lucid import LucidHardPhase3
        LucidHardPhase3.start(sm.getField())
    except ImportError:
        sm.spawnMob(BossConstants.LUCID_HARD_TEMPLATE_ID_3, 600, -500, False, BossConstants.LUCID_HARD_HP_3)
        for c in sm.getField().getChars():
            c.chatMessage("Lucid sinks into her last dream! Defeat her within %d seconds!"
                          % BossConstants.LUCID_HARD_PHASE_3_TIME)
            if c.getInstance() is not None:
                c.getInstance().setTimeout(BossConstants.LUCID_HARD_PHASE_3_TIME + 5)
                break  # one instance for the whole party
else:
    sm.showFieldEffect("quest/party/clear", 0)
    sm.playSound("Party1/Clear", 100)
    if hasattr(sm, "warpFieldLaterIfStillIn"):
        sm.warpFieldLaterIfStillIn(16000, field_id + BossConstants.lucidStep(field_id))  # backup if this script is stopped while it waits
    sm.createClock(10)  # GMS: 10 s to the treasure room
    time.sleep(10)
    sm.warpField(field_id + BossConstants.lucidStep(field_id), 0)
