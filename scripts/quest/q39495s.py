# id 39495 (Kain 1st Job Advancement): starts by itself for a Kain on the beginner job. The advancement comes with
# First Malice (39402); before that nothing happens here.
execfile("scripts/quest/_kain_tutorial.py")

if sm.hasQuestCompleted(FIRST_MALICE):
    first_job()
sm.dispose()
