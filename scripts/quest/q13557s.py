# 13557 - 'Cause It's the Weekend! (On Pinnacle!)
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Maple Administrator (9201257)

sm.setSpeakerID(9201257)  # Maple Administrator
if sm.sendAskAccept("Hi #h0#!\r\nAre you enjoying our event?\r\nI prepared a little something for all you've done so far. Do you want it now?\r\n"):
    sm.startQuest(parentID)
    sm.sendNext("Hoep you like it!\r\n\r\n#i2028180# #t2028180#\r\n#i2028181# #t2028181#")
else:
    sm.sendNext("I guess I COULD just throw it in the garbage.")
