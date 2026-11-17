# [Daily Quest] Limina Research
VELIVAH = 3003810

sm.setSpeakerID(VELIVAH)
sm.sendNext("#b[Daily Quest] Limina Research#k You have completed the quest successfully. It will help us a lot!")
if sm.sendAskYesNo("You have not claimed today's Limina daily quest EXP yet.\r\nDo you want to claim it now?\r\n\r\n#r#n* Limina Daily Quest Completion EXP can only be claimed once a day per world."):
    sm.completeQuest(parentID)
    sm.setSpeakerID(VELIVAH)
    sm.sendSayOkay("#r#e* You have claimed Limina Daily Quest EXP in the Limina area.*#n Limina Daily Quest EXP in the Limina area can only be claimed once a day per world.")
else:
    sm.setSpeakerID(VELIVAH)
    sm.sendNext("Let me know when you're ready to claim your reward.")
