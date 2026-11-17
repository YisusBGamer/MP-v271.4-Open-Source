# Nurse Ling Ling (9330024) | Kerning City Night Market: assistant (choose from the regular list).
# Style list: no official GMS list is known for this salon, so it uses the Henesys lists. Styling is free on this server, like the other beauty NPCs.
from net.swordie.ms.loaders import ItemData

FACE_MALE = [20000, 20005, 20008, 20012, 20016, 20022, 20032]
FACE_FEMALE = [21000, 21002, 21008, 21014, 21020, 21024, 21029]
MENU = [['face', 'Change my face'], ['lens', 'Change my eye colour']]

sm.setSpeakerID(9330024)
zero = chr.isZeroBeta()
al = chr.getAvatarData().getAvatarLook() if not zero else chr.getAvatarData().getZeroAvatarLook()
female = al.getGender() == 1

def exists(item_id):
    return ItemData.getItemDeepCopy(item_id) is not None

def coloured(styles, colour):
    # keep the current colour when the style has it, otherwise show the style's own base colour
    return [s + colour if exists(s + colour) else s for s in styles]

text = "I'm Dr. Nihao's nurse. I can give you a new face too!\r\n\r\n#b"
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
