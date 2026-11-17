# The guild skills On My Way / I Summon Thee, when the guildmate wasn't picked in the request (GuildModule).
from net.swordie.ms.handlers.social.module import GuildModule as GM

# only GuildModule starts this, with the skill; started bare (an NPC click) it just closes
try:
    guildSkill
    bound = True
except NameError:
    bound = False
if bound:
    targets = GM.getMoveTargets(chr)
    going = guildSkill == GM.ON_MY_WAY
    if targets.isEmpty():
        sm.sendSayOkay("None of your guildmates are online in this channel.")
    else:
        text = ("Go to which guildmate?" if going else "Summon which guildmate?") + "\r\n"
        for i in range(targets.size()):
            t = targets.get(i)
            text += "#b#L%d#%s#l#k (#m%d#)\r\n" % (i, t.getName(), t.getFieldID())
        sel = sm.sendNext(text)
        if 0 <= sel < targets.size():
            error = GM.useMoveSkill(chr, guildSkill, targets.get(sel).getId())
            if error is not None:
                sm.sendSayOkay(error)
else:
    sm.dispose()
