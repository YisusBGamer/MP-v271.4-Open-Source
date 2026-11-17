package net.swordie.ms.world;

import net.swordie.ms.ServerConstants;
import net.swordie.ms.client.Account;
import net.swordie.ms.client.Client;
import net.swordie.ms.client.User;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.surprisemission.SurpriseMissionModule;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.enums.WorldId;
import net.swordie.ms.loaders.FieldData;
import net.swordie.ms.util.ErrorLogger;
import net.swordie.ms.util.Util;
import net.swordie.ms.util.container.Tuple;
import net.swordie.ms.world.field.Field;
import net.swordie.ms.world.field.instance.Instance;
import net.swordie.orm.dao.CharDao;
import net.swordie.orm.dao.SworDaoFactory;
import net.swordie.orm.dao.UserDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Created on 11/2/2017.
 */
public class Channel {

    private static final UserDao userDao = (UserDao) SworDaoFactory.getByClass(User.class);
    private static final CharDao charDao = (CharDao) SworDaoFactory.getByClass(Char.class);
    private static final Logger log = LogManager.getRootLogger();

    //CHANNELITEM struct
    private int port;
    private String name;
    private WorldId worldId;
    private int channelId;
    private boolean adultChannel;
    private Map<Integer, Field> fields;
    private Map<Integer, TransferInfo> transfers;
    private Map<Integer, Char> chars = new ConcurrentHashMap<>();
    private List<Tuple<Integer, Instance>> instances = new ArrayList<>(); // < enterFieldId , Instance >
    public final int MAX_DISPLAY_SIZE = 100;
    public final int MAX_SIZE = 1000;

    private Channel(String name, World world, int channelId, boolean adultChannel) {
        this.name = name;
        this.worldId = world.getWorldId();
        this.channelId = channelId;
        this.adultChannel = adultChannel;
        this.port = ServerConstants.LOGIN_PORT + 100 + channelId;
        this.fields = new ConcurrentHashMap<>(); // used by every client thread and by the field timers
        this.transfers = new ConcurrentHashMap<>();
    }

    public Channel(World world, int channelId) {
        this(world.getName() + "-" + channelId, world, channelId, false);
    }

    public Channel(WorldId worldId, int channelId) {
        this.name = worldId + "-" + channelId;
        this.worldId = worldId;
        this.channelId = channelId;
        this.adultChannel = false;
        this.port = ServerConstants.LOGIN_PORT + (100 * worldId.getVal()) + channelId;
        this.fields = new ConcurrentHashMap<>(); // used by every client thread and by the field timers
        this.transfers = new ConcurrentHashMap<>();
    }

    public String getName() {
        return name;
    }

    public int getGaugePx() {
        return (int) Math.max(1, (chars.size() * 64D) / MAX_DISPLAY_SIZE);
    }

    public WorldId getWorldId() {
        return worldId;
    }

    public int getChannelId() {
        return channelId;
    }

    public boolean isAdultChannel() {
        return adultChannel;
    }

    public void setAdultChannel(boolean adultChannel) {
        this.adultChannel = adultChannel;
    }

    public int getPort() {
        return port;
    }

    public Map<Integer, Field> getFields() {
        return fields;
    }

    /**
     * Gets a {@link Field} corresponding to a given ID. If it doesn't exist, creates one.
     *
     * @param id The map ID of the field.
     * @return The (possibly newly created) Field.
     */
    public Field getField(int id) {
        Field field = getFields().get(id);
        if (field != null) {
            return field;
        }
        return createAndReturnNewField(id);
    }

    /**
     * Synchronized so two players entering an empty map at the same time don't each create it and leave one of them in a
     * copy the channel doesn't know about.
     */
    private synchronized Field createAndReturnNewField(int id) {
        Field newField = getFields().get(id);
        if (newField != null) {
            return newField;
        }
        newField = FieldData.getFieldCopyById(id, true);
        if (newField != null) {
            newField.setChannelInstance(this);
            getFields().put(id, newField);
            net.swordie.ms.world.SeasonalEvents.onFieldCreated(newField); // event NPCs
        }
        return newField;
    }

    public Map<Integer, TransferInfo> getTransfers() {
        if (transfers == null) {
            transfers = new ConcurrentHashMap<>();
        }
        return transfers;
    }

    public void addClientInTransfer(Channel oldChannel, Channel newChannel, int characterId, Client client) {
        getTransfers().put(characterId, new TransferInfo(client, client.getUser(), oldChannel, newChannel));
    }

    public void removeClientFromTransfer(int characterId) {
        getTransfers().remove(characterId);
    }

    public Map<Integer, Char> getChars() {
        return chars;
    }

    public void addChar(Char chr) {
        getChars().put(chr.getId(), chr);
    }

    public void removeChar(Char chr) {
        getChars().remove(chr.getId());
    }

    public Char getCharById(int id) {
        return getChars().get(id);
    }

    public Char getCharByName(String name) {
        return Util.findWithPred(getChars().values(), chr -> chr.getName().equalsIgnoreCase(name));
    }

    public void broadcastPacket(OutPacket outPacket) {
        for (Char chr : getChars().values()) {
            chr.write(outPacket);
        }
    }

