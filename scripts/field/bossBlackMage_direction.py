# Mapa de DIRECCION del Mago Oscuro (450013000 / 450013200 / 450013400 / 450013600).
#
# El WZ (Map.wz/Map4) define onUserEnter = "bossBlackMage_direction" en estos
# mapas, pero el script NO existia -> el server lo creaba vacio al vuelo y el
# jugador se quedaba atrapado en el mapa de transicion sin poder continuar.
#
# Estos mapas no tienen vida (solo el portal "sp" con tm=999999999) y no hay
# ningun mob: son salas de paso entre las fases del jefe.
#
# El flujo real es:
#   450013100 (P1) -> 450013200 (direccion) -> 450013300 (P2)
#   450013300 (P2) -> 450013400 (direccion) -> 450013500 (P3)
#   450013500 (P3) -> 450013600 (direccion) -> 450013700 (P4)
#   450013700 (P4) -> 450013750 (P5)
#
# La progresion la hace die_blackMage.py (que warpea a la sala siguiente cuando
# muere el jefe). Aqui solo se muestra el mensaje y se mantiene al jugador en
# la sala; si el grupo se separa, el siguiente warp de la_INSTANCE lo lleva a
# la sala del jefe que toque.

fieldId = sm.getFieldID()

# salas de direccion -> siguiente sala del jefe
NEXT_FIELD = {
    450013000: 450013100,
    450013200: 450013300,
    450013400: 450013500,
    450013600: 450013700,
}

next_field = NEXT_FIELD.get(fieldId)

if next_field is not None:
    # el timer de la instancia sigue corriendo: no hay que hacer nada mas aqui.
    # se avisa por si el jugador entra solo a la sala de transicion.
    sm.sendSayOkay("The path to the Black Mage lies ahead...")
