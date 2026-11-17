# Cube Piece exchange
# rewards = [ [itemID, requiredCubePiece], ... ]

rewards = [
    [2049400, 5],
    [2049402, 10],
    [2048305, 15],
    [2048306, 100],
    [2049701, 100],
]

ITEMID = 2635594  # Cube Piece
amount = sm.getQuantityOfItem(ITEMID)

sm.setSpeakerID(9010000)

menu = "Hello, you currently have #r#e" + str(amount) + "#n#k Cube Piece.\r\nWhich item do you want to exchange?\r\n#fs11##b"
for i in range(len(rewards)):
    x = rewards[i][0]  # reward itemID
    y = rewards[i][1]  # required Cube Piece
    menu += "#L" + str(i) + "# #eCube Piece#n x " + str(y) + " for 1 x #i" + str(x) + "# #e#z" + str(x) + "##n#l\r\n"

sel = sm.sendNext(menu)

if sel >= 0 and sel < len(rewards):
    x = rewards[sel][0]
    y = rewards[sel][1]

    if amount >= y:
        sm.giveItem(x, 1)
        sm.consumeItem(ITEMID, y)
        sm.sendNext("You exchanged #i" + str(x) + "# #z" + str(x) + "# x 1 for Cube Piece x " + str(y))
    else:
        sm.sendNext("You do not have enough #r#eCube Piece.")
