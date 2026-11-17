# Vacant Land (993201010): a pack of Stray Dogs is down. After the second, A Girl in Danger (36613) is done: the girl's
# hand, the shield's light, and Neinheart. Then the real Wildland (993201062).
execfile("scripts/quest/_mihile_story.py")

WILD_DOG = 3645306

if sm.hasQuest(36613) and sm.getQRValueByKey(STORY_QR, "pack") == "1":
    sm.setQRValue(STORY_QR, "pack", "2")
    for x in (-600, -450, -300, -150, 0, 150, 300, 450, 550, 850):
        sm.spawnMob(WILD_DOG, x, 65)
elif sm.hasQuest(36613):
    sm.setQRValue(36613, "kill", "1")
    try:
        scene_start()
        black(500)
        unblack(1000)
        me("*ahem* Are you well, miss?")
        say(GIRL, "Ah... yes. Yes, I believe I am. Thank you...", 1)
        say(GIRL, "Ugh!", 1)
        me("They scratched your leg. Not deeply, as far as I can see. Can you stand?")
        say(GIRL, "Thank you, kind stranger--", 1)
        illust("hand", 1000)
        sm.showEffect("Effect/Direction29.img/mikhail_2022/effect/1", 0, 0, 0, 0, 0, 0, 0)
        sm.sendDelay(3000)
        illust_off(1000)
        sm.blind(1, 255, 0, 0)
        say(GIRL, "Oh.", 1)
        me("(The shield... reacted?)")
        unblack(500)
        say(NEINHEART, "Empress! Empress, are you hurt?")
        me("('Empress?')")
        me("(This little girl is a ruler?)")
        me("(Oh right, I've heard travelers talk about her...)")
        me("(...something about her being a 'merciful monarch who wields her power responsibly.')")
        me("(I had no idea she was so young.)")
        say(GIRL, "I'm well, Neinheart.")
        say(GIRL, "Thanks to this gallant soul.")
        say(GIRL, "I am in your debt, good knight.")
        black(500)
        finish(36613)
    finally:
        scene_end()
    show("03")
    sm.warpInstanceOut(WILDLAND, 0)
sm.dispose()
