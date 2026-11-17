# 31430 - No Free Trade
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Tonero (3001004)

sm.setSpeakerID(3001004)  # Tonero
sm.sendNext("Have you come bearing my modest fee?")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Magnificent! Your desired knowledge shall be found within this missive, which you should take to #p3001002# with all due haste. Be on the prowl for further intelligence, and hesitate not to contact me for all your needs!")
