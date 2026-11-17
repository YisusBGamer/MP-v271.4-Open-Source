# [Tutorial] Combo Kills (16026). The quest is started before it is completed (the next tutorial quests wait for it).
sm.addPopUpSay(9010000, 6000, "#b[Guide] Combo Kill#k\r\n\r\n\r\nThe orb that was just created is a #b[Combo Kill Orb]#k!\r\n\r\n#b[Combo Kill Orbs]#k are created every #b50 combos#k while hunting monsters and grant #bbonus EXP#k!\r\n", "FarmSE.img/boxResult")
sm.startQuest(parentID)
sm.completeQuest(parentID)
