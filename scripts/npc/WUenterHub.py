# Cross World Party Quest (9076001) | Spiegelmann's Guest House: to each Cross World party quest's waiting room
sm.setSpeakerID(9076001)
sel = sm.sendNext("#e<Cross World Party Quest>#n\r\n\r\nWhich party quest would you like to take on?\r\n\r\n"
                  "#b#L0#Moon Bunny's Rice Cake (Lv. 70+, 1-4 players)#l\r\n"
                  "#L1#First Time Together (Lv. 70+, 4 players)#l\r\n"
                  "#L2#Forest of Poison Haze (Lv. 70+, 1-4 players)#l\r\n"
                  "#L3#Remnant of the Goddess (1-4 players)#l\r\n"
                  "#L4#Goo Ruins#l")
DEST = {0: 933000000, 1: 933010000, 2: 933020000, 3: 933030000, 4: 867155850}
if sel in DEST:
    sm.warp(DEST[sel], 0)
sm.dispose()
