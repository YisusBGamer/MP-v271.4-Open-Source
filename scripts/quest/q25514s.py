# 25514 - The Hidden Auguries
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Vieren (1032209)

sm.setSpeakerID(1032209)  # Vieren
if sm.sendNext("Luminous! Luminous, I've gathered the power of the Auguries for you!\r\n#L0##bVieren? Of what do you speak?#l\r\n#k") != 0:
    sm.dispose()
if sm.sendAskAccept("Don't worry about it. Just use the #bAurora Prism#k to come see me in Harmony! "):
    sm.startQuest(parentID)
    sm.sendNext("I'll be waiting for you.")
else:
    sm.sendNext("Don't be like that! This is your best shot at really controlling that dark streak of yours!")
