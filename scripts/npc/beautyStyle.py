# Free style picker (@style, @menu > Character, Big Headward, beauty coupons): every hairstyle, hair colour, face,
# eye colour and skin tone of the game data, previewed before it's applied. Nothing is charged.
# Optional start_on ("hair", "dye", "face", "lens", "skin", "mixdye", "mixlens") opens that part first.
from net.swordie.ms.client.character import BeautyModule, BeautyMix
from net.swordie.ms.loaders import StringData

NONE = 100000000  # sendAskAvatar's "nothing chosen"
BACK = 99
DYE_PREVIEW = 5151000  # preview coupons: hair dye, cosmetic lens, skin care
LENS_PREVIEW = 5152100
SKIN_PREVIEW = 5153000

# blocks usually worn by one gender (the rest are mixed or newer sets); everything is offered to everyone
MENS_HAIR = (30, 33, 35, 36, 40, 43, 45, 46)
WOMENS_HAIR = (31, 34, 37, 38, 41, 44, 47, 48)
MENS_FACE = (20, 25, 50)
WOMENS_FACE = (21, 24, 51)

try:
    start_on
except NameError:
    start_on = None

sm.setSpeakerID(BeautyModule.SPEAKER)


def zero():
    return chr.isZeroBeta()


def dressed():
    return BeautyModule.isDressedUp(chr)


class DressLook(object):
    # Angelic Buster's dress-up look (0 = the job default, shown as her normal look)
    def getHair(self):
        return BeautyModule.getDressHair(chr)

    def getFace(self):
        return BeautyModule.getDressFace(chr)

    def getSkin(self):
        return BeautyModule.getDressSkin(chr)


def look():
    if dressed():
        return DressLook()
    data = chr.getAvatarData()
    return data.getZeroAvatarLook() if zero() else data.getAvatarLook()


def ask(prompt, options, preview=0):
    # preview: a coupon id that sets the client's preview mode. Without one the client picks the mode from the first
    # option (hair/face style) and redraws every option in the current colour, and a list starting with skin 0 gets
    # no preview at all.
    options = [o for o in options]
    if not options:
        sm.sendSayOkay("There are no styles to show here.")
        return
    sm.getNpcScriptInfo().setIndex(preview)
    try:
        answer = sm.sendAskAvatar(prompt, dressed(), zero(), options)
    finally:
        sm.getNpcScriptInfo().setIndex(0)
    if answer != NONE and answer in options:
        if dressed():
            BeautyModule.changeDressUpLook(chr, answer)
        else:
            sm.changeCharacterLook(answer)


def block_label(block, count, mens, womens):
    who = "  #d(mostly men's)#k" if block in mens else "  #d(mostly women's)#k" if block in womens else ""
    return "{0}000 - {0}999: {1} styles{2}".format(block, count, who)


def pick_block(title, blocks, counter, mens, womens):
    text = "#e#b{}#k#n\r\nEvery style is free. Pick a set:\r\n\r\n".format(title)
    for b in blocks:
        text += "#L{}#{}#l\r\n".format(b, block_label(b, counter(b), mens, womens))
    sel = sm.sendNext(text + "\r\n#L{}##rBack#k#l".format(BACK))
    return sel if sel in blocks else None


def hairstyle():
    block = pick_block("Hairstyles", [b for b in BeautyModule.getHairBlocks()], BeautyModule.getHairStyleCount,
                       MENS_HAIR, WOMENS_HAIR)
    if block is not None:
        ask("Preview a hairstyle and pick the one you like.", BeautyModule.getHairStyles(block, look().getHair() % 10))


def hair_colour():
    ask("Pick a hair colour.", BeautyModule.getHairColours(look().getHair()), DYE_PREVIEW)


