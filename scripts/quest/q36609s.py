# 36609 Fence (Atan 1541202, Lv 18): Mihile wants to face the Stray Dogs; Father says no and sends him to his room
# (993201005).
execfile("scripts/quest/_mihile_story.py")

me("You know I've always trusted you. I've trained just the way you've showed me, every day.")
me("No one knows shields better than you. And... I've always wanted to make the Knight's shield shine, to bring my "
   "brother back.")
me("But I'm not good enough to do that yet.")
me("And I'm not going to learn to be good enough by hiding away from Stray Dogs.")
me("I don't think there's much more for me to learn here.")
me("Not in this tiny little area.")
say(ATAN, "...My answer is already no.")
me("Why? Do you think I'm not strong enough?")
me("Listen, Father. I AM strong enough. Those dogs don't even scare me anymore!")
say(ATAN, "You went into their territory, didn't you?")
me("(Oh, no...)")
say(ATAN, "...It's only natural. You've grown. You want your boundaries to grow too.")
say(ATAN, "So pardon me for being the protective parent, but...")
say(ATAN, "...just how many of those dogs did you see out there? One? Maybe you could handle one. Maybe you already "
          "have. But most of the time, they run in packs.")
me("How big of a pack could there possibly be? Four, five? I could take that many.")
say(ATAN, "Heh. Four or five would be a small pack, Mihile. Think more like ten, at least.")
say(ATAN, "What would you do if they surrounded you? Even full-fledged knights take care not to fight that many at once.")
say(ATAN, "Besides... I'm not about to lose another child.")
me("...")
say(ATAN, "Mihile, let me ask you this: why do you want to become the Knight of Light so badly?")
me("To bring him back. To make you... to make #ball of us happy#k.")
say(ATAN, "And if, in your haste, you ended up hurt or worse, would I be happy? Would you?")
me("...No.")
say(ATAN, "I do understand how you feel. Of course I do. But I need you to listen to me. Please.")
say(ATAN, "I can trust you, can't I?", 1)
if ask(MIHILE, "(I'd better go to my room, like Father said.)\r\n\r\n#r(* You will be moved to the room when you "
               "accept.)#k"):
    begin(parentID)
    sm.warpInstanceIn(BEDROOM_SCENE, 0)
sm.dispose()
