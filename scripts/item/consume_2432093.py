# Gollux's Right Shoulder Teleport Rock (2432093): takes you to Gollux's Right Shoulder (863010330) in the Gollux battle you're in, and uses up one rock.
# They drop from Gollux's monsters and stack.
TARGET = 863010330
NAME = "Gollux's Right Shoulder"

fieldID = sm.getFieldID()
if chr.getInstance() is None or not (863010100 <= fieldID <= 863010600):
    chr.chatMessage("This rock only works inside the Gollux battle.")
elif fieldID == TARGET:
    chr.chatMessage("You're already at " + NAME + ".")
else:
    sm.consumeItem()
    sm.warp(TARGET, 0)
sm.dispose()
