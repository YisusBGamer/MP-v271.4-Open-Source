# Snow Island: Entrance to Rien Training Center (140010100), portal in00
# Puo's training for Aran ("Train or Die!" 1-3). Each lesson's monsters live in their own Training Ground (9300341 in
# 914010000, 9300342 in 914010100, 9300343 in 914010200), and each ground's exit leads back to this portal.
GROUNDS = ((21701, 914010000), (21702, 914010100), (21703, 914010200))

target = 0
for quest, field_id in GROUNDS:
    if sm.hasQuest(quest):
        target = field_id
        break
if target:
    sm.warp(target, 1)  # out00, by the ground's exit
else:
    sm.chat("The Training Center is only open for Puo's lessons. Talk to Puo first.")
sm.dispose()
