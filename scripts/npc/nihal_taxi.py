# Camel Cab 
# 2110005 (also 9000090, a hidden copy in Ariant)
# Warps between Ariant and Magatia through Outside North Entrance of Ariant and Sahel 1.

destinationDict = {
    260020000: {"MapID": 261000000}, # Outside North Entrance of Ariant => Magatia
    260020700: {"MapID": 260000000}, # Sahel 1 => Ariant
    260000000: {"MapID": 261000000}, # Ariant (NPC 9000090) => Magatia
}

destination = destinationDict.get(sm.getFieldID())
if destination is None:
    sm.sendSayOkay("The camels are resting. Come back later.")
else:
    response = sm.sendAskYesNo(sm.formatString("Would you like to go to #m{MapID}#?", destination))
    if response:
        sm.warp(destination["MapID"])
sm.dispose()
