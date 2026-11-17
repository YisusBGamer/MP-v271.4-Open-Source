# Package Deliverer: opens the parcel window
from net.swordie.ms.connection.packet import ParcelPacket
from net.swordie.ms.handlers.social import ParcelHandler

chr.write(ParcelPacket.open(ParcelHandler.load(chr.getId())))
sm.dispose()
