# Time To Make a Decision (23148), start - Claudine (Resistance story).
# The client has no lines for the start; written from the quest's own Quest.wz summaries ("The Resistance has met and
# decided to participate in the continental conference, led by Empress Cygnus... Bring word of the Resistance's official
# acceptance back to Empress Cygnus.").
CLAUDINE = 2151003
RESPONSE = 4033096  # Response from the Resistance

if not sm.hasQuestCompleted(parentID):
    sm.setSpeakerID(CLAUDINE)
    if not sm.canHold(RESPONSE):
        sm.sendSayOkay("Make some room in your Etc inventory first.")
    elif sm.hasQuest(parentID):
        if not sm.hasItem(RESPONSE):
            sm.giveItem(RESPONSE)
            sm.sendSayOkay("Here's our answer again. Take it to Empress #p1101000#.")
    else:
        sm.sendNext("The Resistance has met. With the Black Wings' founder serving the Black Mage, we can't pretend this is only our fight any more.")
        sm.sendSay("We've decided to join the continental conference led by Empress #p1101000#. We're not sure we can win, even with all of Maple World's leaders together... but we know we can't win alone.")
        if sm.sendAskAccept("Will you take our answer to Empress #p1101000# in #m130000000#?"):
            sm.startQuest(parentID)
            sm.giveItem(RESPONSE)
            sm.sendSayOkay("Thank you, #h0#. For #p2151005#, too.")
sm.dispose()
