# Spiegelmann (9071000), Monster Park hub: what the park is and where to go
from net.swordie.ms.client.character import MonsterPark

sm.setSpeakerID(9071000)
clears = MonsterPark.getClearsToday(chr)
sm.sendSayOkay("Welcome to #bMonster Park#k! Pick a dungeon at one of the gates and clear all of its stages for "
               "lots of EXP, a day-of-the-week box and Monster Park Coins.\r\n\r\n"
               "#b- Gate 1#k: Lv. 105-199\r\n"
               "#b- Gate 2#k: Arcane River, Lv. 200-265\r\n"
               "#b- Gate 3#k: Grandis Advanced Dungeons, Lv. 260+\r\n\r\n"
               + ("Clears are unlimited and free (today: #b" + str(clears) + "#k)." if MonsterPark.unlimited()
                  else "Each character can clear #b" + str(MonsterPark.CHAR_CLEARS) + "#k times a day (today: #b"
                  + str(clears) + "#k).")
               + " Your first clear of the day gives #b"
               + str(MonsterPark.FIRST_CLEAR_COINS) + " #t" + str(MonsterPark.COIN) + "#s#k, and Sundays give 50% more EXP.")
sm.dispose()
