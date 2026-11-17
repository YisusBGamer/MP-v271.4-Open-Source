# out_finish on the Pantheon battlegrounds (400050000-400051700): once every Specter on the stage is beaten, on to the
# next stage, or out to Pantheon after the last one. Leaving through the last stage wins the battle: the record of
# every battlefield quest held for this battleground is set (their end checks wait for it). Each stage's Specters:
# field/enter_warZone0_*.py.
# quest: (last stage, record)
BATTLES = (
    (25750, 400050500, 25797),  # Deterrence (Kaiser): North Border Battleground 1
    (25751, 400050300, 25798),  # Specter Invasion (Kaiser): East Border Battleground 2
    (25752, 400050700, 25799),  # Stop the Summoning Ritual (Kaiser): West Border Battleground 1
    (25864, 400050500, 25798),  # Specter Smasher (Angelic Buster): North Border Battleground 1
    (25865, 400050000, 25797),  # Specter Invasion (Angelic Buster): East Border Battleground 1
    (25867, 400051700, 25918),  # Kaiser's Idol (Angelic Buster): North Border Battleground 2
    (25868, 400051100, 25800),  # Buying Time (Angelic Buster): South Border Battleground 1
    (25869, 400051400, 25801),  # The Perfect Trap (Angelic Buster): after field/clear_940010070.py
)
NEXT_STAGE = {
    400050100: 400050200, 400050200: 400050300,
    400050400: 400050500,
    400050600: 400050700,
    400050800: 400050900, 400050900: 400051000, 400051000: 400051100,
    400051200: 400051300, 400051300: 400051400,
    400051500: 400051600, 400051600: 400051700,
}
PANTHEON = 400000000  # Cartalion, who takes the reports (every battleground's Map.wz returnMap)

field = sm.getFieldID()
if sm.hasMobsInField():
    sm.chat("Please eliminate all mobs.")
elif field in NEXT_STAGE:
    sm.warp(NEXT_STAGE[field])
else:
    for quest, last, record in BATTLES:
        if last == field and sm.hasQuest(quest):
            sm.startQuest(record)
            sm.setQRValue(record, "1")
    sm.warpInstanceOut(PANTHEON, 0)
sm.dispose()
