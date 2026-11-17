# Captain's Secret Tastes (30069, Mushroom Castle): the Black Viking is beaten (his NPC never appears to report to)
if sm.completeQuest(parentID):
    sm.chatScript("The Black Viking is defeated! The Prime Minister wants a word.")
    if sm.getFieldID() == 106030800 and chr.getField().getLifeByTemplateId(1302005) is None:
        sm.spawnNpc(1302005, 200, 289)
sm.dispose()
