# Spiegelette (9001071), Nihal Desert Train Attendant.
from net.swordie.ms.client.character import NihalTradeModule

if NihalTradeModule.inRun(chr):
    sm.sendSayOkay("How's your Nihal Desert expedition? You have #b%d Gold Coins#k." % NihalTradeModule.getGold(chr))
else:
    sm.sendSayOkay("Straight from freezing to sweltering. Ugh. Spiegelmann starts the trade runs, talk to him!")
sm.dispose()
