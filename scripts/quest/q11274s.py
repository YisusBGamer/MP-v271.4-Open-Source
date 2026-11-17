# 11274 - Indomitable Ice Knight
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Rosette (9070005)

sm.setSpeakerID(9070005)  # Rosette
sm.sendNext("Hello, #h0#! I am Rosette, the Battle Square guide. Right now, I am handing out gifts to those who complete challenges.")
if sm.sendAskAccept("Today's mission is... You have to achieve 1 victory with the Ice Knight in Ice Knight Mode. This mission is not that hard, but it might be hard for you to achieve. But I know you, #h0#, will going to challenge it, right?"):
    sm.startQuest(parentID)
    sm.sendNext("Don't forget! You have to achieve 1 victory with the Ice Knight in Ice Knight Mode. If you complete the mode as challenger, then that will not be counted. Also, if you leave the mode or one day passes, your mission progress will reset, which means you can't complete the mission.")
else:
    sm.sendNext("Really? It's not like it's hard...")
