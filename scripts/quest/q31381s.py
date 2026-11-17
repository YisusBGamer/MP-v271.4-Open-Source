# Fantasy Theme World (Dylan & Delilah, 31363-31392). Official lines where the client has them; otherwise written from
# the quests' Quest.wz summaries / the NPCs' Npc.wz lines. Data: ftwgen/gen_ftw.py.
# A Plan in Crisis (31381), start - Dylan, Fantasy Station <2>: "It looks like Dylan needs to say something." /
# "Talk to Delilah." (Delilah's answer is the quest's own npcSpeech text, which the client shows.)
DYLAN = 2192009
if not sm.hasQuest(parentID) and not sm.hasQuestCompleted(parentID):
    sm.setSpeakerID(DYLAN)
    sm.sendNext("The swingboat STILL isn't fixed?! And look - Delilah's heart rate has dropped again. My plan... my plan is in crisis!")
    if sm.sendAskAccept("#h0#, could you talk to #bDelilah#k for me? Find out how she's feeling."):
        sm.startQuest(parentID)
sm.dispose()
