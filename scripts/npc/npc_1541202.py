# Atan (1541202), Remote House: a new Beginner Warrior's Sword for Mihile if it got lost (36603 says to ask him).
execfile("scripts/quest/_mihile_story.py")

if sm.hasQuestCompleted(36601) and not sm.hasItem(SWORD) and not sm.isEquipped(SWORD) and not sm.hasQuestCompleted(36603):
    if sm.canHold(SWORD):
        say(ATAN, "You lost your sword? Here, take this one. Look after it this time.")
        sm.giveItem(SWORD)
    else:
        sm.chatScript("Make room in your Equip inventory.")
elif sm.hasQuest(36607):
    # Lost Property left before the Stray Dog was down: back out for the picture
    if ask(MIHILE, "(The picture is still out there. Should I go back for it?)"):
        sm.warpInstanceIn(LOST_PROPERTY_FIELD, 0)
else:
    say(ATAN, "Good morning, Mihile.")
sm.dispose()
