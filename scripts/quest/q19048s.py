# [Daily Quest] Cleaning Up Kronos
NEINHEART = 3007108
reqMob = 500

sm.setSpeakerID(NEINHEART)
if sm.sendAskAccept("I have a task for you today. Defeat " + str(reqMob) + " monsters around #bGeardock#k. Will you help?"):
    sm.completeQuestNoCheck(parentID)
    sm.setQRValue(parentID, "date", sm.getCurrentDateAsString())
    if reqMob == 100:
        sm.startQuest(19049)
    elif reqMob == 300:
        sm.startQuest(19050)
    else:
        sm.startQuest(19051)
    sm.sendNext("Come see me when you're done. Don't forget to return #r#ebefore midnight#n#k!")
else:
    sm.sendNext("Let me know when you're ready.")
