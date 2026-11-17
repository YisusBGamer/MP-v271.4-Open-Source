# 25869 - The Perfect Trap
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Cartalion (3000000)

sm.setSpeakerID(3000000)  # Cartalion
if sm.sendNext("Angelic Buster, the Nova finally have a chance to end this!\r\n#L0##b(End this?)\r\nHuh?#l\r\n#k") != 0:
    sm.dispose()
if sm.sendSay(u"Kaiser has a plan to lure Magnus\u2019s armies in and crush them all at once! \r\n#L0##b(He explains Kaiser's plan.)#l\r\n#k") != 0:
    sm.dispose()
if sm.sendSay(u"I\u2019ll lift the Protective Shield to allow the Specters inside. You and the troops will hold them as long as you can, then we ambush them...\r\n#L0##bThat sounds a little on the crazypants side.#l\r\n#k") != 0:
    sm.dispose()
if sm.sendSay(u"Don\u2019t you see? With this one blow, we can reclaim Heliseum! It\u2019s a risk we have to take.\r\n#L0##bIf Kaiser thinks it\u2019s a good idea, I\u2019m in. Who do I need to blow up?#l\r\n#k") != 0:
    sm.dispose()
sm.sendSay(u"Most of Magnus\u2019s forces are pooling on the southern border. That\u2019s where we\u2019ll lay our trap. \r\n#r(Press Accept to move to the battlefield.)#k")
sm.startQuest(parentID)
# Battlefield quest: accepting moves you to the South Pantheon Border (940010070, field/event_25869.py) while the
# shields are down; once those Specters are beaten (field/clear_940010070.py) you go on to South Border Battleground 2,
# and winning there (portal/finish_warZone0.py) sets record 25801, which Cartalion's end check waits for.
if sm.hasQuest(parentID):
    sm.warpInstanceIn(940010070)  # South Pantheon Border
sm.dispose()
