# Portal script lionCastleBoss
# Lo invoca el atributo script del portal 3 del mapa 211061210 (Map.wz/Map).
# El WZ declara el destino 211070000: la unica arista portalNum=3 de Graph.img.
# MigrationHandler.java:211-212 abre el script y NO teletransporta; el warp pendiente se
#   ejecuta al terminar el archivo (ScriptManagerImpl.java:311), por eso no hace falta dispose.
# Sin archivo el chat suelta "Portal not implemented, please report to developer." y el portal
#   queda muerto.

sm.warp(211070000)
