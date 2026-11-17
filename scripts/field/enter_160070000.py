# Ramuramu Valley: Ramuramu Hot Springs (160070000), Chief Slime's village. [Ramuramu] Psych Exam (36010): "You
# have arrived in Ramuramu Valley." - arriving finishes the quest (its record 'step' = 1; the Unsigned Letter
# it's handed in to stands nowhere), and the Chief's quests can start.
if sm.hasQuest(36010):
    sm.setQRValue(36010, "step", "1")
    if sm.completeQuest(36010):
        sm.chatScript("You have arrived in Ramuramu Valley.")
