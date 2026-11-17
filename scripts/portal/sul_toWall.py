# Portal script sul_toWall
# Lo invoca el atributo script del portal 4 del mapa 993000713 (Map.wz/Map).
# El WZ declara el destino 993000723: la unica arista portalNum=4 de Graph.img.
# MigrationHandler.java:211-212 abre el script y NO teletransporta; el warp pendiente se
#   ejecuta al terminar el archivo (ScriptManagerImpl.java:311), por eso no hace falta dispose.
# Sin archivo el chat suelta "Portal not implemented, please report to developer." y el portal
#   queda muerto.

sm.warp(993000723)
