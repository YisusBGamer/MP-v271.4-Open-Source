package net.swordie.orm.dao;

import net.swordie.ms.util.Util;

import net.swordie.ms.client.Account;
import net.swordie.ms.client.AccountBossCooldown;
import net.swordie.ms.client.LinkSkill;
import net.swordie.ms.client.User;
import net.swordie.ms.client.blacklist.BlackListEntry;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.MonsterCollection;
import net.swordie.ms.client.character.damage.DamageSkinSaveData;
import net.swordie.ms.client.character.union.Union;
import net.swordie.ms.client.character.union.UnionRaid;
import net.swordie.ms.client.soulcollection.SoulCollectionEntry;
import net.swordie.ms.client.trunk.Trunk;
import net.swordie.ms.constants.ServerFeatures;
import net.swordie.ms.connection.db.DatabaseManager;
import net.swordie.ms.connection.db.SaveSteps;
import net.swordie.ms.connection.db.converters.InlinedIntSetConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class AccountDao implements SworDao<Account> {

    private static final Logger log = LogManager.getLogger();

    private static final CharDao charDao = (CharDao) SworDaoFactory.getByClass(Char.class);
    private static final DamageSkinSaveDataDao dssdDao = (DamageSkinSaveDataDao) SworDaoFactory.getByClass(DamageSkinSaveData.class);
    private static final LinkSkillDao linkSkillDao = (LinkSkillDao) SworDaoFactory.getByClass(LinkSkill.class);
    private static final AccountBossCooldownDao abcDao = (AccountBossCooldownDao) SworDaoFactory.getByClass(AccountBossCooldown.class);

    private static final TrunkDao trunkDao = (TrunkDao) SworDaoFactory.getByClass(Trunk.class);
    private static final MonsterCollectionDao monsterCollectionDao = (MonsterCollectionDao) SworDaoFactory.getByClass(MonsterCollection.class);
    private static final UnionDao unionDao = (UnionDao) SworDaoFactory.getByClass(Union.class);
    private static final UnionRaidDao unionRaidDao = (UnionRaidDao) SworDaoFactory.getByClass(UnionRaid.class);
    private static final BlackListEntryDao blackListEntryDao = (BlackListEntryDao) SworDaoFactory.getByClass(BlackListEntry.class);
    private static final SoulCollectionDao soulCollectionDao = (SoulCollectionDao) SworDaoFactory.getByClass(SoulCollectionEntry.class);

    public void saveOrUpdate(User user, Account account, Char onlyChar) {
        if (account == null) {
            return;
        }
        boolean needsId = account.getId() == 0;
        var steps = new SaveSteps("account " + account.getId());

        // 1 to 1
        steps.run("trunk", () -> trunkDao.saveOrUpdate(account.getTrunk()));
        steps.run("monster collection", () -> monsterCollectionDao.saveOrUpdate(account.getMonsterCollection()));
        steps.run("union", () -> unionDao.saveOrUpdate(account.getUnion()));
        steps.run("union raid", () -> unionRaidDao.saveOrUpdate(account.getUnionRaid()));
        if (needsId) {
            steps.finish(); // a new account needs all of the above for its row
            steps.required("accounts row", () -> saveRow(user, account));
        } else {
            steps.run("accounts row", () -> saveRow(user, account));
        }

        // 1 to many
        steps.run("damage skins", () -> dssdDao.saveOrUpdate(account, account.getDamageSkins()));
        steps.run("link skills", () -> linkSkillDao.saveOrUpdate(account, account.getLinkSkills()));
        // off: don't load or save them with every account
        if (ServerFeatures.on(ServerFeatures.Feature.BOSS_COOLDOWNS)) {
            steps.run("boss cooldowns", () -> abcDao.saveOrUpdate(account, account.getBossCooldowns()));
        }
        steps.run("blacklist", () -> blackListEntryDao.saveOrUpdate(account, account.getBlackListEntries()));
        steps.run("soul collection", () -> soulCollectionDao.saveOrUpdate(account, account.getSoulCollectionEntries()));

        if (onlyChar != null) {
            steps.run("character " + onlyChar.getId(), () -> charDao.saveOrUpdate(account, onlyChar));
        } else {
            steps.run("characters", () -> charDao.saveOrUpdate(account, account.getCharacters()));
        }
        steps.finish();
    }

    private void saveRow(User user, Account account) {
        boolean needsId = account.getId() == 0;
        String query;
        if (needsId) {
            query = "INSERT INTO accounts(" +
                    "worldId, " +
                    "trunkID, " +
                    "monsterCollectionID, " +
                    "nxCredit, " +
                    "nxPrepaid, " +
                    "unionid, " +
                    "unionraidid, " +
                    "completedAccountQuests, " +
                    "userid, " +
                    "id " +
                    ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        } else {
            query = "UPDATE accounts SET " +
                    "worldId = ?," +
                    "trunkID = ?," +
                    "monsterCollectionID = ?," +
                    "nxCredit = ?," +
                    "nxPrepaid = ?, " +
                    "unionid = ?," +
                    "unionraidid = ?," +
                    "completedAccountQuests = ?," +
                    "userid = ? " +
                    "WHERE ID = ?";
        }
        long id = DatabaseManager.executeQuery(query,
                account.getWorldId(),
                account.getTrunk() == null ? null : account.getTrunk().getId(),
                account.getMonsterCollection() == null ? null : account.getMonsterCollection().getId(),
                account.getNxCredit(),
                account.getNxPrepaid(),
                account.getUnion() == null ? null : account.getUnion().getId(),
                account.getUnionRaid() == null ? null : account.getUnionRaid().getId(),
                InlinedIntSetConverter.toDbColumn(account.getCompletedAccountQuests()),
                user.getId(),
                account.getId()
        );
        if (needsId) {
            account.setId((int) id);
        }
    }

    public void delete(Account account) {
        if (account == null || account.getId() == 0) {
            log.debug("Trying to delete object " + account + " that is null or has id 0");
            return;
        }
        DatabaseManager.executeQuery("DELETE FROM accounts WHERE ID = ?", account.getId());
    }


    @Override
    public Account fromResultSet(ResultSet resultSet, String alias) {
        Account account = null;

        try {
            account = new Account();

            account.setId(resultSet.getInt(alias + ".id"));
            account.setWorldId(resultSet.getInt(alias + ".worldid"));
            account.setNxCredit(resultSet.getInt(alias + ".nxCredit"));
            account.setNxPrepaid(resultSet.getInt(alias + ".nxPrepaid"));
            account.setCompletedAccountQuests(InlinedIntSetConverter.fromDbColumn(resultSet.getString(alias + ".completedAccountQuests")));
            account.setTrunk(trunkDao.fromResultSet(resultSet, "t"));
            account.setMonsterCollection(monsterCollectionDao.fromResultSet(resultSet, "mc"));
            account.setUnion(unionDao.fromResultSet(resultSet, "u"));
            account.setUnionRaid(unionRaidDao.fromResultSet(resultSet, "ur"));
        } catch (SQLException e) {
            log.error("AccountDao.fromResultSet", e);
        }

        return account;
    }

    public Set<Account> getByUserId(int userId) {
        var list = DatabaseManager.executeSelect(this,
                "SELECT * FROM accounts a " +
                        "INNER JOIN trunks t ON t.id = a.trunkid " +
                        "INNER JOIN monster_collections mc ON mc.id = a.monstercollectionid " +
                        "INNER JOIN unions u ON u.id = a.unionid " +
                        "LEFT JOIN unionraid ur ON ur.id = a.unionraidid " +
                        "WHERE userid = ?", "a", userId);

        Set<Account> accounts = new HashSet<>();
        for (var obj : list) {
            if (obj != null) {
                accounts.add((Account) obj);
            }
        }
        return accounts;
    }


    public Account getById(int accountID) {
        var list = DatabaseManager.executeSelect(this,
                "SELECT * FROM accounts a " +
                        "INNER JOIN trunks t ON t.id = a.trunkid " +
                        "INNER JOIN monster_collections mc ON mc.id = a.monstercollectionid " +
                        "INNER JOIN unions u ON u.id = a.unionid " +
                        "LEFT JOIN unionraid ur ON ur.id = a.unionraidid " +
                        "WHERE a.id = ?", "a", accountID);

        Account account = null;
        for (var obj : list) {
            if (obj instanceof Account) {
                account = (Account) obj;
                break;
            }
        }
        return account;
    }

    public void saveOrUpdate(User user, Set<Account> accounts, Char onlyChar) {
        if (user == null) {
            return;
        }
        var steps = new SaveSteps("accounts of login " + user.getId());
        for (var acc : Util.snapshotSet(accounts)) {
            if (onlyChar == null || acc.hasCharacter(onlyChar.getId())) {
                steps.run("account " + acc.getId(), () -> saveOrUpdate(user, acc, onlyChar));
            }
        }
        steps.finish();
    }

    public Account getByCharId(int charId) {
        var list = DatabaseManager.executeSelect(this,
                "SELECT * FROM accounts a " +
                        "INNER JOIN trunks t ON t.id = a.trunkid " +
                        "INNER JOIN monster_collections mc ON mc.id = a.monstercollectionid " +
                        "INNER JOIN unions u ON u.id = a.unionid " +
                        "INNER JOIN characters c on c.accid = a.id " +
                        "LEFT JOIN unionraid ur ON ur.id = a.unionraidid " +
                        "WHERE c.id = ?", "a", charId);

        Account account = null;
        for (var obj : list) {
            if (obj instanceof Account) {
                account = (Account) obj;
                break;
            }
        }
        return account;
    }

    public void saveBossCooldown(Account account) {
        abcDao.saveOrUpdate(account, account.getBossCooldowns());
    }

    public void updateLocation(Account acc) {
        for (var chr : acc.getCharacters()) {
            charDao.updateLocation(chr);
        }
    }
}
