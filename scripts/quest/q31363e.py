# Fantasy Theme World (Dylan & Delilah, 31363-31392). Official lines where the client has them; otherwise written from
# the quests' Quest.wz summaries / the NPCs' Npc.wz lines. Data: ftwgen/gen_ftw.py.
# Dylan's Story (31363), end - Dylan at the Main Gate: "Dylan has made 100 failed professions of love, but now he's
# found a new love and new hope!" The three pictures of his past are the client's own (fantasticThemePark/past).
DYLAN = 2192003
CUT = "Map/Effect3.img/fantasticThemePark/past/cut%d"
if sm.hasQuest(parentID):
    sm.setSpeakerID(DYLAN)
    sm.sendNext("You came! You really came to hear my story! *sniff* Okay. Here goes...")
    for i, text in ((1, "My first profession of love... Rejected."),
                    (2, "My fiftieth... Rejected."),
                    (3, "And my hundredth... Rejected. One hundred times!")):
        sm.onLayer(500, "past%d" % i, 0, -80, 12, CUT % i, 4, True, -1, False)
        sm.sendNext(text)
        sm.offLayer(300, "past%d" % i, False)
    sm.sendNext("But that's all in the past! Because I've found a new love - and with her, new hope!")
    sm.completeQuest(parentID)
sm.dispose()
