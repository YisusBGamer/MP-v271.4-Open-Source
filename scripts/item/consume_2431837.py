# Zero's story (Classic Zeros; Swordie Plus version - see zgen/gen_story.py). Shard of Regret (2431837), for 40702: using it shows the vision the quest needs (record 40713)
if sm.hasQuest(40702):
    if sm.getQRValue(40713) != "1":
        sm.setQRValue(40713, "1")
    sm.sendSayOkay("(Memories rise from the shard... Bring it back to the Temple of Time.)")
else:
    sm.sendSayOkay("(A shard of memory. It doesn't react.)")
sm.dispose()
