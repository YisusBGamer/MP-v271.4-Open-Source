# Portal script glpqPortal6
# Lo invoca el atributo script del portal 6 del mapa 610030600 (Map.wz/Map).
# El WZ declara el destino 610030700: la unica arista portalNum=6 de Graph.img.
# MigrationHandler.java:211-212 abre el script y NO teletransporta; el warp pendiente se
#   ejecuta al terminar el archivo (ScriptManagerImpl.java:311), por eso no hace falta dispose.
# Sin archivo el chat suelta "Portal not implemented, please report to developer." y el portal
#   queda muerto.

sm.warp(610030700)
