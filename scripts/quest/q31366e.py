# Fantasy Theme World (Dylan & Delilah, 31363-31392). Official lines where the client has them; otherwise written from
# the quests' Quest.wz summaries / the NPCs' Npc.wz lines. Data: ftwgen/gen_ftw.py.
# Affix the Love Tester (31366), end - Delilah at the Main Gate: "You gave Delilah the heartbeat sensor!"
# (Dylan's official line before it: "Make sure you tell her how important it is to keep the brooch all day.")
DELILAH, BROOCH = 2192011, 4036091
if sm.hasQuest(parentID) and sm.hasItem(BROOCH):
    sm.setSpeakerID(DELILAH)
    sm.sendNext("A ribbon brooch? For me? ...It's actually really cute.")
    sm.setPlayerAsSpeaker()
    sm.sendSay("Make sure you tell her how important it is to keep the brooch all day.")
    sm.setSpeakerID(DELILAH)
    sm.sendSay("All day? Well... all right. If it means that much to you two.")
    if sm.completeQuest(parentID):
        sm.consumeItem(BROOCH, 1)
sm.dispose()
