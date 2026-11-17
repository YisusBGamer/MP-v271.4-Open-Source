# 11283 - Bowman of Hope
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Maple Administrator (9201257)

sm.setSpeakerID(9201257)  # Maple Administrator
if sm.sendAskAccept("Congratulations on making your 2nd Job Advancement! You were lucky to make your Job Advancement with the Hunter's Star at its peak. This is a special gift that will surely double your joy. Would you like it right now?"):
    sm.startQuest(parentID)
    sm.sendNext("This is a #b#t2450032##k that increases your EXP by 1.5 times, and a #bScroll for ATT#k. These should be pretty helpful for a Bowman like yourself, I think. If you make your 3rd or 4th Job Advancements before the Hunter's Star rises on October 18, you could get another gift. Do your best!")
