package net.swordie.ms.client.character.skills;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.quest.Quest;
import net.swordie.ms.client.character.quest.QuestManager;
import net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat;
import net.swordie.ms.client.character.skills.temp.TemporaryStatManager;
import net.swordie.ms.client.jobs.legend.Luminous;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.connection.packet.model.MessagePacket;
import net.swordie.ms.constants.SkillChangeConstants;
import net.swordie.ms.loaders.QuestData;

/**
 * Created on 2/10/2018.
 */
public class LarknessManager {
	private Char chr;
	private int gauge;
	private LarknessType larknessType;
	public static final int maxLarkness = 10000;
	public static final int minLarkness = 0;
	// Luminous' tutorial quests Eclipse and Sunfire (25500: light, dark) and New Balance (25501: mix) wait on these
	// keys of record 25502.
	private static final int LARKNESS_TUTORIAL_RECORD = 25502;
	private static final int ECLIPSE_AND_SUNFIRE = 25500;
	private static final int NEW_BALANCE = 25501;

	public LarknessManager(Char chr) {
		this.chr = chr;
		this.gauge = maxLarkness / 2; // Half way
		this.larknessType = LarknessType.Dark;
	}

	public int getGauge() {
		return gauge;
	}

	public void setGauge(int gauge) {
		this.gauge = (gauge > maxLarkness ? maxLarkness : (gauge < minLarkness ? minLarkness : gauge)); // Set boundaries  0 ~ 10,000
	}

	public void addGauge(int addedValue) {
		setGauge(getGauge() + addedValue);
	}

	public LarknessType getLarknessType() {
		return larknessType;
	}

	public void setLarknessType(LarknessType larknessType) {
		this.larknessType = larknessType;
	}

	public boolean isDark() {
		return getLarknessType() == LarknessType.Dark;
	}

	public boolean isEquilibrium() {
		TemporaryStatManager tsm = chr.getTemporaryStatManager();
		return tsm.hasStat(CharacterTemporaryStat.Larkness) && tsm.getOption(CharacterTemporaryStat.Larkness).rOption == Luminous.EQUILIBRIUM2;
	}

	public enum LarknessType {
		None(0),
		Dark(1),
		Light(2),
		;
		private byte val;

		LarknessType(int val) {
			this.val = (byte) val;
		}

		public byte getVal() {
			return val;
		}
	}

	/**
	 * If Dark,  Gauge goes up 		(up to 10,000)
	 * If Light, Gauge goes down	(down to 0)
	 * The v271 client keeps no gauge: only the Larkness stat's reason (the mode) reaches it.
	 * @param gaugeChange
	 */
	public void changeGauge(int gaugeChange) {
		addGauge(isDark() ? gaugeChange : -gaugeChange);
		if (getGauge() <= minLarkness || getGauge() >= maxLarkness) {
			if (chr.hasSkill(Luminous.EQUILIBRIUM)) {
				goEquilibriumMode(false);
				return;
			} else {
				changeMode();
			}
		}
	}

	public void goEquilibriumMode(boolean byEquilise) {
		TemporaryStatManager tsm = chr.getTemporaryStatManager();
		var tOpt = 10 + ((Luminous) chr.getJobHandler()).getMoreEquilibriumTime();

		Option o = new Option(2, Luminous.EQUILIBRIUM2, tOpt);
		tsm.putCharacterStatValue(CharacterTemporaryStat.Larkness, o, byEquilise);


		// [Luminous] Custom Skill Change
		if (SkillChangeConstants.PRE_PORT_CUSTOMS) {
			Option o1 = new Option(SkillChangeConstants.EQUILIBRIUM_BOOSTER, Luminous.EQUILIBRIUM2, tOpt);
			tsm.putCharacterStatValue(CharacterTemporaryStat.IndieBooster, o1, byEquilise);
		}


		tsm.sendSetStatPacket();
		markTutorialRecord(NEW_BALANCE, "mix");
		if (chr.getClient().getResolution() != null) {
			chr.activateCustomEffect(chr.getClient().getResolution().toString(), 200);
		}
		chr.resetSkillCoolTime(Luminous.DEATH_SCYTHE);
		chr.resetSkillCoolTime(Luminous.ENDER);
	}

	public void changeMode() {
		setLarknessType(isDark() ? LarknessType.Light : LarknessType.Dark);
		TemporaryStatManager tsm = chr.getTemporaryStatManager();
		Option o = new Option();
		o.nOption = 1;
		o.rOption = isDark() ? Luminous.ECLIPSE : Luminous.SUNFIRE;
		tsm.putCharacterStatValue(CharacterTemporaryStat.Larkness, o);
		tsm.sendSetStatPacket();
		markTutorialRecord(ECLIPSE_AND_SUNFIRE, isDark() ? "dark" : "light");
	}

	/**
	 * Sets key=1 on record 25502 while the tutorial quest is in progress (the quest window then shows it done).
	 */
	private void markTutorialRecord(int questId, String key) {
		QuestManager qm = chr.getQuestManager();
		if (!qm.hasQuestInProgress(questId)) {
			return;
		}
		Quest record = qm.getQuestById(LARKNESS_TUTORIAL_RECORD);
		if (record == null) {
			record = QuestData.createQuestFromId(LARKNESS_TUTORIAL_RECORD);
			qm.addQuest(record);
		}
		if ("1".equals(record.getProperty(key))) {
			return;
		}
		record.setProperty("0", "0");
		record.setProperty(key, "1");
		chr.write(WvsContext.message(MessagePacket.questRecordExMessage(record)));
	}
}