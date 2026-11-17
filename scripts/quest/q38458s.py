# [Daily Quest] A Night's Peace in Lachelein
import time

GRAY_MASK = 3003209
reqMob = 500

sm.setSpeakerID(GRAY_MASK)
sm.sendNext("I am growing more and more disappointed with the never-ending festival in Lachelein.")
sm.setPlayerAsSpeaker()
sm.sendSay("I heard that too...What's the matter?")
sm.setSpeakerID(GRAY_MASK)
sm.sendSay("To bring peace to this endless festival city. Please help me bring some peace to Lachelein, the Dreamy City.")
if sm.sendAskAccept("It would be great if you could help us, #h0#. Of course, we will give you rewards you'll find very useful."):
    if sm.hasQuestCompleted(34272): # [Morass] The Swamp Remains
        reqMob = 100
    elif sm.hasQuestCompleted(34478): # [Arcana] The Harmony of the Forest
        reqMob = 300
    if sm.sendAskAccept("Hello! I have a task for you today. Defeat " + str(reqMob) + " monsters around the #bLachelein#k area."):
        sm.startQuest(parentID)
        sm.completeQuest(parentID)
        sm.setQRValue(parentID, "date", sm.getCurrentDateAsString())
        if reqMob == 100:
            sm.startQuest(38459)
        elif reqMob == 300:
            sm.startQuest(38460)
        else:
            sm.startQuest(38461)
        sm.setSpeakerID(GRAY_MASK)
        sm.sendNext("Come see me after you finish the quest request. Don't forget to return #r#ebefore midnight#n#k!\r\nThe time is #r#e" + time.strftime("%Y/%m/%d %H:%M"))
    else:
        sm.setSpeakerID(GRAY_MASK)
        sm.sendNext("Looks like you're not ready yet. Let me know when you are.")
else:
    sm.setSpeakerID(GRAY_MASK)
    sm.sendNext("Looks like you're not ready yet. Let me know when you are.")
