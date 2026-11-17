from net.swordie.ms.client.character import SwordieCoinRewards
from net.swordie.ms.constants import GameConstants

value = chr.getPolloFritto()  # portals succeeded
amount = GameConstants.randomPortalFrittoCoinFormula(value)  # coins obtained based on the formula

sm.sendNext("Great job, that was a lot of fun!")
sm.sendSayOkay("I will send you back to your previous location.")
chr.resetPolloFritto()
# nothing while Swordie Coins are off; a full Etc inventory sends them to the gift box
SwordieCoinRewards.giveBountyCoins(chr, amount)
sm.warp(chr.getPreviousFieldID())
