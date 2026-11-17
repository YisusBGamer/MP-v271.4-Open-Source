# Mrs. Claus (Happyville) (Christmas in Happyville - the Missing Santa story, see ChristmasEvent)
NPC = 9250051
from net.swordie.ms.world import ChristmasEvent as X

ACTIVE = X.isActive()


def can_start(q, *prev):
    # the event is on, the level is right, the quests before it are done, and it isn't started yet
    if not ACTIVE or chr.getLevel() < X.MIN_LEVEL or sm.hasQuest(q) or sm.hasQuestCompleted(q):
        return False
    for p in prev:
        if not sm.hasQuestCompleted(p):
            return False
    return True


def finish(q):
    return X.finish(chr, q)


def default():
    line = sm.getRandomNpcDefaultLine(NPC)
    sm.sendSayOkay(line if line is not None else "Merry Christmas!")

sm.setSpeakerID(NPC)


QUIZ = [('1. What is the name of Santa Claus called in Germany that he dreams every day?#l\r\n#L0#Odin#l\r\n#L1#Saint Nicolas#l\r\n#L2#SinterKlass#l\r\n#L3#Santa Claus#k#l', 1, "For what reason you wish to know about Santa Claus's present condition when you don't even have a basic history of Santa Claus?"), ("2. What is the date that is repeated in Santa's Dream?#l\r\n#L0#December 31st#l\r\n#L1#December 25th#l\r\n#L2#December 24th#l\r\n#L3#December 6th#k#l", 3, "For what reason you wish to know about Santa Claus's present condition when you don't even have a basic history of Santa Claus?"), ('3. What does little children hang by the Chimney when Santa is trying to take a rest with his horses in his dreams?#l\r\n#L0#Socks#l\r\n#L1#Glove#l\r\n#L2# Boot#l\r\n#L3#Bag #k#l', 2, "For what reason you wish to know about Santa Claus's present condition when you don't even have a basic history of Santa Claus?")]


def quiz():
    for text, answer, wrong in QUIZ:
        if sm.sendNext(text) != answer:
            sm.sendSayOkay(wrong)
            return False
    return True

if can_start(X.MRS_CLAUS, X.NEWS):
    if not sm.hasItem(X.VOL_2):
        sm.sendSayOkay("Where is my husband? ...Do you believe in Santa Claus?\r\n\r\n#b(Mrs. Claus won't talk "
                       "about Santa. Maybe #bSanta Encyclopedia No.2#k, about Santa's family, would help. Monsters "
                       "near your level have the volumes.)#k")
    elif sm.sendAskYesNo('Do I have any business with you?'):
        sm.startQuestNoCheck(X.MRS_CLAUS)
        sm.sendSayOkay("Hmm, Santa Claus? I've never heard of that name. Find somebody else who could answer your question.")
    else:
        sm.sendSayOkay('If not, just do what you were planning to do.')
elif sm.hasQuest(X.MRS_CLAUS):
    sm.sendNext("Oh? Aren't you the one who asked me about Santa Claus some time ago? Hmm, you just don't know how to give up, do you? Well, alright then!")
    sm.sendNext("BUT! I won't say a thing about Santa Claus unless I first figure out a few things about you. So, if you wouldn't mind, indulge me in a quick test and I'll judge your qualifications. Please come talk to me again when you are ready for my pop quiz.")
    finish(X.MRS_CLAUS)
elif can_start(X.QUIZ, X.MRS_CLAUS):
    if not sm.hasItem(X.VOL_1):
        sm.sendSayOkay("Ready for my pop quiz? You'd better read up on Santa's history first: #bSanta Encyclopedia "
                       "No.1#k. Monsters near your level have the volumes.")
    elif quiz():
        if sm.sendAskYesNo("I can't believe I was suspicious about the adventurer who has such a keen interest on Santa Claus. I am sorry. I must apologize to you. Okay... I will answer your questions. Would you like to listen?"):
            for line in ["There is an article about my husband... Well that's what media is saying but the real truth behind is this...", "People say that they haven't got the presents from Santa Claus last year, but in fact, my husband DID visit every single house in Maple World with presents. Unfortunately, quality of the socks that the children hung were all bad which was inevitable for Santa Claus to leave the house without putting in the gift.", "You might think that the quality of the socks doesn't matter as long as they are hung inside the houses. However, if the size or the flexibility does not meet the standard, it is really difficult to put in the gifts. So, the all the gifts that my husband has prepared for year was left in stock. He was very worried about paying the remainder and finally decided to leave home.", "I guess he was very distressed not even to tell his own wife. I am extremely worried... I have no idea where he is or what he is doing. He shouldn't reach far away from here though. I can tell that he is wandering around the usual place just like old days..."]:
                sm.sendNext(line)
            if finish(X.QUIZ):
                sm.sendSayOkay("Please talk to me in a little while if you would like to continue to listen to my story. I am getting thirsty from this conversation. I haven't had such a long conversation like this for a long time.")
        else:
            sm.sendSayOkay('I will only speak with a person who is reliable. If you think you can answer all of my questions then talk to me again.')
