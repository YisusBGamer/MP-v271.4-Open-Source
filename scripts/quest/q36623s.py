# 36623 Shinsoo's Teardrop Part 1 (Atan 1541205, Attacked Ereve): Father is the intruder.
execfile("scripts/quest/_mihile_story.py")

me("Father! Why would you do such a thing?")
say(ATAN, "Mihile... I came for #rShinsoo's Teardrop#k. With it, your brother can return to life.")
say(ATAN, "Isn't that why you wanted to become the Knight of Light? To protect your family?")
if ask(ATAN, "Bring me the Teardrop, Mihile. Please."):
    finish(parentID)
    me("(I became the Knight of Light to protect my family, and to save someone I love...)")
sm.dispose()
