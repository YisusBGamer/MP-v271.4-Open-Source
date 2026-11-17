# Hieizan Temple story (Investigating Hieizan; two quest lines that mirror each other, 58918-58927 and 58928-58940).
# 811000028 Inside Small Shrine -> the shrine west of it (811000033), where Princess No hides for The End of
# Princess No (58927 / 58940).
if sm.hasQuest(58927) or sm.hasQuest(58940) or sm.hasQuestCompleted(58927) or sm.hasQuestCompleted(58940):
    sm.warp(811000033, 1)
else:
    chr.chatMessage("There's nothing beyond here yet.")
sm.dispose()
