# Portal script q34344_f1_pt00
# Lo invoca el atributo script del portal 1 del mapa 993072910 (Map.wz/Map).
# El WZ declara el destino 993072920: la unica arista portalNum=1 de Graph.img.
# MigrationHandler.java:211-212 abre el script y NO teletransporta; el warp pendiente se
#   ejecuta al terminar el archivo (ScriptManagerImpl.java:311), por eso no hace falta dispose.
# Sin archivo el chat suelta "Portal not implemented, please report to developer." y el portal
#   queda muerto.

sm.warp(993072920)
