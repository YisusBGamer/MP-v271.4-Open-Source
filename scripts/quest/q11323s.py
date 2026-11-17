# 11323 - The Legendary Hakuna Matata
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Maple Administrator (9201257)

sm.setSpeakerID(9201257)  # Maple Administrator
if sm.sendNext("Hakuna Matata!\r\nDo you know what this means? It means 'No worries, everything is going to be fine' in Swahili. #h0#Remember this line, and who knows? Maybe it will help you out sometime.\r\n#L0##bHakuna Matata!#l\r\n#k") != 0:
    sm.dispose()
sm.startQuest(parentID)
