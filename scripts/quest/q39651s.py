# id 39651 (First Sword's Calling): Adele's 1st job (15002 -> 15100), self-started at Lv 10 after Knight Meets Boy.
# The tutorial plays it at the Knight Statue (npc 3001918); lines from its quest log (QuestInfo).
execfile("scripts/quest/_adele_tutorial.py")

if chr.getJob() == ADELE_BEGINNER:
    me("#b(It seems the voice of the Knight Statue is calling you.)#k")
    first_job()
    me("#b(A mysterious light awoke the power sealed deep within your soul.)#k")
    if chr.getJob() == ADELE_1:
        sm.resetParam()
        sm.sendSayOkay("You have advanced to #bAdele (1st job)#k.")
elif chr.getJob() == ADELE_1 and not sm.hasQuestCompleted(FIRST_CALLING):
    sm.questJobAdvance(FIRST_CALLING, ADELE_BEGINNER, ADELE_1)
sm.dispose()
