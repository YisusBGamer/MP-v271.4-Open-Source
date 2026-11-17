# Brittany (1012104) | Henesys Hair Salon: assistant (choose from the regular list).
# Style list: official GMS salon list (AyumiLove's MapleStory salon lists). Styling is free on this server, like the other beauty NPCs.
from net.swordie.ms.loaders import ItemData

HAIR_MALE = [33040, 30060, 33150, 33170, 30210, 33100, 30610, 30140, 30200]
HAIR_FEMALE = [31150, 34090, 31300, 31700, 31990, 31350, 34110, 38570, 31070]
MENU = [['hair', 'Change my hairstyle'], ['dye', 'Dye my hair']]

sm.setSpeakerID(1012104)
zero = chr.isZeroBeta()
al = chr.getAvatarData().getAvatarLook() if not zero else chr.getAvatarData().getZeroAvatarLook()
female = al.getGender() == 1

def exists(item_id):
    return ItemData.getItemDeepCopy(item_id) is not None

def coloured(styles, colour):
    # keep the current colour when the style has it, otherwise show the style's own base colour
    return [s + colour if exists(s + colour) else s for s in styles]

text = "Hi, welcome to the Henesys Hair Salon! I'm still learning, but I'll do my best! Pick a style you like.\r\n\r\n#b"
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
if key is not None:
    prompts = {'hair': "Choose the hairstyle you'd like. You can preview each one first!", 'dye': "Choose the hair colour you'd like."}
    answer = sm.sendAskAvatar(prompts[key], False, zero, options)
    sm.changeCharacterLook(answer)
sm.dispose()
