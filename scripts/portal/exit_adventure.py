# The exit of the Explorer/class story maps that use this portal. It sent every one of them to El Nath (Sharp Cliff I);
# now each goes back where it belongs: its own return map, or the map listed here.
EXITS = {
    211040401: 211040300,  # Holy Ground at the Snowfield -> Sharp Cliff I
    910400200: 104020000,  # Six Path Crossway (story) -> Six Path Crossway
    910540600: 211040401,  # Radiant Crystal Passageway -> Holy Ground at the Snowfield
    912090000: 120000000,  # Way to the Beach -> Nautilus Harbor
}
field_id = sm.getFieldID()
info = chr.getField().getInfo()
dest = EXITS.get(field_id, 0)
if dest == 0:
    dest = info.getReturnMap()
    if dest in (0, 999999999, field_id):
        dest = info.getForcedReturn()
    if dest in (0, 999999999, field_id):
        dest = 104020000
if chr.getInstance() is not None:
    sm.warpInstanceOut(dest, 0)
else:
    sm.warp(dest, 0)
sm.dispose()
