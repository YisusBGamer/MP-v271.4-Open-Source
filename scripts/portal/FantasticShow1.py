# Fantasy Theme World (Dylan & Delilah, 31363-31392). Official lines where the client has them; otherwise written from
# the quests' Quest.wz summaries / the NPCs' Npc.wz lines. Data: ftwgen/gen_ftw.py.
# Fantasy Crittertorium <Entrance> (222130200), portal showEnter: the Crittertorium venue (your own copy). For
# 'Restricted Area, Crittertorium' (31382) Targa and Scarboss (8145100, 8145200) wait inside; otherwise the venue is
# the Fantastic Animal Show's (npc/FantasticShow).
VENUE = 222130210
SHOW_QUEST = 31382


def quest_fight():
    return sm.hasQuest(SHOW_QUEST) and sm.getQRValue(SHOW_QUEST, "mobkill") != "1"


def start_show(targa, scarboss):
    # the Crittertorium's stars, with the client's own Targa picture
    sm.warpInstanceIn(VENUE, 0)
    sm.showFieldEffect("fantasticPark/targa", 0)
    sm.spawnMob(targa, -200, 150, False)
    sm.spawnMob(scarboss, 200, 150, False)

if quest_fight():
    start_show(8145100, 8145200)
else:
    sm.chatScript("RESTRICTED AREA. (The Fantastic Animal Show sign next to it tells you about the show.)")
sm.dispose()
