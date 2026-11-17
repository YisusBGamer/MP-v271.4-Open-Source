sm.setSpeakerID(1102102)
# official lines (Quest.wz Say.img)
if sm.sendAskAccept("Yo! I'm Kizan. I'm gonna teach you to get rough-and-tumble."):
    sm.removeEscapeButton()
    sm.startQuest(parentID)
    sm.sendNext("First off, you need to know how to attack. The most basic swing is with the #rCtrl key#k. Go defeat #b3 #o9300730##k monsters in the Drill Hall and show me that you can handle it.")
    for i in range(3):
        sm.spawnMob(9300730, -364, -7, False)
    sm.playSound("Aran/balloon", 100)
    sm.avatarOriented("UI/tutorial.img/4")
else:
    sm.sendSayOkay("What, are you too good to fight?!")