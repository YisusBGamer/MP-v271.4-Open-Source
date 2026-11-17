# 57104 - The New Base
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Yamanaka Yukimori (9130031)

sm.setSpeakerID(9130031)  # Yamanaka Yukimori
if sm.sendAskAccept("We have christened our base Momijigaoka. It is hidden by a copse of scarlet maple trees. I will take you there."):
    sm.startQuest(parentID)
else:
    sm.sendNext("Do you need more time to recover? I won't rush you.")
