# Portal script pt_993063027_out
# Lo invoca el atributo script del portal 11 del mapa 993063027 (Map.wz/Map).
# El WZ declara el destino 450011220: la unica arista portalNum=11 de Graph.img.
# MigrationHandler.java:211-212 abre el script y NO teletransporta; el warp pendiente se
#   ejecuta al terminar el archivo (ScriptManagerImpl.java:311), por eso no hace falta dispose.
# Sin archivo el chat suelta "Portal not implemented, please report to developer." y el portal
#   queda muerto.

sm.warp(450011220)
