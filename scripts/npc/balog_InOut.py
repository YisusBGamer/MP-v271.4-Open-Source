# Mu Young (1061018) | Balrog Boss Map
response = sm.sendAskYesNo("If you leave now, you'll have to start over.\r\n"
                "Are you sure you want to leave?")

if response:
    sm.warpInstanceOut(105100100)  # Bottom of the Temple (the Balrog entrance)