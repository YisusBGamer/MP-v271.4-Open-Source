# 36605 The Second Training Session (Atan 1541202): Wild Pig x15 and their 10 stolen fruits.
execfile("scripts/quest/_mihile_story.py")

say(ATAN, "Next, you'll need to hunt #rWild Pigs#k. Around #r15#k should do it.")
if ask(ATAN, "Those pigs stole some fruit as well. Would you bring back #rWild Pigs' Fruit x10#k while you're at it?"):
    begin(parentID)
    say(ATAN, "Pigs are stronger than mice, of course, but I think you can handle them.")
    me("Of course, Father.")
    me("(The Wild Pigs should be in the #bRemote Forest Plain#k. I'll get them.)")
sm.dispose()
