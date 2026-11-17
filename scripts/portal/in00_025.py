# Hieizan Temple story (Investigating Hieizan; two quest lines that mirror each other, 58918-58927 and 58928-58940).
# 811000025 -> Inside West Tower Central Shrine 1 (811000026), once Mikagami is out of the way (The Reunion 2, 58925
# / 58938).
if sm.hasQuestCompleted(58925) or sm.hasQuestCompleted(58938):
    sm.warp(811000026, 2)
else:
    chr.chatMessage("Mikagami won't let anyone into the shrine.")
sm.dispose()
