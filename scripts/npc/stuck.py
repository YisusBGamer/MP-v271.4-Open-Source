# @stuck - for characters that can't get out of where they are (a portal that doesn't work, a frozen screen, a map
# they can't leave). Asks first, then StuckModule moves them to Henesys and logs where they were (logs/stuck.log).
from net.swordie.ms.client.character import StuckModule

text = "#e#bStuck?#k#n\r\n\r\n" \
       "Use this only if you really can't move on: the portals don't work, you can't teleport out, or your " \
       "screen stays frozen.\r\n\r\n" \
       "I'll move you to #bHenesys#k. If you're in a boss fight or event, you leave it (no reward, and you may " \
       "not be able to go back in today).\r\n\r\n" \
       "Where you got stuck is noted so the problem can be fixed. Are you sure you're stuck?"
if sm.sendAskYesNo(text):
    StuckModule.unstuck(chr, "@stuck")
