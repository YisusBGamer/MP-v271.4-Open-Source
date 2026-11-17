package net.swordie.ms.world.shop;

import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.constants.ItemConstants;
import net.swordie.ms.loaders.ItemData;
import net.swordie.ms.loaders.containerclasses.ItemInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 3/27/2018.
 */
public class NpcShopDlg {

	private int shopID;
	private int selectNpcItemID;
	private int npcTemplateID;
	private int shopVerNo;
	private List<NpcShopItem> items = new ArrayList<>();

	public void generateProjectiles() {
		for (int i : ItemConstants.getRechargeablesList()) {
			ItemInfo ii = ItemData.getItemInfoByID(i);
			if (ii == null) {
				// atm just 2070014 (2070014) that somewhy isn't found
				continue;
			}
			NpcShopItem nsi = new NpcShopItem();
			nsi.setItemID(i);
			nsi.setUnitPrice(1);
			nsi.setMaxPerSlot((short) ii.getSlotMax());
			addItem(nsi);
		}
	}

	public void encode(OutPacket outPacket, List<NpcShopItem> buyBack) {
		// v265: int, int, string, int, all empty on GMS
		outPacket.encodeInt(0);
		outPacket.encodeInt(0);
		outPacket.encodeString("");
		outPacket.encodeInt(0);
		// v271 (0x143fb5a70): the v265 quest byte and its (4, Str) list are gone; the item count follows
		outPacket.encodeShort(getItems().size() + buyBack.size());
		int index = 0;
		for (NpcShopItem item : getItems()) {
			item.encode(outPacket, index++);
		}
		index = 0;
		for (NpcShopItem item : buyBack) {
			item.encode(outPacket, index++);
		}
	}

	public int getShopID() {
		return shopID;
	}

	public void setShopID(int shopID) {
		this.shopID = shopID;
	}

	public List<NpcShopItem> getItems() {
		return items;
	}

	public int getSelectNpcItemID() {
		return selectNpcItemID;
	}

	public void setSelectNpcItemID(int selectNpcItemID) {
		this.selectNpcItemID = selectNpcItemID;
	}

	public int getNpcTemplateID() {
		return npcTemplateID;
	}

	public void setNpcTemplateID(int npcTemplateID) {
		this.npcTemplateID = npcTemplateID;
	}

	public int getShopVerNo() {
		return shopVerNo;
	}

	public void setShopVerNo(int shopVerNo) {
		this.shopVerNo = shopVerNo;
	}

	public void addItem(NpcShopItem nsi) {
		if (ItemConstants.isWeaponKnownToClient(nsi.getItemID())) {
			getItems().add(nsi);
		}
	}

	/** Leaves out rows the client can't show (it disconnects when one scrolls into view). */
	public void setItems(List<NpcShopItem> items) {
		List<NpcShopItem> shown = new ArrayList<>(items.size());
		for (NpcShopItem nsi : items) {
			if (ItemConstants.isWeaponKnownToClient(nsi.getItemID())) {
				shown.add(nsi);
			}
		}
		this.items = shown;
	}

	public NpcShopItem getItemByIndex(int idx, List<NpcShopItem> buyBack) {
		NpcShopItem nsi = null;
		if (idx >= 0 && idx < getItems().size()) {
			nsi = getItems().get(idx);
		} else if (idx >= getItems().size() && idx - getItems().size() < buyBack.size()) {
			nsi = buyBack.get(idx - getItems().size());
		}
		return nsi;
	}
}
