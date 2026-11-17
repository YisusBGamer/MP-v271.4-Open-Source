# Sir Hamselot (3006972), Draco Isle: Exit (993265600, 993265800): back to where you came from
from net.swordie.ms.client.character.union import ChampionRaid

sm.setSpeakerID(3006972)
if sm.sendAskYesNo("Well fought, Champion! You have #b%d Champion Coins#k this month. Shall I take you back?"
                   % ChampionRaid.getCoins(chr.getAccount())):
    ChampionRaid.leaveCabin(chr)
sm.dispose()
