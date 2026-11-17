# 10208 - Remnants of Black Mage
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Maple Administrator (9201257)

sm.setSpeakerID(9201257)  # Maple Administrator
if sm.sendAskAccept("It feels like the force of the Black Mage has increased since Empress Cygnus appeared in Maple World. I even heard that an item got altered because of the force of the Black Mage. If other monsters consume this item, they may turn into more powerful and harder-to-handle monsters. I'll need to find a way to get a hold of this and thoroughly investigate. Will you help me?"):
    sm.startQuest(parentID)
    sm.sendNext("Thank you. The black aura surrounding this altered item... is enough to change you as well. I would like for you to find... #b#t4001239##k for me. You can gather up the altered items and bring them either to me, or you can give them to Gaga or Cassandra as well. Since we're all conducting different experiments, the rewards will be different as well. Thanks in advance.")
else:
    sm.sendNext("You must be afraid of them. That's unfortunate. I'll have to ask someone else for help.")
