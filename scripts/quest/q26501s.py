# 26501 - [Stylist Supreme] The Contest Begins
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Misha (9000226)

sm.setSpeakerID(9000226)  # Misha
if sm.sendAskAccept("You're interested in the Stylist Supreme competition? Dressed like THAT? I suppose the fashion gods would eventually forgive me for sending you to the contest area..."):
    sm.startQuest(parentID)
    sm.sendNext("I love your shoes! You're so brave to wear clothes from a thrift shop!")
else:
    sm.sendNext("Good choice. Let me know if you change your mind, but I really don't think you should.")
