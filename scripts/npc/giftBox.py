# @gifts - the gift box. The v265 client has no gift notifier window, so gifts are listed and claimed here.
from net.swordie.ms.client.character import FirstEnterReward

CLAIM_ALL = 1


def gifts():
    return sorted(list(chr.getFirstEnterRewards()), key=lambda g: g.getId())


def gift_line(g):
    line = g.getDialogText()
    desc = g.getDescription()
    if desc:
        line += "  #d(" + desc + ")#k"
    return line


def claim_all():
    claimed = 0
    problems = []
    for g in gifts():
        msg = FirstEnterReward.claim(chr, g.getId())
        if msg is None:
            claimed += 1
        elif msg not in problems:
            problems.append(msg)
    text = "You claimed #b{}#k gift(s).".format(claimed)
    if problems:
        text += "\r\n\r\n" + "\r\n".join(problems)
    sm.sendSayOkay(text)


while True:
    waiting = gifts()
    if not waiting:
        sm.sendSayOkay("Your gift box is empty.")
        break
    text = "#e#bGift box#k#n  ({} waiting)\r\n\r\n".format(len(waiting))
    text += "#L{}##bClaim all#k#l\r\n\r\n".format(CLAIM_ALL)
    for g in waiting:
        text += "#L{}#{}#l\r\n".format(int(g.getId()) + 10, gift_line(g))
    sel = sm.sendNext(text)
    if sel == CLAIM_ALL:
        claim_all()
        break
    msg = FirstEnterReward.claim(chr, sel - 10)
    if msg is not None:
        sm.sendSayOkay(msg)
        break
