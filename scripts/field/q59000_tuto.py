# Arboren : Stump Town
from net.swordie.ms.client.character import PlayStyleModule

# Beast Tamers only (11000, 112xx): anyone else walking in would be offered the skip and the Beast Tamer skills
skip_run = PlayStyleModule.isSkipRun(chr)  # Express chosen in @menu later in the tutorial
if (chr.getJob() == 11000 or chr.getJob() // 100 == 112) and (skip_run or not sm.hasQuest(59000)): # The Town Prankster
    if skip_run or not sm.hasQuestCompleted(59000): # The Town Prankster
        sm.removeEscapeButton()
        if sm.askTutorialSkip(866000000):
            # Express: the tutorial's quests count as done, so the Stump Town story (59036 on) can start
            for quest in (59000, 59001, 59002, 59003, 59004, 59005, 59006, 59007, 59008, 59009, 59011, 59013, 59015, 59016, 59017, 59018, 59019, 59020):
                if not sm.hasQuestCompleted(quest):
                    sm.completeQuestNoRewards(quest)
            sm.warp(866000000)
            if sm.getChr().getLevel() < 10:
                sm.addLevel(10 - sm.getChr().getLevel())
            sm.giveSkill(110001510, 1, 1)  # Critter Select
            sm.giveSkill(110001501, 1, 1)  # Bear
            sm.giveSkill(110001502, 1, 1)  # Leopard
            sm.giveSkill(110001503, 1, 1)  # Hawk
            sm.giveSkill(110001504, 1, 1)  # Cat
            sm.giveSkill(110001514, 1, 1)  # Homeward Bound
            sm.giveSkill(110001511, 30, 30)  # Maple Guardian
            sm.giveSkill(110001506, 1, 1)  # Guardian Leap
            sm.setSTR(4)
            sm.setINT(4)
            sm.setDEX(4)
            sm.setLUK(4)
            sm.setAP(4 + chr.getLevel() * 5)
            sm.dispose()
        else:
            sm.setPlayerAsSpeaker()
            sm.sendNext("Dun, dun, dun. Hero theme song! I'm #h #, I'm from a town hidden deeep within Arboren Forest!")
            sm.sendNext("I've got the coolest ears and tail, dun dun dun. They're super heroic, dun dun dun.")
            sm.sendNext("And I'm gonna be a hero somedaaaaay. A hero somedaaaaay! Drumroll!")
            sm.sendNext("For reals. Granny Rosanna tells me bedtime stories every night...")
            sm.sendNext("Stories about the #bfive brave heroes#k, who sealed away the terrifying #bBlack Mage#k! \r\n Pew, pew, kaboom! I'm gonna be a hero just like 'em someday soon!")
            sm.setSpeakerID(9390305)
            sm.sendNext("Who'd dig a hole here!?")
            sm.setPlayerAsSpeaker()
            sm.sendNext("Uh oh, what's this? I smell... the need for a Hero!")
            sm.startQuest(59000)
            sm.dispose()