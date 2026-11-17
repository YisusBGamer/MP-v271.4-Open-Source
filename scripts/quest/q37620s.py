# 37620 [Reverse City] Surviving in the Reversed City - Rasol (3004602), Subway Tunnel Refuge
# The end of the story (official): Barr carried the Singularity Maker into the sky so its blast wouldn't hurt anyone
# and used T-Boy's teleporter to return to Friends World; later the town hears he's safe and his book is selling
# well. Rasol rewards you (T-Boy's Monitor + 20 Arcane Symbol Selector Coupons).
MONITOR = 1005497          # T-Boy's Monitor
SELECTOR = 2630437         # Arcane Symbol Selector Coupon
sm.setSpeakerID(3004602) # Rasol
sm.sendNext("You're back! We saw the light over M Tower... Is it really over?")
sm.setPlayerAsSpeaker()
sm.sendSay("(You tell Rasol how the Singularity Maker kept counting down even after you destroyed it, and how Barr carried it up into the sky so the blast wouldn't hurt anyone.)")
sm.setSpeakerID(3004602) # Rasol
sm.sendSay("And then he used T-Boy's teleporter to go home to #bFriends World#k? Then he's safe... That show-off.")
sm.sendSay("Knowing him, he'll put the whole thing in his next book. 'What matters most to the survivor...' Heh.")
sm.sendSay("The city's still upside-down, but we're not hiding in the tunnels anymore. We'll survive. That's what we do.")
if not sm.canHold(MONITOR) or not sm.canHold(SELECTOR, 20):
    sm.sendSayOkay("I have something for you, but your inventory is full. Make room in your #bEquip#k and #bUse#k tabs and talk to me again.")
    sm.dispose()
if not sm.hasQuest(parentID):
    sm.startQuest(parentID)
if sm.completeQuestNoCheck(parentID):
    sm.giveItem(MONITOR)
    sm.giveItem(SELECTOR, 20)
    sm.sendSayOkay("Here: #i1005497# #b#t1005497##k and #b20 #t2630437##k. Thank you, #h0#. Reverse City won't forget you.")
