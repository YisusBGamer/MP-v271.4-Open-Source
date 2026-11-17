# 31490 - Defensive Battle
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Tonero (3001104)
# Battlefield quest: winning it (portal/finish_warZone1.py) sets record 31558.

sm.setSpeakerID(3001104)  # Tonero
sm.sendNext(u"Listen up! #p3001000# got a report from #p3001007#\u2019s patrol that a pack of Specters launched a small-scale attack on a bunch of innocent civilians.")
sm.sendSay(u"They\u2019re sneaking in through the back alleys in the central region, through that forest that crosses the valley. The scouts said there weren\u2019t too many. You should be able to handle it.")
sm.sendSay("#b#p3001000##k sent you an official request. If you want, I can show you a shortcut right to the battlefield...")
sm.startQuest(parentID)
if sm.hasQuest(parentID):
    sm.warpInstanceIn(401071300)  # Occupied Foggy Valley
