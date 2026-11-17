# 2400315
HOODLUMS_KILLED_KEY = "hoodlumKilled"
max = 30

if not field.hasProperty(HOODLUMS_KILLED_KEY):
    killed = 0
else:
    killed = field.getProperty(HOODLUMS_KILLED_KEY)

killed += 1
field.setProperty(HOODLUMS_KILLED_KEY, killed)
if sm.getChr() is None:
    pass  # the hoodlum died with nobody to credit (no killer in the map)
else:
    sm.progressMessageFont(1, 20, 4, 0, "Hoodlums taught a lesson: {}/{}".format(min(killed, max), max))

if sm.getChr() is not None and killed >= max and sm.getFieldID() != 402000002:
    # 34601 is handed in on its own record (clear=1)
    sm.setQRValue(34601, "clear", "1")
    sm.completeQuest(34601)
    sm.removeSkill(60021278)
    sm.warp(402000002)
    field.setProperty(HOODLUMS_KILLED_KEY, 0)
