# Phantom's Letter (41064, Mirror World Ariant): the letter was Phantom's; he waits in town to have a word.
if sm.questStartDialogue(parentID) and sm.getFieldID() == 322000000:
    sm.spawnNpcIfAbsent(2420015, 2300, 276)
sm.dispose()