    public void broadcastPacketByChr(Char byChr, OutPacket outPacket) {
        // probe tests use real megaphones; nobody else should hear them
        if (net.swordie.ms.ext.ServerHooks.isTestCharacter(byChr)) {
            if (getChars().containsKey(byChr.getId())) {
                byChr.write(outPacket);
            }
            return;
        }
        for (Char chr : getChars().values()) {

            // Skip over characters that have 'byChr' blacklisted
            if (chr.hasChrBlacklisted(byChr.getId())) {
                continue;
            }
            chr.write(outPacket);
        }
    }

    public void clearCache() {
        Set<Integer> toRemove = new HashSet<>();
        for (Field field : new HashSet<>(getFields().values())) {
            if (field.getChars().size() == 0) {
                toRemove.add(field.getId());
                field.clear();
            }
        }
        getFields().keySet().removeAll(toRemove);
    }

    public void shutdown() {
        for (Char chr : getChars().values()) {
            try {
                if (chr.getUser() != null) {
                    userDao.saveOrUpdate(chr.getUser(), null);
                } else {
                    charDao.saveOrUpdate(chr);
                }
                chr.getClient().close();
            } catch (Exception e) {
                ErrorLogger.logAsClientError(e);
            }
        }
    }

    public void removeField(Field field) {
        getFields().remove(field.getId());
    }

    public void removeCharsOfUser(User user) {
        List<Char> toRemove = new ArrayList<>();
        for (var chr : getChars().values()) {
            if (chr.getUser() == null || chr.getUser().getId() == user.getId()) {
                toRemove.add(chr);
            }
        }
        for (var chr : toRemove) {
            if (chr.getField() != null) {
                chr.getField().removeChar(chr);
            }
            removeChar(chr);
            if (chr.getClient() != null) {
                chr.getClient().close();
            }
        }
    }

    public Account getAccountById(int accountId) {
        for (var chr : getChars().values()) {
            var acc = chr.getAccount();
            if (acc != null && acc.getId() == accountId) {
                return acc;
            }
        }
        return null;
    }

    // Each item runs with its own try/catch: an exception would otherwise skip the rest, every tick (and an uncaught
    // one cancels the timer for good).

    private static <T> void forEachSafely(Collection<T> items, java.util.function.Consumer<T> action, String what) {
        for (T item : items) {
            long perf = net.swordie.ms.util.PerfLog.start();
            try {
                action.accept(item);
            } catch (Exception e) {
                log.error(String.format("Error in %s for %s", what, item), e);
            }
            if (perf != 0 && System.nanoTime() - perf > 20_000_000L) { // only maps that took 20+ ms are recorded
                net.swordie.ms.util.PerfLog.end("field", what, perf, item instanceof net.swordie.ms.world.field.Field f
                        ? "map " + f.getId() : String.valueOf(item));
            }
        }
    }

    public void checkForItemExpiry() {
        forEachSafely(new ArrayList<>(getChars().values()), Char::checkAndRemoveExpiredItems, "item expiry");
    }

    public void updateBurningFieldLevel() {
        forEachSafely(new ArrayList<>(getFields().values()), Field::updateBurningFieldLevel, "burning field update");
    }

    private Set<Instance> getActiveInstances() {
        return getChars().values().stream()
                .map(Char::getInstance)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    public void updateFields() {
        forEachSafely(new ArrayList<>(getFields().values()), Field::update, "field update");
        forEachSafely(getActiveInstances(), Instance::update, "instance update");
    }

    public void generateMobs() {
        forEachSafely(new ArrayList<>(getFields().values()), f -> f.generateMobs(false), "mob generation");
        var fields = new HashSet<Field>();
        getActiveInstances().forEach(i -> fields.addAll(i.getFields().values()));
        forEachSafely(fields, f -> f.generateMobs(false), "instance mob generation");
    }

    public void generateHarvests() {
        forEachSafely(new ArrayList<>(getFields().values()), Field::spawnRandomHarvest, "harvest generation");
    }

    public void updateChars() {
        forEachSafely(new ArrayList<>(getChars().values()), Char::update, "character update");
    }

    public void giveSurpriseMissions() {
        forEachSafely(new ArrayList<>(getChars().values()), SurpriseMissionModule::tryStartSurpriseMission,
                "surprise missions");
    }

    public User getUserFromTransfer(int userId) {
        for (var transferInfo : new HashSet<>(getTransfers().values())) {
            var client = transferInfo.getClient();
            if (client != null && client.getUser() != null && client.getUser().getId() == userId) {
                return client.getUser();
            }
        }

        return null;
    }

    /**
     * Removes all user's characters currently in transfer
     * @param user the user to remove from transfer
     */
    public void removeUserFromTransfer(User user) {
        for (var acc : user.getAccounts()) {
            for (var chr : acc.getCharacters()) {
                if (chr != null) {
                    removeClientFromTransfer(chr.getId());
                }
            }
        }
    }

    /**
     * Invoked on the start of each new day (server Time)
     */
    public void onNewDay() {
        forEachSafely(new ArrayList<>(getChars().values()), Char::onNewDay, "daily reset");
    }

    public List<Tuple<Integer, Instance>> getInstances() {
        return instances;
    }

    public void setInstances(List<Tuple<Integer, Instance>> instances) {
        this.instances = instances;
    }

    public void addInstance(int enterFieldId, Instance instance) {
        getInstances().add(new Tuple<>(enterFieldId, instance));
    }

    public void removeInstance(Instance instance) {
        var tuple = getInstances().stream().filter(t -> t.getRight() == instance).findFirst().orElse(null);
        if (tuple != null) {
            getInstances().remove(tuple);
        }
    }
}
