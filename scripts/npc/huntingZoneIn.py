maps = {
    102040600 : 931050410,
    260010201 : 931050415,
    261010002 : 931050434,
    261010103 : 931050435,
    261020500 : 931050417,
    261020200 : 931050424,
    240010200 : 931050419,
    240010600 : 931050420,
    240020101 : 931050426,
    240020401 : 931050425,
    240020200 : 931050422,
    220040200 : 931050413,
    211041400 : 931050428,
    250010502 : 931050436,
    251010500 : 931050418,
    220080000 : 931050427,
    230040410 : 931050429,
    240040400 : 931050430,
    270010500 : 931050431,
    270020500 : 931050432,
    270030500 : 931050433,
    # Mystic Gates placed in v232 maps that had no zone here (picked by the zone's boss: Zeno, Griffey, Manon)
    221040400 : 931050414, # Kulan Field V -> Zeno's Field
    240020102 : 931050426, # Griffey Forest -> Spirit Tree Nest (Griffey)
    240020402 : 931050425, # Manon's Forest -> Forest of Whispering Trees (Manon)
}
MYSTIC_GATE = 9073008
time = 6 *60
map = sm.getFieldID()

sm.setSpeakerID(MYSTIC_GATE)
if sm.getFieldID() not in maps:
    sm.dispose()  # no hidden zone behind this gate

response = sm.sendAskYesNo("Do you want to enter?")

if response:
    sm.warpInstanceIn(maps[sm.getFieldID()], 0)
    sm.setInstanceTime(time, map)