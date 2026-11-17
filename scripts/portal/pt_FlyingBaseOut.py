# The older Maple Alliance Outpost (450009000): the same exit as the current one (450009050's out00)
maps = [
    {"MapID": 130000210}, # Sky Ferry
    {"MapID": 450009100}, # White Spear Deck 1
]

sm.flipDialoguePlayerAsSpeaker()
destString = ["Where would you like to go? #b\r\n"]
options = sm.selectionString("#L{i}##m{MapID}##l", maps)
destString.append(options)
destIndex = sm.sendNext(sm.join(destString))
sm.warp(maps[destIndex]["MapID"])
