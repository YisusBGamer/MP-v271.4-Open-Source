# 450013820 - mapa de transicion de la variante "Sc" del Mago Oscuro.
#
# El WZ (Map.wz/Map4) define onUserEnter = "enter_450013820" y el script no
# existia (el server lo creaba vacio al vuelo). El mapa no tiene vida de mob:
# es una sala de paso entre las fases "Sc".
#
# El portal "sp" tiene tm=999999999 (destino invalido): la progresion la hace
# el script de muerte del jefe (die_blackMage) al pasar a la siguiente sala.

sm.sendSayOkay("The path to the Black Mage lies ahead...")
