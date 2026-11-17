# 36616 My Choice (self start, Lv 26): Father wanted this for him; Mihile decides to go (tells Neinheart: q36616e).
execfile("scripts/quest/_mihile_story.py")

me("(That shine... I know it was real. She saw it too. She said I had the power.)")
me("(Father didn't know they'd be here when he told me to stay.)")
me("(If he were here right now, what would he want me to do?)")
me("(He said he was looking for a way to help me awaken as the Knight of Light. Well, here it is. This is exactly what "
   "he wants for me.)")
begin(parentID)
sm.chatScript("Tell Neinheart your decision.")
sm.dispose()
