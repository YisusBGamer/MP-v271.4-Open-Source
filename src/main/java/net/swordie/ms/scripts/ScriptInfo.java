package net.swordie.ms.scripts;

import javax.script.Bindings;
import javax.script.Invocable;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created on 2/19/2018.
 */
public class ScriptInfo {
    private static final int RESPONSE_WAIT_TIME = 1000 * 60 * 10; // 10 minutes

    private ScriptType scriptType;
    private Bindings bindings;
    private int parentID;
    private String scriptName;
    private Invocable invocable;
    private final Lock lock = new ReentrantLock();
    private Queue<Object> responses = new LinkedList<>();
    private int objectID;
    private String fileDir;
    private boolean isActive;

    public ScriptInfo(ScriptType scriptType, Bindings bindings, int parentID, String scriptName) {
        this.scriptType = scriptType;
        this.parentID = parentID;
        this.scriptName = scriptName;
        this.bindings = bindings;
    }

    public ScriptInfo(ScriptType scriptType, Bindings bindings, int parentID, String scriptName, Invocable invocable) {
        this.scriptType = scriptType;
        this.bindings = bindings;
        this.parentID = parentID;
        this.scriptName = scriptName;
        this.invocable = invocable;
    }

    public ScriptType getScriptType() {
        return scriptType;
    }

    public void setScriptType(ScriptType scriptType) {
        this.scriptType = scriptType;
    }

    public Bindings getBindings() {
        return bindings;
    }

    public void setBindings(Bindings bindings) {
        this.bindings = bindings;
    }

    public int getParentID() {
        return parentID;
    }

    public void setParentID(int parentID) {
        this.parentID = parentID;
    }

    public String getScriptName() {
        return scriptName;
    }

    public void setScriptName(String scriptName) {
        this.scriptName = scriptName;
    }

    public Invocable getInvocable() {
        return invocable;
    }

    public void setInvocable(Invocable invocable) {
        this.invocable = invocable;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        this.isActive = active;
    }

    public void reset() {
        watchingMobs = false;
        mobDeaths.clear();
        addResponse(null);
        setParentID(0);
        setScriptName("");
        setInvocable(null);
        setActive(false);
    }

    public int getObjectID() {
        return objectID;
    }

    public void setObjectID(int objectID) {
        this.objectID = objectID;
    }

    private final java.util.concurrent.LinkedBlockingQueue<net.swordie.ms.life.mob.Mob> mobDeaths = new java.util.concurrent.LinkedBlockingQueue<>();
    private volatile boolean watchingMobs;

    /** Queues a mob death; ignored unless the script has called waitForMobDeath. */
    public void addResponseMob(net.swordie.ms.life.mob.Mob mob) {
        if (watchingMobs && mobDeaths.size() < 1000) {
            mobDeaths.offer(mob);
        }
    }

    public net.swordie.ms.life.mob.Mob awaitResponseMob(long timeoutMs) {
        watchingMobs = true;
        try {
            return mobDeaths.poll(timeoutMs, java.util.concurrent.TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            return null;
        }
    }

    public void addResponse(Object response) {
        synchronized (lock) {
            if (response == null || responses.size() > 1000) {
                responses.clear();
            }
            this.responses.add(response);
            lock.notifyAll();
        }
    }

    /** Whether the script is waiting for the player to answer a dialog right now. */
    private volatile boolean waiting;

    public boolean isWaiting() {
        return waiting;
    }

    /** How many times the script has waited for an answer (automated runs answer each wait once). */
    private volatile int waits;

    public int getWaitCount() {
        return waits;
    }

    public Object awaitResponse() {
        return awaitResponse(RESPONSE_WAIT_TIME);
    }

    /** Waits at most the given time for an answer; null if none came (a scene's skip button answers early). */
    public Object awaitResponse(long timeoutMs) {
        waits++; // only the script's own thread waits
        waiting = true;
        try {
            return awaitResponse0(timeoutMs);
        } finally {
            waiting = false;
        }
    }

    private Object awaitResponse0(long timeoutMs) {
        synchronized (lock) {
            long end = System.currentTimeMillis() + timeoutMs;
            while (responses.isEmpty()) {
                long left = end - System.currentTimeMillis();
                if (left <= 0) {
                    break;
                }
                try {
                    lock.wait(left);
                } catch (InterruptedException e) {
                    // intended
                    break;
                }
            }
            return responses.poll();
        }
    }

    public void setFileDir(String fileDir) {
        this.fileDir = fileDir;
    }

    public String getFileDir() {
        return fileDir;
    }
}
