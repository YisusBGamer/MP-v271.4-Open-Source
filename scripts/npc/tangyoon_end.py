# Tangyoon (1097001) | Cooking with Tangyoon: the cauldron (picking ingredients) and the Kitchen Exit
from net.swordie.ms.world.field.instance.instancehelper.pq import TangyoonInstanceHelper as TY
inst = chr.getInstance()
helper = inst.getInstanceHelper() if inst is not None else None
if not isinstance(helper, TY):
    helper = None

sm.setSpeakerID(1097001)
if helper is None:
    if sm.sendAskYesNo("Thanks for the help in the kitchen! Shall I send you back to my kitchen?"):
        sm.warp(TY.KITCHEN, 0)
    sm.dispose()

if helper.isFinished():
    sm.sendSayOkay("That's the end of today's cooking. I'll send you out in a moment.")
elif helper.isBossStage():
    sm.sendSayOkay("The dish is done! Now deal with what came out of the cauldron!")
elif helper.isWaveRunning():
    sm.sendSayOkay("Keep hunting! I'll tell you when we have enough of this ingredient.")
elif not helper.isLeader(chr):
    sm.sendSayOkay("Today's dish is #b" + helper.getDishName() + "#k. Your #bparty leader#k picks the ingredients.")
else:
    menu = "Today's dish is #b" + helper.getDishName() + "#k.\r\nIngredient #b" + str(helper.getStage() + 1) +            " of 5#k: which monster goes in?\r\n\r\n"
    choices = helper.getChoices()
    for i in range(len(choices)):
        menu += "#L" + str(i) + "##o" + str(choices[i]) + "##l\r\n"
    pick = sm.sendNext(menu)
    wrong = helper.pickIngredient(chr, pick)
    if wrong is not None:
        sm.sendSayOkay(wrong)
