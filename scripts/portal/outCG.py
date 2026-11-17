# Phantom contimove
from net.swordie.ms.loaders import FieldData

fieldIdStr = sm.getQRValue(25010)
fieldId = int(fieldIdStr) if fieldIdStr.isdigit() else 0
if FieldData.getFieldInfoById(fieldId) is None:
    fieldId = 100000000
if sm.sendAskYesNo("Do you want to fly to #m" + str(fieldId) + "#?"):
    sm.warp(fieldId)
