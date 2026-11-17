# 13107 - [Maple Castle] Halloween Costumaniac
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Vampire Phantom (9000138)

sm.setSpeakerID(9000138)  # Vampire Phantom
sm.sendNext("You already beat all the b#o9500587##k monsters for the #i3994660##b#t3994660##k?")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Splendid! You took care of those unsightly #b#o9500587##k monsters. Thanks! Now, you want to know more about the #b#t3994658##k right?")
    sm.sendSay("This stone tablet contains the signatures of #bMaple Castle#k students from thousands of years ago. The writing is ancient, but you can still make the signatures out. There's a message, as well:\r\n\r\n#b\"We are destroying the magic scrolls and sealing up the castle to prevent the innocent and pure knowledge from falling into the hands of evil.\"#k")
    sm.sendSay("What exactly were the students afraid of? Whatever it was, it was surely a #bhuge threat#k.\r\n")
    sm.sendSay("Well, that's all I have for you. Here, the #b#t3994658##k is yours. Run along to Neinheart, and make sure he knows how I sacrificed to get him this information.")
