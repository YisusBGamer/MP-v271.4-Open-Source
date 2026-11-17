# 100030400 (Farm Street : Farm Entrance, see CustomFieldScripts) - Explorer story: Evan's lost piglet
# (In Search of a Piglet 32330)
if sm.hasQuest(32330) and not sm.hasItem(4033913):
    sm.giveItem(4033913)
    sm.chatScript("You found the piglet hiding by the farm entrance!")
