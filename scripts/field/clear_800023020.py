# Kurama's mind (800023020): Fury of the Nine-Tailed Fox (58768) - Kurama's Fear is beaten (quest record
# kuraKill, which the quest checks). Back to the Guardian's Cave.
if sm.hasQuest(58768) and sm.getQRValue(58768, "kuraKill") != "1":
    sm.setQRValue(58768, "kuraKill", "1")
    chr.chatMessage("Kurama's fear fades away. Talk to him in the Guardian's Cave.")
    sm.warpInstanceOut(800023018, 2)
