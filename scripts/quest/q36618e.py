# 36618 The Entrance Trial (Neinheart 1101002): Mihile joins the Cygnus Knights.
execfile("scripts/quest/_mihile_story.py")

say(NEINHEART, "And congratulations on joining the Cygnus Knights. Now you can formally begin your training to become the "
               "Knight of Light.")
if finish(parentID, ((TOKEN, 1),)):
    me("(I thought the Knight of Light was supposed to split seas and stop lightning. This royal bodyguard business "
       "is... not what I expected.)")
    me("(And what about Shinsoo's Teardrop? Protecting Ereve? And Neinheart didn't say anything about it granting a "
       "wish...)")
    me("(If I do become the Knight of Light, can I protect her, protect Ereve, AND make the wish?)")
    me("(I wish Father were here. For now, I suppose I should just keep training and getting stronger.)")
    me("('Loved ones above all.' My mission stands.)")
sm.dispose()
