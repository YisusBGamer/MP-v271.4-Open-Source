# Zero's story (Classic Zeros; Swordie Plus version - see zgen/gen_story.py). Mr. Do (2440016), in his training center - Path of the Sage 1 (40300): his lesson
if sm.hasQuest(40300) and not sm.hasItem(4033831):
    sm.setSpeakerID(2440016)
    sm.sendNext("A monk must know his herbs before his fists. Which root calms a raging heart: bellflower or ginseng?")
    sm.sendNext("Bellflower, of course! Not bad. Take this as proof that you passed my lesson.")
    sm.giveItem(4033831)
else:
    sm.setSpeakerID(2440016)
    sm.sendSayOkay("Medicine is patience. Training is patience. Everything is patience!")
sm.dispose()
