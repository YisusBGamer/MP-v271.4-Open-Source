# 410000300 Ristonia Fountain Square: Adele's starting town. The official direction scenes aren't in the data; the
# story continues through its quests.
# A new Adele (beginner job) arriving from the Void meets the boy (Knight Meets Boy 39600, Ristonia 1 39671); the
# Knight Statue's call (npc 3001918) finishes the tutorial. The lines follow the quest logs and Jerome's and Boo's
# own lines (String.wz).
execfile("scripts/quest/_adele_tutorial.py")

if chr.getJob() == ADELE_BEGINNER and not sm.hasQuestCompleted(KNIGHT_MEETS_BOY):
    sm.lockInGameUI(True, False)
    try:
        sm.blind(1, 255, 240, 240, 240, 0)
        sm.sendDelay(300)
        sm.playSound("Sound/SoundEff.img/adele/fountain")
        sm.blind(0, 0, 0, 0, 0, 1300)
        sm.sendDelay(1300)
        npc(JEROME, "Whoa! You... you came out of the #bKnight Statue#k! Are you a knight? A real one?")
        me("(A knight... Yes. That much I remember. My name, and my sword. Nothing more.)")
        npc(BOO, "Boo?")
        npc(JEROME, "Someday, I will save all of Ristonia! And a real knight showed up right when I asked for one. That can't be a coincidence!")
        me("(It was this boy's prayer that reached me in the Void. I'm sure of it.)")
        begin(KNIGHT_MEETS_BOY)
        sm.completeQuestNoCheck(KNIGHT_MEETS_BOY)
        finish(RISTONIA_1)
        npc(JEROME, "I've got things to take care of. Come on, Boo!")
        sm.moveNpcByTemplateId(JEROME, False, 300, 100)
        sm.moveNpcByTemplateId(BOO, False, 300, 100)
        sm.sendDelay(1500)
        me("(What a strange boy...)")
        me("(The statue... It's as if it's calling to me.)")
    finally:
        sm.lockInGameUI(False, True)
    sm.chatScript("Talk to the Knight Statue in the middle of Fountain Square.")
elif chr.getJob() == ADELE_BEGINNER and not sm.hasQuestCompleted(FIRST_CALLING):
    sm.chatScript("Talk to the Knight Statue in the middle of Fountain Square.")
sm.dispose()
