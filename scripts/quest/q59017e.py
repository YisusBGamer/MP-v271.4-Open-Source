# Beast Tamer tutorial. The client has only a few of its lines (Say.img, marked 'official'); the rest follow the
# quests' Quest.wz summaries. The Express skip (field/q59000_tuto.py) still works.
# From Grandmother's House We Go (59017) - Granny Rosanna: off on your hero's journey with your four companions,
# Lai, Fort, Eka and Arby (their skills, as the Express skip gives them).
if sm.hasQuest(parentID):
    sm.setSpeakerID(9390304)
    sm.sendNext("So you're really going. My little one, off to become a hero... Your four friends will look "
                "after you, won't they?")
    sm.setPlayerAsSpeaker()
    sm.sendNext("Don't cry, Granny... Okay, I'm crying too. But that's the sacrifice you make to be a hero!")
    if sm.completeQuest(parentID):
        for skill, level in ((110001510, 1), (110001501, 1), (110001502, 1), (110001503, 1), (110001504, 1),
                             (110001514, 1), (110001511, 30), (110001506, 1)):
            if not sm.hasSkill(skill):
                sm.giveSkill(skill, level, level)
sm.dispose()
