# Baby Moon Bunny (9001102): the Lunar World during the Moon Festival. Moonlight Sonata Music Box (10870) is
# handed in through holidayNpc.py.
sm.setSpeakerID(9001102)
if sm.hasQuestCompleted(10870):
    sm.sendSayOkay("La-la-laaa... Listen! It's the song Grandpa used to hum. I'll play my music box every night, "
                   "forever and ever!")
else:
    sm.sendSayOkay("Grandpa says the moon used to have a song. A really pretty one. I wish I could hear it...")
sm.dispose()
