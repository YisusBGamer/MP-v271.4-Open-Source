# 12887 - Forecasting Rain
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Bruce (9000115)

sm.setSpeakerID(9000115)  # Bruce
sm.sendNext("Sure is hot, isn't it? And the weather is so unpredictable. Mrs. Ming Ming was just telling me that her laundry was ruined by an unexpected storm... So I thought of something. You know how monsters seem to have a sixth sense about the weather?")
if sm.sendAskAccept("Well, I placed #e#bBrain Wave Recorders#k#n on a bunch of them to see how they do it. I just need someone to retrieve the recorders for me so I can compile the data. Will you collect #e#b100 Brain Wave Recorders#k#n for me?"):
    sm.startQuest(parentID)
    sm.sendNext("You'll only find Brain Wave Recorders on monsters that are between 11 levels below and 21 levels above you!")
else:
    sm.sendNext("It would've been such a big help to both me and the townsfolk... Oh well.")
