# Tiv's Forest (993201000): the Mihile story's prologue. Cygnus and Neinheart at the bell talk of Shinsoo's Teardrop
# and the Knight of Light; then the opening captions (993201001).
execfile("scripts/quest/_mihile_story.py")

if sm.hasQuestCompleted(36601) or chr.getJob() != 5000:
    sm.warpInstanceOut(REMOTE_HOUSE, 0)
else:
    try:
        scene_start()
        sm.hideUser(True)
        sm.spawnNpc(CYGNUS, 3926, -268)
        sm.spawnNpc(NEINHEART, 3700, 88)
        sm.blind(1, 255, 0, 0)
        sm.sendDelay(500)
        unblack(1000)
        sm.moveNpcByTemplateId(NEINHEART, False, 180, 100)
        sm.sendDelay(1500)
        say(NEINHEART, "There you are, Your Majesty.")
        say(CYGNUS, "Oh, Neinheart. I thought I might stretch my legs a bit.")
        say(NEINHEART, "Your body is still adjusting to Shinsoo's power. You should rest.")
        say(CYGNUS, "Hmm. If I were to let myself rest, I'd grow far too fond of it.")
        say(NEINHEART, "...Ah, you would never succumb to sloth, if that's what you suggest. But you do seem particularly "
                       "restless today. Are you well, Empress?")
        say(CYGNUS, "Heh, I never could hide anything from you.")
        say(CYGNUS, "I'm the first Empress to walk these grounds since Aria, #rcenturies ago#k.")
        say(CYGNUS, "And the power of Shinsoo, the power to protect Ereve... it's faint now, nowhere near what the legends "
                    "claim it was.")
        say(NEINHEART, "So the Black Wings thought when they attacked the barrier.")
        say(NEINHEART, "But the barrier stands, thanks to your good judgment.")
        say(NEINHEART, "Faint though the power may be, you used it well.")
        say(CYGNUS, "It was your wits that protected us, Neinheart. Without you, Ereve would have fallen for certain.")
        say(NEINHEART, "You flatter me, Empress. And you have no need for modesty yourself.")
        say(CYGNUS, "I speak no flattery, Neinheart. Only gratitude.")
        say(CYGNUS, "Regardless, and even with the #bCygnus Knights#k on the rise, there is always more work to be done.")
        say(CYGNUS, "After all, there are centuries of lost time to make up for.")
        say(NEINHEART, "On that subject...")
        black(500)
        illust("teardrop")
        unblack(500)
        say(NEINHEART, "History speaks of a #bGuardian Stone#k, a jewel that protected Ereve, in its day.")
        say(NEINHEART, "The name of that jewel was '#bShinsoo's Teardrop#k.'")
        say(NEINHEART, "There are gaps in the record, as always, but we do believe the story is true, and the #bstone had "
                       "that power#k.")
        say(NEINHEART, "Contemporary sources describe Shinsoo's teardrop as 'priceless,' 'irreplaceable.'")
        say(NEINHEART, "Imagine if it could protect Ereve in your name. None could deny your lineage, or your right to "
                       "rule.")
        say(NEINHEART, "Only one complication: as you've no doubt inferred, the Teardrop is nowhere to be found.")
        say(NEINHEART, "All we know from the records is that it was lost along with its most recent #bguardian#k.")
        illust_off(500)
        sm.sendDelay(500)
        say(CYGNUS, "It had a #bguardian#k?", 1)
        say(NEINHEART, "A personage known as the '#bKnight of Light#k'.")
        say(NEINHEART, "...who, reportedly, held some connection to Shinsoo's power, I might add.")
        say(NEINHEART, "If we can track down this Knight, or whatever might remain of him, we may recover the Teardrop in "
                       "the process.")
        black(1000)
    finally:
        sm.hideUser(False)
        scene_end()
    sm.warpInstanceOut(OPENING, 0)
sm.dispose()
