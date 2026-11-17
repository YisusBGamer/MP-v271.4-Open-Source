# Fantasy Theme World (Dylan & Delilah, 31363-31392). Official lines where the client has them; otherwise written from
# the quests' Quest.wz summaries / the NPCs' Npc.wz lines. Data: ftwgen/gen_ftw.py.
# Dylan's Story (31363), start (quest notifier, Lv 120): "Ride the cable car set up at the eastern end of Ludibrium
# and go to Fantasy Theme World <Main Gate> to listen to Dylan."
DYLAN = 2192003
if not sm.hasQuest(parentID) and not sm.hasQuestCompleted(parentID):
    sm.setSpeakerID(DYLAN)
    if sm.sendAskAccept("Excuse me! You're a hero, right? Would you... would you listen to my sad story of unrequited love? "
                        "I'm at #bFantasy Theme World <Main Gate>#k. Ride the #bcable car#k at the #beastern end of Ludibrium#k!"):
        sm.startQuest(parentID)
sm.dispose()
