# [Daily Quest] Odium Area Expedition
import time

SYASYA = 3005532
reqMob = 500

sm.setSpeakerID(SYASYA)
if sm.hasQuestCompleted(38653):  # Arteria reached
    reqMob = 100
elif sm.hasQuestCompleted(38404):  # Shangri-La reached
    reqMob = 300
if sm.sendAskAccept("I have a task for you today. Defeat " + str(reqMob) + " monsters around #bOdium#k. Will you help?"):
    sm.completeQuestNoCheck(parentID)
    sm.setQRValue(parentID, "date", sm.getCurrentDateAsString())
    if reqMob == 100:
        sm.startQuest(19029)
    elif reqMob == 300:
        sm.startQuest(19030)
    else:
        sm.startQuest(19031)
    sm.sendNext("Come see me when you're done. Don't forget to return #r#ebefore midnight#n#k!")
else:
    sm.sendNext("Let me know when you're ready.")
