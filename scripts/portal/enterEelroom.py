# Verne Mine: Power Plant Lobby (310050000), portal in01
# Eleanor's room (931020011), whose exit leads back to this portal. Players go in for "Please Catch Eleanor's Pet"
# (23957): the escaped pet is Eleanor's Cockatrice (9300417), which lives only there, caught with the cage the
# quest hands out (2270020).
if sm.hasQuest(23957):
    sm.warp(931020011, 1)  # by the room's exit, out00
else:
    sm.chat("Eleanor's room is locked.")
sm.dispose()
