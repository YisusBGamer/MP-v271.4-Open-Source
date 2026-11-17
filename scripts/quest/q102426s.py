# [Tera Blink] Learn
SPIEGELMANN = 9063173
QUEST = 102426

sm.setSpeakerID(SPIEGELMANN)
sel = sm.sendNext("#b#eDo you have any questions about <Tera Blink>?#k#n\r\n"
                  "#L1##bTell me about #e<Tera Blink>#n.#k#l\r\n"
                  "#L2##bTell me about #e<the World Inside the Hat>#n.#k#l\r\n"
                  "#L3#Tell me about the #b#eTera Blink event period#n.#k#l\r\n"
                  "#L99#I don't have any more questions.#l")
if sel == 1:
    sm.sendNext("#bTera Blink#k is an #bevent#k prepared for warriors who have just joined Maple World.")
    sm.sendNext("You'll grow quickly through all sorts of quests you can complete in the game, new features included.")
    sm.sendNext("And that's not all! Once you complete every quest inside the hat, I'll give you\r\n"
                "#i3019187:# #b#e#t3019187:##n#k and\r\n#i5010321:# #b#e#t5010321:##n#k.")
elif sel == 2:
    sm.sendNext("The #bWorld Inside the Hat#k is a world that exists entirely inside this hat! It's full of new features.")
    sm.sendNext("In the #bWorld Inside the Hat#k you can take on #bALL#k kinds of quests.")
    sm.sendNext("Completing them gives you a huge amount of EXP, and some of the know-how you need to succeed in Maple World.")
    sm.sendNext("You can travel to the World Inside the Hat by talking to me in #bHenesys#k or #bNameless Town#k.")
elif sel == 3:
    sm.sendNext("You can use the hat's features until the #bHYPER BURNING MAX#k event ends.\r\n\r\n"
                "#r#e[Event Period]#n#k\r\n- Until 11:59 PM, 12/31/2026 (Thursday)")
sm.completeQuestNoRewards(QUEST)
sm.dispose()
