# in00 in West Leafre Forest (240010000): the hidden portal to the Leafre Forest's Edge, for A Strange Letter (23290)
if sm.hasQuest(23290) and not sm.isComplete(23290):
    sm.warpInstanceIn(924020100, 0)
else:
    sm.chatScript("There's nothing beyond the bushes.")
    sm.dispose()
