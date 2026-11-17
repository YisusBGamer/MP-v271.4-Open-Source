# 11847 - Rescue the Miwok Boys 10 times! Achieved!
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Blackbull (9000097)

sm.setSpeakerID(9000097)  # Blackbull
if sm.sendAskAccept("Wow! #b#h0##k, you've gone to rescue our boys 10 times already? I don't know how to thank you! This gift seems so paltry. Will you please accept it?"):
    sm.startQuest(parentID)
else:
    sm.sendNext("You shame me with your graciousness! Talk to me if you would like to accept your gift.")
