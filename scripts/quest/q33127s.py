# Black Heaven: the six acts play through Black Heaven's own maps, monsters and stage quests. The client has no
# lines for this story, so the scenes follow the quests' Quest.wz summaries. Stage data: bhgen/bh_story.py.
# [Black Heaven] For Maple World (33127): Moco comes to see you.
MOCO = 1540446
if not sm.hasQuestCompleted(parentID):
    sm.setSpeakerID(MOCO)
    sm.sendNext("Hi! I'm Moco, keeper of the Epics. Something terrible is brewing in the sky over Maple World... "
                "The Black Wings are up to something, and the Maple Alliance needs heroes.")
    if not sm.hasQuest(parentID):
        sm.startQuestNoCheck(parentID)
    sm.completeQuestNoCheck(parentID)
    sm.sendSayOkay("When you're ready, open the #bEpic#k window - or talk to #bNeinheart#k in Ereve - to begin "
                   "#eAct 1: Secrets in the Wings#n.")
sm.dispose()
