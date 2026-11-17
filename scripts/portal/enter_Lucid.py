# Portal script enter_Lucid
# Lo invoca el atributo script del portal 14 del mapa 993267201 (Map.wz/Map).
# El WZ declara el destino 450004000: la unica arista portalNum=14 de Graph.img.
# MigrationHandler.java:211-212 abre el script y NO teletransporta; el warp pendiente se
#   ejecuta al terminar el archivo (ScriptManagerImpl.java:311), por eso no hace falta dispose.
# Sin archivo el chat suelta "Portal not implemented, please report to developer." y el portal
#   queda muerto.

sm.warp(450004000)
