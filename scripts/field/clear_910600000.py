# 910600000 Golem's Temple Entrance (Kidnapping of Camila copy): the Enraged Golem is down.
# Chief Stan (1012003) hands in 22557 once record 22598 = 2. The map's own portal leads back to Windflower Forest.
if sm.hasQuest(22557) and sm.getQRValue(22598) != "2":
    sm.startQuest(22598)
    sm.setQRValue(22598, "2")
    sm.chatScript("You rescued Camila! Take her back to Chief Stan.")
