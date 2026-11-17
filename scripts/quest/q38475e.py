# [Daily Quest] Labyrinth of Suffering Research
VELIVAH = 3003810

sm.setSpeakerID(VELIVAH)
sm.sendNext("#b[Daily Quest] Labyrinth of Suffering Research#k You have completed the quest successfully. It will help us a lot!")
if sm.sendAskYesNo("You have not claimed today's Labyrinth of Suffering daily quest EXP.\r\nDo you want to claim it now?\r\n\r\n#r#n* Labyrinth of Suffering daily quest completion EXP can only be claimed once per day per world."):
    sm.completeQuest(parentID)
    sm.setSpeakerID(VELIVAH)
    sm.sendSayOkay("#r#e* You have received the Labyrinth of Suffering Area daily quest completion EXP.*#n Labyrinth of Suffering Area daily quest completion EXP can only be received once per day per world.")
else:
    sm.setSpeakerID(VELIVAH)
    sm.sendNext("Let me know when you're ready to claim your reward.")
