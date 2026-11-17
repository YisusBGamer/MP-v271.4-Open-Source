# 20762 - The Mind of a Knight: Part Two
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Neinheart (1101002)

sm.setSpeakerID(1101002)  # Neinheart
sm.sendNext("#h0#, your training seems to be producing adequate results. Your mind seems sharper, and more prepared for the dangers ahead. You could make the Cygnus Knights proud one day.")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("I have found that most fighters have a small up-tick in their productivity after receiving gifts. As such, I have had something prepared for you.")
