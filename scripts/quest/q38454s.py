# [Daily Quest] Chu Chu's Finest Cuisine
import time

MASTER_LYCK = 3003152
reqMob = 500

sm.setSpeakerID(MASTER_LYCK)
sm.sendNext("Help me cook a great dish to treat everyone to a feast.")
if sm.sendAskAccept("It would be great if you could help us, #h0#. Of course, we will give you rewards you'll find very useful."):
    if sm.hasQuestCompleted(34478): # [Arcana] The Harmony of the Forest
        reqMob = 100
    elif sm.hasQuestCompleted(34331): # [Lachelein] Decisive Battle
        reqMob = 300
    if sm.sendAskAccept("Hello! I have a task for you today. Defeat " + str(reqMob) + " monsters around the #bChu Chu#k area."):
        sm.startQuest(parentID)
        sm.completeQuest(parentID)
        sm.setQRValue(parentID, "date", sm.getCurrentDateAsString())
        if reqMob == 100:
            sm.startQuest(38455)
        elif reqMob == 300:
            sm.startQuest(38456)
        else:
            sm.startQuest(38457)
        sm.setSpeakerID(MASTER_LYCK)
        sm.sendNext("Come see me after you finish the quest request. Don't forget to return #r#ebefore midnight#n#k!\r\nThe time is #r#e" + time.strftime("%Y/%m/%d %H:%M"))
    else:
        sm.setSpeakerID(MASTER_LYCK)
        sm.sendNext("Looks like you're not ready yet. Let me know when you are.")
else:
    sm.setSpeakerID(MASTER_LYCK)
    sm.sendNext("Looks like you're not ready yet. Let me know when you are.")
