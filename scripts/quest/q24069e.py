# 24069 - Memories of the Past
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Athena Pierce (1033221)

sm.setSpeakerID(1033221)  # Athena Pierce
sm.sendNext("Your Highness...\r\n#L0##bSo this is where you were hiding. Astilda was worried about you, Athena. Why did you come here without telling anyone?#l\r\n#k")
sm.sendSay("Th-they say you're leaving! It's not true, right?\r\n#L0##bIt IS true, Athena. I need to go with them...#l\r\n#k")
sm.sendSay("You're not going to fight the Black Mage, are you?\r\n#L0##bI am. I'm going to meet this Black Mage everyone is talking about, and I'm going to make him stop hurting people.#l\r\n#k")
sm.sendSay("Don't go! What if you get hurt?!\r\n#L0##bDon't worry! I'm going with my friends, and they're all strong, like me. We'll be fine.#l\r\n#k")
sm.sendSay("You're lying! I heard the Black Mage is so strong he could, like, reorganize the whole map just by snapping his fingers! Please don't go!\r\n#L0##bI have no choice...#l\r\n#k")
sm.sendSay("Can't you just send someone else? You're the ruler of the Elves! Who'll lead us if you're gone?!\r\n#L0##bThat's exactly why I HAVE to go! If the Black Mage isn't stopped, there may not be any Elves left to lead! #l\r\n#k")
sm.sendSay("Then...take me with you!\r\n#L0##bExcuse me?#l\r\n#k")
sm.sendSay("I can't let you go alone, Your Highness! Who'll keep you safe?\r\n#L0##bTh-that's...that's okay, Athena.#l\r\n#k")
sm.sendSay("I'm going with you, no matter what!")
sm.tryCompleteQuest(parentID)
