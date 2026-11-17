# west in City Center (331000000): the Hannam subway station, while Kinesis is handling the subway attacks
# (Codebreaker Jay 2 - A Pale Threat, 22730-22733)
if sm.hasQuestCompleted(22730) and not sm.hasQuestCompleted(22733):
    sm.warp(331003000, 1)
else:
    sm.chatScript("The subway station is closed off. There's nothing to do there right now.")
    sm.dispose()
