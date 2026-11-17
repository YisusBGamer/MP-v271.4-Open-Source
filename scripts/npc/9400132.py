# DJ JM (9400132) at the MMF Campgrounds (HolidayEvents.MMF): his Lost Equipment (59900-59906, 2016), the daily
# Crumpled Flyer (59907), the weekly Fireworks Show invitation (59920-59922) and the stages.
from net.swordie.ms.world import HolidayEvents as H
from net.swordie.ms.world import SeasonalEvents

sm.setSpeakerID(9400132)
GEAR = ["Deck", "Mixer", "Headphones", "Laptop", "Speaker (L)", "Speaker (R)", "Lighting Rig"]


def gear():
    q = H.jmEquipmentQuest(chr)
    if q == 0:
        sm.sendSayOkay("All my gear is back where it belongs, and the booth is yours to keep. You're a lifesaver!")
        return
    i = q - H.JM_EQUIPMENT_FIRST
    item = H.JM_ITEM_FIRST + i
    if not sm.hasQuest(q):
        if i == 0:
            sm.sendNext("You're just the person I wanted to see! The truck hit a pothole on the way to the festival, "
                        "and my gear went flying. Now the #rmonsters#k have it! Seven pieces, all over Maple World.")
        if sm.sendAskYesNo("Next up: my #b%s#k (#i%d#). Some monster near your level has it. Could you get it "
                           "back for me?" % (GEAR[i], item)):
            sm.startQuestNoCheck(q)
            sm.sendSayOkay("Sorry for the trouble. I'm counting on you! (Hunt monsters near your level.)")
    elif sm.hasItem(item):
        if H.returnJmEquipment(chr, q):
            if q == H.JM_EQUIPMENT_LAST:
                sm.sendSayOkay("My Lighting Rig! The show is complete! You've been through a lot this week, so here: "
                               "my very own #bDJ Booth#k chair. Spin some tracks!")
            else:
                sm.sendSayOkay("Oh hey! My %s! Still works, too. Thanks, friend." % GEAR[i])
        else:
            sm.sendSayOkay("Make some room in your Set-up inventory first!")
    else:
        sm.sendSayOkay("Still looking for my #b%s#k (#i%d#)? Monsters near your level have it." % (GEAR[i], item))


def flyer():
    if sm.hasQuest(H.CRUMPLED_FLYER_QUEST):
        if H.flattenedFlyer(chr):
            sm.sendSayOkay("Perfectly flat! You're a natural. Here's the #bgift box#k I put together. Same time "
                           "tomorrow?")
        else:
            sm.sendSayOkay("The folds should straighten out if you hold onto the crumpled flyer for #b30 minutes#k. "
                           "It still looks kind of crumpled. (And it disappears if you log out!)")
    elif H.canTakeFlyer(chr):
        sm.sendNext("Got a minute? I made this flyer for the Maple Music Festival, but I got so pumped I crumpled it.")
        if sm.sendAskYesNo("Can you take this #bCrumpled MMF Flyer#k and straighten it out? Just keep it with you for "
                           "#b30 minutes#k and bring it back. I'll give you a #bGift Box#k I worked hard on!"):
            sm.startQuestNoCheck(H.CRUMPLED_FLYER_QUEST)
            sm.sendSayOkay("Let it air out for 30 minutes to un-crumple the creases. Be warned, it'll disappear if "
                           "you log out!")
        else:
            sm.sendSayOkay("You can only un-crumple one flyer a day. Think it over.")
    else:
        sm.sendSayOkay("One flyer a day is plenty. Come back tomorrow!")


def fireworks():
    w = H.mmfWeek()
    q = H.FIREWORKS_QUESTS[w]
    letter = H.FIREWORKS_LETTERS[w]
    box = H.FIREWORKS_BOXES[w]
    if sm.hasQuest(q):
        if sm.hasItem(letter):
            if sm.canHold(box):
                sm.consumeItem(letter, 1)
                sm.completeQuestNoRewards(q)
                sm.giveItem(box, 1)
                sm.sendSayOkay("The invitation's ready! Here's this week's #bFireworks Show Commemorative Box#k.")
            else:
                sm.sendSayOkay("Make some room in your Use inventory first!")
        else:
            sm.sendSayOkay("Keep the #bFireworks Show Invitation Envelope#k with you for #b1 hour#k. Then it'll turn "
                           "into an invitation. Remember, it disappears if you log out!")
    elif sm.hasQuestCompleted(q):
        sm.sendSayOkay("You've got this week's commemorative box already. A new one every week of the festival!")
    else:
        sm.sendNext("Did you know there's a #bFireworks Show#k here every Saturday at #b3 PM#k and #b8 PM#k?")
        if sm.sendAskYesNo("Take this #bFireworks Show Invitation Envelope#k and keep it with you for #b1 hour#k. "
                           "Then bring me the invitation, and I'll give you a #bFireworks Show Commemorative Box#k! "
                           "(One a week.)"):
            sm.startQuestNoCheck(q)
            sm.sendSayOkay("Keep it for 1 hour. Remember, it'll disappear if you log out!")


if not SeasonalEvents.isActive(H.MMF):
    sm.sendSayOkay("The festival's over for this year. See you next time!")
else:
    text = "Yo! Welcome to the #bMaple Music Festival#k!\r\n"
    text += "\r\n#L0##bMy lost equipment#k#l"
    text += "\r\n#L1##bThe crumpled flyer#k (daily)#l"
    text += "\r\n#L2##bFireworks Show invitation#k (weekly)#l"
    text += "\r\n#L3#The concert stages#l"
    text += "\r\n#L4#Take me back to town.#l"
    sel = sm.sendNext(text)
    if sel == 0:
        gear()
    elif sel == 1:
        flyer()
    elif sel == 2:
        fireworks()
    elif sel == 3:
        sm.sendSayOkay("The stages are dark this year... the rhythm-game rigs never made it to this world. But the "
                       "Campgrounds are where the party's at anyway!")
    elif sel == 4:
        sm.warp(H.mmfReturnTown(chr), 0)
sm.dispose()
