# 410003210 (Hotel Arcus : Train Locomotive). The Beep of Treasure (38118) is handed in to Rusty (3005002), who isn't
# placed on any map: reaching the locomotive plays the scene, finishes the quest and brings you back to the Hotel
# Arcus Saloon, as the quest describes ("After barely escaping danger, you returned to the Saloon").
RUSTY = 3005002
BELLE = 3005014
SALOON = 410003000

if sm.hasQuest(38118):
    try:
        sm.setSpeakerID(BELLE)
        sm.setBoxChat()
        sm.sendNext("The beeping's coming from right up front! The treasure has to be in the locomotive!")
        sm.setSpeakerID(RUSTY)
        sm.sendNext("Treasure? That's the engine's core, girl! I had no idea the robots here were still active...")
        sm.sendNext("If that thing keeps running, this train's going straight off the end of the line. "
                    "Pull the lever, quick, and let's get out of here!")
        sm.setPlayerAsSpeaker()
        sm.sendNext("(You pulled the lever. The train shuddered, and everyone jumped clear just in time.)")
    finally:
        # also when the dialog is closed: the escape happened
        if sm.hasQuest(38118) and not sm.questEndDialogue(38118, RUSTY):
            sm.completeQuestNoCheck(38118)
        if sm.hasQuestCompleted(38118):
            sm.warp(SALOON, 0)
