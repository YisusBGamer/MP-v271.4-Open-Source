# 36615 Unfamiliar Guests Part 2 (Neinheart 1541203): they seek the Knight of Light and invite Mihile to Ereve.
execfile("scripts/quest/_mihile_story.py")

me("If... If I might ask, what brings you to this... humble place?")
me("There's nothing to see but grass and trees, as far as I know, and I've lived here all my life.")
say(NEINHEART, "Might I ask you a question, Mihile? Have you heard the tale of the #bKnight of Light#k?")
say(NEINHEART, "Or rather, we seek one worthy of the name. And our search has brought us here.")
say(NEINHEART, "As you may know, the Knight of Light is the #bEmpress's Knight#k. He protects her home, and all that is "
               "closest to her.")
say(NEINHEART, "The throne has... been without such a knight for some time now.")
me("(The Empress's Knight? Father never told me that...)")
say(NEINHEART, "If you would accept our invitation, we can tell you more on the way to #bEreve#k. Please, come with us.")
me("Hmm? Right now?")
say(NEINHEART, "Indeed. Her Majesty believes you are a prime candidate for the Knighthood.")
say(NEINHEART, "...but all things in time.")
say(NEINHEART, "What say you, Mihile? Will you join us in Ereve and see what you're capable of?")
me("W-wait. I... I need time to think about this.")
say(NEINHEART, "Of course. This is quite sudden, after all. Well, it's an invitation, not an order. Let us know once "
               "you've made up your mind.")
say(CYGNUS, "Mihile, I know the power of light inside you. I can sense it.")
say(CYGNUS, "I would love to be the one to awaken that power in you. It's your choice.")
me("(For just a moment, I saw the shield shine.)")
me("(And they know about the Knight of Light. Maybe even more than Father or I do...)")
me("(If I go with them, they could teach me everything I want to know.)")
me("(But...)")
try:
    scene_start()
    black(500)
    say(ATAN, "Keep yourself out of danger while I'm gone. And please stay close to home.")
    unblack(500)
finally:
    scene_end()
finish(parentID)
sm.dispose()
