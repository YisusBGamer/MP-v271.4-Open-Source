# 2559 - Sky High
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Instructor Irvin (9072000)

sm.setSpeakerID(9072000)  # Instructor Irvin
sm.sendNext("Oh, did the Maple Administrator send you? Interested in an airplane?")
sm.sendSay("You... You don't know what an airplane is? Well, I guess it is a little new. It's, er, a bit like the mounts that you have, but can take you long distances, like to other continents.")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("There are some things you must know before I can give you an airplane. Listen carefully.")
    sm.sendSay("First, airplanes are very new to Maple World. They are similar to mounts, but you can ride them to distant continents. Just not all continents, okay?")
    sm.sendSay("You can fly to #bVictoria Island, Ereve, Edelstein, Ludibrium, Ariant, Mu Lung, or Leafre#k from #bOrbis#k, using the airplane. You can also fly the opposite route, of course. Lastly, you can fly to #bVictoria Island#k from #bEdelstein#k, and vice-versa. These are the only locations you can take an airplane to... The others are a bit too dangerous yet...")
    sm.sendSay("If you want to head to another continent, you need to speak with a member of the Station staff first. If they have an available airplane, they will offer you that, or a space on a regular ship.")
