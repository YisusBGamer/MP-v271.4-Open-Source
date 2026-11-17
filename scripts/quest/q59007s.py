# Beast Tamer tutorial. The client has only a few of its lines (Say.img, marked 'official'); the rest follow the
# quests' Quest.wz summaries. The Express skip (field/q59000_tuto.py) still works.
# Critter Champs (59007) - Arby invites you to join (the quest is handed in to him, so joining finishes it).
if not sm.hasQuest(parentID) and not sm.hasQuestCompleted(parentID):
    sm.setSpeakerID(9390463)
    sm.sendNext("You know, I'm not just any cat. I'm a member of the #bCritter Champs#k, along with three other "
                "creatures. We protect the forest!")
    if sm.sendAskAccept("And you're a soon-to-be hero, right? So..."):
        sm.startQuest(parentID)
        sm.sendNext("Hey, you wanna join us? Great idea! So glad you mentioned it!")  # official end line (Say.img)
        sm.completeQuest(parentID)
sm.dispose()
