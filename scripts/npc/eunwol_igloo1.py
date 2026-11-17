# Igloo 1 on Riena Strait (914050007), Finding Aran 3 (Shade 38049): guess which igloo the penguins are in. The
# record keeps the igloos already tried ("t<n>..."); each untried igloo is equally likely, the last one always is.
# Found: record 38049 = clear, then the quest is handed in from the quest window.
import random

IGLOO = "1"

if sm.hasQuest(38049) and sm.getQRValue(38049) != "clear":
    record = sm.getQRValue(38049)
    tried = record[1:] if record.startswith("t") else ""
    if IGLOO in tried:
        sm.sendSayOkay("(You already looked in this igloo. Nobody's in there.)")
    elif random.random() < 1.0 / (3 - len(tried)):
        sm.setQRValue(38049, "clear")
        sm.sendSayOkay("(The penguins were hiding in this igloo!)")
        sm.chatScript("Just as you started to feel like an idiot for playing the penguin's game, Aran showed up.")
    else:
        sm.setQRValue(38049, "t" + tried + IGLOO)
        sm.sendSayOkay("(Nobody's in this igloo...)")
sm.dispose()
