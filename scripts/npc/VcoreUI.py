from net.swordie.ms.client.character.skills.vmatrix import V271Matrix

# The v271 V Matrix has no nodes: the window opens from the client (Skill window / main menu), so only the V point
# spender is left here


def spend_v_points():
    # builds from before the V point spender lack the method
    if not hasattr(V271Matrix, "spendPoints"):
        return "Spending V points arrives with the next server update."
    return V271Matrix.spendPoints(chr)

sm.setSpeakerID(1540945) # Archelle

res = sm.sendNext("Why hello there. How can I help you out today?\r\n"
            "#k(Open the V Matrix itself from your Skill window or the main menu.)\r\n\r\n#b"
            "#L2#Spend my V points#l")

if res == 2:
    if sm.sendAskYesNo("I'll turn the #bNodestones#k you carry into V points and spend them where they add the most "
                       "damage: Boost cores first, then your skill cores, then common cores that attack or raise "
                       "damage. Utility cores like Blink are left alone. Go ahead?"):
        sm.sendNext(spend_v_points())
