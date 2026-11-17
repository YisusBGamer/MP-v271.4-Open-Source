# Leave a pre-departure waiting room / boat cabin and return to the station.
FALLBACK = {
    2000110: 2000100,
    104020111: 104020110,
    200000112: 200000100, 200000122: 200000100, 200000132: 200000100, 200000152: 200000100,
    220000111: 220000100,
    240000111: 240000000,
    260000110: 260000000,
}
field = sm.getField()
fid = field.getId()
target = field.getInfo().getReturnMap()
if target <= 0 or target >= 999999999 or target == fid:
    target = FALLBACK.get(fid, 0)
if target <= 0:
    sm.sendSayOkay("The ship will be departing soon. Please wait here.")
elif sm.sendAskYesNo("Do you want to leave the waiting room? You will have to buy another ticket to board again."):
    sm.warp(target, 0)
sm.dispose()
