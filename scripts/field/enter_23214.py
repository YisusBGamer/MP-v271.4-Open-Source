# 931050120, Mastema's sparring dimension: Sparring with Mastema (23214, the Demon 3rd job; q23214s sends you here).
# Her past self (9001038) appears while the quest still needs her beaten; her death script (die_mastema2) sends you
# back to her room.
MASTEMA = 9001038
QUEST = 23214

if sm.hasQuest(QUEST) and not sm.isComplete(QUEST) and not sm.hasMobsInField():
    sm.spawnMob(MASTEMA, 671, -14, False)
    sm.chatScript("Spar with Mastema's past self!")
