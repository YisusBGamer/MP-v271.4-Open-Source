# 450004200 / 450004500 (Lachelein : Nightmare Clocktower): the dream collapses - on to Lucid's second phase in the
# Crumbling Clocktower (Normal 450004250, Hard 450004550, Easy 450003920). Each difficulty stays in its map set.
from net.swordie.ms.constants import BossConstants
nxt = sm.getFieldID() + BossConstants.lucidStep(sm.getFieldID())
if hasattr(sm, "warpLaterIfStillIn"):
    sm.warpLaterIfStillIn(15000, sm.getFieldID(), nxt)  # backup if the scene below never finishes
sm.lockInGameUI(True, False)
sm.blind(True, 255, 240, 240, 240, 0)
sm.sendDelay(500)
sm.showEffect("Map/Effect3.img/BossLucid/Lucid5", 0, 89, 36, 1, 0, 1, 0)
sm.setSpeakerType(3)
sm.setParam(37)
sm.setColor(1)
sm.setInnerOverrideSpeakerTemplateID(3003250) # Lucid
sm.sendNext("#face6#No! The dream is collapsing!")
sm.blind(False, 0, 0, 0, 0, 1500)
sm.playSound("Sound/SoundEff.img/ArcaneRiver/phase2", 200)
sm.showEffect("Map/Effect3.img/BossLucid/Lucid2", 0, 89, 36, 10, 0, 1, 0)
sm.showEffect("Map/Effect3.img/BossLucid/Lucid3", 0, -140, 100, 11, 0, 1, 0)
sm.showEffect("Map/Effect3.img/BossLucid/Lucid4", 0, 89, 36, 1, 0, 1, 0)
sm.sendDelay(2000)
sm.blind(True, 255, 240, 240, 240, 1300)
sm.sendDelay(1600)
sm.lockInGameUI(False, True)
sm.warp(nxt)
