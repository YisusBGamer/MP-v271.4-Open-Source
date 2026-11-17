# Door from Morass to Esfera. v265's Esfera story starts with [Esfera] The Strangers (37851); 34562 is the old
# story's first quest (blocked in v265).
if sm.hasQuest(37851) or sm.hasQuestCompleted(37851) or sm.hasQuestCompleted(34562):
    sm.warpInstanceOut(450007000)
else:
    sm.chat("Something's blocking the way.")
