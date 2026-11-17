# Fantasy Theme World (Dylan & Delilah, 31363-31392). Official lines where the client has them; otherwise written from
# the quests' Quest.wz summaries / the NPCs' Npc.wz lines. Data: ftwgen/gen_ftw.py.
# Fantastic Animal Show (2192001, its Npc.wz script): the Crittertorium's show. The client data has the show's stars
# - Targa and Scarboss (8145100 / 8145200), Angry Targa and Furious Scarboss (8145101 / 8145201, in the Monster
# Collection's Fantasy Theme World page) - but no script for how the show was run; this is my reading: after the
# story's own visit (31382), the show can be watched any time - Angry Targa and Furious Scarboss, in your own copy.
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

ENTRANCE = 223030200  # Fantasy Animal Show <Entrance>: the Scarlion and Targa boss
BOSS_ROOM = 223030210


def enter_boss():
    # the old go_animalShow portal is gone from the v271 map, so this sign is the way in.
    # Contributed by Dapsy (from the Discord).
    from net.swordie.ms.enums import BossCooldown
    if not sm.sendAskYesNo("#e<Fantastic Animal Show>#n\r\nStarring #rTarga#k and #rScarlion#k! Battle them? (once a day)"):
        return
    if sm.getParty() is None:
        sm.sendSayOkay("Please create a party before going in.")
    elif not sm.isPartyLeader():
        sm.sendSayOkay("Please have your party leader talk to me.")
    elif sm.checkParty(BossCooldown.ScarlionTarga):
        sm.warpInstanceIn(BOSS_ROOM, 0, True)
        sm.setInstanceTime(60 * 30)
        sm.setBossCooldown(BossCooldown.ScarlionTarga)


sm.setSpeakerID(2192001)
if sm.getFieldID() == ENTRANCE:
    enter_boss()
elif quest_fight():
    sm.sendSayOkay("The #bFantasy Crittertorium#k! The entrance is right next to this sign.")
elif not sm.hasQuestCompleted(SHOW_QUEST):
    sm.sendSayOkay("#e<Fantastic Animal Show>#n\r\nStarring #rTarga#k and #rScarboss#k at the Fantasy Crittertorium!\r\n#r(Restricted Area)#k")
elif sm.sendAskYesNo("#e<Fantastic Animal Show>#n\r\nStarring #rAngry Targa#k and #rFurious Scarboss#k! Watch the show? (Level 120+)"):
    if chr.getLevel() < 120:
        sm.sendSayOkay("The show is for guests level 120 and above.")
    else:
        start_show(8145101, 8145201)
sm.dispose()
