# Lagoon (2460022) | Zero's Temple: Zero stylist.
# Style list: no official GMS list is known for this salon, so it uses the Henesys (hair) and Henesys Plastic Surgery (faces) lists. Styling is free on this server, like the other beauty NPCs.
from net.swordie.ms.loaders import ItemData

HAIR_MALE = [33040, 30060, 30210, 30140, 30200, 33170, 33100, 33150, 30610]
HAIR_FEMALE = [31150, 34090, 31300, 31700, 31350, 31740, 34110, 31990, 38570, 31070]
FACE_MALE = [20000, 20001, 20003, 20004, 20005, 20006, 20007, 20008, 20012, 20014, 20015, 20022, 20028, 20031, 20016, 20032]
FACE_FEMALE = [21000, 21001, 21002, 21003, 21004, 21005, 21006, 21007, 21008, 21012, 21013, 21014, 21023, 21026, 21020, 21024, 21029]
MENU = [['hair', 'Change hairstyle'], ['dye', 'Dye hair'], ['face', 'Change face'], ['lens', 'Change eye colour'], ['skin', 'Change skin tone']]

sm.setSpeakerID(2460022)
zero = chr.isZeroBeta()
al = chr.getAvatarData().getAvatarLook() if not zero else chr.getAvatarData().getZeroAvatarLook()
female = al.getGender() == 1

def exists(item_id):
    return ItemData.getItemDeepCopy(item_id) is not None

def coloured(styles, colour):
    # keep the current colour when the style has it, otherwise show the style's own base colour
    return [s + colour if exists(s + colour) else s for s in styles]

text = "I'm Lagoon, the Transcendents' beautician. I style whichever of you is out right now: tag to Alpha or Beta to style the other one.\r\n\r\n#b"
for i in range(len(MENU)):
    text += "#L%d#%s#l\r\n" % (i, MENU[i][1])
sel = sm.sendNext(text)
key = MENU[sel][0] if 0 <= sel < len(MENU) else None
options = []
if key == "hair":
    options = coloured(HAIR_FEMALE if female else HAIR_MALE, al.getHair() % 10)
elif key == "dye":
    base = (al.getHair() // 10) * 10
    options = [base + c for c in range(8)]
elif key == "face":
    options = coloured(FACE_FEMALE if female else FACE_MALE, (al.getFace() // 100) % 10 * 100)
elif key == "lens":
    face = al.getFace()
    base = face - (face // 100) % 10 * 100
    options = [base + c * 100 for c in range(9)]
elif key == "skin":
    from net.swordie.ms.client.character import BeautyModule
    options = list(BeautyModule.getSkins())  # every skin tone of the game data
if key is not None:
    prompts = {'hair': "Choose the hairstyle you'd like. You can preview each one first!", 'dye': "Choose the hair colour you'd like.", 'face': "Choose the face you'd like. You can preview each one first!", 'lens': "Choose the eye colour you'd like.", 'skin': "Choose the skin tone you'd like."}
    answer = sm.sendAskAvatar(prompts[key], False, zero, options)
    sm.changeCharacterLook(answer)
sm.dispose()
