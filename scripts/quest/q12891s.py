# 12891 - Gale Gourd
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Lisa (9000119)

sm.setSpeakerID(9000119)  # Lisa
if sm.sendAskAccept("Haven't you noticed there's no breeze? My good friend Windy the Wind Fairy usually carries the wind with her and brings it wherever it's needed, but she lost her wind the other day when she thought she saw a cloud shaped like a--well, nevermind. The point is, we need to get her wind back!"):
    sm.startQuest(parentID)
    sm.sendNext("Cassandra's been passing out gourds for travelers to fill with wind. Aren't you helping her with that? When you take her your filled Gale Gourd, ask her to spare some for Windy the Wind Fairy!")
else:
    sm.sendNext("Then you will never feel the soothing effects of a cool breeze ever again. EVER.")
