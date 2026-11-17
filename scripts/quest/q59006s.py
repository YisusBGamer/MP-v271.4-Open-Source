# Beast Tamer tutorial. The client has only a few of its lines (Say.img, marked 'official'); the rest follow the
# quests' Quest.wz summaries. The Express skip (field/q59000_tuto.py) still works.
# Becoming a Hero... (59006) - Arby explains Ability Points (the record "ap" is set in npc/9390463.py once
# they're all assigned).
if not sm.hasQuest(parentID) and not sm.hasQuestCompleted(parentID):
    sm.setSpeakerID(9390463)
    sm.sendNext("Ooh, you've reached level 2! When you level up, you get #bAbility Points#k. "
                "Put them into your stats to get stronger!")
    if sm.sendAskAccept("Open your stats window with #bS#k and press #bAuto-Assign#k. Easy-peasy. "
                        "Talk to me again when you're done!"):
        sm.startQuest(parentID)
        if sm.getChr().getAvatarData().getCharacterStat().getAp() == 0:
            sm.setQRValue(parentID, "ap", "1")
sm.dispose()
