# 10631 - Gaga's Alibi
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Cassandra (9010010)

sm.setSpeakerID(9010010)  # Cassandra
if sm.sendNext("So, what did Gaga say? Did you get my letter back? \r\n#L0##bGaga told me that he's never seen the letter... Are you sure the letter disappeared after Gaga came by? Maybe you misplaced it...#l\r\n#k") != 0:
    sm.dispose()
if sm.sendSay("That's what Gaga told you? NO! Gaga! I will never forgive him! I'm absolutely, 1,000% certain. It's him. He stole it. Did Gaga stutter when he talked to you?\r\n#L0##bA-a little. He stuttered and tried to avoid eye contact with me. He looked kind of nervous...#l\r\n#k") != 0:
    sm.dispose()
sm.sendSay("See? That's what Gaga does when he lies. How dare he lie to me! If this is what Gaga wants, then I have plans of my own!")
sm.tryCompleteQuest(parentID)
