# Plastic Roy (9000124 / 2012036), in most towns: royal plastic surgery. Styling is free on this server, like the
# other face and hair NPCs: pick a face set for your gender, or the unisex/event sets, and keep your eye colour.
from net.swordie.ms.loaders import StringData

al = chr.getAvatarData().getAvatarLook() if not chr.isZeroBeta() else chr.getAvatarData().getZeroAvatarLook()

SETS = [(21000, "Face Set 1"), (24000, "Face Set 2"), (51000, "Face Set 3")] if al.getGender() \
    else [(20000, "Face Set 1"), (25000, "Face Set 2"), (50000, "Face Set 3")]
SETS = SETS + [(23000, "Unisex / Event Set 1"), (26000, "Unisex / Event Set 2"), (27000, "Unisex / Event Set 3"),
               (28000, "Unisex / Event Set 4")]

text = "Hi! I'm Plastic Roy. Want a royal new look? Which faces would you like to see?\r\n\r\n#b"
for i, (start, label) in enumerate(SETS):
    text += "#L%d#%s (%d ~ %d)#l\r\n" % (i, label, start, start + 999)
sel = sm.sendNext(text)
if 0 <= sel < len(SETS):
    start = SETS[sel][0]
    eye = (al.getFace() // 100) % 10 * 100  # eye colour is the hundreds digit of a face id
    options = [o + eye for o in StringData.getFaces(start, start + 999) if (o // 100) % 10 == 0]
    if len(options) == 0:
        sm.sendSayOkay("Sorry, I have no faces from that set right now.")
    else:
        answer = sm.sendAskAvatar("I can change your face to anything you'd like!", False, chr.isZeroBeta(), options)
        sm.changeCharacterLook(answer)
sm.dispose()
