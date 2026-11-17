# Rose Garden monsters (8211000-8211007) die: points for the Rose Garden dailies ([Daily] Part-Time Gardener Stage
# 1-3, record 31035 "score"). Quest.wz: regular monsters 3 points, Golden Bees 25, the King Castle Golem 50. Like
# monster kill counts, the points go to the top attacker and the party members on the map.
SCORE_RECORD = 31035
DAILIES = (31044, 31045, 31046)
POINTS = {8211003: 25, 8211004: 50}
REGULAR_POINTS = 3


def add_score(c, points):
    qm = c.getQuestManager()
    if not any(qm.hasQuestInProgress(q) for q in DAILIES):
        return
    csm = c.getScriptManager()
    v = csm.getQRValue(SCORE_RECORD, "score")
    score = int(v) if v and v.isdigit() else 0
    csm.setQRValue(SCORE_RECORD, "score", str(score + points))


killer = mob.getMostDamageChar() if mob is not None else None
if killer is not None:
    points = POINTS.get(mob.getTemplateId(), REGULAR_POINTS)
    chars = set([killer])
    party = killer.getParty()
    if party is not None:
        chars.update(party.getPartyMembersInSameField(killer))
    for c in chars:
        add_score(c, points)
