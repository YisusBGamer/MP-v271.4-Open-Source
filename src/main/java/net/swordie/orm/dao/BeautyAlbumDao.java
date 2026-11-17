package net.swordie.orm.dao;

import net.swordie.ms.client.character.BeautyAlbum;
import net.swordie.ms.connection.db.DatabaseManager;

import java.util.Arrays;
import java.util.stream.Collectors;

/** Beauty Album slots and the dress-up look of a character (beauty_albums). */
public class BeautyAlbumDao {

    public BeautyAlbum byChar(int charId) {
        BeautyAlbum album = new BeautyAlbum(charId);
        var rows = DatabaseManager.queryRows("SELECT haircap, facecap, skincap, hairs, faces, skins, dressface, "
                + "dresshair, dressskin, dresson FROM beauty_albums WHERE charid = ?", charId);
        if (rows.isEmpty()) {
            return album;
        }
        var row = rows.get(0);
        album.setSlots(BeautyAlbum.HAIR, parse((String) row.get("hairs"), num(row.get("haircap")),
                album.slots(BeautyAlbum.HAIR).length, album.maxSlots(BeautyAlbum.HAIR)));
        album.setSlots(BeautyAlbum.FACE, parse((String) row.get("faces"), num(row.get("facecap")),
                album.slots(BeautyAlbum.FACE).length, album.maxSlots(BeautyAlbum.FACE)));
        album.setSlots(BeautyAlbum.SKIN, parse((String) row.get("skins"), num(row.get("skincap")),
                album.slots(BeautyAlbum.SKIN).length, album.maxSlots(BeautyAlbum.SKIN)));
        album.setDressFace(num(row.get("dressface")));
        album.setDressHair(num(row.get("dresshair")));
        album.setDressSkin(num(row.get("dressskin")));
        album.setDressOn(num(row.get("dresson")) != 0);
        return album;
    }

    public void save(BeautyAlbum album) {
        DatabaseManager.executeQuery("INSERT INTO beauty_albums (charid, haircap, facecap, skincap, hairs, faces, "
                        + "skins, dressface, dresshair, dressskin, dresson) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) "
                        + "ON DUPLICATE KEY UPDATE haircap = VALUES(haircap), facecap = VALUES(facecap), "
                        + "skincap = VALUES(skincap), hairs = VALUES(hairs), faces = VALUES(faces), "
                        + "skins = VALUES(skins), dressface = VALUES(dressface), dresshair = VALUES(dresshair), "
                        + "dressskin = VALUES(dressskin), dresson = VALUES(dresson)",
                album.getCharId(), album.slots(BeautyAlbum.HAIR).length, album.slots(BeautyAlbum.FACE).length,
                album.slots(BeautyAlbum.SKIN).length, join(album.slots(BeautyAlbum.HAIR)),
                join(album.slots(BeautyAlbum.FACE)), join(album.slots(BeautyAlbum.SKIN)), album.getDressFace(),
                album.getDressHair(), album.getDressSkin(), album.isDressOn() ? 1 : 0);
    }

    private static int num(Object o) {
        return o instanceof Number n ? n.intValue() : 0;
    }

    private static String join(int[] arr) {
        return Arrays.stream(arr).mapToObj(String::valueOf).collect(Collectors.joining(","));
    }

    private static int[] parse(String s, int cap, int min, int max) {
        int n = Math.max(min, Math.min(max, cap));
        int[] arr = new int[n];
        Arrays.fill(arr, -1);
        if (s != null && !s.isEmpty()) {
            String[] parts = s.split(",");
            for (int i = 0; i < parts.length && i < n; i++) {
                try {
                    arr[i] = Integer.parseInt(parts[i].trim());
                } catch (NumberFormatException ignored) {
                }
            }
        }
        return arr;
    }
}
