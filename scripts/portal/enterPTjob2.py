# 915010000 -> 915010001 Orbis Treasure Vault
if sm.hasQuest(25101) and not sm.hasMobsInField():
    sm.warp(915010001, 1)
elif sm.hasQuest(25101):
    sm.chat("Break the lock first.")
elif sm.hasQuest(25100):
    sm.chat("Complete 'The Erstwhile Vault' in your quest window and accept 'That's So Raven' first.")
else:
    sm.chat("The vault is locked tight.")
sm.dispose()
