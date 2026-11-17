# 3706 - Dragon Squad's Mission
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Nine Spirit's Baby Dragon (2081008)

sm.setSpeakerID(2081008)  # Nine Spirit's Baby Dragon
sm.sendNext("Kyuuu? Kaooo kyuuuuuh!")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("(Unable to understand exactly what the baby dragon is crying out loud, but just listening to it roar makes my skin tingle with joy and excitement at the prospect of it growing up to be the next dominant dragon in this area.)")
