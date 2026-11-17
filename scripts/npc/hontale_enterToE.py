# Encrypted Slate of the Squad(2083000) | Cave of Life, Cave Entrance
# Opens the back door to the Entrance to Horntail's Cave. That map is the normal, shared one (not a private copy),
# so the Mark of the Squad there can start the fight: from a private copy it refused with "already in their own instance".
response = sm.sendAskYesNo("The letters on the slate glitter and the backdoor opens. Do you want to go to the secret path?")
if response:
    sm.warp(240050400, 0)
