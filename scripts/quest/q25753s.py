# 25753 - Buying Time (Kaiser), from Fenelle (3000001). Battlefield quest: accepting moves you to the South Pantheon
# Border (940000150, field/event_25753.py); beating every Specter there (field/clear_940000150.py) sets record 25800.
# The quest is then finished from the quest window. No Say.img start lines exist: the prompt is the quest's own summary.
sm.setSpeakerID(3000001)  # Fenelle
if sm.sendAskAccept("Just a little longer, and the shield will be fully restored. Head south and #bdefeat all Specters "
                    "on the battlefield#k.\r\n#r#e(If you accept, you will automatically move to the relevant field.)"):
    sm.startQuest(parentID)
    if sm.hasQuest(parentID):
        sm.warpInstanceIn(940000150)  # South Pantheon Border
sm.dispose()
