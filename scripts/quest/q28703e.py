# 28703 - Gaga's Confession
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Gaga (9000021)

sm.setSpeakerID(9000021)  # Gaga
sm.sendNext("Do you have all 100 Gift Wrapping Papers? I can't afford to go halfway on my love!\r\n")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Thank you so much! With your help, XX, I can work up my courage, back it with a mind-blowing gift, profess my love, and live happily ever after! If you keep bringing me sets of 100, I can do this!\r\n")
