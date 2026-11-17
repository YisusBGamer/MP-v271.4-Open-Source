# Fantasy Theme World (Dylan & Delilah, 31363-31392). Official lines where the client has them; otherwise written from
# the quests' Quest.wz summaries / the NPCs' Npc.wz lines. Data: ftwgen/gen_ftw.py.
# Thrills, Chills, and Honesty (31386), start - Nurse Aily, Funky Station <3>: "Dylan apologizes and bravely bares his
# soul to Delilah. Go to Fantasy Theme World <Main Gate> and meet Dylan." (Dylan's official lines at the Main Gate end it.)
AILY = 2192025
if not sm.hasQuest(parentID) and not sm.hasQuestCompleted(parentID):
    sm.setSpeakerID(AILY)
    if sm.sendAskAccept("Everything's ready! Dylan is waiting for Delilah at #bFantasy Theme World <Main Gate>#k. Go on - you don't want to miss this!"):
        sm.startQuest(parentID)
sm.dispose()
