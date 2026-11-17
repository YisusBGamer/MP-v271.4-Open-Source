# 59124 - Good Job... For Now
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Oz (1101004)

sm.setSpeakerID(1101004)  # Oz
sm.sendNext("#bMihile#k told me that you did a great job. I'll give you #b#p1101002##k's second mission.\r\n")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Your next stop is Sleepywood. People there are talking about #bTristan's Sword#k. ")
    sm.sendSay("Go to Sleepywood and get #bTristan's Sword#k. Talk to the excavation leader, #b#p9390352##k. He knows what's going on around there.\r\n")
