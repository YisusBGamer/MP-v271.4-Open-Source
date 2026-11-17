# Gelimer's Trap (23144), end - Vita, in Gelimer's Trap (Resistance story).
# The client has no lines for it; written from the quest's own Quest.wz summaries ("Vita is..." / 'I Repent!':
# "Vita was caught in the explosion, another casualty in Gelimer's evil conspiracy. Unfortunately, you failed to
# rescue her before the blast.").
VITA = 2159348

sm.setSpeakerID(VITA)
sm.sendNext("#h0#...? You came... You actually came for me.")
sm.setPlayerAsSpeaker()
sm.sendSay("Of course I did! Come on, #p2151005#, let's get out of here before #p2154009# shows up.")
sm.setSpeakerID(VITA)
sm.sendSay("No, wait! Don't come any closer! He put something on me... It's a trap, #h0#! Run!")
sm.setPlayerAsSpeaker()
sm.sendSay("(A blinding flash fills the room. When the smoke clears, #p2151005# is gone...)")
if sm.completeQuest(parentID) and sm.getFieldID() == 931000631:
    sm.warpInstanceOut(310040210, 0)
sm.dispose()
