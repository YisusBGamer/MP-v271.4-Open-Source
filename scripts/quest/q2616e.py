# 2616 - Helping JM with His Problem
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: JM From tha Streetz (1052002)

sm.setSpeakerID(1052002)  # JM From tha Streetz
sm.sendNext("Hey, buddy, you really delivered on the goods. Thanks. And tell the Dark Lord thanks as well.")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("What's that? The Dark Lord and I? Well, we've both lived in Kerning City for a long time, but I wouldn't call us 'close' exactly, not as close as he is with his friends outside of town.")
    sm.sendSay("You know, the friends that send him a #bDelivery Package once a week#k. I mean, I assumed the person sending it is a friend of the Dark Lord's. Makes sense, right?")
    sm.sendSay("#b(Well, that's it for JM. Who's left? According to Ryden... Don Giovanni of the Hair Salon, was it?)")
