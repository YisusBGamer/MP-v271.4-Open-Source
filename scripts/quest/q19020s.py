# [Daily Quest] Cernium Research
import time

FLAME_PRIEST = 3004540
reqMob = 500

sm.setSpeakerID(FLAME_PRIEST)
if sm.hasQuestCompleted(38319):  # Odium reached
    reqMob = 100
elif sm.hasQuestCompleted(38120):  # Hotel Arcus reached
    reqMob = 300
if sm.sendAskAccept("I have a task for you today. Defeat " + str(reqMob) + " monsters around #bCernium#k. Will you help?"):
    sm.completeQuestNoCheck(parentID)
    sm.setQRValue(parentID, "date", sm.getCurrentDateAsString())
    if reqMob == 100:
        sm.startQuest(19021)
    elif reqMob == 300:
        sm.startQuest(19022)
    else:
        sm.startQuest(19023)
    sm.sendNext("Come see me when you're done. Don't forget to return #r#ebefore midnight#n#k!")
else:
    sm.sendNext("Let me know when you're ready.")
