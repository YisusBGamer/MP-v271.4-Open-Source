# Honnou-ji Eastern Wall (807050200), Hayato and Kanna story (no script in the map data, see CustomFieldScripts):
# The Demon's Betrayer (Hayato 57153 / Kanna 57449) ends at Hashiba Hideyoshi, who starts Questionable Allegiance
# (57154 / 57450): 10 Oda Pursuers interrupt the meeting, then Honda Tadakatsu takes the report and starts No Love for
# the Demon King (57155 / 57451). Only inside the copy of the map enterHonnouji makes for these quests.
# Kanna's copies of the quests are accepted too.
HIDEYOSHI = 9130025
TADAKATSU = 9130027
ODA_PURSUER = 9421569
BETRAYER, ALLEGIANCE, NO_LOVE = (57153, 57449), (57154, 57450), (57155, 57451)


def has(qs):
    return any(sm.hasQuest(q) for q in qs)


def done(qs):
    return any(sm.hasQuestCompleted(q) for q in qs)


def had(qs):
    return any(sm.hasHadQuest(q) for q in qs)


pos = chr.getPosition()
if has(BETRAYER) or (done(BETRAYER) and not had(ALLEGIANCE)):
    sm.spawnNpc(HIDEYOSHI, pos.getX() + 250, pos.getY())
if has(ALLEGIANCE) or (done(ALLEGIANCE) and not had(NO_LOVE)):
    sm.spawnNpc(TADAKATSU, pos.getX() + 400, pos.getY())
if has(ALLEGIANCE) and not sm.hasMobsInField():
    for dx in (-300, -200, -100, 100, 200, 300, 450, 550, 650, 750):
        sm.spawnMob(ODA_PURSUER, pos.getX() + dx, pos.getY(), False)
