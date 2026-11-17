# mTour_Shuttle.py
# Script de Teletransporte a MNN Broadcasting Station (ID: 867149000)

MNN_BROADCAST_STATION_MAP_ID = 867149000 

# --- Diálogo de la Imagen 1 (sendAskYesNo) ---
# Se utiliza la etiqueta #m para mostrar el nombre del mapa en el diálogo
dialog_question = "[Guide] MNN Broadcasting Station Shuttle\r\n"
dialog_question += "You can board a bus that goes to the MNN Broadcasting Station from here.\r\n"
dialog_question += "Would you like to travel to the MNN Broadcasting Station?"

response = sm.sendAskYesNo(dialog_question)

if response:
    # --- Diálogo de la Imagen 2 (sendNext) y Teletransporte ---
    
    # Muestra el mensaje de la segunda imagen.
    sm.sendNext("The bus to MNN Broadcasting Station is now departing.")
    
    # Ejecuta el teletransporte.
    sm.warp(MNN_BROADCAST_STATION_MAP_ID, 0)
    
else:
    # Si el jugador dice NO, se muestra un mensaje simple antes de terminar.
    sm.sendSayOkay("Alright, maybe next time!")
    
# Finaliza el diálogo y el script. (Crucial)
sm.dispose()
