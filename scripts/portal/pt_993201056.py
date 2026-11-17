# Little Room (993201056) -> Remote House (993201057), once the sword is out of the Weapon Box (36601).
if sm.hasQuestCompleted(36601):
    sm.warp(993201057, 1)
else:
    sm.chatScript("Take the Beginner Warrior's Sword out of the Weapon Box first.")
sm.dispose()
