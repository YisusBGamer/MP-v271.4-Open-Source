# 36937 The Special Ink - start (Jin Jin the painter, Mu Lung). The ink's materials (Straw/Wooden Training Doll Remnants,
# Training Jar Fragments) come from the Training Ridge (925041000), which only this quest leads to.
TRAINING_RIDGE = 925041000

if sm.questStartDialogue(parentID, 2091008):
    if sm.sendAskYesNo("#b(Go to the #m925041000# to gather the materials now?)#k"):
        sm.warpInstanceIn(TRAINING_RIDGE, 0)
        sm.setInstanceTime(1200, 250000000)
sm.dispose()
