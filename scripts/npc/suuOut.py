# Lotus (v265) battle and treasure maps, leave NPC: to the Core Entrance
from net.swordie.ms.constants import BossConstants

CORE_ENTRANCE = 350060300

sm.flipSpeaker()
sm.flipDialoguePlayerAsSpeaker()
sm.setBoxChat()

mode = BossConstants.getLotusV265Mode(sm.getFieldID())
treasure = mode >= 0 and sm.getFieldID() == BossConstants.LOTUS_V265_FIELD_BASE[mode] + 600
if treasure or not sm.hasMobsInField():
    dialog = "Do you want to leave the Core?"
else:
    dialog = "Are you sure you want to leave the battlefield and abandon your party members?"

if sm.sendAskYesNo(dialog):
    sm.warpInstanceOut(CORE_ENTRANCE)
