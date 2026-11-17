# Portal script pt_to_peri
# Lo invoca el atributo script del portal 15 del mapa 993244300 (Map.wz/Map).
# El WZ declara el destino 102000000: la unica arista portalNum=15 de Graph.img.
# MigrationHandler.java:211-212 abre el script y NO teletransporta; el warp pendiente se
#   ejecuta al terminar el archivo (ScriptManagerImpl.java:311), por eso no hace falta dispose.
# Sin archivo el chat suelta "Portal not implemented, please report to developer." y el portal
#   queda muerto.

sm.warp(102000000)
