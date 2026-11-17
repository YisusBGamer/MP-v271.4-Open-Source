# id 39401 (Just Match the Headcount) end, East Side 1 (410000416), the Regulator (3004952)
# Kain's tutorial. The game data has no lines for it: written from its quest log. First Malice (39402) follows.
execfile("scripts/quest/_kain_tutorial.py")

npc(REGULATOR, "What is your condition?")
me("(My Malice is nearly spent.)")
npc(REGULATOR, "Headcount matched. Condition below standard. You need your #rMalice Stone#k.")
finish(HEADCOUNT)
if not sm.hasQuestCompleted(FIRST_MALICE):
    first_malice()
sm.dispose()
