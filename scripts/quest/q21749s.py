# 21749 - Back to the Past
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Tru (1002104)

sm.setSpeakerID(1002104)  # Tru
sm.sendNext("Oh, hello there. You've leveled up so much that I didn't even recognize you at first. This task should be a breeze for you then. What task, you ask?")
sm.sendSay("While you were training, #p1201000# and I have been thoroughly looking into your past and the Seal Stone. And guess what? We received an interesting piece of information just recently. Do you know the town that consists of toys for kids known as #m220000000#?")
sm.sendSay("There are two clocktowers in #m220000000# that control time. These towers allow time in #m220000000# to remain frozen. I hear the clocks stop time because the toys will become useless if the kids grow up.")
sm.sendSay("But apparently, one of the clocktowers broke. No one knows why or how, but the broken clocktower has #bcreated a time gap in #m220000000#, allowing people to travel to the past#k. Oh, and this is where it gets interesting...")
sm.sendSay("Based on the information we've collected from people that've entered the time portal, we were able to conclude that the time they traveled to in #m220000000# is #bclose to the time when you were around#k! The way people dressed, the items they used, the surroundings, it all points to that time! We might be able to find more information on the Seal Stone then, don't you think?")
sm.sendSay("I mean, I am not worried about the Seal Stone itself. I just thought there was a possibility of you meeting someone that knew you back in that time period.")
sm.sendSay("#bThe right clocktower#k... Specifically, the Helios Tower, is the broken one. Inside #bthe building that resembles a pink bunny head#k, you will find a device that manages time. #bTake the ladder to the top of Helios Tower and continue going up#k. You'll be able to enter the past from there.")
sm.startQuest(parentID)
