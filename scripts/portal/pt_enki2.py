# Portal script pt_enki2
# Lo invoca el atributo script del portal 7 del mapa 993062650 (Map.wz/Map).
# El WZ declara el destino 993062662: la unica arista portalNum=7 de Graph.img.
# MigrationHandler.java:211-212 abre el script y NO teletransporta; el warp pendiente se
#   ejecuta al terminar el archivo (ScriptManagerImpl.java:311), por eso no hace falta dispose.
# Sin archivo el chat suelta "Portal not implemented, please report to developer." y el portal
#   queda muerto.

sm.warp(993062662)
