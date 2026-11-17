# Sigils (reactors 6109000-6109004 = glpqskill0-4: Warrior, Bowman, Magician, Thief, Pirate) in the Forgotten Storage
# Chamber (610030200), Crimsonwood Keep party quest. A hit from the sigil's own class lights it; a hit from any other
# class puts out all five. Five lit sigils open the way on (portal glpqPortal1).
from net.swordie.ms.constants import JobConstants

SIGILS = [
    (6109000, "WarriorSigil", "Warrior", JobConstants.isWarriorEquipJob),
    (6109001, "ArcherSigil", "Bowman", JobConstants.isArcherEquipJob),
    (6109002, "MageSigil", "Magician", JobConstants.isMageEquipJob),
    (6109003, "ThiefSigil", "Thief", JobConstants.isThiefEquipJob),
    (6109004, "PirateSigil", "Pirate", JobConstants.isPirateEquipJob),
]

sigil = [s for s in SIGILS if s[0] == parentID]
if sm.getFieldID() == 610030200 and sigil and not sm.fieldHasProperty("Stage1Cleared"):
    reactorId, prop, name, fits = sigil[0]
    if fits(chr.getJob()):
        if not sm.fieldHasProperty(prop):
            sm.changeReactorState(reactorId, 1)
            sm.setFieldProperty(prop, True)
            sm.chatBlue("The %s Sigil has been activated!" % name)
            if all(sm.fieldHasProperty(s[1]) for s in SIGILS):
                sm.setFieldProperty("Stage1Cleared", True)
                sm.chatBlue("The Antellion grants you access to the next portal! Proceed.")
    else:
        for s in SIGILS:
            sm.setFieldProperty(s[1], False)
            sm.changeReactorState(s[0], 0)
        sm.chatRed("Someone has tampered with the wrong Sigil! All of the Sigils have been reset!")
sm.dispose()
