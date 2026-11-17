#Alcaster's crystal | Lionheart Castle Questline
# Alcaster's official lines (Quest.wz Say.img). The quest itself hands over the crystal (Act.img); the script used to
# give a second one.
if not sm.canHold(2430159):
    sm.setSpeakerID(2020005)
    sm.sendSayOkay("I don't think you have enough room in your inventory. please check your inventory again.")
    sm.dispose()

if sm.questStartDialogue(parentID, 2020005):
    sm.warp(211060000)
sm.dispose()
