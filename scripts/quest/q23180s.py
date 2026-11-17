# 23180 [Verne Mine] Albert's Daughter (Albert, Edelstein). Albert's old mansion (310000004) has no portal from town:
# the quest takes you there, and Gabrielle's Clothes (4037283) are in its closet. Handed in to Albert.
CLOTHES = 4037283
MANSION = 310000004

if sm.questStartDialogue(parentID, 2152006):
    if sm.sendAskYesNo("#b(Sneak into the #m310000004# now?)#k"):
        sm.warp(MANSION, 0)
        sm.setPlayerAsSpeaker()
        if not sm.hasItem(CLOTHES) and sm.canHold(CLOTHES, 1):
            sm.giveItem(CLOTHES)
        sm.sendSayOkay("#b(You slip into the mansion's closet and find #t4037283#. Take them back to Albert.)#k")
sm.dispose()
