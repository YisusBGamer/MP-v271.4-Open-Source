# Beast Tamer tutorial. The client has only a few of its lines (Say.img, marked 'official'); the rest follow the
# quests' Quest.wz summaries. The Express skip (field/q59000_tuto.py) still works.
# Restoring HP (59011) - you were badly hurt in the fight; Lai has red potions.
if not sm.hasQuest(parentID) and not sm.hasQuestCompleted(parentID):
    sm.setSpeakerID(9390302)
    sm.sendNext("You're hurt. Using my power takes a toll when you're not used to it.")
    if sm.sendAskAccept("Here, take these red potions. Press #bI#k to open your inventory and double-click a red "
                        "potion to drink it."):
        sm.startQuest(parentID)
        if sm.hasQuest(parentID) and not sm.hasItem(2000000) and sm.canHold(2000000, 3):
            sm.giveItem(2000000, 3)  # Red Potion
        sm.sendSayOkay("Drink that potion, boss! If you don't have any, forfeit your quest and talk to me again!")  # official (Say.img)
sm.dispose()
