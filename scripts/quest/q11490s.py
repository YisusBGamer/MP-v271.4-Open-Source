# 11490 - You can rent Legendary Equipment for free!
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Maple Administrator (9201257)

sm.setSpeakerID(9201257)  # Maple Administrator
if sm.sendAskAccept("Hello, #b#h0##k! You must know all about the #bLegends Awards and the Age of Artisans Revamp#k going on now, right? Well, I'm renting out profession gear if you need it! I've got #t01502007#s and #t01512007#s. What do you need, #b#h0##k?"):
    sm.startQuest(parentID)
else:
    sm.sendNext("You know, if you don't have any professions, now would be a good time to start.")
