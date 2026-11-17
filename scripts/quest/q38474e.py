# [Daily Quest] Moonbridge Research
VELIVAH = 3003810

sm.setSpeakerID(VELIVAH)
sm.sendNext("#b[Daily Quest] Moonbridge Research#k You have completed the quest successfully. It will help us a lot!")
if sm.sendAskYesNo("You have not received today's Moonbridge Daily Quest EXP yet.\r\nDo you want to claim it right now?\r\n\r\n#r#n* Moonbridge Daily Quest completion EXP can only be received once per day per world."):
    sm.completeQuest(parentID)
    sm.setSpeakerID(VELIVAH)
    sm.sendSayOkay("#r#e* You have received Daily Quest completion EXP in the Moonbridge area.*#n Daily Quest completion EXP in the Moonbridge area can only be received once per day per world.")
else:
    sm.setSpeakerID(VELIVAH)
    sm.sendNext("Let me know when you're ready to claim your reward.")
