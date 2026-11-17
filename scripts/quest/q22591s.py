# The Past, Onyx Dragons, Black Mage (22591, Evan): the sleeping dragon tells its story when the quest starts, which
# is what the quest checks (quest record 22601).
if sm.questStartDialogue(parentID):
    sm.setQRValue(22601, "1", False)
sm.dispose()
