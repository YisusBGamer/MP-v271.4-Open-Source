# 23709 - Born to Hate
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Beryl (2159392)

sm.setSpeakerID(2159392)  # Beryl
if sm.sendNext("I am fully repaired! Why can't I beat you?\r\n#L0##bWhy are you here, Beryl? What is #p2154009# scheming?#l\r\n#k") != 0:
    sm.dispose()
if sm.sendSay("You dare utter his name after what you've done?! My father--\r\n#L0##bYou think #p2154009# is your father?#l\r\n#k") != 0:
    sm.dispose()
if sm.sendSay("I know he is.\r\n#L0##bThose are lies, Beryl! He is a monster and a kidnapping thief! He stole us from our homes, he erased our memory. THAT is who #p2154009# is. You are were human once, just like me.#l\r\n#k") != 0:
    sm.dispose()
if sm.sendSay("My past means nothing. I am #p2159391#, a Xenoroid. I am #p2154009#'s masterpiece!\r\n#L0##bYou're fooling yourself! Remember who you were!#l\r\n#k") != 0:
    sm.dispose()
if sm.sendSay("I'm not a failure like you. My past self was weak and insignificant. The new me will change the world!\r\n#L0##bA failure?#l\r\n#k") != 0:
    sm.dispose()
if sm.sendSay("Shut up and act like a Xenoroid! You've been corrupted by your feelings and memories. You're pathetic! I don't understand why father prizes you so highly.\r\n#L0##bWhat?#l\r\n#k") != 0:
    sm.dispose()
if sm.sendSay("I won't stand for it. You're a failure and a traitor, but father loves you more than me! \r\n#L0##bI-is that why you hate me?#l\r\n#k") != 0:
    sm.dispose()
if sm.sendSay("Shut up!\r\n#L0##bI knew you didn't like me, but #p2300000# insisted I was mistaken. She said you didn't have emotions, but if you really dislike me this much, you MUST still have some feelings!#l\r\n#k") != 0:
    sm.dispose()
sm.sendSay("I can't stand here and listen to you spewing your soft-hearted nonsense any more! I'm leaving, but this isn't over. Next time we meet, it's the end for you.")
sm.startQuest(parentID)
