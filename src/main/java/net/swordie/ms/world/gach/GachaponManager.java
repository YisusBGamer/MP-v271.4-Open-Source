package net.swordie.ms.world.gach;

import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.connection.packet.GachaponDlg;
import net.swordie.ms.util.container.Tuple;
import net.swordie.ms.world.gach.result.GachaponDlgType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GachaponManager {
    private Map<GachaponDlgType, List<Tuple<Item, Short>>> rewards = new HashMap<>();

    public GachaponManager() {
        rewards.put(GachaponDlgType.TOWN, new ArrayList<>());
        rewards.put(GachaponDlgType.NEBULITE, new ArrayList<>());
        rewards.put(GachaponDlgType.CHAIR, new ArrayList<>());
        rewards.put(GachaponDlgType.MOUNT, new ArrayList<>());
        rewards.put(GachaponDlgType.SPECIAL, new ArrayList<>());
    }

    public List<Tuple<Item, Short>> getRewards(GachaponDlgType dialog) {
        return rewards.get(dialog);
    }

    public void addItem(GachaponDlgType dialog, Item item, short quantity) {
        List<Tuple<Item, Short>> rewarded = rewards.get(dialog);
        if (rewarded == null) {
            return;
        }
        rewarded.add(new Tuple<>(item, quantity));
        rewards.put(dialog, rewarded);
    }

    public OutPacket encode(GachaponDlgType dlg) {
        return encode(dlg, 0);
    }

    /** The gachapon window; the town gachapon shows the pool of that map's town. */
    public OutPacket encode(GachaponDlgType dlg, int fieldId) {
        GachaponInfo info = GachaponConstants.getInfo(dlg, fieldId);
        if (info == null) {
            GachaponConstants.init();
            info = GachaponConstants.getInfo(dlg, fieldId);
        }
        List<String> messages = info != null ? info.getMessages() : new ArrayList<>();
        List<Integer> hot = info != null ? info.getHotItems() : new ArrayList<>();
        return GachaponDlg.openGach(dlg, messages, getRewards(dlg), hot);
    }
}
