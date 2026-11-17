# Violetta camera (2432656), for [Mushroom Castle] Pure Fan Love (30055): Anne Nah wants photos of Princess Violetta's
# three symbols. Officially, double-clicking the camera opens photo mode (move the mouse to the symbol and click), a
# client mode this server has no packets for, so using the camera in a symbol's map takes that photo. Maps and photos
# are the quest's own (Quest.wz placeSummary/demandSummary).
PHOTOS = {
    106030000: (4034107, "Violetta's Banner"),    # Banquet Hall
    106030200: (4034108, "Violetta's Portrait"),  # Castle Corridor 1
    106030210: (4034109, "Violetta's Statue"),    # Violetta's Garden 1
}
QUEST = 30055

if not sm.hasQuest(QUEST):
    sm.chatScript("There's nothing you need to photograph right now.")
elif sm.getFieldID() in PHOTOS:
    photo, name = PHOTOS[sm.getFieldID()]
    if sm.hasItem(photo):
        sm.chatScript("You already have a photo of %s." % name)
    elif sm.canHold(photo):
        sm.giveItem(photo)
        sm.chatScript("Click! You took a photo of %s." % name)
    else:
        sm.chatScript("Make room in your Etc inventory first.")
else:
    sm.chatScript("Violetta's symbols are in the Banquet Hall, Castle Corridor 1 and Violetta's Garden 1.")
sm.dispose()
