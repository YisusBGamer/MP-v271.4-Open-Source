# Fantasy Theme World (Dylan & Delilah, 31363-31392). Official lines where the client has them; otherwise written from
# the quests' Quest.wz summaries / the NPCs' Npc.wz lines. Data: ftwgen/gen_ftw.py.
# Fantasy Crittertorium <Venue> (222130210): Targa and Scarboss are beaten - 31382's record (mobkill=1), which the
# Caped Mascot inside checks ("You defeated Targa and Scarboss.").
if sm.hasQuest(31382):
    sm.setQRValue(31382, "mobkill", "1")
    sm.chatScript("You defeated Targa and Scarboss. Talk to the caped mascot.")
else:
    sm.chatScript("What a show! Take the exit when you're ready.")
