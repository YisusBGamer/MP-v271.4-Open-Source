# El Nath (211000000, in99): the portal near Alcaster for Touch of Oda 1 (Hayato 57133) and Fact or Fiction
# (Kanna 57433): a hidden place where Oda's men are working with the Black Wings. From there (807050100 / 807051100,
# out00): back to El Nath. v271's Map.wz no longer ships those two Secret Place maps, so only warp when they exist.
from net.swordie.ms.loaders import FieldData

if sm.getFieldID() == 211000000:
    if sm.hasQuest(57133):
        field_id = 807050100
    elif sm.hasQuest(57433):
        field_id = 807051100
    else:
        field_id = 0
    if field_id and FieldData.getFieldInfoById(field_id) is not None:
        sm.warpInstanceIn(field_id, 0)
    else:
        sm.chat("There is nothing to see here.")
else:
    sm.warpInstanceOut(211000000, 11)
sm.dispose()
