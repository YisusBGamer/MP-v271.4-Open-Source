# Mirror Cave (140030000), the Mirror of Desire. Aran's v265 job advancements:
#   36962 Bringing Back the Brain (2nd): the memory of Sir Blacksmith's test (914021000, npc/1203000.py)
#   36964 Making Red Jade (3rd): the memory where Tititi makes the Red Jade (914022100, npc/1203001.py)
#   36966 Taming the Polearm (4th): Maha's rage, fought in the memory of the test (914021000)
# The v232 quests (21201, 21302) are blocked in v265 but kept for characters that have one in progress.
RED_JADE = 4037303
RAGE = 8645453

if sm.hasQuest(36962) and sm.getQRValue(36962, "clear") != "cl":
    sm.warpInstanceIn(914021000, 0)
    sm.setInstanceTime(900, 140030000)
elif sm.hasQuest(36964) and not sm.hasItem(RED_JADE):
    sm.warpInstanceIn(914022100, 0)
    sm.setInstanceTime(900, 140030000)
elif sm.hasQuest(36966) and not sm.isComplete(36966):
    sm.warpInstanceIn(914021000, 0)
    sm.setInstanceTime(1200, 140030000)
    sm.spawnMob(RAGE, 300, 540, False)
    sm.chatScript("Defeat Maha's rage!")
elif sm.hasQuest(21201):
    sm.warpInstanceIn(914021000, 0)
    sm.addQRValue(21203, "0")
    sm.setInstanceTime(900, 140030000)
elif sm.hasQuest(21302):
    sm.warpInstanceIn(914022100, 0)
    sm.setQRValue(21203, "1", False)
    sm.setInstanceTime(1200, 140030000)
else:
    sm.chat("The Mirror of Desire shows nothing but your own reflection.")
sm.dispose()
