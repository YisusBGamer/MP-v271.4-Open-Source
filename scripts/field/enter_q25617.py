# 910142060-910142079 Forest Edge: Unexpected Encounter (Luminous 25617). Black Mage minions (Void Priests, Lv. 60)
# are in the way to Lania's house; clear_910142060 sets the quest's record once they're down.
# The map has no exit portal, so anyone without the fight to do is sent on to Lania's Front Yard.
# only 910142060 has a clear script: any other copy just sends the player on
if sm.getFieldID() == 910142060 and sm.hasQuest(25617) and sm.getQRValue(25616, "fight") != "1":
    if not sm.hasMobsInField():
        for i, x in enumerate((150, 300, 450, 600, 750, 900)):
            sm.spawnMob(9300532 if i % 2 == 0 else 9300533, x, -32, False)
        sm.chatScript("What are the Black Mage's minions doing here? Is Lania safe?!")
else:
    sm.warpInstanceOut(101000101, 0)
