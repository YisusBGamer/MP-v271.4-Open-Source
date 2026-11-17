# Tititi (1203001), in the Mirror of Desire's memory (914022100). Aran 3rd job (36964 Making Red Jade): the Red Jade
# (4037303) that decorated Maha is made again; then back to Maha in Rien.
QUEST = 36964
RED_JADE = 4037303

sm.setSpeakerID(1203001)
if sm.hasQuest(QUEST) and not sm.hasItem(RED_JADE):
    sm.sendNext("A jade for the great polearm? Leave it to Tititi! Red, red, the reddest of reds...")
    if not sm.canHold(RED_JADE, 1):
        sm.sendSayOkay("Make some room in your Etc inventory first.")
        sm.dispose()
    sm.giveItem(RED_JADE)
    sm.setPlayerAsSpeaker()
    sm.sendSayOkay("#b(You made the #t4037303# through the Mirror of Desire. Take it to #p1201002# in Rien.)#k")
    sm.warpInstanceOut(140000000, 0)
elif sm.sendAskYesNo("Leave the Mirror of Desire?"):
    sm.warpInstanceOut(140030000, 0)
sm.dispose()
