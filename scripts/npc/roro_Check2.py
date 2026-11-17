# Magic Library Checker (1032218, 1032219, 1032220 -> keys c1, c2, c3) in Ellinia's Magic Library: Luminous's
# "Terror of the Library" (25566) asks to search 3 spots; its end checks keys c1-c3 = 1 on record 25566 (Quest.wz).
QUEST = 25566
KEY = {1032218: "c1", 1032219: "c2", 1032220: "c3"}.get(parentID, "c1")

if sm.hasQuest(QUEST):
    if sm.getQuestEx(QUEST, KEY) != "1":
        sm.setQuestEx(QUEST, KEY, "1")
        sm.chatScript("You search the Magic Library.")
    else:
        sm.chatScript("You've already searched here.")
sm.dispose()
