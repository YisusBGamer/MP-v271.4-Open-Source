# [Daily Quest] Peace in Arcana
import time

TREE_SPIRITS = 3003322
reqMob = 500

sm.setSpeakerID(TREE_SPIRITS)
sm.sendNext("You hear the whispers of the Tree Spirits in the wind. Do they need your help again?.")
sm.setPlayerAsSpeaker()
sm.sendSay("I heard that too...What's the matter?")
sm.setSpeakerID(TREE_SPIRITS)
sm.sendSay("Please help restore balance to the Arcana forest in Grove of the Spirit Tree.")
if sm.sendAskAccept("It would be great if you could help us, #h0#. Of course, we will give you rewards you'll find very useful."):
    if sm.hasQuestCompleted(37871): # [Esfera] Mirrors in Mirrors
        reqMob = 100
    elif sm.hasQuestCompleted(34272): # [Morass] The Swamp Remains
        reqMob = 300
    if sm.sendAskAccept("Hello! I have a task for you today. Defeat " + str(reqMob) + " monsters around the #bArcana#k area."):
        sm.startQuest(parentID)
        sm.completeQuest(parentID)
        sm.setQRValue(parentID, "date", sm.getCurrentDateAsString())
        if reqMob == 100:
            sm.startQuest(38463)
        elif reqMob == 300:
            sm.startQuest(38464)
        else:
            sm.startQuest(38465)
        sm.setSpeakerID(TREE_SPIRITS)
        sm.sendNext("Come see me after you finish the quest request. Don't forget to return #r#ebefore midnight#n#k!\r\nThe time is #r#e" + time.strftime("%Y/%m/%d %H:%M"))
    else:
        sm.setSpeakerID(TREE_SPIRITS)
        sm.sendNext("Looks like you're not ready yet. Let me know when you are.")
else:
    sm.setSpeakerID(TREE_SPIRITS)
    sm.sendNext("Looks like you're not ready yet. Let me know when you are.")
