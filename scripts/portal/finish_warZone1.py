# east00 on the Heliseum battlegrounds (401070000-401073300): once every Specter on the stage is beaten, on to the
# next stage, or out after the last one. Leaving through the last stage wins the battle: the record of every
# battlefield quest held for this battleground is set (their end checks wait for it). Each stage's Specters:
# field/enter_warZone1_*.py.
# quest: (last stage, record, exit field, exit portal)
BATTLES = (
    (31802, 401070000, 31803, 401000000, 1),  # Heliseum Reclamation HQ in Danger (Magnus prequest)
    (31402, 401070000, 31550, 401000000, 1),  # Specters Attack (Kaiser)
    (31601, 401070000, 31602, 401000000, 1),  # Backstage Pass (Angelic Buster)
    (31806, 401070200, 31807, 401000000, 1),  # Securing the Front Lines (Magnus prequest)
    (31406, 401070200, 31551, 401000000, 1),  # Push the Line (Kaiser)
    (31439, 401070500, 31553, 401000000, 1),  # Rescuing Captain Tiron (Kaiser)
    (31440, 401070900, 31554, 401000000, 1),  # Operation, Go! (Kaiser)
    (31651, 401070900, 31652, 401000000, 1),  # Dream Team (Angelic Buster)
    (31442, 401071200, 31556, 401040000, 0),  # Heliseum or Bust (Kaiser)
    (31490, 401071300, 31558, 401040000, 0),  # Defensive Battle (Kaiser)
    (31455, 401071500, 31549, 401040000, 0),  # Rival Revival (Kaiser)
    (31470, 401071800, 31557, 401040000, 0),  # The Cold Sting of Friendship (Kaiser)
    (31497, 401071800, 31565, 401040000, 0),  # Buying Some Time (Kaiser)
    (31498, 401072100, 31566, 401040000, 0),  # Through the Gate (Kaiser)
    (31617, 401072400, 31618, 401000000, 1),  # When in Doubt, Patrol About (Angelic Buster)
    (31621, 401072700, 31622, 401000000, 1),  # The Cannonball's Wake (Angelic Buster)
    (31491, 401073100, 31559, 401040000, 0),  # Counteroffensive (Kaiser)
    (31677, 401073100, 31678, 401040000, 0),  # Plan B (Angelic Buster)
    (31653, 401073300, 31654, 401030600, 0),  # Edea's Magical Surprise (Angelic Buster)
)
NEXT_STAGE = {
    401070100: 401070200,
    401070300: 401070400, 401070400: 401070500,
    401070600: 401070700, 401070700: 401070800, 401070800: 401070900,
    401071000: 401071100, 401071100: 401071200,
    401071400: 401071500,
    401071600: 401071700, 401071700: 401071800,
    401071900: 401072000, 401072000: 401072100,
    401072200: 401072300, 401072300: 401072400,
    401072500: 401072600, 401072600: 401072700,
    401072800: 401072900, 401072900: 401073000, 401073000: 401073100,
    401073200: 401073300,
}
# without any of the quests: the last stage's return field (Map.wz returnMap; zone 11 starts from the Black Market)
EXIT = {
    401071300: (401040000, 0), 401071500: (401040000, 0), 401071800: (401040000, 0), 401072100: (401040000, 0),
    401073100: (401040000, 0), 401073300: (401030600, 0),
}
# the end NPC isn't placed on any map: the battle is reported on the spot
REPORT_HERE = {31601: 3001015, 31651: 3000018}
BADGE = 4033361  # Specter Commander Badge, from the odd Specter of Push the Line's last stage

field = sm.getFieldID()
held = [b for b in BATTLES if b[1] == field and sm.hasQuest(b[0])]
needs_badge = sm.hasQuest(31406) and field == 401070200 and not sm.hasItem(BADGE)

if sm.hasMobsInField():
    sm.chat("Please eliminate all mobs.")
elif field in NEXT_STAGE:
    sm.warp(NEXT_STAGE[field])
elif needs_badge and not sm.canHold(BADGE):
    sm.chat("Please make room in your ETC inventory.")
else:
    if field == 401070000:
        sm.createQuestWithQRValue(31803, "1")
    elif field == 401070200:
        sm.setQRValue(31807, "1", False)
    for quest, last, record, out, portal in held:
        if quest not in (31802, 31806):
            sm.startQuest(record)
            sm.setQRValue(record, "1")
    if needs_badge:
        sm.giveItem(BADGE)
    for quest, last, record, out, portal in held:
        if quest in REPORT_HERE and not sm.questEndDialogue(quest, REPORT_HERE[quest]):
            sm.completeQuestNoCheck(quest)  # nobody else to hand it in to
    if held:
        out, portal = held[0][3], held[0][4]
    else:
        out, portal = EXIT.get(field, (401000000, 1))
    sm.warpInstanceOut(out, portal)
sm.dispose()
