# 36618 The Entrance Trial (Neinheart 1101002, Ereve): the Knight of Light's duties; the Knight Emblem (Knight's
# Token) from the First Drill Hall.
execfile("scripts/quest/_mihile_story.py")

if ask(NEINHEART, "Now that you've had a bit of a tour, we have business to discuss."):
    say(NEINHEART, "First off, I promised to tell you about the #bKnight of Light#k.")
    say(NEINHEART, "Listen well, for the Empress will need you to remember it.")
    say(NEINHEART, "Ah, a question first. How much do you know of the Empress?")
    say(NEINHEART, "She watches over Maple World. Though she is herself mortal, and far from omnipotent.")
    say(NEINHEART, "And so, long ago, she appointed a #bspecial knight#k to defend her and serve her will.")
    say(NEINHEART, "For generations, each Empress had an #bEmpress's Knight#k -- or a #bKnight of Light#k, if you prefer "
                   "-- standing beside her.")
    say(NEINHEART, "Should you indeed manifest the power of the light, you will inherit that same title.")
    me("(Protecting the Empress... is that why the shield lit up? Was it reacting to her?)")
    me("(I don't remember Father telling me any of this...)")
    say(NEINHEART, "Far lesser known is the Knight of Light's other duty.")
    say(NEINHEART, "The Knight protected '#bShinsoo's Teardrop#k,' a jewel with a mysterious power.")
    say(NEINHEART, "The records don't quite explain what that power was...")
    say(NEINHEART, "...but they do suggest it was key to Ereve's protection.")
    say(NEINHEART, "Even when there was no Empress, the Knights of Light did what they could to protect the jewel, and "
                   "with it, Ereve itself.")
    say(NEINHEART, "I believe the Empress saw that light in you, but what it means, or whether it will happen again, "
                   "those are open questions.")
    say(NEINHEART, "I do, however, have a theory. The Knight of Light protects the Empress, and doing so, of course, does "
                   "require an Empress to protect.")
    say(NEINHEART, "If your power is what she suspects, then it may very well require her presence to manifest in full.")
    say(NEINHEART, "We will make you a member of the Cygnus Knights, and train you by the Empress's side. That will "
                   "awaken your power, if anything can.")
    say(NEINHEART, "More than that, you'll receive professional training in a fine knightly order.")
    if ask(NEINHEART, "What say you? Will you join us?"):
        begin(parentID)
        say(NEINHEART, "Now, whatever light may dwell inside you, ALL applicants for knighthood must pass the same basic "
                       "test. I hope you weren't expecting special treatment.")
        say(NEINHEART, "Go to the #bFirst Drill Hall#k, defeat the monsters there, and bring back a #bKnight Emblem#k.")
        say(NEINHEART, "Ah, but you've only just arrived. Go left. You'll find the #bDrill Hall#k just outside of Ereve "
                       "proper.")
        say(NEINHEART, "In fact, let me send you there at once.")
        sm.warpInstanceIn(DRILL_HALL, 0)
sm.dispose()
