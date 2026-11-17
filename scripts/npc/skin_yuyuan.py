# Yura (9310550) | Yu Garden: beauty treatment.
# Style list: the same skin tones as the other dermatologists. Styling is free on this server, like the other beauty NPCs.
from net.swordie.ms.loaders import ItemData

MENU = [['skin', 'Change my skin tone']]

sm.setSpeakerID(9310550)
zero = chr.isZeroBeta()
al = chr.getAvatarData().getAvatarLook() if not zero else chr.getAvatarData().getZeroAvatarLook()
female = al.getGender() == 1

def exists(item_id):
    return ItemData.getItemDeepCopy(item_id) is not None

def coloured(styles, colour):
    # keep the current colour when the style has it, otherwise show the style's own base colour
    return [s + colour if exists(s + colour) else s for s in styles]

text = "Welcome! With our skin care you can preview your new skin tone before you decide.\r\n\r\n#b"
for i in range(len(MENU)):
    text += "#L%d#%s#l\r\n" % (i, MENU[i][1])
sel = sm.sendNext(text)
key = MENU[sel][0] if 0 <= sel < len(MENU) else None
options = []
if key == "skin":
    from net.swordie.ms.client.character import BeautyModule
    options = list(BeautyModule.getSkins())  # every skin tone of the game data
if key is not None:
    prompts = {'skin': "Choose the skin tone you'd like."}
    answer = sm.sendAskAvatar(prompts[key], False, zero, options)
    sm.changeCharacterLook(answer)
sm.dispose()
