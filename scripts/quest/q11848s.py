# 11848 - Rescue the Miwok Boys 15 times! Achieved!
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Blackbull (9000097)

sm.setSpeakerID(9000097)  # Blackbull
if sm.sendAskAccept("You've gone to rescue our boys 15 times already? I didn't realize we had lost so many! Please, take this gift!"):
    sm.startQuest(parentID)
else:
    sm.sendNext("I am embarrassed by your amazing gracious attitude! Please talk to me again if you change your mind.")
