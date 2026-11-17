# Zero's story (Classic Zeros; Swordie Plus version - see zgen/gen_story.py). Shard of Oblivion (2431838), for 40703: using it shows the vision the quest needs (record 40714)
if sm.hasQuest(40703):
    if sm.getQRValue(40714) != "1":
        sm.setQRValue(40714, "1")
    sm.sendSayOkay("(Memories rise from the shard... Bring it back to the Temple of Time.)")
else:
    sm.sendSayOkay("(A shard of memory. It doesn't react.)")
sm.dispose()
