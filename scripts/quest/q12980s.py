# 12980 - Alphabetical!
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Maple Administrator (9201257)

sm.setSpeakerID(9201257)  # Maple Administrator
if sm.sendAskAccept("I see here you're cleared for another Alphabet Cookie. Which one do you need to complete your collection?"):
    sm.startQuest(parentID)
else:
    sm.sendNext("That's odd, my notes are never wrong... but you're not missing any letters. Thanks anyway, I suppose!")
