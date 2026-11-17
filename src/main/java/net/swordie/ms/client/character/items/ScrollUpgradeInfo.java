package net.swordie.ms.client.character.items;

import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.constants.ItemConstants;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.connection.Encodable;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.enums.EnchantStat;
import net.swordie.ms.enums.EquipBaseStat;
import net.swordie.ms.enums.SpellTraceScrollType;
import net.swordie.ms.loaders.ItemData;
import net.swordie.ms.util.Util;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author Sjonnie
 * Created on 8/18/2018.
 */
public class ScrollUpgradeInfo implements Encodable {
    /** Armor's 4th successful upgrade adds this much ATT and Magic ATT on top of the scroll (GMS Spell Trace rule). */
    public static final int ARMOR_BONUS_UPGRADE = 4;
    public static final int ARMOR_BONUS_ATTACK = 1;

    private int iconID;
    private String title;
    private SpellTraceScrollType type;
    private int option;
    private TreeMap<EnchantStat, Integer> stats; // needs to be sorted
    private int cost;
    private int chance;
    private int oldCost;

    public ScrollUpgradeInfo(int iconID, String title, SpellTraceScrollType scrollType, int scrollOption,
                             TreeMap<EnchantStat, Integer> scrollStats, int cost, int chance) {
        this.iconID = iconID;
        this.title = title;
        this.type = scrollType;
        this.option = scrollOption;
        this.stats = scrollStats;
        this.cost = cost;
        this.chance = chance;
    }

    public int getMask() {
        int mask = 0;
        for (EnchantStat es : getStats().keySet()) {
            mask |= es.getVal();
        }
        return mask;
    }

    @Override
    public void encode(OutPacket outPacket) {
        encode(outPacket, 0);
    }

    /** v265 layout (client 0x1406a90c0, which first reads three ints in 0x1406a88c0) */
    public void encode(OutPacket outPacket, int index) {
        outPacket.encodeInt(2);
        outPacket.encodeInt(71);
        outPacket.encodeInt(index);
        outPacket.encodeInt(getIconID());
        outPacket.encodeString(getTitle());
        outPacket.encodeInt(getType().ordinal());
        outPacket.encodeInt(getOption());
        outPacket.encodeInt(-1);
        outPacket.encodeByte(0); // v271 stat block flag (0x1407f12e0)
        outPacket.encodeInt(getMask());
        for (Map.Entry<EnchantStat, Integer> entry : getStats().entrySet()) {
            outPacket.encodeInt(entry.getValue());
        }
        outPacket.encodeInt(GameConstants.boosted(getChance())); // the odds the roll uses
        outPacket.encodeInt(getOldCost());
        outPacket.encodeInt(getCost());
        outPacket.encodeByte(getType() != SpellTraceScrollType.Normal);
        outPacket.encodeInt(0); // count of (4, 4) pairs
    }

    public boolean applyTo(Equip equip, Char chr) {
        boolean success = false;
        switch (getType()) {
            case Normal:
                if (equip.getTuc() <= 0) {
                    chr.chatMessage("This item cannot be scrolled.");
                    return false;
                }

                int chance = getChance() + chr.getAvatarData().getCharacterStat().getExtraScrollChance(equip)
                        + net.swordie.ms.handlers.social.module.GuildModule.skillValue(chr, // Enhancement Mastery
                        net.swordie.ms.handlers.social.module.GuildModule.ENHANCEMENT_MASTERY,
                        net.swordie.ms.client.character.skills.SkillStat.itemUpgradeBonusR);
                success = GameConstants.rollScrollSuccess(chance);
                if (success) {
                    for (Map.Entry<EnchantStat, Integer> entry : getStats().entrySet()) {
                        EnchantStat es = entry.getKey();
                        int val = entry.getValue();
                        equip.addStat(es.getEquipBaseStat(), val);
                    }
                    equip.addStat(EquipBaseStat.tuc, -1);
                    equip.addStat(EquipBaseStat.cuc, 1);
                    if (equip.getCuc() == ARMOR_BONUS_UPGRADE && isBonusArmor(equip)) {
                        equip.addStat(EquipBaseStat.iPAD, ARMOR_BONUS_ATTACK);
                        equip.addStat(EquipBaseStat.iMAD, ARMOR_BONUS_ATTACK);
                    }
                } else {
                    boolean tucSaved = Util.succeedProp(net.swordie.ms.handlers.social.module.GuildModule.skillValue(
                            chr, net.swordie.ms.handlers.social.module.GuildModule.UPGRADE_SALVATION,
                            net.swordie.ms.client.character.skills.SkillStat.itemTUCProtectR));
                    if (tucSaved && !equip.hasAttribute(EquipAttribute.UpgradeCountProtection)) {
                        chr.chatMessage("The guild's Upgrade Salvation kept your upgrade slot.");
                    }
                    if (!tucSaved && !equip.hasAttribute(EquipAttribute.UpgradeCountProtection)) {
                        equip.addStat(EquipBaseStat.tuc, -1);
                    }
                }
                equip.removeAttribute(EquipAttribute.LuckyDay);
                equip.removeAttribute(EquipAttribute.UpgradeCountProtection);
                break;
            case CleanSlate:
                Equip fullTucEquip = ItemData.getEquipDeepCopy(equip.getItemId(), false);
                int maxTuc = fullTucEquip.getTuc() + equip.getIuc();
                if (equip.getTuc() + equip.getCuc() >= maxTuc) {
                    chr.chatMessage("This item has no open slots to recover.");
                    chr.dispose();
                    return false;
                }

                success = GameConstants.rollScrollSuccess(getChance());
                if (success) {
                    equip.addStat(EquipBaseStat.tuc, 1);
                }
                break;
            case Innocence:
                success = GameConstants.rollScrollSuccess(getChance());
                if (success) {
                    equip.applyInnocenceScroll();
                }
                break;
        }
        return success;
    }

    /** Armor and gloves, the pieces Spell Trace's 4th-upgrade bonus applies to (not weapons, shields, accessories). */
    private static boolean isBonusArmor(Equip equip) {
        BodyPart bp = BodyPart.getByVal(ItemConstants.getBodyPartFromItem(equip.getItemId(), 0));
        return bp != null && bp != BodyPart.Weapon && bp != BodyPart.Shield && bp != BodyPart.MechanicalHeart
                && !ItemConstants.isAccessory(equip.getItemId());
    }

    public int getIconID() {
        return iconID;
    }

    public void setIconID(int iconID) {
        this.iconID = iconID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public SpellTraceScrollType getType() {
        return type;
    }

    public void setType(SpellTraceScrollType type) {
        this.type = type;
    }

    public int getOption() {
        return option;
    }

    public void setOption(int option) {
        this.option = option;
    }

    public TreeMap<EnchantStat, Integer> getStats() {
        return stats;
    }

    public void setStats(TreeMap<EnchantStat, Integer> stats) {
        this.stats = stats;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getChance() {
        return chance;
    }

    public void setChance(int chance) {
        this.chance = chance;
    }

    public int getOldCost() {
        return oldCost;
    }

    public void setOldCost(int oldCost) {
        this.oldCost = oldCost;
    }
}