elif can_start(X.RECIPE, X.QUIZ):
    sm.sendNext("Indeed, as I get older, it's becoming harder and harder to speak for long periods of time. Alright, so let's get back to where we left off. Where was I?")
    sm.sendNext("Yes, ah yes! We were talking about Christmas Socks. Pay attention, because you'll need to carefully choose the Christmas socks that will be hung as decorations.")
    sm.sendNext('The standard ones would be the Christmas Socks of Happyville, which have a tradition that spans over six decades. However, a robbery occurred in the Socks factory that drove sock production to a screeching halt. I had worked there as a QC manager, but I hardly remember anything.')
    if sm.sendAskAccept("First, to make Happyville Socks, you'll need information on how to make it, including the order of steps. You'll be able to find that information on #bSanta Encyclopedia No.3#k, the one about #bRecipe for Christmas Socks#k. Could you retrieve that for me?"):
        sm.startQuestNoCheck(X.RECIPE)
        sm.sendSayOkay("Thanks! Get them as soon as possible. I'll wait here, practicing my knitting skills.")
    else:
        sm.sendSayOkay('Give up now? What happened to your ambition? Somebody had to do it for the sake of a happy Christmas in Maple World and I truly believed it would be you. If you change your mind, please come to me again. Please...')
elif sm.hasQuest(X.RECIPE):
    if sm.hasItem(X.VOL_3):
        sm.sendNext('Did you get the #bSanta Encyclopedia No.3#k, Recipe for Christmas Socks? Great! Could you read it for me? My sight is rather weakening.')
        sm.sendNext("Oh yes, I remember! Now I remember it. You need the #b Sock Frame and Colorful Yarns #k. Ah, so let's see. It requires a good amount of Colorful Yarns, but I think I have some extra Sock Frames. Please give me some time to find it. Come talk to me later.")
        finish(X.RECIPE)
    else:
        sm.sendSayOkay("I guess you haven't found it yet. It's not an easy task, since I know some monsters have taken it. But be patient, my dear Mapler. You'll need it to make standard socks.")
elif can_start(X.KNITTING, X.RECIPE):
    if not sm.hasItem(X.VOL_3):
        sm.sendSayOkay("Where did the recipe go? I need #bSanta Encyclopedia No.3#k to remember the steps.")
    else:
        sm.sendNext("Here it is, my old #bSocks Frame#k! Now we only need the #bColorful Yarn#k. Monsters love to "
                    "steal yarn balls from Happyville, so you'll find plenty on them.")
        if sm.sendAskAccept("Hunt monsters near your level, and the yarn you find goes right into the frame. When "
                            "the frame is full, bring it back and I'll knit you a proper Happyville sock. Will you "
                            "do it?"):
            if X.startKnitting(chr):
                sm.sendSayOkay("Off you go! I'll keep my needles warm.")
elif sm.hasQuest(X.KNITTING):
    progress = X.knitProgress(chr)
    if progress >= X.KNIT_GOAL and sm.hasItem(X.SOCKS_FRAME):
        sm.sendNext("My, what lovely yarn! Give me the frame... knit, purl, knit, purl... and there!")
        if finish(X.KNITTING):
            sm.sendSayOkay("A #bCompleted Sock#k, Happyville quality! Now hang it on the #bChristmas Tree#k at the "
                           "east end of town. The tree will want #bSanta Encyclopedia No.4#k too: it's about what "
                           "Santa is like.")
    elif not sm.hasItem(X.SOCKS_FRAME):
        sm.startQuestNoCheck(X.KNITTING)
        X.startKnitting(chr)
        sm.sendSayOkay("You lost the #bSocks Frame#k? Here's another one. Fill it with #bColorful Yarn#k from "
                       "monsters near your level.")
    else:
        sm.sendSayOkay("The frame is %d%% full. Monsters near your level have the #bColorful Yarn#k." % (
            progress * 100 / X.KNIT_GOAL))
elif sm.hasQuestCompleted(X.KNITTING) and not sm.hasQuest(X.HANGING) and not sm.hasQuestCompleted(X.HANGING):
    sm.sendSayOkay("Hang your sock on the #bChristmas Tree#k at the east end of town, dear. Bring #bSanta "
                   "Encyclopedia No.4#k with you.")
else:
    default()

sm.dispose()
