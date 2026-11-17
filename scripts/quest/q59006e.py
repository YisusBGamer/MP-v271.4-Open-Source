# Beast Tamer tutorial. The client has only a few of its lines (Say.img, marked 'official'); the rest follow the
# quests' Quest.wz summaries. The Express skip (field/q59000_tuto.py) still works.
# Becoming a Hero... (59006): Ability Points assigned.
if sm.hasQuest(parentID):
    sm.setPlayerAsSpeaker()
    sm.sendNext("Whoa, I totally feel stronger now. HYAH! Hero-ville, here I come!")
    sm.completeQuest(parentID)
sm.dispose()
