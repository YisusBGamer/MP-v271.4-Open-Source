# General Store Back Yard (913070020), Mihile's "Tread Carefully in the Back Yard" (20033): the egg nests are
# reactors (mihailItem00); Bigby breaks loose after the last egg
if sm.hasQuest(20033):
    sm.chatScript("Limbert's General Store Back Yard")
    sm.progressMessageFont(3, 20, 20, 0, "Hit the chicken nests for Eggs. Careful with Bigby...")
sm.dispose()
