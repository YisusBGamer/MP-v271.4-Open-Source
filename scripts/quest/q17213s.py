# 17213 - Honorary Commercian
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Maple Administrator (9201257)

sm.setSpeakerID(9201257)  # Maple Administrator
if sm.sendAskAccept("Hello, #e#b#h0##k#n. Have you been to the #e#rCommerci Republic#k#n on the west coast of Dawnveil? I am sure someone of your means can get there easily. But I do need to give you one bit of information.\r\nDo you want to hear it?"):
    sm.startQuest(parentID)
    sm.sendNext("You can become a honorary citizen of the #b#eCommerci Republic#k#n. Go to Commerci in Dawnveil, collect Commerci emblems, do favors for Mayor Berry, catch bounty monsters, and participate in trades. These are all part of the #eCommerci Life#n\r\nYou will become a honorary Commerci Citizen if you live out each part of the Commerci Life 10 times.")
else:
    sm.sendNext("It won't hurt to just hear me out, you know.")
