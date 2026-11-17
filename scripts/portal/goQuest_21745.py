# Portal script goQuest_21745.
# Mu Lung Dojo Hall: Bellflower Hill, where the Training Bellflowers drop what How to Meet Mu Gong (21745, Aran) needs.
if sm.hasQuest(21745):
    sm.warp(925041001, 1)
else:
    chr.chatMessage("Only those running an errand for the dojo's students may go out there.")
sm.dispose()
