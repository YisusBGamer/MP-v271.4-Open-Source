# Portal script q3367out
# Lo invoca el atributo script del portal 3 del mapa 926130201 (Map.wz/Map).
# El WZ declara el destino 926130100: la unica arista portalNum=3 de Graph.img.
# MigrationHandler.java:211-212 abre el script y NO teletransporta; el warp pendiente se
#   ejecuta al terminar el archivo (ScriptManagerImpl.java:311), por eso no hace falta dispose.
# Sin archivo el chat suelta "Portal not implemented, please report to developer." y el portal
#   queda muerto.

sm.warp(926130100)
