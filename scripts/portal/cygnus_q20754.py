# Portal script cygnus_q20754.
# Crossroads of Ereve / Ereve: the portal below the sign leads to the Hallowed Ground during Lambent Essence
# (20754, Mihile). Catch the Spirits of Light there with the Light Catchers.
if sm.hasQuest(20754):
    sm.warp(913060000, 2)
else:
    chr.chatMessage("The Hallowed Ground can only be entered on Neinheart's orders.")
sm.dispose()
