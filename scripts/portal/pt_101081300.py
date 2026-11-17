# id 4 (in00), field 101081300
# The cottage door. It opens once "Home Invasion" has started ("I'd rather take my chances in there") and stays open
# after that; players come in at the house's own door (out00, which leads back here).
HOME_INVASION = 37159
RUENNAS_HOME = 101082000
HOUSE_DOOR = 1  # out00
if sm.hasQuest(HOME_INVASION) or sm.hasQuestCompleted(HOME_INVASION):
    sm.warp(RUENNAS_HOME, HOUSE_DOOR)
else:
    sm.setSpeakerType(8)
    sm.setParam(2)
    sm.sendNext("But it's pitch dark in there. I can't just walk in uninvited.")
