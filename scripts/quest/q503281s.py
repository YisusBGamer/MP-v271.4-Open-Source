# [Legion] Weekly Dragon Extermination
# the quest record carries the kill counts, so no start date goes into it
sm.setSpeakerID(9010106)
sm.sendNext("Hello!\r\nHow is your #bLegion#k doing?\r\nWatching your Legion grow stronger makes me so proud.")
if sm.sendAskYesNo("If you defeat the #b100 Dragon Whelps#k guarding the #rHuge Dragon#k and #r20 Golden Wyverns#k, I will give you #b#i4310229:##t4310229# x200#k as a reward.\r\nWhat do you think?\r\n\r\n#r*This quest can only be completed once a week,\r\nand it will be reset at #e#rmidnight on Sunday#n.#k."):
    sm.startQuest(parentID)
    sm.sendSayOkay("Oh, you like a challenge, just as I predicted!\r\nYou can hunt #rDragon Whelps#k and #rGolden Wyverns#k in #rDragon's Domain#k, which you can reach by starting a #bLegion Raid#k. Come and see me in town when you finish your weekly quest.\r\nGood luck!")
else:
    sm.sendNext("Come and see me whenever you are ready for the #b[Legion] Weekly Dragon Extermination#k.")
