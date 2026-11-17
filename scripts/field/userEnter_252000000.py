# Golden Temple | Golden Temple (252000000)
sm.showFieldEffect("Map/Effect.img/temaD/enter/goldTemple") # Golden Temple Theme Dungeon Effect
# Grand Crystal (25655, Luminous story): "Vieren says that the legendary Grand Crystal may be hidden in the Golden
# Temple. Go look for it." (the quest's own text; Vieren's riddle: 'The ultimate light is the slumbering gold.').
# Coming to the Golden Temple finds it.
if sm.hasQuest(25655) and not sm.hasItem(4033334):
    if sm.canHold(4033334):
        sm.giveItem(4033334)
        sm.chatScript("A soft light glimmers somewhere in the slumbering gold of the temple... You found the Grand Crystal!")
    else:
        sm.chatScript("Make some room in your Etc inventory first.")
sm.dispose()
