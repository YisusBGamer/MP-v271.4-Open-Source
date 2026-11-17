# 14208 - [Spring Event] All Done
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Mommy Spring Bunny (9001117)

sm.setSpeakerID(9001117)  # Mommy Spring Bunny
if sm.sendAskAccept("Whew! Holiday food preparation never seems to end!\r\nI hope everyone knows how hard I'm working!\r\nHm? What's this? For me?\r\n"):
    sm.startQuest(parentID)
    sm.sendNext("Goodness gracious, what beautiful Rice Cakes!\r\nWho made these? \r\n")
else:
    sm.sendNext("Oh dear, you almost had me there. Er, anybody have an extra Rice Cake dish to share with me?")
