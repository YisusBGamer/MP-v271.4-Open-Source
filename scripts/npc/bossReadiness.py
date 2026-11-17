# @bosses - which bosses you can expect to beat with your current damage range. See BossReadinessModule.
from net.swordie.ms.client.character import BossReadinessModule as BR

bosses = BR.getBosses()
rng = BR.getRangeMax(chr)
base = BR.getBaseDps(chr)
has_reason = hasattr(BR, "getReason")

ready = []
possible = []
notyet = []
for boss in bosses:
    verdict = BR.getVerdict(chr, boss, base)
    if verdict == 2:
        ready.append(boss)
    elif verdict == 1:
        possible.append(boss)
    else:
        notyet.append(boss)


def line(boss, color, why=False):
    secs = BR.getKillSeconds(chr, boss, base)
    entry = boss.getEntryLevel()
    text = "{}{} ({})#k  Lv. {}+, {} HP, {} min - about #b{}#k\r\n".format(
        color, boss.getName(), boss.getDifficulty(), entry, BR.formatNumber(boss.getHp()),
        boss.getTimeLimitSeconds() / 60, BR.formatSeconds(secs))
    if why:
        reason = BR.getReason(chr, boss, base) if has_reason else (
            "needs Lv. {}".format(entry) if chr.getLevel() < entry else "")
        if reason:
            text += "    #d{}#k\r\n".format(reason)
    return text


measured = BR.getMeasuredDps(chr)
text = "#e#bBoss Check#k#n\r\n"
if measured > 0:
    text += "Measured from your last boss fights (#b{}s#k of fighting): #b{}#k damage per second before boss " \
            "defense.\r\n".format(BR.getMeasuredSeconds(chr), BR.formatNumber(measured))
else:
    text += "#rNot measured yet#k: this is a rough guess from your damage range (#b{}#k). Fight a boss or the " \
            "training dummy for 30 seconds or more, then check again for real numbers.\r\n".format(
                BR.formatNumber(rng))
text += "Estimated solo kill times, against each boss's own time limit:\r\n\r\n"

if ready:
    text += "#e#gYou should beat these#k#n\r\n"
    for boss in ready:
        text += line(boss, "#g")
    text += "\r\n"
if possible:
    text += "#e#bPossible, but close to the time limit#k#n\r\n"
    for boss in possible:
        text += line(boss, "#b")
    text += "\r\n"
if notyet:
    text += "#e#rNot yet#k#n (showing the next 5, and why)\r\n"
    for boss in notyet[:5]:
        text += line(boss, "#r", True)
    text += "\r\n"

text += "#dEstimates for a solo fight, allowing for time spent dodging and between phases, from your damage, " \
        "each boss's defense (your Ignore DEF), level and Arcane or Sacred Force. Your measurement keeps up with your last " \
        "{} boss fights: buff up and use your full rotation for the best numbers. Deaths make kills slower.#k".format(
            BR.DpsTracker.MAX_FIGHTS)
sm.sendSayOkay(text)
