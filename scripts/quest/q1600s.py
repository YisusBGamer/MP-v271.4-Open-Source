# [Silent Crusade] A Cry for Help
# Jake's official lines (Quest.wz Say.img). The decline line used to be an offensive made-up one.
from net.swordie.ms.constants import QuestConstants

JAKE = 1052006
SUBWAY_TRANSTICKET = 2030028

sm.setSpeakerID(JAKE)
response = sm.sendAskAccept("Help! You gotta help me! She's gonna get clobbered!")

if response:
    if not sm.canHold(SUBWAY_TRANSTICKET):
        sm.sendSayOkay("Please make some room in your 'use' inventory.")
        sm.dispose()

    sm.giveItem(SUBWAY_TRANSTICKET)
    sm.startQuest(parentID)
    sm.showFieldEffect("Map/Effect.img/crossHunter/chapter/start1")
    sm.createQuestWithQRValue(QuestConstants.SILENT_CRUSADE_WANTED_TAB_1, "")
    sm.createQuestWithQRValue(QuestConstants.SILENT_CRUSADE_WANTED_TAB_2, "")
    sm.createQuestWithQRValue(QuestConstants.SILENT_CRUSADE_WANTED_TAB_3, "")
    sm.createQuestWithQRValue(QuestConstants.SILENT_CRUSADE_WANTED_TAB_4, "")
    sm.sendSayOkay("Some lady followed these creeps into the Subway. Those guys looked real dangerous! Think you could go in "
                   "after her and make sure she's okay? I'm sure they were headed for the #bSubway Construction Site#k.")
else:
    sm.sendSayOkay("What's wrong?")
