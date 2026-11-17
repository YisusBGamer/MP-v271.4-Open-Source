# Portal script talkToMai.
# Entrance to Adventurer Training Center (Maple Road): into one of the four training rooms, an empty one if possible.
ROOMS = {
    4000021: (4000022, 4000023, 4000024, 4000025),
    1010000: (1010100, 1010200, 1010300, 1010400),
}
rooms = ROOMS.get(sm.getFieldID(), ROOMS[4000021])
channel = chr.getClient().getChannelInstance()
target = rooms[0]
for room in rooms:
    if channel.getField(room).getChars().size() == 0:
        target = room
        break
sm.warp(target, 4)
sm.dispose()
