from net.swordie.ms.client.character import SwordieCoinRewards
from net.swordie.ms.constants import GameConstants

dmg = chr.getPolloFritto()  # damage done
amount = GameConstants.randomPortalInfernoWolfCoinFormula(dmg)  # coins obtained based on the formula

sm.sendNext("Nice work out there, soldier!")
sm.sendSayOkay("I will send you back.")
chr.resetPolloFritto()
# nothing while Swordie Coins are off; a full Etc inventory sends them to the gift box
SwordieCoinRewards.giveBountyCoins(chr, amount)
sm.warp(chr.getPreviousFieldID())
