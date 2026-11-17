# id 450007010 (Esfera : Living Spring 2), field 450007010
# Many maps use this script on entering. In Burning Royal Library Section 6 (410000890) Aaron (3004529), needed for
# Library in Flames 2 (39919), shows when record 39900 key "29" is "h1".
if sm.getFieldID() == 410000890 and sm.hasQuest(39919):
    sm.setQRValue(39900, "29", "h1")
