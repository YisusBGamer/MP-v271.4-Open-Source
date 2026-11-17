# The Great Gachapierrot

from net.swordie.ms.world.gach.result import GachaponDlgType


def open_gachapon(dlg_type):
    # the town gachapon shows and gives the rewards of the town it stands in; NpcHandler handles the spins
    chr.write(chr.getGachaponManager().encode(dlg_type, chr.getFieldID()))

answer = sm.sendNext("Try out the new and improved Great Gachapierrot! Which service would you like to use ?\r\n#b#L0#Use a Gachapon Ticket.#l\r\n#L1#Use a Chair Gachapon Ticket.#l\r\n#L2#Nebulite Gachapon Ticket Exchange.#l\r\n#L3#Mount Gachapon Ticket Exchange.#l#k\r\n#r#L4#Use a Powergacha Ticket(Event Quest).")

if answer == 0:
    open_gachapon(GachaponDlgType.TOWN)
elif answer == 1:
    open_gachapon(GachaponDlgType.CHAIR)
elif answer == 2:
    open_gachapon(GachaponDlgType.NEBULITE)
elif answer == 3:
    open_gachapon(GachaponDlgType.MOUNT)
elif answer == 4:
    open_gachapon(GachaponDlgType.SPECIAL)
