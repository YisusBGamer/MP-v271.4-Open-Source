# 20036 Preparation for Knighthood (Atan 1541202, Remote House): the Three Sacred Rules; Mihile becomes a knight
# apprentice (5100, Lv 10) as soon as he is ready, as on the official server.
execfile("scripts/quest/_mihile_story.py")

if not sm.canHold(MEDAL) or not sm.canHold(SPROUT_POTION, 50):
    sm.chatScript("Make room in your inventory.")
else:
    me("Good morning, Father.")
    say(ATAN, "You're up early.")
    me("Of course. There's training to do.")
    say(ATAN, "Hahaha, like father, like son.")
    me("Whatever helps the family, right?")
    say(ATAN, "Of course...")
    say(ATAN, "But please don't push yourself too hard. You might get hurt.")
    say(ATAN, "Your effort's commendable, but use that effort wisely. Don't rush. You don't need to.")
    me("I understand, Father.")
    if ask(ATAN, "Are you ready to train?"):
        begin(parentID)
        knighthood()
sm.dispose()
