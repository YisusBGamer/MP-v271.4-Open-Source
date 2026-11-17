# Cliff (2230100), merchant at the Kritias Marketplace
# [Kritias] Serving Boy (32506) sends you to buy a Fancy Handkerchief from Cliff, so he sells it. The client data has
# no price for it; 5,000 mesos is this server's. Otherwise Cliff says his own client lines.
CLIFF = 2230100
HANDKERCHIEF = 4033937  # Fancy Handkerchief
PRICE = 5000

sm.setSpeakerID(CLIFF)
if sm.hasQuest(32506) and not sm.hasItem(HANDKERCHIEF):
    if sm.sendAskYesNo("A #bFancy Handkerchief#k? Pst! Keep your voice down, Nita might hear... "
                       "It's yours for #b5,000 mesos#k. Deal?"):
        if sm.getMesos() < PRICE:
            sm.sendSayOkay("Sorry, friend. No mesos, no handkerchief.")
        elif not sm.canHold(HANDKERCHIEF):
            sm.sendSayOkay("Make some room in your Etc inventory first.")
        else:
            sm.deductMesos(PRICE)
            sm.giveItem(HANDKERCHIEF)
            sm.sendSayOkay("Pleasure doing business! Finest handkerchief in Kritias, that one.")
else:
    line = sm.getRandomNpcDefaultLine(CLIFF)
    if line is not None:
        sm.sendSayOkay(line)
sm.dispose()
