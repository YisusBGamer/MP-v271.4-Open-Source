# 915010000 Orbis Treasure Vault Entrance (Phantom 2nd job)
# "That's So Raven" (25101): the lock (9001045) has to be broken to get into the vault.
LOCK = 9001045
if (sm.hasQuest(25100) or sm.hasQuest(25101)) and not sm.hasMobsInField():
    sm.spawnMob(LOCK, 170, 182, False)
if sm.hasQuest(25100):
    sm.chat("You found your old vault. Complete 'The Erstwhile Vault' in your quest window, then accept 'That's So Raven'.")
