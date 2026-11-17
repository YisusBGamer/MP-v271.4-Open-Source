# D-03 Transmitter (2430942), the Shadowdealers' receiver from Fool's Errand: shows whether it's ON, i.e. whether
# a [Communication] quest (31631-31644) is coming through. Not used up.
active = [q for q in range(31631, 31645) if sm.hasQuest(q)]
if active:
    sm.chat("The D-03 Transmitter is ON. A message from the Shadowdealers is coming through.")
else:
    sm.chat("The D-03 Transmitter is OFF. No messages right now.")
sm.dispose()
