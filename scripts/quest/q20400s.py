# 20400 - Chasing the Knight's Target
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Neinheart (1101002)

sm.setSpeakerID(1101002)  # Neinheart
sm.sendNext("It's been a while since I last saw you. I can't even recognize you now, seeing how powerful you have become since our last meeting. I can honestly say that you just might be one of the most powerful Knights in all of Cygnus Knights, Chief Knights included. Okay, enough pleasantries. Let's get down to business.")
sm.sendSay("It's a new mission. According to the information we acquired, a member of the #rBlack Wings#k is targeting the Empress. In order to prevent that, Advanced Knight #b#p1103000##k has been secretly tracing that individual, but it doesn't look too good from here.")
if sm.sendAskAccept("If it's Victoria Island, at least we know everything that goes on there. This one's Ossyria, where not even the intelligence officials know everything inside out. This means the Advanced Knight will need help. Please provide help to #p1103000#. The last place she contacted was at #b#m211000000##k, so try looking for #p1103000#."):
    sm.startQuest(parentID)
else:
    sm.sendNext("Hmmm... you seem way too at ease. It's a waste of talent and firepower for an accomplished individual like you to just sit around, being content with the way things are...")
