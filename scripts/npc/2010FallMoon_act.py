# Smartie Moon Bunny (9001104): hosts the Moon Festival in the major towns (HolidayEvents.MOON, Sep 8 - Oct 4). The
# festival's quests (Moonlight Sonata, Gaga's Moonlight Bottle, the login titles, the Full Moon coupons) open first
# through holidayNpc.py when there's one to start or hand in; this is the rest: the Lunar World, Time To Make a Wish
# (Wish Papers from monsters near your level, 30 = the wish), and the festival's guide.
from net.swordie.ms.world import HolidayEvents as H
from net.swordie.ms.world import SeasonalEvents

NPC = 9001104
sm.setSpeakerID(NPC)

# (quest, what you wish for, the scrolls to pick from for the 'pick one' wishes)
WISHES = [
    (11681, "a #bGolden Hammer 50%#k", []),
    (11682, "a #bSpecial Potential Scroll#k", []),
    (11683, "a #bClean Slate Scroll 10%#k", []),
    (11684, "an #bAdvanced Equip Enhancement Scroll#k", []),
    (11685, "an #bArmor ATT / Magic ATT scroll#k", [2046227, 2046228]),
    (11686, "a #bWeapon ATT scroll#k", [2046014, 2046015, 2046113]),
    (11687, "an #bArmor stat scroll#k", [2046229, 2046230, 2046231, 2046232]),
    (11688, "a #bMochi Ice Cream Chair#k", []),
]


def paper(q):
    return H.WISH_PAPER_FIRST + q - H.WISH_FIRST


def wishes_on():
    return [w for w in WISHES if sm.hasQuest(w[0])]


def make_wish():
    on = wishes_on()
    if on:
        q, what, picks = on[0]
        sm.sendSayOkay("You've already made a wish today: %s. Collect #b30 #t%d##k from monsters near your level and "
                       "bring them to me! (#b%d / 30#k)" % (what, paper(q), min(30, sm.getQuantityOfItem(paper(q)))))
        return
    qm = chr.getQuestManager()
    text = "What do you wish for under the full moon? Each wish has its own #bWish Paper#k: collect #b30#k of them " \
           "from monsters near your level and the wish comes true. (One wish of each kind a day.)\r\n"
    n = 0
    for i, (q, what, picks) in enumerate(WISHES):
        if qm.canStartQuestQuietly(q):
            text += "\r\n#L%d#I wish for %s.#l" % (i, what)
            n += 1
    if n == 0:
        sm.sendSayOkay("You've made every wish there is today! Come back tomorrow, and the moon will listen again.")
        return
    sel = sm.sendNext(text)
    if sel < 0 or sel >= len(WISHES):
        return
    q, what, picks = WISHES[sel]
    sm.startQuestNoCheck(q)
    sm.sendSayOkay("The moon heard you! Now hunt monsters near your level for #b30 #t%d##k (#i%d#). Bring them to "
                   "me, or to Grandpa Moon Bunny in the Lunar World." % (paper(q), paper(q)))


def hand_in():
    for q, what, picks in wishes_on():
        have = sm.getQuantityOfItem(paper(q))
        if have < H.WISH_PAPERS:
            sm.sendSayOkay("Your wish for %s needs #b30 #t%d##k. You have #b%d#k. Monsters near your level have them!"
                           % (what, paper(q), have))
            return
        sel = -1
        if picks:
            text = "Thirty Wish Papers! Your wish comes true. Which one would you like?\r\n"
            for i, item in enumerate(picks):
                text += "\r\n#L%d##i%d# #t%d##l" % (i, item, item)
            sel = sm.sendNext(text)
            if sel < 0 or sel >= len(picks):
                sel = 0
        if H.completeWish(chr, q, sel):
            sm.sendSayOkay("Happy Moon Festival! May all your wishes come true, today and every day.")
        else:
            sm.sendSayOkay("Hmm, your inventory looks full. Make some room and come back!")
        return
    sm.sendSayOkay("You haven't made a wish yet today. Want to make one?")


def lunar_world():
    if sm.sendAskYesNo("Grandpa Moon Bunny and Baby Moon Bunny live up in the #bLunar World#k. Shall I send you to "
                       "the moon? (The rabbit hole out of there brings you back to this town.)"):
        H.rememberTown(chr)
        sm.warp(H.LUNAR_WORLD, 0)


if not SeasonalEvents.isActive(H.MOON):
    sm.sendSayOkay("The moon is shy tonight... Come back during the #bMoon Festival#k in September!")
elif chr.getLevel() < 10:
    sm.sendSayOkay("Happy Moon Festival! Come back when you're #bLv. 10#k, and I'll have a wish for you.")
else:
    if H.moonGratitudeDue(chr):
        H.giveMoonGratitude(chr)
    text = "Happy Moon Festival! The moon is full and round, and it's in a wish-granting mood!\r\n"
    text += "\r\n#L0##bTake me to the Lunar World.#k#l"
    if wishes_on():
        text += "\r\n#L2##bMy Wish Papers are ready!#k#l"
    else:
        text += "\r\n#L1##bI want to make a wish.#k#l"
    text += "\r\n#L3#What happens during the Moon Festival?#l"
    sel = sm.sendNext(text)
    if sel == 0:
        lunar_world()
    elif sel == 1:
        make_wish()
    elif sel == 2:
        hand_in()
    elif sel == 3:
        sm.sendNext("Grandpa Moon Bunny wants to hear the lost #bMoonlight Sonata#k one more time, for his grandson. "
                    "Talk to me and I'll pass his request along! It's a long trip: Cassandra, the fairies of Ellinia, "
                    "and Gold Richie in Lith Harbor all know a piece of the story.")
        sm.sendNext("Make a #bwish#k once a day: collect 30 of its Wish Papers from monsters near your level and it "
                    "comes true. #bGaga#k's moonlight business needs a bottle carried for 30 minutes with the lid "
                    "open... I hand those out for him too.")
        sm.sendSayOkay("Log in on #b1, 3 and 7 days#k of the festival for a title each, and around the full moon "
                       "(#bSep 10 - 16#k) I have two 2x EXP Coupons for you every day!")
sm.dispose()
