# @gear - Gear Guide: which equipment slots are still empty, and where to farm items for them. See GearGuideModule.
from net.swordie.ms.client.character import GearGuideModule

cats = GearGuideModule.getCategories()
level = chr.getLevel()

# where slots that no monster drops come from (shops, and which bosses for androids)
NOTES = {
    "ANDROID": "#eHow to get an Android#n\r\n"
               "#b#i1662088# Damienroid#k: Hard Damien\r\n"
               "#b#i1662111# Lucidroid#k: Hard Lucid (her Final Music Box)\r\n"
               "#b#i1662132# Adam Wondroid#k / #b#i1662133# Eve Wondroid#k: Chaos Papulatus\r\n"
               "An android needs a #bMechanical Heart#k of the same grade or lower to work: the Android Shop "
               "(#b@menu#k > Shops) sells hearts, and a basic Android.",
    "HEART": "#eShop#n: the Android Shop (#b@menu#k > Shops) sells the permanent hearts (Iron to Lidium) and the "
             "Electronic Heart series (14 days). A heart only works with an Android of its grade or higher.",
    "POCKET": "#eShop#n: Nave at the Henesys Market sells pocket items for 50,000 mesos; Laku in Monster Park trades "
              "Spiegelmann's Autograph for 100 Monster Park coins. The slot opens at Charm level 30.",
    "BADGE": "#eShop#n: Laku in Monster Park has Spiegelmann's Badge Chest for 30 Monster Park coins.",
    "EMBLEM": "#eShop#n: Shiro in the Free Market sells the gold class emblems for 10,000,000 mesos.",
}


def show_category(cat):
    have = GearGuideModule.countEquipped(chr, cat)
    text = "#e#b{}#k#n  (equipped: {}/{})\r\n\r\n".format(cat.getTitle(), have, cat.getSlots())
    bosses = GearGuideModule.getBossSources(chr, cat)
    if not bosses.isEmpty():
        text += "#eBosses#n  (#gGreen#k: around or below your level)\r\n"
        for src in bosses:
            color = "#g" if src.level <= level + 10 else "#r"
            icons = "".join(["#i{}#".format(i) for i in list(src.items)[:5]])
            more = " +{}".format(src.items.size() - 5) if src.items.size() > 5 else ""
            text += "{}{}#k (Lv. {})\r\n{}{}\r\n".format(color, src.name, src.level, icons, more)
        text += "\r\n"
    mobs = GearGuideModule.getMonsterSources(chr, cat)
    if not mobs.isEmpty():
        text += "#eMonsters near your level#n\r\n"
        for src in mobs:
            icons = "".join(["#i{}#".format(i) for i in list(src.items)[:4]])
            text += "#b{}#k (Lv. {}) {}\r\n".format(src.name, src.level, icons)
        text += "\r\n"
    note = NOTES.get(cat.name())
    if note:
        text += note + "\r\n\r\n"
    if GearGuideModule.hasGenericDrops(cat):
        text += "Monsters near your level also drop #b{}#k for your class. Use #b@menu#k > Travel > Hunting Grounds to find maps " \
                "for your level.\r\n".format(cat.getTitle().lower())
    if bosses.isEmpty() and mobs.isEmpty() and not GearGuideModule.hasGenericDrops(cat):
        text += "No monster or boss drops this for your class on this server. Look for it in quests, events and " \
                "shops.\r\n"
    text += "\r\nTip: #b@menu#k > Info shows what the monsters in your map drop, and who drops an item."
    sm.sendNext(text)


