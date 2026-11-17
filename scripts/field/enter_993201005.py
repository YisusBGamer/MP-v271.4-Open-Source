# Somewhere in the Little Room (993201005): Mihile in his room after the argument (Fence, 36609); the next day.
execfile("scripts/quest/_mihile_story.py")

if sm.hasQuest(36609):
    try:
        scene_start()
        sm.blind(1, 255, 0, 0)
        sm.sendDelay(300)
        unblack(1000)
        me("(Father's right. I know I need to grow stronger, but...)")
        me("(...if I hurt myself trying, it'll hurt him too.)")
        me("(Still, isn't he holding me back a little too much?)")
        black(1000)
        finish(36609)
    finally:
        scene_end()
    caption("The Next Day")
sm.warpInstanceOut(REMOTE_HOUSE, 0)
sm.dispose()
