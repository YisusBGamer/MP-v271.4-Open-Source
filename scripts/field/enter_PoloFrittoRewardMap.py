from net.swordie.ms.client.minigames import EagleHunt
from net.swordie.ms.client.surprisemission import SurpriseMissionCompleter
if chr is not None:
    SurpriseMissionCompleter.completeHelpPolloAndFritto(chr)
EagleHunt.finish(sm.getChr())  # takes the Eagle Hunt gun back, pays its score
