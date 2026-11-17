# Sir Hamselot (3006971), Wanderer's Cabin (993265400): into the Champion Raid on Draco Isle
from net.swordie.ms.client.character.union import ChampionRaid

sm.setSpeakerID(3006971)
choice = sm.sendNext("Draco Isle awaits, Champion! Your Champions fight as one squad: switch between them with "
                     "#bTab#k and the #bnumber keys#k. Each Champion can fight for #b%d seconds#k; the raid ends "
                     "when all of them are spent or the sixth stage falls.\r\n"
                     "#b#L0#Enter the Champion Raid#l\r\n"
                     "#L1#How do Champion Coins work?#l" % (ChampionRaid.PLAY_MS / 1000))
if choice == 0:
    block = ChampionRaid.getEntryBlock(chr)
    if block is not None:
        sm.sendSayOkay(block)
    elif sm.sendAskYesNo("All buffs are removed when you enter. You get #b5 minutes#k in the staging grounds to "
                         "get ready. Enter now?"):
        ChampionRaid.enter(chr)
elif choice == 1:
    sm.sendSayOkay("Every mission you clear raises your #bscore#k. When the raid ends, beating this month's best "
                   "score earns the difference in #bChampion Coins#k (you have #b%d#k). Scores and coins reset on "
                   "the 1st of each month." % ChampionRaid.getCoins(chr.getAccount()))
sm.dispose()
