# Wealthy Merchant Hatsar (9001089): porters for hire, so you can carry more goods.
from net.swordie.ms.client.character import NihalTradeModule

NAMES = ["Scrawny Porter", "Dune Muscleman", "Oasis Dancer", "Sand Warrior"]
if not NihalTradeModule.inRun(chr):
    sm.sendSayOkay("Come to me if you need porters. Not for free, of course. Start a trade run with Spiegelmann first.")
else:
    text = "Porters, for a price. Who will you hire?"
    for i, name in enumerate(NAMES):
        text += "\r\n#L%d##b%s#k (carries +%d, %d Gold Coins)%s#l" % (i, name, NihalTradeModule.workerWeight(i),
                NihalTradeModule.workerFee(i), " - keeps thieves away" if i == NihalTradeModule.SAND_WARRIOR else "")
    sel = sm.sendNext(text)
    if 0 <= sel < len(NAMES):
        sm.sendSayOkay(NihalTradeModule.hire(chr, sel))
sm.dispose()
