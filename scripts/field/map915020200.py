# 915020200 Leafre Treasure Vault Entrance (Phantom 4th job): reaching it is what "What's in a Phantom?" (25120) needs
# (record 25123). The Guardioso guards the vault ("An Expensive Acquisition", 25121; map915020200_PT).
if 2500 <= chr.getJob() <= 2512 and sm.hasQuestCompleted(38053) and sm.getQRValue(38054) == "":
    sm.setQRValue(38054, "impossible", False)  # Shade: Phantom Reunion can start
if chr.getJob() == 2411 and sm.hasQuest(25120) and sm.getQRValue(25123) != "1":
    sm.setQRValue(25123, "1", False)
    sm.chat("You reached your Leafre vault. Complete 'What's in a Phantom?' in your quest window, then accept 'An Expensive Acquisition'.")
