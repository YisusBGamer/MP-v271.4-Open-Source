# 922030200 (Ludibrium : Library Vault) - Phantom story, Unwritten Records (25432): the possessed Wiz
if sm.hasQuest(25432) and not sm.isComplete(25432) and not sm.hasMobsInField():
    sm.spawnMob(9300505, 200, 31, False)
    sm.chatScript("Wiz the Librarian has been possessed!")