def face():
    block = pick_block("Faces", [b for b in BeautyModule.getFaceBlocks()], BeautyModule.getFaceStyleCount,
                       MENS_FACE, WOMENS_FACE)
    if block is not None:
        ask("Preview a face and pick the one you like.", BeautyModule.getFaceStyles(block, look().getFace() // 100 % 10))


def eye_colour():
    ask("Pick an eye colour.", BeautyModule.getFaceColours(look().getFace()), LENS_PREVIEW)


def skin():
    ask("Pick a skin tone.", BeautyModule.getSkins(), SKIN_PREVIEW)


HAIR_COLOURS = ("Black", "Red", "Orange", "Blonde", "Green", "Blue", "Purple", "Brown")
EYE_COLOURS = ("Black", "Blue", "Red", "Green", "Hazel", "Sapphire", "Violet", "Amethyst", "White")


def colour_name(names, c):
    return names[c] if 0 <= c < len(names) else str(c)


def mix_text(eye):
    m = BeautyMix.currentMix(chr, eye)
    if m is None:
        return ""
    return "  #d(mixed with {} {}%)#k".format(colour_name(EYE_COLOURS if eye else HAIR_COLOURS, m[0]), m[1])


def mixed_colour(eye):
    # the base colour is the current one (change it under Hair colour / Eye colour first)
    names = EYE_COLOURS if eye else HAIR_COLOURS
    current = BeautyMix.currentId(chr, eye)
    base = current // 100 % 10 if eye else current % 10
    what = "eye" if eye else "hair"
    text = "#e#bMixed {} colour#k#n\r\nYour base colour is #b{}#k. Pick the colour to mix in:\r\n\r\n".format(
        what, colour_name(names, base))
    for c in range(len(names)):
        if c != base:
            text += "#L{}#{}#l\r\n".format(c, names[c])
    if BeautyMix.currentMix(chr, eye) is not None:
        text += "\r\n#L{}##rRemove the mixed colour#k#l".format(BACK - 1)
    sel = sm.sendNext(text + "\r\n#L{}##rBack#k#l".format(BACK))
    if sel == BACK - 1:
        BeautyMix.clearFromStudio(chr, eye)
        return
    if sel == base or not 0 <= sel < len(names):
        return
    pct = sm.sendAskNumber("How much #b{}#k should the {} colour have (1-99%)? The rest stays #b{}#k.".format(
        names[sel], what, colour_name(names, base)), 50, 1, 99)
    if 1 <= pct <= 99 and not BeautyMix.applyFromStudio(chr, eye, sel, pct):
        sm.sendSayOkay("That colour blend can't be used on this look.")


def search(is_hair):
    query = sm.sendAskText("Type part of the {} name:".format("hairstyle" if is_hair else "face"), "", 1, 30)
    query = str(query).strip() if query is not None else ""
    if query == "":
        return
    found = StringData.getHairStringByName(query) if is_hair else StringData.getFaceStringByName(query)
    options = []
    current = look()
    for key in (found.keySet() if found is not None else []):
        try:
            iid = int(key)
        except ValueError:
            continue
        if is_hair:
            wanted = iid // 10 * 10 + current.getHair() % 10
            colours = [c for c in BeautyModule.getHairColours(iid)]
        else:
            wanted = iid - iid // 100 % 10 * 100 + current.getFace() // 100 % 10 * 100
            colours = [c for c in BeautyModule.getFaceColours(iid)]
        choice = wanted if wanted in colours else (colours[0] if colours and BeautyModule.exists(colours[0]) else None)
        if choice is not None and choice not in options:
            options.append(choice)
    if not options:
        sm.sendSayOkay("Nothing found for \"{}\".".format(query))
        return
    options.sort()
    ask("Found {} style(s). Pick the one you like.".format(min(len(options), 255)), options[:255])


ACTIONS = [("Hairstyle", hairstyle), ("Hair colour", hair_colour), ("Mixed hair colour", lambda: mixed_colour(False)),
           ("Face", face), ("Eye colour", eye_colour), ("Mixed eye colour", lambda: mixed_colour(True)),
           ("Skin tone", skin), ("Search hairstyles by name", lambda: search(True)),
           ("Search faces by name", lambda: search(False))]
STARTS = {"hair": hairstyle, "dye": hair_colour, "face": face, "lens": eye_colour, "skin": skin,
          "mixdye": lambda: mixed_colour(False), "mixlens": lambda: mixed_colour(True)}

if start_on in STARTS:
    STARTS[start_on]()

while True:
    al = look()
    text = "#e#bStyle studio#k#n  #d@style#k\r\nChange your look as often as you like, free of charge.{}\r\n" \
           "Hair: #b#z{}##k{}\r\nFace: #b#z{}##k{}\r\nSkin tone: #b{}#k\r\n\r\n".format(
               "\r\n(Styling Zero's Beta. Tag to Alpha to style Alpha.)" if zero() else
               "\r\n(Styling your dress-up look. Switch it off to style your normal look.)" if dressed() else "",
               al.getHair(), mix_text(False), al.getFace(), mix_text(True), al.getSkin())
    for i in range(len(ACTIONS)):
        text += "#L{}##b{}#k#l\r\n".format(i, ACTIONS[i][0])
    text += "\r\n#kAndroids are styled by Lauren in the Henesys Hair Salon."
    sel = sm.sendNext(text)
    if not 0 <= sel < len(ACTIONS):
        break
    ACTIONS[sel][1]()
sm.dispose()
