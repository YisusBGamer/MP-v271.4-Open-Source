# Vacant Land (993201010): the Wildland for A Girl in Danger (36613). Stray Dogs corner a girl; Mihile draws them off.
# 20 dogs in two packs within 20 minutes; clear_993201010 sends the second pack and ends the fight.
execfile("scripts/quest/_mihile_story.py")

WILD_DOG = 3645306
GIRL_NPC = 1541204

if sm.hasQuest(36613) and sm.getQRValueByKey(36613, "kill") != "1":
    show("04")
    sm.spawnNpc(GIRL_NPC, 700, 65)
    try:
        scene_start()
        sm.blind(1, 255, 0, 0)
        sm.sendDelay(300)
        unblack(1000)
        me("(...The Stray Dogs came all the way out here?)")
        say(STRAY_DOG_SAY, "Grrr...")
        say(GIRL, "H-help! Someone!", 1)
        say(GIRL, "Eeyaa!", 1)
        say(STRAY_DOG_SAY, "Grrr...")
        black(300)
        me("Oof!")
        unblack(300)
        me("(I have their attention. That'll give her time to run.)")
        me("Run! Get out of here!")
        say(GIRL, "What about you?", 1)
    finally:
        scene_end()
    sm.setQRValue(STORY_QR, "pack", "1")
    caption("Defeat all the Stray Dogs.")
    for x in (-600, -450, -300, -150, 0, 150, 300, 450, 550, 850):
        sm.spawnMob(WILD_DOG, x, 65)
elif sm.hasQuest(36613) or sm.hasQuestCompleted(36613):
    sm.warpInstanceOut(WILDLAND, 0)
else:
    sm.warpInstanceOut(REMOTE_HOUSE, 0)
sm.dispose()
