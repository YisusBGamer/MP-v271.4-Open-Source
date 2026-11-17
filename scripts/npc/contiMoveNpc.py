# Maple Admin
# 9000086
# Nautilus (120000100)
# Continent mover: sends the player to a ship station. No fee. Destination list is an assumption.

MAPS = [
    ["Victoria Island Station", 104020100],
    ["Orbis Station", 200000100],
    ["Ludibrium Station", 220000100],
    ["Ereve Sky Ferry", 130000210],
]

text = "Where would you like to go? #b\r\n"
i = 0
while i < len(MAPS):
    text += "\r\n#L" + str(i) + "#" + MAPS[i][0] + "#l"
    i += 1

answer = sm.sendNext(text)
if sm.sendAskYesNo("Would you like to go to #b" + MAPS[answer][0] + "#k?"):
    sm.warp(MAPS[answer][1])
