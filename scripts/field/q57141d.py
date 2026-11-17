# Honnou-ji Infiltration 2 (Hayato 57141, Kanna 57437): 807050201 Eastern Wall Exterior, 807050202 Temple,
# 807050203 Eastern Grounds, 807050204 Eastern Wall (with Princess Sakuno, back with the escape route).
QUESTS = ((57141, 57141), (57437, 57437))
active = []
for q, r in QUESTS:
    if sm.hasQuest(q):
        active.append((q, r))
field = sm.getFieldID()
if active:
    if field == 807050201:
        sm.chat("The Eastern Wing of the temple is right over this wall. Find Princess Sakuno.")
    elif field in (807050202, 807050203):
        sm.chat("Princess Sakuno is here somewhere. Talk to her.")
    elif field == 807050204:
        for q, r in active:
            if sm.getQRValue(r) != "1":
                sm.setQRValue(r, "1", False)
        sm.chat("Princess Sakuno is safe! Report to the one guarding the escape route.")
