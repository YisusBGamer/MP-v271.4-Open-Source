# 31434 - Tonero's Request
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Tonero (3001004)

sm.setSpeakerID(3001004)  # Tonero
if sm.sendNext("Come, come! Details, details!\r\n#L0##bI just traded with someone named #p3001005#. I complete his task, and he gave me #t4033398#.#l\r\n#k") != 0:
    sm.dispose()
if sm.sendSay("Ahh, #p3001005#. You may rest assure that he is a trustworthy soul, so your information should check out. Sigh...beaten again by #p3001005#. He's too good. \r\n#L0##bToo good?#l\r\n#k") != 0:
    sm.dispose()
if sm.sendSay("In truth, #p3001005# is an incredible merchant, and a disciple of the school of strongarm tactics. My theatrics have never quite measured up, so I do my best to be more mobile. He's beaten me this time. Now, I'll grant you your intelligence, so I have one last request. When next you meet #p3001005#, trade with him anyway.\r\n#L0##bHuh? Why?#l\r\n#k") != 0:
    sm.dispose()
sm.sendSay("Because I long to see #b#p3001005##k helped. This will be as volunteer work for you, yes? Now that you have what you seek.")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("This envelope is the fee. You know what to do, right? Farewell, then, and good fortune in your travels!")
