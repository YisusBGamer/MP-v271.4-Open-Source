# Spiegelmann (9001069), Adventure Express: starts a Nihal Desert trade run.
from net.swordie.ms.client.character import NihalTradeModule

if NihalTradeModule.inRun(chr):
    sm.sendSayOkay("Your trade run is under way! Head into the Trade Zone and make your fortune. "
                   "You have #b%d Gold Coins#k." % NihalTradeModule.getGold(chr))
else:
    sel = sm.sendNext("Ho ho! Welcome aboard the Nihal Desert Adventure Express!\r\n"
                      "#L0##bStart a trade run#k#l\r\n#L1#How does trading work?#l\r\n#L2#Leave the express#l")
    if sel == 0:
        NihalTradeModule.startRun(chr)
        sm.sendSayOkay("Here are your starting #bGold Coins#k. Buy goods from a merchant, carry them to a merchant who "
                       "wants them, and sell for more. You have #b20 minutes#k before the express leaves. Hire a porter from "
                       "Hatsar here if you want to carry more, then step off the train into the Trade Zone!")
    elif sel == 1:
        sm.sendSayOkay("Every registered merchant sells some goods and buys others; the goods' descriptions say who. "
                       "Buy low, sell high in another market. Masarayu buys anything, but cheaply, and Moon Bunny "
                       "sells a few goods at a discount. Hatsar rents out porters so you can carry more. Watch out for "
                       "Desert Thieves on the desert roads! When time is up, I turn your profit into EXP.")
    elif sel == 2:
        sm.warp(NihalTradeModule.returnField(chr))
sm.dispose()
