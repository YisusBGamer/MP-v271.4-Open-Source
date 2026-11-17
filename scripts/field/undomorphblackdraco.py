# Leafre of Past: Leafre in Flames 6 (272000600), the deepest of the burning maps. [Arkarium] The Last Onyx Dragon
# (31173): "Go find the Last Onyx Dragon Egg that has not been cursed yet. You were told that the egg was lost deep
# inside Leafre in Flames." (the quest's own text). The egg lies at the far end of this map while the quest needs it
# (quest items only show for characters on the quest).
if sm.hasQuest(31173) and not sm.hasItem(4033081):
    sm.dropItem(4033081, 1440, -20)
