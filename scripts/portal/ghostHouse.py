# Fantasy Theme World (Dylan & Delilah, 31363-31392). Official lines where the client has them; otherwise written from
# the quests' Quest.wz summaries / the NPCs' Npc.wz lines. Data: ftwgen/gen_ftw.py.
# Funky Station <2> (222110100), portal in00: the Ghostacular Mansion, next to Oliver. Oliver's official lines: "The
# portal on the left there will take you directly to Ghostacular Mansion"; "I'll guide you into Ghostacular Mansion once
# you pass this test" (Admission Hurdle - Courage Test 2, 31391). Your own copy of the mansion.
if sm.hasQuestCompleted(31391) or sm.hasQuest(31369):
    sm.warpInstanceIn(222110110, 0)
else:
    sm.chatScript("Oliver: There's a strict admission policy. Pass the test of courage first, if you dare!")
sm.dispose()
