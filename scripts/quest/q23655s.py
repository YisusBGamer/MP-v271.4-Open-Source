# 23655 - An Outside Perspective 2
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Fenelle (3000001)

sm.setSpeakerID(3000001)  # Fenelle
if sm.sendAskAccept("Ah, #b#h0##k, would you be willing to peruse the #bthe western barrier#k?"):
    sm.startQuest(parentID)
    sm.sendNext("Lovely! The east has been fortified, but we must be diligent.")
else:
    sm.sendNext("I'm afraid #m400000000# is a little short-handed, otherwise I would never ask a visitor for help.")
