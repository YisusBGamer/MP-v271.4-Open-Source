# 36614 Unfamiliar Guests Part 1 (Neinheart 1541203, Wildland): Mihile listens in on the Empress and her tactician
# (993201009).
execfile("scripts/quest/_mihile_story.py")

say(NEINHEART, "You protected our Empress. Thank you.")
me("*ahem* Think nothing of it. I did what was needed.")
say(GIRL, "Neinheart, a word, please...")
if ask(MIHILE, "Should I listen in?\r\n\r\n#r(* When you accept, you'll be moved to the relevant map.)#k"):
    begin(parentID)
    sm.warpInstanceIn(GUESTS_SCENE, 0)
sm.dispose()
