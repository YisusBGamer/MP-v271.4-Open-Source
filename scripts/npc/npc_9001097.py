# Desert Fox (9001097): the best porter in Nihal Desert, if you can pay.
from net.swordie.ms.client.character import NihalTradeModule

FOX = NihalTradeModule.FOX_WORKER
if not NihalTradeModule.inRun(chr):
    sm.sendSayOkay("The little fox eyes you curiously.")
elif NihalTradeModule.currentWorker(chr) == FOX:
    sm.sendSayOkay("The Desert Fox happily carries your load.")
elif sm.sendAskYesNo("This little critter can carry loads many times his own weight (+%d goods). Hire him for #b%d Gold Coins#k?"
                     % (NihalTradeModule.workerWeight(FOX), NihalTradeModule.workerFee(FOX))):
    sm.sendSayOkay(NihalTradeModule.hire(chr, FOX))
sm.dispose()
