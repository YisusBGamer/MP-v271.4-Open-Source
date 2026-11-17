# 9130022 Naoe Kanetsugu (Momijigaoka, 807000000). The Unfamiliar Hillside (807040000) has no way in once left, and
# Kanna's first quests (57400, 57401) are turned in there: a Kanna who left before finishing them is taken back.
from net.swordie.ms.constants import JobConstants

HILLSIDE = 807040000

if JobConstants.isKanna(chr.getJob()) and not sm.hasQuestCompleted(57401):
    if sm.sendAskYesNo("Kanna, you left the hillside before we finished talking. Shall I take you back to the "
                       "Unfamiliar Hillside?"):
        sm.warp(HILLSIDE)
else:
    sm.sendSayOkay("Lord Kenshin trusts me with Momijigaoka's affairs. Speak your business quickly.")
sm.dispose()
