# [Daily Quest] Investigate the Tallahart Ancient God's Power
import time

NEINHEART = 3006843
reqMob = 500

sm.setSpeakerID(NEINHEART)
if sm.hasQuestCompleted(36873):  # Geardock reached
    reqMob = 300
if sm.sendAskAccept("I have a task for you today. Defeat " + str(reqMob) + " monsters around #bTallahart#k. Will you help?"):
    sm.completeQuestNoCheck(parentID)
    sm.setQRValue(parentID, "date", sm.getCurrentDateAsString())
    if reqMob == 100:
        sm.startQuest(19045)
    elif reqMob == 300:
        sm.startQuest(19046)
    else:
        sm.startQuest(19047)
    sm.sendNext("Come see me when you're done. Don't forget to return #r#ebefore midnight#n#k!")
else:
    sm.sendNext("Let me know when you're ready.")
