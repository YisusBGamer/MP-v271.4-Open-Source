map = 200090020

response = sm.sendAskYesNo("Would you like to go to #m" + str (map) + "#?")

if response:
    sm.warp(map, 0)
