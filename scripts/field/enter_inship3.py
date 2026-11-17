# Black Heaven: the six acts play through Black Heaven's own maps, monsters and stage quests. The client has no
# lines for this story, so the scenes follow the quests' Quest.wz summaries. Stage data: bhgen/bh_story.py.
# This map's enter script (enter_inship3): 350022300.

ACT_ORDER = (33120, 33121, 33122, 33123, 33124, 33125)
ACT_MAPS = {33120: [350011010,
         350011020,
         350011100,
         350011200,
         350011300,
         350011800,
         350011900,
         350011400,
         350011430,
         350011500,
         350011600,
         350013000,
         350013100,
         350013200,
         350013300,
         350013500,
         350013600],
 33121: [350020090,
         350020100,
         350020300,
         350020120,
         350020700,
         350022100,
         350022200,
         350022300,
         350023100,
         350023200,
         350023300,
         350023400,
         350023500,
         350023600,
         350024000,
         350024100],
 33122: [350031000,
         350031004,
         350031200,
         350031300,
         350031400,
         350031600,
         350032100,
         350032200,
         350032300,
         350032400,
         350033000,
         350033100,
         350033200],
 33123: [350040000,
         350040020,
         350040030,
         350040100,
         350040130,
         350040150,
         350040170,
         350040200,
         350040240,
         350040260,
         350042050,
         350042100,
         350042200,
         350042350,
         350042400,
         350042700,
         350043001],
 33124: [350050000,
         350050300,
         350051100,
         350051200,
         350051400,
         350052150,
         350052200,
         350052400,
         350052600,
         350053000,
         350054000,
         350055000,
         350056100,
         350058050,
         350058150,
         350058300,
         350058600],
 33125: [350060000,
         350061100,
         350061120,
         350062200,
         350062110,
         350062130,
         350062150,
         350062400,
         350063005,
         350063200]}
ACT_NAMES = {33120: 'Act 1: Secrets in the Wings', 33121: 'Act 2: To War, Lumiere', 33122: 'Act 3: Clash of the Heavens', 33123: 'Act 4: Returning Fire', 33124: 'Act 5: To the Core!', 33125: 'Act 6: Black Heaven'}
HONOR = {33120: 1000, 33121: 2500, 33122: 5000, 33123: 6500, 33124: 7500, 33125: 12500}
TITLE_QUESTS = {1: 33304, 2: 33305, 3: 33306, 4: 33307, 5: 33308, 6: 33309, 7: 33310}
ACT_REWARD_QUESTS = {33121: (33179, 33285), 33123: (33286,), 33125: (33288, 33294)}
ACT_TITLE = {33121: 2}


def current_act():
    for a in ACT_ORDER:
        if sm.hasQuest(a):
            try:
                return a, int(sm.getQRValue(a, "stage"))
            except:
                return a, 0
    return None, None


def say(lines):
    for who, text in lines:
        if who == 'P':
            sm.setPlayerAsSpeaker()
        else:
            sm.setSpeakerID(who)
        sm.sendNext(text)


def milestone(quests):
    for q in quests:
        if not sm.hasQuestCompleted(q):
            if not sm.hasQuest(q):
                sm.startQuestNoCheck(q)
            sm.completeQuestNoCheck(q)


def give_title(tag):
    sm.setQRValue(33304, "tag%d" % tag, "1")
    milestone([TITLE_QUESTS[tag]])
    if all(sm.getQRValue(33304, "tag%d" % t) == "1" for t in range(1, 8)):
        sm.setQRValue(33304, "tag8", "1")
        milestone([33311])  # Heart of the Alliance: every Black Heaven title


def finish_act(act):
    sm.completeQuestNoCheck(act)
    chr.addHonorExp(HONOR[act])
    milestone(ACT_REWARD_QUESTS.get(act, ()))
    if act in ACT_TITLE:
        give_title(ACT_TITLE[act])
    if act == 33125:
        sm.setQRValue(500663, "BH_check", "clear")  # what the boss window checks for Lotus
    sm.chatScript("[Black Heaven] %s complete! (+%d Honor EXP)" % (ACT_NAMES[act], HONOR[act]))
    try:
        back = int(sm.getQRValue(act, "ret"))
    except:
        back = 130000000
    sm.warpInstanceOut(back if back > 0 else 130000000, 0)


def advance(act, idx):
    maps = ACT_MAPS[act]
    if idx + 1 >= len(maps):
        finish_act(act)
    else:
        sm.setQRValue(act, "stage", str(idx + 1))
        sm.warp(maps[idx + 1], 0)


def end_stage(act, idx, st):
    say(st.get('after', ()))
    milestone(st.get('done', ()))
    if st.get('title'):
        give_title(st['title'])
    advance(act, idx)

STAGES = {350022300: {(33121, 7): {'done': [33145],
                          'kind': 'fight',
                          'lines': [(1540446, 'Last corridor!')],
                          'map': 350022300}}}


act, idx = current_act()
st = STAGES.get(sm.getFieldID(), {}).get((act, idx)) if act else None
if st is not None:
    sm.setQRValue(act, "map", str(sm.getFieldID()))
    say(st.get('lines', ()))
    if st['kind'] == 'scene':
        end_stage(act, idx, st)
    else:
        field = chr.getField()
        for mob in field.getMobs():
            mob.setNotRespawnable(True)  # the map's own monsters: beat them all once
        spawn = [s for s in st.get('spawn', ()) if not sm.hasMobsInField(sm.getFieldID(), s[0])]
        if spawn:
            pos = chr.getPosition()
            for mobId, dx in spawn:
                sm.spawnMob(mobId, pos.getX() + dx, pos.getY(), False)
        if not sm.hasMobsInField():
            end_stage(act, idx, st)  # nothing to fight here (already cleared)
