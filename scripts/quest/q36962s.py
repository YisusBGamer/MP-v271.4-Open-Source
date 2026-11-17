# 36962 Bringing Back the Brain (Lilin, Rien). Aran 2nd job: relive past training in the Mirror Cave (portal
# enterMCave -> npc/1203000.py sets clear=cl), then report to Maha (q36962e).
sm.setSpeakerID(1201000)
sm.sendNext("Maha says there's a place on the island where you can relive your past training? He must mean the "
            "#bMirror Cave#k at the Ice Cliff.")
if not sm.sendAskAccept("The #bMirror of Desire#k inside shows what you long for. Maybe it can show you your past. "
                        "Will you go?"):
    sm.dispose()
sm.startQuest(parentID)
sm.sendSayOkay("You should be able to find the Mirror Cave by travelling west out of the village.")
sm.dispose()
