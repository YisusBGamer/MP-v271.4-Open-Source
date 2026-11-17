# Swordie Coin Shop (@shop > Swordie Coin Shop): sections, lines and buying all come from SwordieCoinShop; this only
# shows them. Esc or Back leaves a page.
from net.swordie.ms.client.character import SwordieCoinShop
from net.swordie.ms.constants import ItemConstants

COIN = ItemConstants.SWORDIE_COIN
BACK = 99


def back_line():
    return "\r\n#L{}##rBack#k#l".format(BACK)


def header():
    return "#e#bSwordie Coin Shop#k#n   #i{}# #b{}#k coins\r\n".format(COIN, SwordieCoinShop.coins(chr))


def icon(line):
    first = line.options()[0]
    return "#i{}# ".format(first[0][0]) if len(first) > 0 else ""


def cap_text(line):
    left = SwordieCoinShop.capLeft(chr, line)
    if left < 0:
        return ""
    if line.weekly():
        return "  #d({} of {} left this week)#k".format(left, line.cap())
    return "  #d({} of {} left)#k".format(left, line.cap())


def buy_page(line):
    option = 0
    if line.options().size() > 1:
        text = header() + "\r\n#b{}#k - #r{}#k coins{}\r\nWhich one?\r\n".format(line.name(), line.price(),
                                                                              cap_text(line))
        for i in range(line.options().size()):
            opt = line.options()[i]
            pic = "#i{}# ".format(opt[0][0]) if len(opt) > 0 else ""
            text += "\r\n#L{}#{}#b{}#k#l".format(i, pic, line.optionNames()[i])
        option = sm.sendNext(text + "\r\n" + back_line())
        if option < 0 or option >= line.options().size():
            return
    problem = SwordieCoinShop.optionProblem(chr, line, option)
    if problem is not None:
        sm.sendSayOkay(problem)
        return
    what = line.optionNames()[option] if line.options().size() > 1 else line.name()
    ask = "Buy #b{}#k for #r{}#k Swordie Coins?{}".format(what, line.price(), cap_text(line))
    if line.note() is not None:
        ask += "\r\n\r\n#d{}#k".format(line.note())
    if not sm.sendAskYesNo(ask):
        return
    why = SwordieCoinShop.buy(chr, line.id(), option)
    if why is None:
        sm.sendSayOkay("Thank you! You have #b{}#k Swordie Coins left.".format(SwordieCoinShop.coins(chr)))
    else:
        sm.sendSayOkay(why)


def section_page(section_id, title):
    while True:
        lines = SwordieCoinShop.lines(section_id)
        text = header() + "#e{}#n\r\n".format(title)
        for i in range(lines.size()):
            l = lines[i]
            text += "\r\n#L{}#{}#b{}#k - #r{}#k{}#l".format(i, icon(l), l.name(), l.price(), cap_text(l))
        sel = sm.sendNext(text + "\r\n" + back_line())
        if 0 <= sel < lines.size():
            buy_page(lines[sel])
        else:
            return


sm.setSpeakerID(SwordieCoinShop.SPEAKER)
if not SwordieCoinShop.isOpen():
    sm.sendSayOkay("The Swordie Coin Shop is closed right now. Your coins are kept.")
else:
    sections = SwordieCoinShop.SECTIONS
    while True:
        text = header() + "\r\nCoins come from the first weekly clear of each boss, level 200, 250, 260 and 270, " \
                          "Maple Achievements, Daily Gift days 1 and 15, Polo's and Fritto's bounties and rebirths.\r\n"
        for i in range(len(sections)):
            text += "\r\n#L{}##b{}#k#l".format(i, sections[i][1])
        sel = sm.sendNext(text + "\r\n" + back_line())
        if 0 <= sel < len(sections):
            section_page(sections[sel][0], sections[sel][1])
        else:
            break
sm.dispose()
