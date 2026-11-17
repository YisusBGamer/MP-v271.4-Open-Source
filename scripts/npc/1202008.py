# Head Wolf (1202008)
# The wolf mount shop that used to be here relied on constants that don't exist on this server (the script crashed
# before saying anything), so only the trip to see the wolves is offered.

selection = sm.sendNext(
    "What do you want?\r\n#b"
    "#L0#Go see some wolves#l"
)

if selection == 0:
    if sm.sendAskYesNo("Are you ready to go?"):
        sm.warp(914030000)
