# 12890 - Beating Heat Stroke
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Icarus (9000118)

sm.setSpeakerID(9000118)  # Icarus
if sm.sendAskAccept("Hi t-there! I'm f-f-fine. R-really! I've been hang gliding off the t-top of Perion 'cause hang gliding's the b-best thing EVER, but now I can't s-stop sweating. Or t-throwing up. The pharmacist s-says I have h-heat stroke, but what d-does he know? But maybe you should g-get me some #e#bForeverfreeze Ice#k#n, like he prescribed."):
    sm.startQuest(parentID)
    sm.sendNext("You c-c-can get Foreverfreeze Ice f-from #b#eIce Pillars#k#n that appear all over Maple World. Just s-smack 'em. Get me #b#e20 p-p-pieces of Foreverfreeze Ice#k#n, okay?")
else:
    sm.sendNext("Oh, w-well. I j-just hope I don't throw up on anyone when I hang glide off the t-top of Perion later.")
