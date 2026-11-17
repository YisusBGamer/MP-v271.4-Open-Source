# Lucid treasure rooms (Normal 450004300, Hard 450004600, Easy 450004900)
from net.swordie.ms.constants import BossConstants

CHESTS = [BossConstants.LUCID_REWARD_BOX_EASY, BossConstants.LUCID_REWARD_BOX_NORMAL, BossConstants.LUCID_REWARD_BOX_HARD]

if any(m.getTemplateId() in CHESTS for m in sm.getField().getMobs()):
    question = "You have defeated Lucid.\r\nWould you like to leave? The unopened treasure will be lost."
else:
    question = "You have defeated Lucid.\r\nWould you like to leave?"
if sm.sendAskYesNo(question):
    sm.warpInstanceOut(450004000)
sm.dispose()
