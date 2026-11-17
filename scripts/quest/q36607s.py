# 36607 Lost Property (self start): the photo of Mihile's brother, dropped in the Clearing; a Stray Dog is there.
execfile("scripts/quest/_mihile_story.py")

me("(I knew something was missing. My brother's picture! Did I drop it while I was hunting the Sparrows?)")
me("(It's the only picture of him there is. What if someone else picked it up? What if the Sparrows tore it to pieces?)")
me("(I need to go find it, now.)")
me("(Father said to stay inside, but... He'll understand.)")
if ask(MIHILE, "(The only question is... am I ready?)\r\n\r\n#r(* If you say yes, you'll head out to find the picture.)#k"):
    begin(parentID)
    me("(Please let it still be there...)")
    sm.warpInstanceIn(LOST_PROPERTY_FIELD, 0)
sm.dispose()
