# Silver Mane
# 6006
# Kenta (2060005). Official lines (Quest.wz Say.img).
# The quest itself takes the 500 + 500 items and the 50,000,000 mesos (Act.img). This script used to take them as
# well before completing, so completing then failed (the items were gone): the player lost everything and the
# quest stayed unfinished.

silver_mane = 80001305

if sm.questEndDialogue(parentID, 2060005):
    sm.giveSkill(silver_mane)
sm.dispose()
