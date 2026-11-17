# [Daily Quest] Vanishing Journey Research
import time

RONA = 3003104
reqMob = 500

sm.setSpeakerID(RONA)
sm.sendNext("Vanishing Journey is a mysterious place where everything seems to be constantly destroyed.")
sm.setPlayerAsSpeaker()
sm.sendSay("What's up?")
sm.setSpeakerID(RONA)
sm.sendSay("Help the Time Guard with their research by completing the daily quests they hand out.")
if sm.sendAskAccept("It would be great if you could help us, #h0#. Of course, we will give you rewards you'll find very useful."):
    if sm.hasQuestCompleted(34331): # [Lachelein] Decisive Battle
        reqMob = 100
    elif sm.hasQuestCompleted(34218): # [Chu Chu] Goodbye, Chu Chu Island
        reqMob = 300
    if sm.sendAskAccept("Hello! I have a task for you today. Defeat " + str(reqMob) + " monsters around the #bVanishing Journey#k area."):
        sm.startQuest(parentID)
        sm.completeQuest(parentID)
        sm.setQRValue(parentID, "date", sm.getCurrentDateAsString())
        if reqMob == 100:
            sm.startQuest(38451)
        elif reqMob == 300:
            sm.startQuest(38452)
        else:
            sm.startQuest(38453)
        sm.setSpeakerID(RONA)
        sm.sendNext("Come see me after you finish the quest request. Don't forget to return #r#ebefore midnight#n#k!\r\nThe time is #r#e" + time.strftime("%Y/%m/%d %H:%M"))
    else:
        sm.setSpeakerID(RONA)
        sm.sendNext("Looks like you're not ready yet. Let me know when you are.")
else:
    sm.setSpeakerID(RONA)
    sm.sendNext("Looks like you're not ready yet. Let me know when you are.")
