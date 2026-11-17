# 25513 - Reunion after Centuries
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Vieren (1032209)

sm.setSpeakerID(1032209)  # Vieren
if sm.sendNext("Luminous... Luminous...!\r\n#L0##bHm? Who goes there?#l\r\n#k") != 0:
    sm.dispose()
if sm.sendSay("Can you hear the sound of my voice?\r\n#L0##bI know that voice... Vieren? Is that you?#l\r\n#k") != 0:
    sm.dispose()
if sm.sendSay("That's right! I knew you'd remember me, even after all these centuries. Come drop by and we can catch up. I'm in Harmony these days.\r\n#L0##bHarmony, our old sanctum? I would if I could, old friend, but I've lost my way. I don't know how to get there.#l\r\n#k") != 0:
    sm.dispose()
if sm.sendAskAccept("Psh, no problem! Here, take this. You can use it to get to Harmony."):
    sm.startQuest(parentID)
    sm.sendNext("I already charged that sucker up, so come on over whenever you feel like it.")
else:
    sm.sendNext("Come now! That's no way to treat an old friend.")
