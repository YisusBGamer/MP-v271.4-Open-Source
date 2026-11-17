package net.swordie.ms.handlers.user;

import net.swordie.ms.client.character.BeautyAlbum;
import net.swordie.ms.client.character.BeautyModule;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.packet.BeautyAlbumPacket;
import net.swordie.ms.handlers.Handler;
import net.swordie.ms.handlers.header.InHeader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Beauty Album (hair, face and skin rooms). Every request is an int key then a presence-bit byte and its fields:
 * Refresh (no body; sent on field entry), Change 0x143e33170 (int code, int normal look), Delete 0x143e33280
 * (int code), Save 0x143e33380 (int code, int tab, short normal look). Codes: 20000+i face, 30000+i hair,
 * 40000+i skin. Everything is free; full rooms grow by themselves.
 */
public class BeautyAlbumHandler {
    private static final Logger log = LogManager.getLogger(BeautyAlbumHandler.class);

    @Handler(op = InHeader.USER_BEAUTY_DATA_REQUEST)
    public static void handleBeautyDataRequest(Char chr, InPacket inPacket) {
        if (chr == null || inPacket.getUnreadAmount() < 4) {
            return;
        }
        int key = inPacket.decodeInt();
        BeautyAlbum album = chr.getBeautyAlbum();
        if (key == BeautyAlbumPacket.REFRESH_REQUEST) {
            chr.write(BeautyAlbumPacket.refresh(album));
            return;
        }
        int mask = inPacket.getUnreadAmount() > 0 ? inPacket.decodeByte() & 0xFF : 0;
        if (key == BeautyAlbumPacket.CHANGE_REQUEST) {
            int code = (mask & 1) != 0 ? inPacket.decodeInt() : -1;
            boolean main = (mask & 2) == 0 || inPacket.decodeInt() != 0;
            int id = album.get(code);
            boolean ok = id >= 0 && BeautyModule.applyLook(chr, id, main);
            chr.write(BeautyAlbumPacket.change(ok ? BeautyAlbumPacket.OK : BeautyAlbumPacket.FAIL));
        } else if (key == BeautyAlbumPacket.DELETE_REQUEST) {
            int code = (mask & 1) != 0 ? inPacket.decodeInt() : -1;
            if (album.set(code, -1)) {
                BeautyModule.ALBUM_DAO.save(album);
            }
            chr.write(BeautyAlbumPacket.refresh(album));
        } else if (key == BeautyAlbumPacket.SAVE_REQUEST) {
            int code = (mask & 1) != 0 ? inPacket.decodeInt() : -1;
            if ((mask & 2) != 0) {
                inPacket.decodeInt(); // the open tab (same type as the code)
            }
            boolean main = (mask & 4) == 0 || inPacket.decodeShort() != 0;
            int type = BeautyAlbum.typeOf(code);
            int id = BeautyModule.currentLook(chr, type, main);
            if (id < 0 || !album.set(code, id)) {
                chr.write(BeautyAlbumPacket.save(BeautyAlbumPacket.FAIL, code, -1));
                return;
            }
            boolean grew = album.isFull(type) && album.extend(type);
            BeautyModule.ALBUM_DAO.save(album);
            chr.write(BeautyAlbumPacket.save(BeautyAlbumPacket.OK, code, id));
            if (grew) {
                chr.write(BeautyAlbumPacket.extend(type, album.slots(type).length));
            }
        } else {
            log.debug("Unknown beauty album request key {} ({} bytes left)", key, inPacket.getUnreadAmount());
        }
    }
}
