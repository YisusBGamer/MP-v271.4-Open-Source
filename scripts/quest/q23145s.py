# I Repent! (23145), start - Claudine (Resistance story).
# The client has no lines for it; written from the quest's own Quest.wz summaries ("Report back to Claudine and
# give her the bad news." / "Claudine seems to be regretting her choices. If she had only asked for help, Vita
# could have been saved... We can't do this alone.").
CLAUDINE = 2151003

if not sm.hasQuest(parentID) and not sm.hasQuestCompleted(parentID):
    sm.setSpeakerID(CLAUDINE)
    sm.sendNext("#h0#! You're back! Where's #p2151005#? What happened out there?!")
    sm.setPlayerAsSpeaker()
    sm.sendSay("(You tell Claudine about the trap and the explosion. You couldn't save #p2151005#.)")
    sm.setSpeakerID(CLAUDINE)
    sm.sendSay("...This is my fault. I never trusted her. If I'd asked for help instead of sending you in alone, she might still be here.")
    if sm.sendAskAccept("We can't keep doing this alone, #h0#. Things might go differently with some help..."):
        sm.startQuest(parentID)
sm.dispose()
