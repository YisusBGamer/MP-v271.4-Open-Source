# Fantasy Theme World (Dylan & Delilah, 31363-31392). Official lines where the client has them; otherwise written from
# the quests' Quest.wz summaries / the NPCs' Npc.wz lines. Data: ftwgen/gen_ftw.py.
# True Confession (31383) - Dylan, Crittertorium entrance: "It kind of looks like Dylan had an epiphany about
# considering more than just his own feelings." / "Dylan decided to apologize to Delilah and be straightforward about
# his feelings for her." (no NPC to report to: it ends here)
DYLAN = 2192010
if not sm.hasQuestCompleted(parentID):
    sm.setSpeakerID(DYLAN)
    sm.sendNext("This is all my fault. I was so busy with my brilliant plan that I never once asked Delilah what SHE wanted...")
    sm.sendSay("No more tricks. No more cardiotachometers. I'm going to apologize to her, and tell her how I really feel.")
    if not sm.hasQuest(parentID):
        sm.startQuest(parentID)
    if sm.hasQuest(parentID):
        sm.completeQuest(parentID)
sm.dispose()
