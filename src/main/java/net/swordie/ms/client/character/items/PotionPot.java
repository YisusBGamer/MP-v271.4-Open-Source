package net.swordie.ms.client.character.items;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.quest.Quest;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.enums.SpecStat;
import net.swordie.ms.handlers.header.OutHeader;
import net.swordie.ms.loaders.ItemData;
import net.swordie.ms.loaders.QuestData;
import net.swordie.ms.util.FileTime;

import java.util.HashMap;
import java.util.Map;

/**
 * Potion Pot (5820000-5820999): potions dragged in fill it, using it refills HP and MP from it. Pot Embiggener
 * (5821xxx) raises the capacity by 1,000,000 up to 10,000,000; a Rust Remover (55000[1x]) renews an expired pot.
 * Packets (v265 RE): in 1175 use, 1176 add, 1177 HP/MP options, 1178 increase; out 654 POTION_POT_UPDATE
 * (4 item, 4 char, 4 max, 4 hp, 4 mp, FT start, FT end) then 652 POTION_POT_RESULT (1 type; 0 + 1 code). The pot
 * is kept in quest record {@link #QR}; CharacterData lists it (the "(int x5, long x2)" list, 36 bytes a pot).
 */
public class PotionPot {
    public static final int QR = 500783;
    public static final int STEP = 1_000_000, MAX = 10_000_000;
    public static final int NO_ITEM = 1, FULL = 2, MAX_CAPACITY = 3, RUSTY = 4, NOT_RUSTY = 5, EMPTY = 6;

    public int itemId, max = STEP, hp, mp;
    public long start, end; // millis, end 0 = never
    public boolean useHp = true, useMp = true;

    public static boolean isPot(int itemId) {
        return itemId / 1000 == 5820;
    }

    public static PotionPot of(Char chr) {
        Quest q = chr.getQuestManager() == null ? null : chr.getQuestManager().getQuestById(QR);
        if (q == null || q.getQRValue() == null || q.getQRValue().isEmpty()) {
            return null;
        }
        Map<String, String> m = new HashMap<>();
        for (String kv : q.getQRValue().split(";")) {
            String[] p = kv.split("=", 2);
            if (p.length == 2) {
                m.put(p[0], p[1]);
            }
        }
        try {
            PotionPot pot = new PotionPot();
            pot.itemId = Integer.parseInt(m.getOrDefault("id", "0"));
            pot.max = Integer.parseInt(m.getOrDefault("max", String.valueOf(STEP)));
            pot.hp = Integer.parseInt(m.getOrDefault("hp", "0"));
            pot.mp = Integer.parseInt(m.getOrDefault("mp", "0"));
            pot.start = Long.parseLong(m.getOrDefault("s", "0"));
            pot.end = Long.parseLong(m.getOrDefault("e", "0"));
            pot.useHp = !"0".equals(m.get("oh"));
            pot.useMp = !"0".equals(m.get("om"));
            return pot.itemId > 0 ? pot : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public void save(Char chr) {
        var qm = chr.getQuestManager();
        Quest q = qm.getQuestById(QR);
        if (q == null) {
            q = QuestData.createQuestFromId(QR);
            qm.addQuest(q);
        }
        q.setQrValue(String.format("id=%d;max=%d;hp=%d;mp=%d;s=%d;e=%d;oh=%d;om=%d", itemId, max, hp, mp, start, end,
                useHp ? 1 : 0, useMp ? 1 : 0));
    }

    public boolean isRusty() {
        return end > 0 && end < System.currentTimeMillis();
    }

    /** The character's pot for this pot item, made the first time (its end is the item's own expiry). */
    public static PotionPot getOrCreate(Char chr, Item potItem) {
        PotionPot pot = of(chr);
        if (pot == null || pot.itemId != potItem.getItemId()) {
            pot = pot == null ? new PotionPot() : pot;
            pot.itemId = potItem.getItemId();
            pot.start = System.currentTimeMillis();
            FileTime exp = potItem.getDateExpire();
            pot.end = exp == null || exp.isMaxTime() || exp.toMillis() <= 0 ? 0 : exp.toMillis();
            pot.save(chr);
        }
        return pot;
    }

    /** One pot record: 4 item, 4 char, 4 max, 4 hp, 4 mp, FT start, FT end (36 bytes) */
    public void encode(OutPacket out, int charId) {
        out.encodeInt(itemId);
        out.encodeInt(charId);
        out.encodeInt(max);
        out.encodeInt(hp);
        out.encodeInt(mp);
        out.encodeFT(FileTime.fromEpochMillis(start));
        out.encodeFT(end == 0 ? FileTime.fromType(FileTime.Type.MAX_TIME) : FileTime.fromEpochMillis(end));
    }

    /** CharacterData's pot list: this character's pot, or none */
    public static void encodeList(OutPacket out, Char chr) {
        PotionPot pot = chr == null ? null : of(chr);
        if (pot == null) {
            out.encodeInt(0);
            return;
        }
        out.encodeInt(1);
        pot.encode(out, chr.getId());
    }

    public OutPacket update(int charId) {
        OutPacket out = new OutPacket(OutHeader.POTION_POT_UPDATE);
        encode(out, charId);
        return out;
    }

    public static OutPacket result(int type, int code) {
        OutPacket out = new OutPacket(OutHeader.POTION_POT_RESULT);
        out.encodeByte(type);
        if (type == 0) {
            out.encodeByte(code);
        }
        return out;
    }

    /** HP and MP a potion puts in the pot: its flat amount, or its % of the character's max */
    public static int[] potionValue(Char chr, int itemId) {
        var ii = ItemData.getItemInfoByID(itemId);
        if (ii == null) {
            return new int[]{0, 0};
        }
        var spec = ii.getSpecStats();
        long hp = spec.getOrDefault(SpecStat.hp, 0);
        long mp = spec.getOrDefault(SpecStat.mp, 0);
        hp += (long) chr.getMaxHP() * spec.getOrDefault(SpecStat.hpR, 0) / 100;
        mp += (long) chr.getMaxMP() * spec.getOrDefault(SpecStat.mpR, 0) / 100;
        return new int[]{(int) Math.min(Integer.MAX_VALUE, hp), (int) Math.min(Integer.MAX_VALUE, mp)};
    }
}
