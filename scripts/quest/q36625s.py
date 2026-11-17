# 36625 Hidden Truth (self start, Lv 28): the brother does not return; the truth about Atan comes out.
execfile("scripts/quest/_mihile_story.py")

me("(Brother will be here soon...)")
begin(parentID)
try:
    scene_start()
    sm.blind(1, 200, 0, 1000)
    sm.sendDelay(1500)
    say(ATAN, "...He isn't coming back, Mihile. The Teardrop can't bring back my son.")
    say(ATAN, "And you... you were never my son. I am the one who killed #bChromile#k, the Knight of Light. You are his.")
    me("(No... This can't be true.)")
    sm.blind(0, 0, 0, 1000)
    sm.sendDelay(1000)
    finish(parentID)
finally:
    scene_end()
sm.dispose()
