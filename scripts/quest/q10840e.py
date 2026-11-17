# 10840 - Gold Richie's Research Support
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Gaga (9000021)

sm.setSpeakerID(9000021)  # Gaga
sm.sendNext("Ahh... It must be done quickly...")
sm.sendSay("You're the helper Gold Richie sent, right? You brought the Monster Purple Crystals?")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Hmm... It's almost complete? Great work. I'll show it to you when it's done.")
    sm.sendSay("Please tell Gold Richie that I'm almost finished.")
