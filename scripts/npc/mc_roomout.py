# Monster Carnival waiting rooms: Assistants Red/Blue (2042003/2042004) send everyone back out.
field = sm.getField()
target = field.getInfo().getReturnMap()
if target <= 0 or target >= 999999999 or target == field.getId():
    target = 980000000
sm.sendSayOkay("All of you will exit this place as soon as you talk to me.")
sm.warp(target, 0)
