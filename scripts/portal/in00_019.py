# Hieizan Temple story (Investigating Hieizan; two quest lines that mirror each other, 58918-58927 and 58928-58940).
# 811000019 East Tower Central Shrine Entrance -> inside the shrine (811000020), where Miroku waits for Chasing the
# Boy (58923 / 58936).
if sm.hasQuest(58923) or sm.hasQuest(58936) or sm.hasQuestCompleted(58923) or sm.hasQuestCompleted(58936):
    sm.warp(811000020, 1)
else:
    chr.chatMessage("The shrine's doors are shut tight.")
sm.dispose()
