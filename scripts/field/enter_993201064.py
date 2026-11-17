# Somewhere in Ereve (993201064): The Story of Chromile (36619) in the garden: Chromile, his murdered family, and their
# picture. Then Atan's house (993201066).
execfile("scripts/quest/_mihile_story.py")

if sm.hasQuest(36619):
    try:
        scene_start()
        sm.blind(1, 255, 0, 0)
        sm.sendDelay(300)
        unblack(1000)
        say(NEINHEART, "Chromile was, as you know, the most recent Knight of Light. Which is likely why his records are "
                       "more complete, though not entirely so.")
        say(NEINHEART, "By all accounts, he was the model of a knight. Selfless, honorable, and quite popular in his day.")
        say(NEINHEART, "Even so, it seems he had his enemies. One, at least.")
        say(NEINHEART, "A cruel and cowardly man -- or a woman, perhaps; no one knows -- snuck into his house and murdered "
                       "him. Not only him, but his wife, and even their infant son.")
        say(NEINHEART, "We came across this picture of the family.")
        illust("family", 1000)
        sm.sendDelay(1000)
        say(NEINHEART, "Her Majesty believes in you, Mihile, and I have no reason to doubt her.")
        say(NEINHEART, "Chromile left quite a legend behind. That may not be easy to live up to. But Ereve will benefit "
                       "from having you here; I believe that much is certain.")
        me("(I've never seen this picture before in my life... Have I?)")
        me("(Huh. So that's Chromile? He does look like me.)")
        illust_off(1000)
        sm.blind(1, 255, 0, 1000)
        sm.sendDelay(1000)
        me("(Now I see what the elder was talking about.)")
        say(NEINHEART, "...Mihile? Are you listening?")
        unblack(500)
        me("Yes! ...To everything but those last few words.")
        say(NEINHEART, "I asked if you had any questions.")
        say(NEINHEART, "...Well, you must be tired. We can leave it there for today.")
        say(NEINHEART, "Tomorrow, your training begins in earnest. Be ready.")
        me("Yes, sir.")
        black(1000)
    finally:
        scene_end()
    sm.warpInstanceIn(ATANS_HOUSE_SCENE, 0)
else:
    sm.warpInstanceOut(EREVE, 0)
sm.dispose()
