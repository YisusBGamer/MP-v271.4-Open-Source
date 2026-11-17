# John's Box (1204033), Lith Harbor
# Aran quest 21767 (Secrets of the Wooden Box): the medicine inside goes back to John (20000).
MEDICINE = 4032423

sm.setSpeakerID(1204033)
if sm.hasQuest(21767):
    if sm.hasItem(MEDICINE):
        sm.sendNext("(You already took the medicine from the box. Show it to #p20000#.)")
    elif sm.canHold(MEDICINE):
        sm.giveItem(MEDICINE)
        sm.sendNext("(You find unidentified medication inside the box.)")
    else:
        sm.sendNext("(Make room in your Etc inventory to take what's inside.)")
elif sm.hasQuest(21766) and not sm.hasQuestCompleted(21767):
    sm.sendNext("(Inside the wooden box, there a mysterious form of medication...)")
    sm.startQuest(21767)
    if sm.hasQuest(21767) and not sm.hasItem(MEDICINE) and sm.canHold(MEDICINE):
        sm.giveItem(MEDICINE)
else:
    sm.sendNext("(It's John's precious box.)")
