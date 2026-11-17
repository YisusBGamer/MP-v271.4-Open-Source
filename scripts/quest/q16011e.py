#
# @author once a bakery never a bakery
# @npc Dame Appropiation - Legion Manager
# @quest [Legion] Whip The Whelps
#

sm.setSpeakerID(9010106)
sm.sendSayOkay("Awesome! Your #bLegion#k is pretty tough! Here is #b#i4310229:# #t4310229# x10#k as your reward."
               "\r\nCheck in tomorrow for more training missions!\r\n\r\n")
sm.addUnionCoin(10, False)  # matches the reward the quest promises (x10)
sm.completeQuestNoRewards(16011)