# 103000003 Kerning City Hideout, secret00: the secret room in the Thieves' Guild's basement.
# The Hidden Room (Explorer story 32259): the jewel room, with an intruder (field/explorer_hidden_room).
if sm.hasQuest(32259) and not sm.isComplete(32259):
    sm.warpInstanceIn(910350240, 0)
else:
    sm.chat("The secret room is locked.")
sm.dispose()
