# 31406 - Push the Line
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Edea (3001000)
# Battlefield quest: winning it (portal/finish_warZone1.py) sets record 31551 and hands over the odd Specter's badge.

sm.setSpeakerID(3001000)  # Edea
if sm.sendNext(u"Your efforts in clearing the supply lines have opened up our options.\r\n#L0##bThose dogs were just being used. There\u2019s gotta be somebody controlling them. #l\r\n#k") != 0:
    sm.dispose()
if sm.sendSay("Correct. And that is why I need you to take back the #b#m401010010##k. \r\n#L0##bI like how you tell me to do things.#l\r\n#k") != 0:
    sm.dispose()
if sm.sendAskAccept(u"It is one of my many talents. The battle will probably be frustrating, due to their high defense and shield usage, but I am sure you will overcome. Won\u2019t you?"):
    sm.startQuest(parentID)
    sm.sendNext("I know you will have no problems.")
    if sm.hasQuest(parentID):
        sm.warpInstanceIn(401070100)  # Occupied Twisted Forest
else:
    sm.sendNext("Hmm, maybe I can find the pink haired dancer to help us...")
