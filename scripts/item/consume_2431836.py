# Zero's story (Classic Zeros; Swordie Plus version - see zgen/gen_story.py). Shard of Recollection (2431836), for 40701: using it shows the vision the quest needs (record 40712)
if sm.hasQuest(40701):
    if sm.getQRValue(40712) != "1":
        sm.setQRValue(40712, "1")
    sm.sendSayOkay("(Memories rise from the shard... Bring it back to the Temple of Time.)")
else:
    sm.sendSayOkay("(A shard of memory. It doesn't react.)")
sm.dispose()
