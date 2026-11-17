# 53256 - Kyrin of the Nautilus
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Kyrin (1090000)

sm.setSpeakerID(1090000)  # Kyrin
sm.sendNext("You'll have to hoist your own sails if you want to get the respect of the crew.")
sm.sendSay("And you're not going to get ANYWHERE without my help, so I hope you don't mind taking orders. ")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("You ready to start pulling your weight around here?")
