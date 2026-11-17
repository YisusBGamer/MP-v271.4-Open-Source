# Zoo (230000003) | the Mushroom House door between the Ribbon Pigs: during Making a Saddle (22404) it leads to
# Kenta's Sea of Silence (923030000), where Freezers drop Kenta's Freezer Leather and Kenta's Seruf a Seruf Pearl.
SEA_OF_SILENCE = 923030000
SADDLE_QUEST = 22404

if sm.hasQuest(SADDLE_QUEST):
    sm.warp(SEA_OF_SILENCE, 0)
else:
    sm.chatScript("The door of the Mushroom House is locked.")
sm.dispose()
