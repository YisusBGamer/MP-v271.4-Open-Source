#Murt's Letter | Lionheart Castle Questline
# Murt's official lines (Quest.wz Say.img). The quest itself hands over the letter (Act.img); the script used to
# give a second one.
if not sm.canHold(4032859):
    sm.setSpeakerID(2161004)
    sm.sendSayOkay("Please make some space in your inventory.")
    sm.dispose()

if sm.questStartDialogue(parentID, 2161004):
    sm.setSpeakerID(2161004)
    sm.sendNext("Just get to #bEl Nath#k and give my letter to Alcaster!")
    sm.warp(211000000)
sm.dispose()
