# [Daily Quest] Carcion Recovery Support
import time

DUHAN = 3006551
reqMob = 500

sm.setSpeakerID(DUHAN)
if sm.hasQuestCompleted(36996):  # Tallahart reached
    reqMob = 300
if sm.sendAskAccept("I have a task for you today. Defeat " + str(reqMob) + " monsters around #bCarcion#k. Will you help?"):
    sm.completeQuestNoCheck(parentID)
    sm.setQRValue(parentID, "date", sm.getCurrentDateAsString())
    if reqMob == 100:
        sm.startQuest(19041)
    elif reqMob == 300:
        sm.startQuest(19042)
    else:
        sm.startQuest(19043)
    sm.sendNext("Come see me when you're done. Don't forget to return #r#ebefore midnight#n#k!")
else:
    sm.sendNext("Let me know when you're ready.")
