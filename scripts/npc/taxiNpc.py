# Taxi
# 9000089
# Nautilus (120000100)
# Warps from Nautilus to the Victoria Island towns for a fee. Fee is an assumption.

FEE = 1000
maps = [
    {"MapID": 104000000}, # Lith Harbor
    {"MapID": 100000000}, # Henesys
    {"MapID": 103000000}, # Kerning City
    {"MapID": 101000000}, # Ellinia
    {"MapID": 102000000}, # Perion
    {"MapID": 105000000}, # Sleepywood
]

currentMap = sm.getFieldID()
maps = [mapEntry for mapEntry in maps if mapEntry["MapID"] != currentMap]

destString = ["Where would you like to go? The ride costs #b" + str(FEE) + " mesos#k.\r\n"]
options = sm.selectionString("#L{i}##m{MapID}##l", maps)
destString.append(options)
destination = sm.sendNext(sm.join(destString))

if sm.getMesos() < FEE:
    sm.sendSayOkay("You don't have enough mesos.")
else:
    sm.deductMesos(FEE)
    sm.warp(maps[destination]["MapID"])
