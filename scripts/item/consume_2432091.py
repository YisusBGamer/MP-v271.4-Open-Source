# Gollux's Head Teleport Rock (2432091): takes you to Gollux's Head (863010600) in the Gollux battle you're in, and uses up one rock.
# They drop from Gollux's monsters and stack.
TARGET = 863010600
NAME = "Gollux's Head"

fieldID = sm.getFieldID()
if chr.getInstance() is None or not (863010100 <= fieldID <= 863010600):
    chr.chatMessage("This rock only works inside the Gollux battle.")
elif fieldID == TARGET:
    chr.chatMessage("You're already at " + NAME + ".")
else:
    sm.consumeItem()
    sm.warp(TARGET, 0)
sm.dispose()
