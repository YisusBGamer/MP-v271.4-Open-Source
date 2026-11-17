# Fantasy Theme World (Dylan & Delilah, 31363-31392). Official lines where the client has them; otherwise written from
# the quests' Quest.wz summaries / the NPCs' Npc.wz lines. Data: ftwgen/gen_ftw.py.
# Restricted Area, Crittertorium (31382), end - the Caped Mascot inside the venue (2192019): "You defeated Targa
# and Scarboss." His official line before: "There's no way Targa and Scarboss simply allowed you to leave!" Delilah
# faints (Nurse Aily's official lines in 'Party Prep'); back to the entrance.
MASCOT = 2192019
if sm.hasQuest(parentID) and sm.getQRValue(parentID, "mobkill") == "1":
    sm.setSpeakerID(MASCOT)
    sm.sendNext("You... you actually beat Targa AND Scarboss?! I only meant you'd get a thrill watching them!")
    sm.sendSay("Wait - is the young lady all right? I think she just fainted!")
    if sm.completeQuest(parentID) and chr.getInstance() is not None:
        sm.warpInstanceOut(222130200, 0)
sm.dispose()
