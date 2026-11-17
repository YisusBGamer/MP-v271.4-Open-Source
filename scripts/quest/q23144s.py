# Gelimer's Trap (23144), start - Claudine (Resistance story: Battle Mage, Wild Hunter, Mechanic, Blaster, Xenon).
# The client has no lines for it; written from the quest's own Quest.wz summaries ("Claudine knew the mission was a
# trap, but your mind was made up. You cannot let Gelimer treat Vita as his experiment! Hurry to the mines and rescue
# Vita!"). Gelimer's Trap is your own copy.
CLAUDINE = 2151003

if not sm.hasQuest(parentID) and not sm.hasQuestCompleted(parentID):
    sm.setSpeakerID(CLAUDINE)
    sm.sendNext("#p2154009# wants us to come looking for #p2151005#. It's a trap, #h0#. It has to be.")
    sm.setPlayerAsSpeaker()
    sm.sendSay("Trap or not, I can't leave #p2151005# with him! He's treating her like one of his experiments!")
    sm.setSpeakerID(CLAUDINE)
    if sm.sendAskAccept("...I knew you'd say that. Then go, and be careful. Bring #p2151005# home."):
        sm.startQuest(parentID)
if sm.hasQuest(parentID):
    sm.warpInstanceIn(931000631, 0)
sm.dispose()
