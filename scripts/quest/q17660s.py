# id 17660 ([Commerci Republic] Clairenapped), field 865030000
sm.setSpeakerID(9390236) # Leon Daniella
sm.setParam(2)
sm.sendNext("What's going on? Where's Claire?")
sm.setParam(0)
sm.sendSay("I got ambushed! Some guys with crazy ninja faces came and I was like, 'BOOM no way guys stop it!' and then I attacked, but they were like, 'jump on his back and mess him up!' and now Claire's gone!")
sm.sendSay("Do you think she ran away? Or was she kidnapped? Maybe she doesn't like my cologne. Do I wear too much cologne?")
sm.setParam(2)
sm.sendSay("Calm down, Leon. She'll be fine.")
sm.setParam(0)
res = sm.sendAskYesNo("We have to find her before something happens!")
sm.startQuest(parentID)
sm.showNpcSpecialActionByTemplateId(9390227, "summon", 0)
sm.showNpcSpecialActionByTemplateId(9390228, "summon", 0)
sm.setParam(5)
sm.setInnerOverrideSpeakerTemplateID(9390227) # Guild Assassin
sm.sendNext("You'd be better off worrying about your own lives.")
sm.setParam(1)
sm.sendSay("Who are you?")
sm.setParam(5)
sm.sendSay("You won't be alive long enough for that to matter.")
sm.setParam(3)
sm.sendSay("You're one of the assassins who attacked the Heaven Empire envoy!")
sm.setParam(5)
sm.sendSay("You got me! Now prepare to meet your maker.")
sm.startQuest(16689)


def spawn_line(mob, count, x1, x2, y):
    """spreads count monsters evenly between x1 and x2 on the ground at y"""
    for i in range(count):
        x = x1 + (x2 - x1) * (i + 1) // (count + 1)
        sm.spawnMob(mob, x, y, False)

# The Masked Mysteriosos attack (6 + 6, the quest's kill counts). The quest is started only once.
if not sm.hasMobsInField(sm.getFieldID(), 9390833) and not sm.hasMobsInField(sm.getFieldID(), 9390834):
    spawn_line(9390833, 6, -700, 1000, -270)
    spawn_line(9390834, 6, -600, 1000, -270)
