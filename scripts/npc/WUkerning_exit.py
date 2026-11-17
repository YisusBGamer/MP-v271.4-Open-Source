# Nella (9076112) | First Time Together (Cross World): leaving, Platform Puppets and the Returning Path
from net.swordie.ms.world.field.instance.instancehelper.pq import FirstTimeTogetherInstanceHelper as FT
inst = chr.getInstance()
helper = inst.getInstanceHelper() if inst is not None else None
if not isinstance(helper, FT):
    helper = None
sm.setSpeakerID(parentID)
fid = sm.getFieldID()
if fid == FT.RETURNING_PATH:
    if sm.sendAskYesNo("Well done! Shall I take you back to the waiting room?"):
        sm.warpInstanceOut(FT.WAITING_ROOM, 0)
    sm.dispose()
if helper is not None and fid == FT.STAGE_3:
    sel = sm.sendNext("Need a hand?\r\n\r\n#b#L0#Place or remove a Platform Puppet.#l\r\n#L1#Leave the party quest.#l")
    if sel == 0:
        text = "Which platform (1 to 5 cats, from the left)? Puppets now on: " + str(sorted(list(helper.getPuppets())))
        for i in range(1, 6):
            text += "\r\n#b#L" + str(i) + "#Platform " + str(i) + "#l"
        pick = sm.sendNext(text)
        sm.sendSayOkay(helper.togglePuppet(pick))
        sm.dispose()
if sm.sendAskYesNo("Are you sure you want to leave your party behind and go back to the waiting room?"):
    sm.warpInstanceOut(FT.WAITING_ROOM, 0)
sm.dispose()
