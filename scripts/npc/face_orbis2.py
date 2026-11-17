# Riza the Assistant (2012009) | Orbis Plastic Surgery: assistant (choose from the regular list).
# Style list: official GMS list (as collected by the HeavenMS project from GMS). Styling is free on this server, like the other beauty NPCs.
from net.swordie.ms.loaders import ItemData

FACE_MALE = [20003, 20011, 20021, 20022, 20023, 20027, 20031]
FACE_FEMALE = [21004, 21007, 21010, 21012, 21020, 21021, 21030]
MENU = [['face', 'Change my face'], ['lens', 'Change my eye colour']]

sm.setSpeakerID(2012009)
zero = chr.isZeroBeta()
al = chr.getAvatarData().getAvatarLook() if not zero else chr.getAvatarData().getZeroAvatarLook()
female = al.getGender() == 1

def exists(item_id):
    return ItemData.getItemDeepCopy(item_id) is not None

def coloured(styles, colour):
    # keep the current colour when the style has it, otherwise show the style's own base colour
    return [s + colour if exists(s + colour) else s for s in styles]

text = "Hi! I'm not the head doctor, but I can still give you a new face. Pick one you like!\r\n\r\n#b"
for i in range(len(MENU)):
    text += "#L%d#%s#l\r\n" % (i, MENU[i][1])
sel = sm.sendNext(text)
key = MENU[sel][0] if 0 <= sel < len(MENU) else None
options = []
if key == "face":
    options = coloured(FACE_FEMALE if female else FACE_MALE, (al.getFace() // 100) % 10 * 100)
elif key == "lens":
    face = al.getFace()
    base = face - (face // 100) % 10 * 100
    options = [base + c * 100 for c in range(9)]
if key is not None:
    prompts = {'face': "Choose the face you'd like. You can preview each one first!", 'lens': "Choose the eye colour you'd like."}
    answer = sm.sendAskAvatar(prompts[key], False, zero, options)
    sm.changeCharacterLook(answer)
sm.dispose()
