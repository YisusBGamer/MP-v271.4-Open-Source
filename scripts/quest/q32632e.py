# 32632 - The Fairy Queen
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Ephenia (2510002)

sm.setSpeakerID(2510002)  # Ephenia
if sm.sendNext("What do YOU want? Come to peddle your inferior human wares? Because YOU have NOTHING that I want.\r\n#L0##b(Talk about the dark monsters.)#l\r\n#k") != 0:
    sm.dispose()
if sm.sendSay("Dark monsters? What of them? They're hardly as vile as you humans, what with your stealing and kidnapping.\r\n#L0##b(Talk about the White Mage.)#l\r\n#k") != 0:
    sm.dispose()
sm.sendSay("#b(Ephenia's look softens a bit when she hears the name.)#k\r\nWhite Mage...? W-what business do you have with the White Mage?")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("He was the one good human that I met in my life. I liked to watch him study magic... He was an idealist, and his passion was incomparable. I know he will make the world a much better place.")
    sm.sendSay("But...I can't just tell you where he is. I don't even know who you are.")