def show_progression():
    sm.sendNext("#e#bPost-200 equipment roadmap#k#n\r\n\r\n"
                "#eStarter#n\r\n"
                "Use level 140 #bPensalir#k armor and weapon while filling the #bBoss Accessory Set#k from Zakum, "
                "Horntail, Hilla, Pink Bean and Magnus. Set thresholds matter more than a small raw-stat gain.\r\n\r\n"
                "#eMid-game#n\r\n"
                "Build #bCRA hat, top and bottom#k. Pair them with five #bAbsoLab#k pieces: weapon, shoulder, cape, "
                "shoes and gloves. CRA top and bottom remain valuable because two separate items provide twice the "
                "potential and Star Force slots of one overall.\r\n\r\n"
                "#eLate-game#n\r\n"
                "Replace those five AbsoLab slots with #bArcane Umbra#k as pieces become available. Do not vendor a "
                "higher-tier piece just because equipping one by itself would temporarily break a completed set.")
    sm.sendSay("#e#bAccessory roadmap#k#n\r\n\r\n"
               "Start with the #bBoss Accessory Set#k, then build the four-piece #bSuperior Gollux Set#k: ring, "
               "pendant, earrings and belt. Its final bonus gives 30% Boss Damage and 30% Ignore DEF. "
               "#bPitched Boss#k pieces are the final accessory tier available in this v232 data.\r\n\r\n"
               "#eEnhancement checkpoints#n\r\n"
               "Use #b@enhance#k for scrolling and Star Force. Add three-line potentials, then use the AutoCuber. "
               "A practical stopping point is two useful Legendary lines; perfect three-prime-line results can "
               "take many cubes. #b@upgrades#k now includes real set bonuses and gives recognized progression sets "
               "extra weight.")


def show_potentials():
    sm.sendNext("#e#bOptimal regular potential targets#k#n\r\n\r\n"
                "The AutoCuber reads the exact v232 option pool for the selected item, so its displayed percentages "
                "change with the item's required level.\r\n\r\n"
                "#eWeapon / Secondary#n: perfect boss mix is two 40% Boss lines plus one ATT/Magic ATT line. That "
                "attack line is 12% on level 150 gear and 13% on level 160+ gear. Triple ATT is also offered.\r\n"
                "#eEmblem#n: cannot roll Boss Damage; triple ATT/Magic ATT is the pure damage target, with an "
                "ATT plus Ignore DEF mix available when needed.\r\n"
                "#eGloves#n: 8% Critical Damage per prime line, up to 24% theoretical.\r\n"
                "#eHat#n: -2 seconds per prime cooldown line, up to -6 seconds for classes that benefit; otherwise "
                "use main stat.\r\n"
                "#eOther armor/accessories#n: three prime main-stat lines total 36% on level 150 gear or 39% on "
                "level 160+ gear. Xenon receives All Stat targets and Demon Avenger receives HP targets.")
    sm.sendSay("#e#bSpecial and bonus potential targets#k#n\r\n\r\n"
               "Rings, pendants, face accessories, eye accessories and earrings can also target three 20% Item "
               "Drop or Meso Obtained lines. Bonus-potential perfect and two-line targets are calculated from the "
               "same item-specific data instead of using one hard-coded percentage for every level.\r\n\r\n"
               "Damage-optimal WSE lines depend on your current Boss Damage and Ignore DEF. The presets therefore "
               "offer triple ATT, a balanced Boss mix, and an Ignore DEF mix rather than claiming one combination "
               "is best for every character.")


while True:
    text = "#e#bGear Guide#k#n\r\n#L100##bPost-200 equipment and set roadmap#k#l\r\n" \
           "#L101##bOptimal potential targets#k#l\r\n\r\n" \
           "Pick a slot to see where to farm it. #rRed#k slots aren't full yet.\r\n\r\n"
    for i in range(len(cats)):
        cat = cats[i]
        have = GearGuideModule.countEquipped(chr, cat)
        color = "#g" if have >= cat.getSlots() else "#r"
        text += "#L{}#{}{}#k  ({}/{})#l\r\n".format(i, color, cat.getTitle(), have, cat.getSlots())
    sel = sm.sendNext(text)
    if sel == 100:
        show_progression()
    elif sel == 101:
        show_potentials()
    elif 0 <= sel < len(cats):
        show_category(cats[sel])
    else:
        break
