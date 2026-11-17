# Ninja Castle <Chamber of Wisdom>: Baetan's riddle at the door
from net.swordie.ms.world.field.instance.instancehelper.pq import NinjaCastleInstanceHelper as NC
inst = chr.getInstance()
helper = inst.getInstanceHelper() if inst is not None else None
if not isinstance(helper, NC):
    helper = None

RIDDLES = [
    ("In which land does the Ninja Castle stand?", "Zipangu", ["Mu Lung", "Ellin Forest"]),
    ("What is the lowest rank of ninja in the castle?", "Genin", ["Chunin", "Jonin"]),
    ("Which castle soldier wears a full suit of armor?", "Kacchuu Musha", ["Kaede Kunoichi", "Sakura Genin"]),
    ("What is the false Daimyo's true form?", "Gigatoad", ["Kaede Toad", "Pitiful Toad"]),
    ("Who is Hamadaya's only daughter?", "Akai", ["Princess Suzume", "Hoshi"]),
    ("Which princess wants the false Daimyo unmasked?", "Princess Suzume", ["Akai", "Princess No"]),
    ("What does Hoshi love to spin?", "A top", ["A fan", "A parasol"]),
]

if helper is None:
    sm.warpInstanceOut(NC.REAR_CORRIDOR, 0)
elif helper.isChamberCleared(chr):
    helper.leaveChamber(chr)
elif sm.hasMobsInField():
    sm.chatRed("Defeat the guards before you try the riddle again.")
else:
    sm.setSpeakerID(9100037)
    q, right, wrong = RIDDLES[sm.getRandomIntBelow(len(RIDDLES))]
    answers = [right] + wrong
    order = []
    while answers:
        order.append(answers.pop(sm.getRandomIntBelow(len(answers))))
    text = "Answer me this, and the door is yours.\r\n#b" + q + "#k\r\n"
    for i in range(len(order)):
        text += "#L%d#%s#l\r\n" % (i, order[i])
    sel = sm.sendNext(text)
    if 0 <= sel < len(order):
        helper.answerRiddle(chr, order[sel] == right)
        if order[sel] == right:
            sm.sendNext("Correct. You may go.")
            helper.leaveChamber(chr)
sm.dispose()
