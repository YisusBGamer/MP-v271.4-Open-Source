# D-03 Transmitter (2437253), Heliseum Reclamation HQ: shows whether it's ON (a Heliseum quest in progress).
# Not used up.
active = [q for q in range(34700, 34760) if sm.hasQuest(q)]
if active:
    sm.chat("The D-03 Transmitter is ON. Heliseum Reclamation HQ can reach you.")
else:
    sm.chat("The D-03 Transmitter is OFF. No messages right now.")
sm.dispose()
