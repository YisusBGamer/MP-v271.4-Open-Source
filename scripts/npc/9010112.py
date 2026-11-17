# Imperial Knight Sir Hamselot (9010112), Legion Champion, in the towns
from net.swordie.ms.client.character.union import ChampionModule
from net.swordie.ms.enums import UIType

PATH = ChampionModule.PATH_QUEST
TRIALS = ChampionModule.TRIAL_QUESTS
# (boss, minutes) of the trial that raises rank i to i + 1 (Quest.wz texts)
TRIAL_BOSS = [("Lotus (Hard)", 20), ("Verus Hilla (Hard)", 20), ("Black Mage (Hard)", 45),
              ("Chosen Seren (Hard)", 20), ("Kalos the Guardian (Normal)", 20)]


def champion_rank():
    slot = ChampionModule.getSlotOf(chr)
    if slot < 0:
        return -1
    return ChampionModule.getSlots(chr.getAccount())[slot].rank


def explain():
    sm.sendNext("Champions are the mightiest warriors of a Legion. Register up to #b2 characters#k as Champions in "
                "the #bChampion tab of the Legion window#k; every Champion at #bRank A#k or higher opens one more "
                "slot, up to #b6#k.")
    sm.sendNext("A Champion starts at #bRank C#k. To rise, a Champion must take the #bChampion Trial#k for the next "
                "rank and defeat a powerful foe #balone#k within the time limit:\r\n\r\n"
                "#bRank B#k: Lotus (Hard), 20 min\r\n#bRank A#k: Verus Hilla (Hard), 20 min\r\n"
                "#bRank S#k: Black Mage (Hard), 45 min\r\n#bRank SS#k: Chosen Seren (Hard), 20 min\r\n"
                "#bRank SSS#k: Kalos the Guardian (Normal), 20 min")
    sm.sendSayOkay("After a trial, report to me, then raise the rank in the Legion window. Every rank your Champions "
                   "reach strengthens the #bChampion Insignia#k, whose stats all characters of your Legion share.")


def trial():
    rank = champion_rank()
    if rank < 0:
        sm.sendSayOkay("Only a #bLegion Champion#k can take the Champion Trials. Register this character in the "
                       "#bChampion tab of the Legion window#k first.")
        return
    if rank >= len(TRIALS):
        sm.sendSayOkay("You are already a #bRank SSS#k Champion. There is no greater honor to win!")
        return
    quest = TRIALS[rank]
    boss, minutes = TRIAL_BOSS[rank]
    nxt = ChampionModule.rankName(rank + 1)
    if sm.hasQuestCompleted(quest):
        sm.sendSayOkay("You already proved yourself worthy of #bRank %s#k. Raise your rank in the #bLegion "
                       "window#k." % nxt)
    elif sm.hasQuest(quest):
        if sm.getQRValue(ChampionModule.TRIAL_RECORD, "clear") == "1":
            if sm.completeQuest(quest):
                sm.sendSayOkay("You proved you're qualified to be a Rank %s Champion! Raise your rank in the "
                               "#bLegion window#k." % nxt)
        else:
            sm.sendSayOkay("It doesn't look like you completed the champion trial yet. Come back after you're "
                           "qualified.\r\n\r\n#r* You can complete it by defeating %s alone within %d minutes." %
                           (boss, minutes))
    elif sm.sendAskYesNo("Prove your strength to become a #bRank %s#k Champion: defeat #b%s#k alone within "
                         "#b%d minutes#k. Will you take the trial?" % (nxt, boss, minutes)):
        sm.setQRValue(ChampionModule.TRIAL_RECORD, "clear", "0")
        sm.startQuest(quest)
        sm.sendSayOkay("Go forth, Champion! Return to me once %s has fallen." % boss)


sm.setSpeakerID(9010112)
if not sm.hasQuestCompleted(PATH):
    if sm.hasQuest(PATH):
        if sm.completeQuest(PATH):
            ChampionModule.sendRecords(chr)
            sm.sendSayOkay("Welcome! Register your Champions in the #bLegion window#k. Each Champion's rank rises "
                           "through the #bChampion Trials#k, and every rank earns your Champions a "
                           "#bChampion Insignia#k.")
    elif sm.hasQuestCompleted(16013):
        sm.sendSayOkay("Hark, good knight! Dame Appropriation is gathering the fellowship of champions. Speak with "
                       "her first.")
    else:
        sm.sendSayOkay("Where champions gather, evil quakes and tyrants wake cold in the night! Come back once you "
                       "lead a #bLegion#k of your own.")
else:
    rank = champion_rank()
    status = "Rank #b%s#k Champion" % ChampionModule.rankName(rank) if rank >= 0 else "not a Champion"
    choice = sm.sendNext("Hark, good knight! Have you the courage and strength to join the fellowship of champions?"
                         "\r\n(This character: %s)\r\n"
                         "#b#L0#Open the Legion window#l\r\n"
                         "#L1#Take a Champion Trial#l\r\n"
                         "#L2#What are Legion Champions?#l\r\n"
                         "#L3#Go to Draco Isle (Champion Raid)#l" % status)
    if choice == 0:
        ChampionModule.sendRecords(chr)
        sm.openUI(UIType.UNION_BOARD)
    elif choice == 1:
        trial()
    elif choice == 2:
        explain()
    elif choice == 3:
        from net.swordie.ms.client.character.union import ChampionRaid
        ChampionRaid.toCabin(chr)
sm.dispose()
