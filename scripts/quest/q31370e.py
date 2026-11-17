# Fantasy Theme World (Dylan & Delilah, 31363-31392). Official lines where the client has them; otherwise written from
# the quests' Quest.wz summaries / the NPCs' Npc.wz lines. Data: ftwgen/gen_ftw.py.
# A Good Day to Confess (31370), end - Delilah: "Delilah tells you she's not feeling well after the mansion." (Dylan's
# official line next: "And why would she want to ride the carousel? That isn't thrilling or exciting at all!")
DELILAH = 2192012
if sm.hasQuest(parentID):
    sm.setSpeakerID(DELILAH)
    sm.sendNext("I'm sorry... I'm not feeling very well after that mansion. I really don't like scary things.")
    sm.sendSay("Honestly, I'd much rather just ride the carousel...")
    sm.completeQuest(parentID)
sm.dispose()
