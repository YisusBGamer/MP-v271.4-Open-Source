# Rooney (major towns, while Christmas is on): Happyville, the story (Christmas in Happyville - the Missing Santa story,
# see ChristmasEvent)
NPC = 1022101
from net.swordie.ms.world import ChristmasEvent as X

ACTIVE = X.isActive()


def can_start(q, *prev):
    # the event is on, the level is right, the quests before it are done, and it isn't started yet
    if not ACTIVE or chr.getLevel() < X.MIN_LEVEL or sm.hasQuest(q) or sm.hasQuestCompleted(q):
        return False
    for p in prev:
        if not sm.hasQuestCompleted(p):
            return False
    return True


def finish(q):
    return X.finish(chr, q)


def default():
    line = sm.getRandomNpcDefaultLine(NPC)
    sm.sendSayOkay(line if line is not None else "Merry Christmas!")

sm.setSpeakerID(NPC)


def start_encyclopedia():
    sm.sendNext('Hmm, you came here to find out about the article on Santa Claus just like the other reporters.\r\nI read the newspaper this morning, too. I heard that Santa Claus was very disappointed at the fact that many Maplers do not believe in his existence anymore, and that it made him call a strike this year!')
    sm.sendNext("Well, Maplers believed in Santa Claus at first. There used to be a book known as the #bSanta Encyclopedia#k in the library of Happyville. Maplers began to believe in the existence of Santa Claus after reading this book. However, monsters with huge appetites literally devoured them all and they're all gone now.")
    if sm.sendAskAccept('If we can bring that book to life again, Maplers will begin returning to their belief in Santa Claus! Can you help Maplers reshape their faith in Santa Claus?'):
        sm.startQuestNoCheck(X.ENCYCLOPEDIA)
        sm.sendSayOkay('Yes! I knew you would do it! Please find all #bSanta Encyclopedia#k from #bNo.1 to No.6#k! If you can find that, we can make a complete set of Santa Encyclopedia. Oh, almost forgot to mention. After you collected the first 5 books, you should talk to Mrs. Claus in Happyville.')
    else:
        sm.sendSayOkay("You don't care whether Santa Claus comes to our town or not?")


def has_all_volumes():
    for v in X.ALL_VOLUMES:
        if not sm.hasItem(v):
            return False
    return True


def go_happyville():
    X.rememberTown(chr)
    sm.warp(X.HAPPYVILLE)


if not ACTIVE:
    sm.sendSayOkay("Happy Holidays! Happyville only opens its gates at Christmas time.")
elif sm.hasQuest(X.NEWS):
    if sm.sendAskYesNo('Helloooo! Are you here for to wish me Merry Christmas?'):
        sm.sendNext('Merry Christmas!')
        if finish(X.NEWS):
            start_encyclopedia()
    else:
        sm.sendSayOkay('At Maple Herald, we always do our best to deliver news as accurately and quickly as possible. If you are curious about the happenings in Maple World today, please stop by!')
elif sm.hasQuest(X.ENCYCLOPEDIA) and has_all_volumes():
    if sm.sendAskYesNo('Did you collect all 6 Santa Encyclopedia?'):
        if finish(X.ENCYCLOPEDIA):
            sm.sendNext('Excellent! How brilliant of you to get all these Santa Encyclopedias!\r\nWhat? You met with Mrs. Claus making the socks? Wow. There were so many good things happened while you were finding this complete set of Encyclopedia. Now you too believe in Santa Claus right?\r\nThanks to you, Maplers will start to believe in Santa Claus again.')
            sm.sendSayOkay("Santa Claus will surely come to Maple World this year. Thank you, and Merry Christmas! "
                           "(Santa in #bSanta's House#k and the #bSnow Blower#k still have a sock for you every day.)")
elif can_start(X.ENCYCLOPEDIA, X.NEWS):
    start_encyclopedia()
else:
    text = "Did you hear about a town near Ellinia that's covered in snow? That's #bHappyville#k!\r\n#b"
    text += "#L0#Take me to Happyville.#l\r\n"
    if chr.getLevel() >= X.MIN_LEVEL:
        text += "#L1#Where am I in the Missing Santa story?#l\r\n"
    text += "#L2#Nothing, thanks.#l"
    sel = sm.sendNext(text)
    if sel == 0:
        go_happyville()
    elif sel == 1:
        g = X.guide(chr)
        if g.map() != 0 and g.map() != sm.getFieldID():
            if sm.sendAskYesNo(g.text() + "\r\n\r\nShall I take you to Happyville?"):
                go_happyville()
        else:
            sm.sendSayOkay(g.text())

sm.dispose()
