# Kyrin (1090000) | Navigation Room
# Swordie Plus: a 2nd job test in progress without its 30 Dark Marbles (left the test map, or died in it):
# offer to go back into the test, as the instructors do on the official servers
JOB_TESTS = {1425: 912040000, 1426: 912040000, 1428: 912040000}
for _quest in sorted(JOB_TESTS.keys()):
    if sm.hasQuest(_quest) and not sm.hasItem(4031013, 30):
        if sm.sendAskYesNo("You haven't brought me the #b30 #t4031013##k yet. Would you like to go back to the test?"):
            sm.warp(JOB_TESTS[_quest], 0)
        sm.dispose()

if sm.getChr().getLevel() >= 10 and sm.getChr().getJob() == 0:
    if sm.sendAskYesNo("Would you like to become a Pirate?"):
        sm.giveItem(2330000, 3000)
        sm.giveItem(1492014)
        sm.giveItem(1482000)
        sm.setJob(500)
        sm.setSTR(4)
        sm.setINT(4)
        sm.setDEX(4)
        sm.setLUK(4)
        sm.setAP(4 + chr.getLevel() * 5)
        sm.completeQuest(1400)
