# 31062 - [Star Force] Stars Falling into the Dead Mine
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Scadur (2020007)

sm.setSpeakerID(2020007)  # Scadur
sm.sendNext("Last night, I was tracking a huge White Fang across a field. I was looking at the sky, judging the light, when an overwhelming sense of foreboding hit me.  ")
if sm.sendAskAccept("The field started quaking, and the western sky turned red as fire. Then that star shot across and fell into the #bDead Mine#k. I booked it right back here to El Nath. I got me a strong survivor's instinct, see, and I knew staying there wouldn't be safe. And that's my tale. Tell it to #bAlcaster#k, would you?"):
    sm.startQuest(parentID)
    sm.sendNext("Go tell #bAlcaster#k. I've got things to take care of.\r\n")
else:
    sm.sendNext("Something bad is happening. Trust me on that one.")
