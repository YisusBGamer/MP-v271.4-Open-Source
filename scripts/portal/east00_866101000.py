# Beast Tamer tutorial. The client has only a few of its lines (Say.img, marked 'official'); the rest follow the
# quests' Quest.wz summaries. The Express skip (field/q59000_tuto.py) still works.
# Stump Town (866101000), east exit: out to the Eastern Outskirts and Wolf Forest once Bran has dared you
# (Brave Like a Hero, 59002). The tutorial's forest maps are your own (an instance) until you reach Stump Town.
if sm.hasQuest(59002) or (sm.hasQuestCompleted(59002) and not sm.hasQuestCompleted(59008)):
    sm.warpInstanceIn(866102000, 1)
else:
    sm.chat("Granny Rosanna would never let you wander off into the forest by yourself.")
sm.dispose()
