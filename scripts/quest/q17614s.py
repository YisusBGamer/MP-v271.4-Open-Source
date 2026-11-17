# [Commerci Republic] Ciao, Untril Next Time
sm.setSpeakerID(9390202) # Leon Daniella
sm.sendNext("Good! Now I leave. I just came to Berry to get fish from Berry. Haha! They have the same name! Berry, Berry, Berry, Berry")


sm.setPlayerAsSpeaker() # Has to be Player Avatar
sm.sendNext("Do you have to leave right this second? I wanted to ask my new best pal some questions.")

sm.setSpeakerID(9390202) # Leon Daniella
sm.sendNext("Oh, shoot! I have to catch a ship. Shoot! Hey! Why don't you come to #bSan Commerci#k? You can meet my dad! He's really cool.")

sm.setPlayerAsSpeaker() # Has to be Player Avatar
sm.sendNext("Berry did suggest I visit.")

sm.setSpeakerID(9390202) # Leon Daniella
response = sm.sendAskYesNo("Yeah! Just go to San Commerci and wait for me. I have a couple of voyages to make, but don't leave until I get back. Promise?")

if response:
    sm.sendSayOkay("Okay, go east from the town, follow the coastal road, and you'll be in San Commerci in no time. It's a single road so you won't get lost, but be careful of thieves.\r\nI'll see you in #e#bSan Commerci#k#n!")
    sm.startQuest(parentID)
    sm.warpInstanceOut(865010200, 0)
else:
    sm.sendSayOkay("You're not gonna promise your best friend a simple thing?")
sm.dispose()
