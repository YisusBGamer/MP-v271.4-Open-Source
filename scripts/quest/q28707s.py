# 28707 - Love Spreader
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Cody (9200000)

sm.setSpeakerID(9200000)  # Cody
if sm.sendAskAccept("Everyone is so busy trying to prepare a gift for their crushes on Valentine's Day! I saw what you did for them, and figured you deserved something for yourself, you little Cupid.\r\n"):
    sm.startQuest(parentID)
    sm.sendNext("Here, take this Valentine's Day Buff! I'll keep on giving it to you each time you help everyone that needs it. Happy Valentine's Day!\r\n")
else:
    sm.sendNext("Giving up on the Maple Awards already? Well, let me know if you decide fame IS for you, after all.")
