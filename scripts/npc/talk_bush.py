# Pile of leaves (1541067), Maple Island map 10000. The Girl With the Maple Leaf Hairpin (36301): Sugar's hairpin
# is hidden in the leaves.
if sm.hasQuest(36301) and not sm.hasItem(4036846):
    if sm.canHold(4036846):
        sm.giveItem(4036846)
        sm.chat("You found Sugar's hairpin in the leaves.")
    else:
        sm.chat("Make room in your Etc inventory first.")
elif sm.hasQuest(36301):
    sm.chat("You already have Sugar's hairpin. Take it back to her.")
sm.dispose()
