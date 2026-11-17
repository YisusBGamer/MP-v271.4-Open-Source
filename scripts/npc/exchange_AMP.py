# Kritias anti-magic: feed it to a worn Inverse Codex (its EXP, InverseCodex), or exchange 700 for an Anheim Coin and
# an Anti-magic Stone plus 1,200 for a Kritias Commemorative Coin. Anti-magic comes from Anti-magic Stone Pieces
# (quest record 500784).
from net.swordie.ms.client.character.items import InverseCodex

ANTI_MAGIC_QR = 500784
ANHEIM_COIN = 4310091
KRITIAS_COIN = 4310092
ANTI_MAGIC_STONE = 4009005

def anti_magic():
    cur = sm.getQRValue(ANTI_MAGIC_QR)
    return int(cur) if cur and cur.isdigit() else 0

sel = sm.sendNext("Hello! What can I do for you? (You have #b%d anti-magic#k.)\r\n\r\n#b#L0#I want to put anti-magic into Inverse Codex.#l\r\n#L1#Exchange 700 anti-magic for an Anheim Coin.#l\r\n#L2#Exchange 1 Anti-magic Stone and 1,200 anti-magic for a Kritias Commemorative Coin.#l\r\n#L3#Buy an Inverse Codex (100 Kritias Commemorative Coins).#l" % anti_magic())

if sel == 0:
    have = anti_magic()
    if have <= 0:
        sm.sendSayOkay("You don't have any anti-magic to put in.")
    else:
        codex = InverseCodex.feed(chr, have)
        if codex is None:
            sm.sendSayOkay("Eh? I don't think you have the Inverse Codex equipped.\r\nPlease check again as you can only put in anti-magic while you're equipping it.")
        else:
            sm.setQRValue(ANTI_MAGIC_QR, "0")
            if codex.level >= InverseCodex.MAX_LEVEL:
                sm.sendSayOkay("I put #b%d anti-magic#k into your Inverse Codex. It's at its highest level, #b%d#k." % (have, codex.level))
            else:
                sm.sendSayOkay("I put #b%d anti-magic#k into your Inverse Codex. It's now level #b%d#k; #b%d#k more anti-magic for the next level. It loses some every day, so keep feeding it." % (have, codex.level, codex.needed()))
elif sel == 1:
    if anti_magic() < 700:
        sm.sendSayOkay("You need #b700 anti-magic#k for an Anheim Coin.")
    elif not sm.canHold(ANHEIM_COIN):
        sm.sendSayOkay("Please make room in your Etc inventory.")
    else:
        sm.setQRValue(ANTI_MAGIC_QR, str(anti_magic() - 700))
        sm.giveItem(ANHEIM_COIN)
elif sel == 2:
    if anti_magic() < 1200 or not sm.hasItem(ANTI_MAGIC_STONE):
        sm.sendSayOkay("You need #b1 Anti-magic Stone and 1,200 anti-magic#k for a Kritias Commemorative Coin.")
    elif not sm.canHold(KRITIAS_COIN):
        sm.sendSayOkay("Please make room in your Etc inventory.")
    else:
        sm.consumeItem(ANTI_MAGIC_STONE)
        sm.setQRValue(ANTI_MAGIC_QR, str(anti_magic() - 1200))
        sm.giveItem(KRITIAS_COIN)
elif sel == 3:
    if not sm.hasItem(KRITIAS_COIN, 100):
        sm.sendSayOkay("The Inverse Codex costs #b100 Kritias Commemorative Coins#k.")
    elif not sm.canHold(InverseCodex.ITEM):
        sm.sendSayOkay("Please make room in your Equip inventory.")
    else:
        sm.consumeItem(KRITIAS_COIN, 100)
        sm.giveItem(InverseCodex.ITEM)
        sm.sendSayOkay("Here's your #t1162018#. Wear it in your Pocket slot and bring me anti-magic to make it grow.")
