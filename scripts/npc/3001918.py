# Knight Statue (3001918), Fountain Square: the end of the Adele tutorial, First Sword's Calling (39651) and its
# hidden Ristonia 2 (39672). The parenthesized lines are the statue's and the quest log's (String.wz, QuestInfo).
execfile("scripts/quest/_adele_tutorial.py")

if chr.getJob() == ADELE_BEGINNER and sm.hasQuestCompleted(KNIGHT_MEETS_BOY):
    me("#b(This statue of a knight has a very strange aura.)#k")
    me("#b(It seems the voice of the Knight Statue is calling you.)#k")
    sm.lockInGameUI(True, False)
    try:
        sm.playSound("Sound/SoundEff.img/adele/sword0")
        sm.blind(1, 255, 240, 240, 240, 1300)
        sm.sendDelay(1300)
        finish(RISTONIA_2)
        first_job()
        sm.blind(0, 0, 0, 0, 0, 1300)
        sm.sendDelay(1300)
    finally:
        sm.lockInGameUI(False, True)
    me("#b(A mysterious light awoke the power sealed deep within your soul.)#k")
    if chr.getJob() == ADELE_1:
        sm.resetParam()
        sm.sendSayOkay("You have advanced to #bAdele (1st job)#k.")
        me("(That boy... I can't stop thinking about him. I should find out what he's up to.)")
else:
    line = sm.getRandomNpcDefaultLine(parentID)
    if line is not None:
        sm.setSpeakerID(parentID)
        sm.sendSayOkay(line)
sm.dispose()
