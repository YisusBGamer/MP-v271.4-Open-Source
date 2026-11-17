# Neinheart (1101002), Ereve.
# After the Black Mage's defeat ([Limina] Defying Destiny, 35815) at level 255, he also takes you to the Evening
# Conference Pavilion, where the Genesis Weapon quests take place (official: from the quest notifier).
# From level 120 he also runs the Black Heaven story (official: from the event notifier) and starts or continues the
# current act at its current stage (quest/q3312Xs scripts and the stage maps' scripts, data in bhgen/bh_story.py);
# finishing Act 6 gives the Maple World Savior medal.
# From level 135, Heroes of Maple: each act is a short series of real fights in its maps (finishing it also opens
# Damien in the boss window).
BH_ACTS = (
    (33120, 'Act 1: Secrets in the Wings', (350011010, 350011020, 350011100, 350011200, 350011300, 350011800, 350011900, 350011400, 350011430, 350011500, 350011600, 350013000, 350013100, 350013200, 350013300, 350013500, 350013600), '10 Boogies went missing spying on the Black Wings in Verne Mine.'),
    (33121, 'Act 2: To War, Lumiere', (350020090, 350020100, 350020300, 350020120, 350020700, 350022100, 350022200, 350022300, 350023100, 350023200, 350023300, 350023400, 350023500, 350023600, 350024000, 350024100), 'The Maple Alliance sets sail aboard the Lumiere.'),
    (33122, 'Act 3: Clash of the Heavens', (350031000, 350031004, 350031200, 350031300, 350031400, 350031600, 350032100, 350032200, 350032300, 350032400, 350033000, 350033100, 350033200), 'The Black Heaven attacks the Lumiere at dawn.'),
    (33123, 'Act 4: Returning Fire', (350040000, 350040020, 350040030, 350040100, 350040130, 350040150, 350040170, 350040200, 350040240, 350040260, 350042050, 350042100, 350042200, 350042350, 350042400, 350042700, 350043001), 'Francis knows a way onto the Black Heaven - through the Scrapyard.'),
    (33124, 'Act 5: To the Core!', (350050000, 350050300, 350051100, 350051200, 350051400, 350052150, 350052200, 350052400, 350052600, 350053000, 350054000, 350055000, 350056100, 350058050, 350058150, 350058300, 350058600), 'The Alliance storms the Black Heaven, zone by zone, toward the core.'),
    (33125, 'Act 6: Black Heaven', (350060000, 350061100, 350061120, 350062200, 350062110, 350062130, 350062150, 350062400, 350063005, 350063200), 'Lotus, Gelimer, and the fate of Maple World.'),
)

HOM_ACTS = (
    (33910, 'Act 1: Slumbering Dragon Island', 350110010, ((8240132, 0, 399), (8240132, 250, 399), (8240132, 500, 399), (8240132, 750, 399), (8240132, 1000, 399)), 'Devil Murukun swarm the shores of Slumbering Dragon Island.'),
    (33911, 'Act 1.5: Reunion', 350120200, ((8240162, -1000, 268), (8240162, -700, 268), (8240162, -400, 268), (8240162, -100, 268), (8240162, 200, 268)), 'Ruins scorpions block the way through the Nihal Desert.'),
    (33912, 'Act 2: Abraxas', 350120900, ((8240163, -900, 268), (8240163, -300, 268), (8240163, 300, 268), (8240163, 900, 268), (8240163, 1500, 268)), 'Ruins spirits rise before the gate.'),
    (33913, 'Act 3: Unfamiliar World', 350131300, ((8240177, -800, 234), (8240177, -450, 234), (8240177, -100, 234), (8240177, 250, 234), (8240177, 600, 234)), 'Guard robots patrol the lobby.'),
    (33914, 'Final Act: The World Tree', 350142010, ((8240180, 0, 17), (8240180, 400, 17), (8240180, 800, 17), (8240180, 1200, 17), (8240180, 1600, 17)), "The World Tree's polluted sap lashes out."),
)


def genesis_ok():
    return chr.getLevel() >= 255 and (sm.hasQuestCompleted(35815) or sm.hasQuest(35650) or sm.hasQuestCompleted(35650))


def next_act():
    for act in BH_ACTS:
        if not sm.hasQuestCompleted(act[0]):
            return act
    return None


