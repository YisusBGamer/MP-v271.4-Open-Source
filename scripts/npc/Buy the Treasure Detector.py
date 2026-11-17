# NPC script Buy the Treasure Detector
# Es el NPC 9310147. Unica linea de texto que Npc.wz le dedica:
#   Npc.wz/9310147.img/info/script/0/script = "Buy the Treasure Detector"
# Ese valor hace de NOMBRE del script (NpcData.java:33-46) y NpcHandler.java:87 lo
# pasa tal cual, asi que el archivo tiene que llamarse exactamente asi. Es el caso
# en el que el WZ si trae contenido: no hay mas texto en su img (el chatter de
# info/speak apunta a String.wz/Npc.img/n0,n1 y lo pinta el cliente solo).
# Sin archivo, el servidor contesta con una de las 50 frases inventadas en Java
# (ScriptManagerImpl.java:379-432), no con un error.
sm.sendNext("Buy the Treasure Detector")
