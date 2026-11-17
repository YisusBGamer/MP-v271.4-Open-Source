# Spiegelmann's Adventure Express (Nihal Desert): talk to Spiegelmann to start trading.
from net.swordie.ms.client.character import NihalTradeModule

if not NihalTradeModule.inRun(chr):
    sm.chatScript("All aboard! Talk to Spiegelmann to start your Nihal Desert trade run.")
