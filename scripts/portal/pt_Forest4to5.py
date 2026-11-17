# Portal script pt_Forest4to5
# Lo invoca el atributo script del portal 2 del mapa 875010230 (Map.wz/Map).
# El WZ declara el destino 875010240: la unica arista portalNum=2 de Graph.img.
# MigrationHandler.java:211-212 abre el script y NO teletransporta; el warp pendiente se
#   ejecuta al terminar el archivo (ScriptManagerImpl.java:311), por eso no hace falta dispose.
# Sin archivo el chat suelta "Portal not implemented, please report to developer." y el portal
#   queda muerto.

sm.warp(875010240)
