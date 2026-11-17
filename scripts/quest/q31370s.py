# Fantasy Theme World (Dylan & Delilah, 31363-31392). Official lines where the client has them; otherwise written from
# the quests' Quest.wz summaries / the NPCs' Npc.wz lines. Data: ftwgen/gen_ftw.py.
# A Good Day to Confess (31370), start - Dylan, Funky Station <2>: "Dylan is ecstatic that things are going
# according to plan, but Delilah looks really upset. Talk to Delilah and see how she's feeling."
DYLAN = 2192004
if not sm.hasQuest(parentID) and not sm.hasQuestCompleted(parentID):
    sm.setSpeakerID(DYLAN)
    sm.sendNext("Did you see that, #h0#?! Her heartbeat was going crazy the whole time we were in there! It's working! My plan is working!")
    sm.setPlayerAsSpeaker()
    sm.sendSay("(Delilah, on the other hand, looks really upset.)")
    sm.setSpeakerID(DYLAN)
    if sm.sendAskAccept("Hm? Delilah looks a little pale... Could you go see how she's feeling?"):
        sm.startQuest(parentID)
sm.dispose()
