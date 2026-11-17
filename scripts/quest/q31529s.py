# 31529 - Punishment for a Traitor
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Edea (3001100)

sm.setSpeakerID(3001100)  # Edea
sm.sendNext("The research is going well. If we can obtain the final relic from #rVelderoth#k, we should be able to make the key to reach #rMagnus#k.")
if sm.sendAskAccept("What we must do next is clear. Defeat #rVelderoth#k and take his relic. We are almost done reclaiming Heliseum."):
    sm.startQuest(parentID)
    sm.sendNext("You should depart for the castle. Unlike #rTreglow#k and #rVictor#k, I know nothing about your foe's abilities. I trust you will exercise caution.")
else:
    sm.sendNext("This is the final stretch. You aren't going to turn coward now, are you?")
