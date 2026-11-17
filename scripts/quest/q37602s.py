# 37602 [Reverse City] The Most Normal Person - start: Legato (3003111), Nameless Town
# Official summary: go to Reverse City and find out what happened. Done by entering the Subway Tunnel Entrance
# (field script enter_450014010, where you meet T-Boy and Rasol).
sm.setSpeakerID(3003111) # Legato
sm.sendNext("Whoever was on the other end of that walkie-talkie is in trouble. A city turned upside-down...")
if not sm.sendAskAccept("Will you go to the #bsubway station entrance#k and find out what happened to #bReverse City#k?"):
    sm.sendSayOkay("Think it over. Those voices sounded desperate.")
    sm.dispose()
sm.startQuest(parentID)
sm.sendSayOkay("Go to the subway station entrance: head east from #m450001002# to reach the #b#m450014010##k. Be careful.")
