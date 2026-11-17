# Beast Tamer tutorial. The client has only a few of its lines (Say.img, marked 'official'); the rest follow the
# quests' Quest.wz summaries. The Express skip (field/q59000_tuto.py) still works.
# Reading Maps (59019): the World Map.
if not sm.hasQuest(parentID) and not sm.hasQuestCompleted(parentID):
    sm.startQuest(parentID)
    sm.chatScript("To open the World Map, press the [W] key, then click on Stump Town. After that, press the Navigate "
                  "button on the upper right!")  # official (Say.img)
sm.dispose()
