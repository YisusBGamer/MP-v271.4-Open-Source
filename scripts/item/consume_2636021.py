# Sacred Symbol Selector Coupon: a Sacred Symbol of an area whose story the character has finished (not Grand ones)
AREAS = [
    (1713000, 39818),  # Cernium
    (1713001, 38121),  # Hotel Arcus
    (1713002, 38320),  # Odium
    (1713003, 38405),  # Shangri-La
    (1713004, 38654),  # Arteria
    (1713005, 38703),  # Carcion
]

sm.setSpeakerID(9010000)
qm = chr.getQuestManager()
done = [symbol for symbol, quest in AREAS if qm.hasQuestCompleted(quest)]
if not done:
    sm.sendSayOkay("You haven't finished the story of any area with a Sacred Symbol yet.")
else:
    text = "Which Sacred Symbol would you like?\r\n"
    for i, symbol in enumerate(done):
        text += "#L%d##v%d# #t%d##l\r\n" % (i, symbol, symbol)
    selection = sm.sendNext(text)
    if 0 <= selection < len(done):
        symbol = done[selection]
        if not sm.canHold(symbol):
            sm.sendSayOkay("Please make room in your Equip inventory first.")
        else:
            sm.consumeItem(parentID)
            sm.giveItem(symbol)
