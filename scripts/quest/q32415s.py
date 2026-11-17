# 32415 - Start today's Cross World Party Quest!
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Spiegelette (9076000)
# Record 7906 "count" holds today's Cross World clears (PartyQuestRecords.addCrossWorldDailyClear); it starts over
# with each day's quest.

sm.setSpeakerID(9076000)  # Spiegelette
if sm.sendAskAccept("What's up, #b#e#h0##n#k? Do you know you can complete Cross World Party Quests up to #r5 times each day#k? If you reach that max count today, you'll get a gift! You just gotta try this."):
    sm.startQuest(parentID)
    if sm.hasQuest(parentID):
        sm.setQRValue(7906, "count", "0")
    sm.sendNext("Super! Good luck!")
else:
    sm.sendNext("If you're gonna get through these Cross World Party Quests, might as well do 5! Talk to me anytime you change your mind!")
