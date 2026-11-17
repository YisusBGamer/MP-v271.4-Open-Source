# Gate to the Future (271000000), entered from the Temple of Time
# [Henesys Ruins] Into the Light (31102) waits for its record to say "end" and is handed to Shinsoo (2143002), who
# only exists in this scene. No official lines exist for it; written from the quest's own summaries.
if sm.hasQuest(31102):
    sm.setQRValue(31102, "end")
    if sm.completeQuest(31102):
        sm.chatScript("You followed the light into the Gate to the Future. What lies beyond...?")
