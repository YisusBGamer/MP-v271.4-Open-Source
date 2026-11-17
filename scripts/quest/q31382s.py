# Fantasy Theme World (Dylan & Delilah, 31363-31392). Official lines where the client has them; otherwise written from
# the quests' Quest.wz summaries / the NPCs' Npc.wz lines. Data: ftwgen/gen_ftw.py.
# Restricted Area, Crittertorium (31382), start - Dylan: "Check out the Crittertorium venue." (Nurse Aily's
# official line later: "why in the world would you enter a place that clearly says it's a Restricted Area?!" and
# "that strange red-caped person suggested it".)
DYLAN = 2192009
if not sm.hasQuest(parentID) and not sm.hasQuestCompleted(parentID):
    sm.setSpeakerID(DYLAN)
    sm.sendNext("That caped mascot said the Crittertorium is the most thrilling place in the whole park...")
    if sm.sendAskAccept("The sign says #rRestricted Area#k, but how dangerous could it be? Come on, #h0#! Into the #bCrittertorium#k!"):
        sm.startQuest(parentID)
sm.dispose()
