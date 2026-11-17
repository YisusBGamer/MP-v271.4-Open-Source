# The Decision
# 24051
# No NPC: Mercedes's own thoughts. Official lines (Quest.wz Say.img).
# The quest gives the 2,000 EXP itself (Act.img); the script used to give it a second time.

sm.flipDialoguePlayerAsSpeaker()
sm.sendNext("He's a crass tree, but maybe he has a point... Perhaps it's time I settle down.")
sm.sendSay("My battle with the Black Mage is ancient history. Nobody cares about what I did in the past.")
sm.sendSay("I'm too weak to protect Maple World... Even #o0210100#s give me some trouble. I may even be weaker than the townsfolk...")
sm.sendSay("And I don't have my old friends to rely on anymore. I'm on my own here. What can I do on my own?")
sm.sendSay("...No, that's not right. I'm the sovereign of the Elves! So long as my people are suffering under the Curse of the Black Mage...")
sm.sendSay("#b...I will NEVER give up!#k")
sm.sendSay("I need to clear my mind.")

sm.startQuest(parentID)
sm.completeQuest(parentID)
