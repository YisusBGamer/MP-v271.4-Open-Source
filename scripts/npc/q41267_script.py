# 2460014 Magic Pentacle (Mirror World Magatia) - Keeny the Genius (41267): draw the pentacle three times, following
# Keeny's design. Record 41271 counts the drawings (the Magic Pentacle's look follows it, Npc.wz conditions).
DRAWINGS = 3
if sm.hasQuest(41267) and sm.getQRValue(41267, "clear") != "clear":
    done = sm.getQRValue(41271)
    done = int(done) if done and done.isdigit() else 0
    if sm.sendAskYesNo("#b(Keeny's Pentacle Design is full of tiny symbols. Draw the pentacle following it?)#k"):
        done += 1
        sm.setQRValue(41271, str(done))
        if done >= DRAWINGS:
            sm.setQRValue(41267, "clear", "clear")
            sm.sendSayOkay("#b(The last line connects, and the pentacle glows. It's complete. Show it to Dr. De Lang.)#k")
        else:
            sm.sendSayOkay("#b(You traced the pentacle, but it's not quite right yet. Try again. (%d/%d))#k"
                           % (done, DRAWINGS))
elif sm.hasQuest(41267):
    sm.sendSayOkay("#b(The pentacle is complete. Show it to Dr. De Lang.)#k")
else:
    sm.sendSayOkay("#b(A magic pentacle drawn on the floor of the laboratory.)#k")
sm.dispose()
