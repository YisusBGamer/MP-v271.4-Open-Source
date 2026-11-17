# Maple Admin
# 9000088
# Nautilus (120000100)
# Warps to Ardentmill; return field is stored so the Ardentmill exit portal (out_profession) brings the player back.

response = sm.sendAskYesNo("Would you like to visit #m910001000#?")
if response:
    sm.setReturnField()
    sm.warp(910001000)
