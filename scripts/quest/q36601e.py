# 36601 Start (Weapon Box 1541201): the Beginner Warrior's Sword, and the photograph of Mihile's brother.
execfile("scripts/quest/_mihile_story.py")

if not sm.canHold(SWORD):
    sm.chatScript("Make room in your Equip inventory.")
elif ask(MIHILE, "(Time for swordsmanship practice?)"):
    if finish(parentID):
        sm.giveItem(SWORD)
        me("...!")
        me("(Oh, I almost dropped this.)")
        me("(My brother. I wish I'd known him.)")
        me("(I only have the stories Father told me.)")
        me("(He was sincere, kinder than anyone...)")
        me("(Father must miss him terribly. Even though I never met him myself, I feel like I miss him too.)")
        me("(So when I become the Knight of Light... When I take that jewel out of the shield and hold it...)")
        me("(...I'll wish him back to life.)")
        sm.chatScript("Leave the room through the portal and talk to Father.")
sm.dispose()
