# Jack Barricade (9201112) inside the Crimsonwood Keep party quest: the story at the hallway (610030100) and the
# sigil state in the Forgotten Storage Chamber (610030200, reactor/glpqskill0).
SIGILS = [("WarriorSigil", "Warrior"), ("ArcherSigil", "Bowman"), ("MageSigil", "Magician"),
          ("ThiefSigil", "Thief"), ("PirateSigil", "Pirate")]

if sm.getFieldID() == 610030100:
    sm.sendNext("Agh, you have made it in. Let me tell you real quick: they've caught us already. Master Guardians are "
                "about to come here in about a minute. We'd better hurry.")
    sm.sendNext("The portal to the Twisted Masters is busted. We have to find an alternate way, one that will take us "
                "through many death traps.")
    sm.sendSayOkay("You can find the portal somewhere around here... you'd better find it, quick. I'll catch up.")
elif sm.getFieldID() == 610030200:
    if sm.fieldHasProperty("Stage1Cleared"):
        sm.sendSayOkay("The Antellion has opened the way. Proceed to the next stage.")
    else:
        unlit = [name for prop, name in SIGILS if not sm.fieldHasProperty(prop)]
        sm.sendSayOkay("Each Sigil must be lit by one of its own class. A hit from the wrong class puts them all out."
                       "\r\n\r\nNot lit yet: #b" + ", ".join(unlit) + "#k")
sm.dispose()
