# @guide - what would raise this character's boss damage the most, and where to get it on this server. @gear covers
# the empty equipment slots and who drops items for them. See GuideModule.
from net.swordie.ms.client.character import GuideModule

tips = GuideModule.getTips(chr)
sections = [GuideModule.QUICK, GuideModule.GEAR, GuideModule.CHARACTER, GuideModule.BUFFS]
colors = {GuideModule.QUICK: "#g", GuideModule.GEAR: "#b", GuideModule.CHARACTER: "#d", GuideModule.BUFFS: "#r"}

# one page per section, so no page gets too long for the dialog
pages = []
n = 1
for section in sections:
    entries = [t for t in tips if t.getSection() == section]
    if not entries:
        continue
    page = "#e{}{}#k#n\r\n\r\n".format(colors[section], section)
    for t in entries:
        page += "{}#e{}. {}#n#k\r\n{}\r\n\r\n".format(colors[section], n, t.getTitle(), t.getText())
        n += 1
    pages.append(page)

intro = "#e#bDamage Guide#k#n - what would make you stronger against bosses, most worthwhile first " \
        "({} tips, {} pages).\r\n\r\n".format(n - 1, len(pages))
for i in range(len(pages)):
    text = (intro if i == 0 else "") + pages[i]
    if i == len(pages) - 1:
        sm.sendSayOkay(text + "#dCheck again after upgrading (@menu > Gear) to see what's next.#k")
    else:
        sm.sendNext(text)
