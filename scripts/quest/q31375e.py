# Fantasy Theme World (Dylan & Delilah, 31363-31392). Official lines where the client has them; otherwise written from
# the quests' Quest.wz summaries / the NPCs' Npc.wz lines. Data: ftwgen/gen_ftw.py.
# Sensor Recovery (31375), end - the Caped Mascot keeps his word (his official offer: "I'll return this to you if you
# defeat, say, 150?") and hands over the Ribbon Brooch (4036094) that 'Pin the Brooch on Delilah' (31376) asks for.
MASCOT, BROOCH = 2192018, 4036094
if sm.hasQuest(parentID):
    sm.setSpeakerID(MASCOT)
    if not sm.canHold(BROOCH):
        sm.sendSayOkay("Make some room in your Etc inventory first.")
    elif sm.completeQuest(parentID):
        if not sm.hasItem(BROOCH):
            sm.giveItem(BROOCH)
        sm.sendSayOkay("A deal's a deal. Here's your brooch. And remember - if it's thrills you want, nothing beats the Crittertorium!")
sm.dispose()
