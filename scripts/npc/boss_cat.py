# Boss Cat
# 9120013
# Showa Town (801000000)
# Quiz for Sakura Fraidy Cat and the Orange Marble (8012)

ORANGE_MARBLE = 4031064
FRIED_CHICKEN = 2020001

questions = [
    "Question no.1:What's the name of the vegetable store owner in Showa Town?\r\n#b"
    "#L0#Sami #l\r\n"
    "#L1#Kami #l\r\n"
    "#L2#Umi #l\r\n",

    "Question no.2: Which of these NPCs does NOT stand in front of the movie theater at Showa Town?\r\n#b"
    "#L0#Sky #l\r\n"
    "#L1#Furano #l\r\n"
    "#L2#Shinta #l\r\n",

    "Question no.3: What is the name of the NPC that transfers travelers from Showa Town to the Mushroom Shrine?\r\n#b"
    "#L0#Spinel #l\r\n"
    "#L1#Perry #l\r\n"
    "#L2#Transporter #l\r\n"
]

correct_answers = [2, 2, 1]
correct = 0

if sm.hasQuest(8012) and not sm.hasItem(ORANGE_MARBLE):
    if not sm.hasItem(FRIED_CHICKEN, 300):
        sm.sendSayOkay("I like pretty objects, the ones that sparkle. Sparkles won't turn their back on you like those mean humans."
                       " Sure, I stole that marble, but boo-hoo! I've been abused by humans for as long as all the other cats here."
                       " If you want your precious marble back, you'll get it on my terms. Tell you what, I'm going to ask you some questions."
                       " If you get them all right, the Orange Marble is yours. But taking my questionnaire is gonna cost you. I want #b300 #z+" +
                       str(FRIED_CHICKEN)+"##k."
                       " And if you mess up a question, I expect another #b300 #z+"+str(FRIED_CHICKEN)+"##k to let you start again.")
    else:
        give_chicken = sm.sendAskYesNo(
            "Did you get them all? Are you going to try to answer all of my questions?")
        if not sm.canHold(ORANGE_MARBLE):
            sm.sendSayOkay(
                "Please check your pockets to see if you have room in your ETC inventory")
        elif give_chicken:
            sm.consumeItem(FRIED_CHICKEN, 300)
            sm.sendNext("Good job! The alley cats are gonna feast tonight! Now, on to my questions, I'm sure you're aware of this, but remember, if you get a single one wrong, it's over. This is all or nothing!")
            for i in range(len(questions)):
                answer = sm.sendNext(questions[i])
                if answer != correct_answers[i]:
                    sm.sendSayOkay(
                        "You're wrong! So thanks for the chicken! See you again.")
                    break
                correct += 1
            if(correct == 3):
                sm.sendNext(
                    "Wow, you answered all the questions correctly! I may not be the most fond of humans, but I HATE breaking a promise! So, as promised, here's the Orange Marble.\r\nYou earned it!")
                sm.giveItem(ORANGE_MARBLE)
                sm.sendSayOkay("Our business is concluded, thank you very much! You can leave now!")
        else:
            sm.sendSayOkay("I'm going to starve because of you.")
else:
    sm.sendSayOkay("Meow")
