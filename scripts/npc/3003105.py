# Kanto | Lachelein Phantasma Coin exchange

ARCANE_RIVER_DROPLET = 4001878
BUTTERFLY_DROPLET = 4001879
PHANTASMA_COIN = 4310218
ARCANE_DROPLETS_PER_COIN = 9
MAX_BATCH = 100

arcane_droplets = sm.getQuantityOfItem(ARCANE_RIVER_DROPLET)
butterfly_droplets = sm.getQuantityOfItem(BUTTERFLY_DROPLET)
max_exchange = min(arcane_droplets // ARCANE_DROPLETS_PER_COIN, butterfly_droplets, MAX_BATCH)

if max_exchange < 1:
    sm.sendSayOkay("Bring me 9 #z{}#s and 1 #z{}#s for each #z{}#s you want.".format(
        ARCANE_RIVER_DROPLET, BUTTERFLY_DROPLET, PHANTASMA_COIN))
else:
    quantity = sm.sendAskNumber(
        "I can exchange 9 #z{}#s and 1 #z{}#s for 1 #z{}#s.\r\n"
        "How many coins would you like? (Up to {} per exchange.)".format(
            ARCANE_RIVER_DROPLET, BUTTERFLY_DROPLET, PHANTASMA_COIN, max_exchange),
        1, 1, max_exchange)

    if quantity > max_exchange:
        sm.sendSayOkay("Please choose no more than {} coins.".format(max_exchange))
    elif quantity > 0:
        arcane_cost = quantity * ARCANE_DROPLETS_PER_COIN
        if not sm.hasItem(ARCANE_RIVER_DROPLET, arcane_cost) or not sm.hasItem(BUTTERFLY_DROPLET, quantity):
            sm.sendSayOkay("You no longer have enough droplets for that exchange.")
        elif not sm.canHold(PHANTASMA_COIN, quantity):
            sm.sendSayOkay("Please make room in your Etc. inventory.")
        else:
            sm.consumeItem(ARCANE_RIVER_DROPLET, arcane_cost)
            sm.consumeItem(BUTTERFLY_DROPLET, quantity)
            sm.giveItem(PHANTASMA_COIN, quantity)
            sm.sendSayOkay("You received {} #z{}#s.".format(quantity, PHANTASMA_COIN))
