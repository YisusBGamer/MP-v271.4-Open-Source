# Lawry - Rebirth (also opened with @rebirth). The rebirth itself is done by RebirthModule (sm.rebirth()).
# Equipment that can't be worn at the new level (10) is taken off for the player (after they confirm, and only if all of it fits
# in the Equip inventory). The first rebirth also comes with a gift of level 1 equipment (gift box).
from net.swordie.ms.constants import GameConstants

sm.setSpeakerID(3004604)

if GameConstants.REBIRTH_RESET_STATS:
    statsLine = "- Your stats and AP are reset to a new character's.\r\n"
else:
    statsLine = "- You keep your stats (STR, DEX, INT, LUK, max HP/MP) and unspent AP.\r\n"
count = sm.getRebirthCount()
blocker = sm.getRebirthBlockerIgnoringEquips()

intro = "I can give you a second life... a #brebirth#k."
if count > 0:
    intro += " You have been reborn #b" + str(count) + "#k time(s) already."

if blocker is not None:
    sm.sendSayOkay(intro + "\r\n\r\n#r" + blocker + "#k")
    sm.dispose()

giftLine = ""
if count == 0:
    giftLine = "- For your first rebirth you get a #bgift#k of level 1 equipment any class can wear (sword, top, " \
               "bottom, shoes and cape), waiting in your gift box.\r\n"

sm.sendNext(intro + "\r\n\r\nWhen you are reborn:\r\n"
            "#b- You go back to level " + str(GameConstants.JOB_PATH_MIN_LEVEL) + " as a Beginner, in Henesys.\r\n"
            + statsLine +
            "- Your skills keep their levels. Unspent SP is reset.\r\n"
            "- Your V Matrix cores are unequipped (you keep them).\r\n"
            "- You get bonus AP for every rebirth you have done.\r\n"
            + giftLine +
            "- Right away you choose your job path again with #e@job#n "
            "(any class, your previous one is offered first), then advance automatically.#k")

# equipment that can't be worn at the new level has to come off first
equips = sm.getRebirthBlockingEquipNames()
if len(equips) > 0:
    shown = list(equips)[:10]
    text = "You can't wear these at level " + str(GameConstants.JOB_PATH_MIN_LEVEL) + ", so they have to come off first:\r\n#b"
    for name in shown:
        text += "- " + name + "\r\n"
    if len(equips) > len(shown):
        text += "...and " + str(len(equips) - len(shown)) + " more.\r\n"
    text += "#k\r\nShall I unequip them for you? They'll go into your #bEquip#k inventory (" + str(len(equips)) + \
            " free slots needed)."
    if not sm.sendAskYesNo(text):
        sm.sendSayOkay("Come back when you're ready.")
        sm.dispose()
    problem = sm.unequipForRebirth()
    if problem is not None:
        sm.sendSayOkay("#r" + problem + "#k\r\n\r\nNothing was unequipped. Make some room and talk to me again.")
        sm.dispose()
    sm.sendNext("Done. Your equipment is in your Equip inventory.")

if sm.sendAskYesNo("Do you want to be reborn? #rThis can't be undone.#k"):
    if sm.sendAskYesNo("Are you #rcompletely sure#k? You will go back to #rlevel " + str(GameConstants.JOB_PATH_MIN_LEVEL) + "#k."):
        if sm.rebirth() and count == 0:
            sm.sendSayOkay("Welcome to your new life! Your #bfirst rebirth gift#k is waiting in your gift box.")