def run_act(acts, story):
    act = None
    for a in acts:
        if not sm.hasQuestCompleted(a[0]):
            act = a
            break
    if act is None:
        sm.sendSayOkay("You've seen {} through to the end. Thank you.".format(story))
        return
    qid, name, first_map, mobs, line = act
    verb = "Continue" if sm.hasQuest(qid) else "Begin"
    if not sm.sendAskYesNo("#e{}#n\r\n\r\n{}\r\n\r\n{} this act now? (A few battles in a row; you'll come back "
                           "here when it's over.)".format(name, line, verb)):
        return
    if not sm.hasQuest(qid):
        sm.startQuestNoCheck(qid)
    sm.setQRValue(qid, "ret", str(sm.getFieldID()))
    sm.warpInstanceIn(first_map, 0)
    field = chr.getField()
    if field.getId() == first_map:
        for mob, x, y in mobs:
            field.spawnMob(mob, x, y, False, 0)


def black_heaven():
    if not sm.hasQuestCompleted(33127):  # [Black Heaven] For Maple World
        sm.sendNext("Gelimer's Black Wings have built a flying fortress, the #bBlack Heaven#k. The Cygnus Knights and the "
                    "Resistance are joining forces aboard the airship #bLumiere#k to stop it. We need you with us.")
        if not sm.hasQuest(33127):
            sm.startQuestNoCheck(33127)
        sm.completeQuestNoCheck(33127)
    act = next_act()
    if act is None:
        sm.sendSayOkay("The Black Heaven has fallen, thanks to you. The Alliance won't forget it.")
        return
    qid, name, maps, line = act
    verb = "Continue" if sm.hasQuest(qid) else "Begin"
    if not sm.sendAskYesNo("#e{}#n\r\n\r\n{}\r\n\r\n{} this act now?".format(name, line, verb)):
        return
    if not sm.hasQuest(qid):
        sm.startQuestNoCheck(qid)
        sm.setQRValue(qid, "stage", "0")
    try:
        idx = int(sm.getQRValue(qid, "stage"))
    except:
        idx = 0
    sm.setQRValue(qid, "ret", str(sm.getFieldID()))
    sm.warpInstanceIn(maps[min(idx, len(maps) - 1)], 0)


sm.setSpeakerID(1101002)
if sm.hasQuest(20860) and not sm.hasQuestCompleted(20860):
    # "The Five Paths" can be started without its start script completing it: approve now, so the paths open
    sm.completeQuestNoRewards(20860)
    sm.sendSayOkay("Very well. You have my approval as a Knight-in-Training. Go to the Chief Knight whose path you "
                   "wish to follow.")
elif sm.hasQuest(20320):
    if sm.sendAskYesNo("Are you ready to enter the Test area?"):
        sm.warpInstanceIn(913070200)
        sm.setInstanceTime(300, 130000000)
elif sm.hasQuest(20411):
    if sm.sendAskYesNo("Are you ready to leave?"):
        sm.warpInstanceIn(913070100, 0)
        sm.setInstanceTime(300, 130000000)
else:
    options = []
    if chr.getLevel() >= 120:
        options.append(("bh", "The Black Heaven"))
    if chr.getLevel() >= 135:
        options.append(("hom", "Heroes of Maple"))
    if genesis_ok():
        options.append(("genesis", "The Evening Conference Pavilion (Genesis Weapon)"))
    if len(options) == 0:
        sm.sendSayOkay("The Cygnus Knights stand ready. Is there something you need?")
    else:
        text = "The Alliance has work for you. What would you like to discuss?\r\n\r\n#b"
        for i in range(len(options)):
            text += "#L{}#{}#l\r\n".format(i, options[i][1])
        sel = sm.sendNext(text)
        if 0 <= sel < len(options):
            if options[sel][0] == "bh":
                black_heaven()
            elif options[sel][0] == "hom":
                sm.sendNext("Damien, the Sword of Destruction, is gathering demons on Slumbering Dragon Island. The "
                            "Heroes are regrouping - and they could use your help.")
                run_act(HOM_ACTS, "Heroes of Maple")
            elif sm.sendAskYesNo("The Alliance is meeting at the #bEvening Conference Pavilion#k about what the Black "
                                 "Mage left behind. Shall I take you there?"):
                sm.warp(993060090, 0)
sm.dispose()
