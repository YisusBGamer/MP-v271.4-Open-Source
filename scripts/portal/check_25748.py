# Portal script check_25748.
# Kaiser story: Suspicious Hideout Interior 3 -> Official's Space, for Enemy Mastermind (25748).
if sm.hasQuest(25748) or sm.hasQuestCompleted(25748):
    sm.warp(400010510, 1)
else:
    chr.chatMessage("Something blocks the way deeper into the base.")
sm.dispose()
