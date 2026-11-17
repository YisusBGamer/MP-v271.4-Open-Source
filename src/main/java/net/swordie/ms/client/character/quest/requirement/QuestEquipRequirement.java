package net.swordie.ms.client.character.quest.requirement;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.loaders.DatSerializable;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

/**
 * Equipped-item requirement. Built from:
 * <ul>
 *     <li><code>equipAllNeed</code>: every listed item must be equipped (one group per item)</li>
 *     <li><code>equipSelectNeed</code>: at least one listed item must be equipped (a single group)</li>
 *     <li><code>itemEquip</code>: completion requirement, entries sharing an <code>orGroup</code> form one group</li>
 * </ul>
 * The requirement is met when every group has at least one of its items equipped.
 */
public class QuestEquipRequirement implements QuestStartRequirement {

    private List<List<Integer>> groups = new ArrayList<>();

    public List<List<Integer>> getGroups() {
        return groups;
    }

    public void addGroup(List<Integer> itemIDs) {
        if (itemIDs != null && !itemIDs.isEmpty()) {
            getGroups().add(new ArrayList<>(itemIDs));
        }
    }

    @Override
    public boolean hasRequirements(Char chr) {
        List<Item> equipped = chr.getEquippedInventory() == null ? new ArrayList<>() : chr.getEquippedInventory().getItems();
        for (List<Integer> group : getGroups()) {
            boolean found = false;
            for (Item item : equipped) {
                if (group.contains(item.getItemId())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void write(RandomAccessFile dos) throws IOException {
        dos.writeShort(getGroups().size());
        for (List<Integer> group : getGroups()) {
            dos.writeShort(group.size());
            for (int id : group) {
                dos.writeInt(id);
            }
        }
    }

    @Override
    public DatSerializable load(RandomAccessFile dis) throws IOException {
        QuestEquipRequirement qer = new QuestEquipRequirement();
        short size = dis.readShort();
        for (int i = 0; i < size; i++) {
            short groupSize = dis.readShort();
            List<Integer> group = new ArrayList<>();
            for (int j = 0; j < groupSize; j++) {
                group.add(dis.readInt());
            }
            qer.addGroup(group);
        }
        return qer;
    }
}
