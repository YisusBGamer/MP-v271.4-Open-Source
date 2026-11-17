# 11193 - Magician of Hope
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Maple Administrator (9201257)

sm.setSpeakerID(9201257)  # Maple Administrator
if sm.sendAskAccept("Congratulations on making your 2nd Job Advancement! You were lucky to make your Job Advancement with the Wise Man's Star at its peak. This is a special gift that will surely double your joy. Would you like it right now?"):
    sm.startQuest(parentID)
    sm.sendNext("Behold! This #b#t2450028##k increases your EXP by 1.5 times, and comes with a #bScroll for Magic Attack#k. This is just what a rising Magician needs. If you make your 3rd or 4th Job Advancement before the Wise Man's Star rises on October 18, you could get another gift. Challenge yourself!")
