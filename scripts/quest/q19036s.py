# [Daily Quest] Defeat the Arteria Remnants
import time

NEINHEART = 3006405
reqMob = 500

sm.setSpeakerID(NEINHEART)
if sm.hasQuestCompleted(36996):  # Tallahart reached
    reqMob = 100
elif sm.hasQuestCompleted(38702):  # Carcion reached
    reqMob = 300
if sm.sendAskAccept("I have a task for you today. Defeat " + str(reqMob) + " monsters around #bArteria#k. Will you help?"):
    sm.completeQuestNoCheck(parentID)
    sm.setQRValue(parentID, "date", sm.getCurrentDateAsString())
    if reqMob == 100:
        sm.startQuest(19037)
    elif reqMob == 300:
        sm.startQuest(19038)
    else:
        sm.startQuest(19039)
    sm.sendNext("Come see me when you're done. Don't forget to return #r#ebefore midnight#n#k!")
else:
    sm.sendNext("Let me know when you're ready.")
