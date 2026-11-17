# Golden Hammer 50% Coupon: GMS v271 retired Golden Hammers (every equip has the slot built in), and a Golden Hammer
# 50% now gives Spell Trace x12,000 when used, so the coupon gives those Spell Traces directly.
TRACES = 12000

if sm.canHold(4001832, TRACES):
    sm.giveItem(4001832, TRACES)
    sm.consumeItem(5680482)
else:
    sm.chat("Make sure you have enough empty slots in your Etc tab!")
