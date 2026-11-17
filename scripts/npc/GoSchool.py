# Schrodinger (9330189), in Perion, Nautilus, Rien and 820000000: the Maple Leaf High School homeroom (744000000).
# The town you left from is kept, and the homeroom's back portal (ComeBackHome) returns you there.
response = sm.sendAskYesNo("Do you wish to visit the #b#m"+ str(744000000) +"##k?")

if response:
    sm.setReturnField()
    sm.warp(744000000, 0)
