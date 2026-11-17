# That's a Lot of Firepower (31615), start: Kaiser hands over the Blue Dragon Cannon (Angelic Buster's Heliseum story).
# The client has no lines for it; written from the quest's own Quest.wz summary ("Kaiser won't let you fight the battle
# on your own and asks you to deliver the Blue Dragon Cannon to Edea while he buys time.") and the crate's description.
KAISER = 3001010
CRATE = 4033388  # Blue Dragon Cannon Crate

sm.setSpeakerID(KAISER)
if not sm.canHold(CRATE):
    sm.sendSayOkay("Make some room in your Etc inventory first. The cannon's crate is not small.")
else:
    sm.sendNext("Specters are closing in on this path. I'll hold them back here.")
    sm.setPlayerAsSpeaker()
    sm.sendSay("Then I'm staying too! Two heroes are better than one!")
    sm.setSpeakerID(KAISER)
    sm.sendSay("No. The Blue Dragon Cannon is our only chance of taking back headquarters. If the Specters get their hands on it, everything we've worked for is lost.")
    if sm.sendAskYesNo("Take it to #b#p3001000##k at the Reclamation HQ. I'll buy you all the time you need. Will you do this for me?"):
        sm.startQuest(parentID)
        sm.giveItem(CRATE)
        sm.sendSayOkay("Go! The path to the right leads to the Reclamation HQ. And Angelic Buster... thank you.")
    else:
        sm.sendSayOkay("There's no time to argue. Talk to me when you're ready.")
sm.dispose()
