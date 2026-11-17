# Portal script 867168003_PCS01
# Lo invoca el atributo script del portal 12 del mapa 867168003 (Map.wz/Map).
# El WZ declara el destino 867168001: la unica arista portalNum=12 de Graph.img.
# MigrationHandler.java:211-212 abre el script y NO teletransporta; el warp pendiente se
#   ejecuta al terminar el archivo (ScriptManagerImpl.java:311), por eso no hace falta dispose.
# Sin archivo el chat suelta "Portal not implemented, please report to developer." y el portal
#   queda muerto.

sm.warp(867168001)
