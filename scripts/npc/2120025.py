# Spin Man: the Haunted Mansion's host. In the major towns during Halloween (SeasonalEvents) and at the
# mansion's entrance. Hands out the invitation (Prologue), stands in for the old Halloween notifier, runs the
# Kaleido-Spinner and trades Ticket Pieces.
from net.swordie.ms.world import HalloweenEvent
from net.swordie.ms.world import SeasonalEvents

NPC = 2120025
ACTIVE = HalloweenEvent.isActive()
IN_MANSION = sm.getFieldID() // 1000000 == 229 or sm.getFieldID() // 100000 == 9229


def spinner():
    while True:
        n = sm.getQuantityOfItem(HalloweenEvent.KALEIDO_TICKET)
        if n <= 0:
            sm.sendSayOkay("You need a #bKaleido-Spinner Event Ticket#k #i4001863# to spin. You get them from the "
                           "Haunted Mansion's quests, from #bTicket Pieces#k (monsters near your level), and from "
                           "#rElite Pumpkin Zombies#k.")
            return
        if not sm.sendAskYesNo("Spin the #bKaleido-Spinner#k? (You have #b%d#k ticket%s.)\r\n\r\n"
                               "Modest, Great or ULTRA Jackpot: chairs, Elixirs, Spell Traces, the Halloween "
                               "Damage Skin, the Halloween Earrings, Ring, Pendant, Epaulette or a Jack-O-Lantern "
                               "mount!" % (n, "" if n == 1 else "s")):
            return
        prize = HalloweenEvent.spin(chr)
        if prize is None:
            sm.sendSayOkay("Make sure every inventory tab has a free slot, then try again.")
            return
        sm.sendNext("#e~ Round and round she goes... ~#n\r\n\r\n" + prize)


def invitation():
    if chr.getLevel() < HalloweenEvent.MIN_LEVEL:
        sm.sendSayOkay("The Masked Gentleman only invites guests who are #bLv. 33#k or higher. Come back when "
                       "you're a little more... seasoned.")
        return False
    sm.sendNext("Ahem! A letter for you, sealed in black wax:\r\n\r\n#e\"You are cordially invited to a "
                "Halloween party at the Haunted Mansion, a place filled with thrilling mysteries. Do come. We'll "
                "be waiting. Forever.\"#n\r\n\r\n- the #bMasked Gentleman#k")
    if not sm.sendAskAccept("Will you accept the invitation?"):
        sm.sendSayOkay("Suit yourself! The invitation will keep.")
        return False
    sm.startQuestNoCheck(15102)
    HalloweenEvent.finish(chr, 15102)
    return True


def go_to(field):
    HalloweenEvent.rememberTown(chr)
    sm.warp(field)


if not ACTIVE:
    if IN_MANSION and sm.sendAskYesNo("The party's over until next Halloween. Shall I take you back to town?"):
        sm.warp(HalloweenEvent.returnTown(chr))
    elif not IN_MANSION:
        sm.sendSayOkay("Halloween's over! See you next year.")
else:
    pieces = sm.getQuantityOfItem(HalloweenEvent.TICKET_PIECE)
    tickets = sm.getQuantityOfItem(HalloweenEvent.KALEIDO_TICKET)
    text = "Happy Halloween! I'm #bSpin Man#k. Halloween runs #b%s#k (Pacific).\r\n" % \
           SeasonalEvents.windowText(SeasonalEvents.HALLOWEEN)
    text += "#L0##bThe Haunted Mansion#k#l\r\n"
    text += "#L1#Spin the Kaleido-Spinner  #g(%d)#k#l\r\n" % tickets
    text += "#L2#Trade Ticket Pieces  #g(%d/%d)#k#l\r\n" % (pieces, HalloweenEvent.PIECES_PER_TICKET)
    text += "#L3#Elite Pumpkin Zombies#l\r\n"
    if IN_MANSION:
        text += "#L4#Back to town#l\r\n"
    sel = sm.sendNext(text)
    if sel == 0:
        if not sm.hasQuestCompleted(15102):
            if invitation() and not IN_MANSION and sm.sendAskYesNo("Shall I take you to the #bHaunted Mansion#k "
                                                                   "now?"):
                go_to(HalloweenEvent.ENTRANCE)
        else:
            guide = HalloweenEvent.guide(chr)
            if guide.done():
                sm.sendSayOkay(guide.text())
            elif guide.map() != 0 and guide.map() != sm.getFieldID():
                if sm.sendAskYesNo(guide.text() + "\r\n\r\nShall I take you there?"):
                    go_to(guide.map())
            elif not IN_MANSION:
                if sm.sendAskYesNo(guide.text() + "\r\n\r\nShall I take you to the #bHaunted Mansion#k?"):
                    go_to(HalloweenEvent.ENTRANCE)
            else:
                sm.sendSayOkay(guide.text())
    elif sel == 1:
        spinner()
    elif sel == 2:
        made = HalloweenEvent.exchangePieces(chr)
        if made > 0:
            sm.sendSayOkay("Here you go: #b%d Kaleido-Spinner Event Ticket%s#k #i4001863#." % (made, "" if made == 1
                                                                                                else "s"))
        else:
            sm.sendSayOkay("Bring me #b%d Kaleido-Spinner Ticket Pieces#k #i4034722# and I'll make you a ticket. "
                           "Monsters near your level drop them (Lv. 33+)." % HalloweenEvent.PIECES_PER_TICKET)
    elif sel == 3:
        q = HalloweenEvent.ELITE_PUMPKIN_QUEST
        if sm.hasQuestCompleted(q):
            sm.sendSayOkay("You've already defeated 100 Elite Pumpkin Zombies. Spooky!")
        elif sm.hasQuest(q) and sm.isComplete(q):
            if sm.canHold(1182191) and sm.canHold(3015346):
                sm.completeQuest(q)
                sm.sendSayOkay("100 Elite Pumpkin Zombies! Take the #bBrilliant Halloween Badge#k and the "
                               "#bElite Pumpkin Pal#k chair!")
            else:
                sm.sendSayOkay("Make room in your Equip and Set-up inventories first.")
        else:
            if not sm.hasQuest(q) and chr.getLevel() >= HalloweenEvent.MIN_LEVEL:
                sm.startQuestNoCheck(q)
            sm.sendSayOkay("During Halloween, an #rElite Pumpkin Zombie#k sometimes appears instead of an Elite "
                           "Boss. Each one drops Kaleido-Spinner tickets and a Halloween Surprise Badge Box. Defeat "
                           "#b100#k of them for the Brilliant Halloween Badge and the Elite Pumpkin Pal chair!")
    elif sel == 4:
        sm.warp(HalloweenEvent.returnTown(chr))
