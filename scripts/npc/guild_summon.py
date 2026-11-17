# The guild skill I Summon Thee, asked of the guildmate (GuildModule.useMoveSkill).
from net.swordie.ms.handlers.social.module import GuildModule as GM

# only GuildModule starts this, with the summoner; started bare (an NPC click) it just closes
try:
    summonerName
    bound = True
except NameError:
    bound = False
if bound:
    if sm.sendAskYesNo("Your guildmate #b%s#k wants to summon you to #b#m%d##k. Go?" % (summonerName, summonField)):
        error = GM.acceptSummon(chr, summonerId, summonField)
        if error is not None:
            sm.sendSayOkay(error)
else:
    sm.dispose()
