# Dimensional Crack balloons (2040036-2040045) and Arturo (2040035)
from net.swordie.ms.world.field.instance.instancehelper.pq import DimensionalCrackInstanceHelper as DC

fid = sm.getFieldID()
inst = chr.getInstance()
helper = inst.getInstanceHelper() if inst is not None else None
if helper is not None and not isinstance(helper, DC):
    helper = None

# Arturo, outside the party quest
if fid == DC.EXIT_MAP or helper is None:
    if sm.sendAskYesNo("Thank you for saving Ludibrium! Shall I send you back to the #bEos Tower 101st floor#k?"):
        sm.warp(DC.ENTRANCE, 0)
    sm.dispose()

if fid == DC.STAGE_1:
    sm.setSpeakerID(2040036)
    if helper.isStageCleared(1):
        sm.sendSayOkay("You have collected all the passes. Please proceed to the next stage through the portal.")
    else:
        sm.sendSayOkay("Welcome! Defeat the monsters in this stage and collect the #b20 Dimensional Passes#k they "
                       "hold. Once your party has them all, the portal will open.")

elif fid == DC.STAGE_2:
    sm.setSpeakerID(2040039)
    if helper.isStageCleared(2):
        sm.sendSayOkay("You have defeated all the monsters lurking in the darkness. Please proceed to the next stage "
                       "through the portal.")
    else:
        sm.sendSayOkay("Behind these five doors are dark rooms. Defeat all the #rDark Eyes#k and #rShadow Eyes#k "
                       "hiding in the darkness, then come back here.")

elif fid == DC.STAGE_3:
    sm.setSpeakerID(2040041)
    if helper.isStageCleared(3):
        sm.sendSayOkay("You made it to the top! Please proceed through the portal.")
    else:
        sm.sendSayOkay("Each row has three numbered boxes, and only one of them leads up. Stand on a box and press "
                       "#bUp#k. A wrong box sends you back to the bottom. Once one of you reaches the top, I'll bring "
                       "the whole party up.")

elif fid == DC.STAGE_4:
    sm.setSpeakerID(2040042)
    if helper.checkStage4(chr):
        sm.sendSayOkay("All the Rombots are gone! Please proceed through the portal.")
    else:
        sm.sendSayOkay("Please defeat all #r4 Rombots#k, then talk to me again.")

elif fid == DC.STAGE_5:
    sm.setSpeakerID(2040043)
    if helper.isStageCleared(5):
        sm.sendSayOkay("That's the right answer! Please proceed through the portal.")
    elif not helper.isLeader(chr):
        sm.sendSayOkay("Please have your #bparty leader#k talk to me. The question is: #r" +
                       helper.getStage5Question() + "#k")
    else:
        question = helper.getStage5Question()
        if sm.sendAskYesNo("Here is the question: #r" + question + "#k\r\n\r\nThe nine boxes are numbered "
                           "#b1 to 9#k from the top. #bExactly 3#k members must stand on the boxes of the answer's "
                           "three digits.\r\n\r\nAre your members in place? Shall I check?"):
            wrong = helper.checkStage5(chr)
            if wrong is None:
                sm.sendSayOkay("That's the right answer! The portal is open.")
            else:
                sm.sendSayOkay(wrong + "\r\n\r\nThe question is: #r" + question + "#k")

elif fid == DC.STAGE_BOSS:
    sm.setSpeakerID(2040044)
    if helper.isFinished():
        if sm.sendAskYesNo("You've driven Alishar back through the crack! Shall I take your party out?"):
            helper.leave()
    else:
        sm.sendSayOkay("#rAlishar#k came through the crack! Defeat it, then talk to me.")

else:
    sm.sendSayOkay("Keep going, the Dimensional Crack must be closed!")
