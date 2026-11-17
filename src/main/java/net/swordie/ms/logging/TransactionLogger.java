package net.swordie.ms.logging;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.items.Item;
import net.swordie.orm.dao.SworDaoFactory;
import net.swordie.orm.dao.TradeTransactionDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TransactionLogger {

    private static final int MAX_QUEUE_SIZE = 1;

    private static final Logger log = LogManager.getLogger();
    private static final Lock lock = new ReentrantLock();
    private static final TradeTransactionDao tradeTransactionDao = (TradeTransactionDao) SworDaoFactory.getByClass(TradeTransaction.class);

    private static final List<TradeTransaction> transactionQueue = new ArrayList<>();


    public static void addTradeTransaction(UUID tradeUuid, Item item, Char from, Char to) {
        queue(TradeTransaction.fromTrade(tradeUuid, item, from, to));
    }

    public static void addTradeTransaction(UUID tradeUuid, long money, Char from, Char to) {
        if (money == 0) {
            return;
        }

        queue(TradeTransaction.fromTrade(tradeUuid, money, from, to));
    }

    public static void addDropTransaction(Item item, Char from, Char to) {
        queue(TradeTransaction.fromDrop(item, from, to));
    }

    public static void addDropTransaction(long money, Char from, Char to) {
        if (money == 0) {
            return;
        }

        queue(TradeTransaction.fromDrop(money, from, to));
    }

    private static void queue(TradeTransaction tt) {
        lock.lock();
        try {
            transactionQueue.add(tt);
            if (transactionQueue.size() >= MAX_QUEUE_SIZE) {
                flush();
            }
        } finally {
            lock.unlock();
        }
    }

    private static void flush() {
        lock.lock();
        try {
            tradeTransactionDao.saveOrUpdate(getTransactionQueue());
            getTransactionQueue().clear();
        } catch (RuntimeException e) {
            // the trade or drop itself goes on; the rows stay queued for the next flush
            log.error("Could not write " + transactionQueue.size() + " trade log row(s): " + e.getMessage());
        } finally {
            lock.unlock();
        }
    }

    private static List<TradeTransaction> getTransactionQueue() {
        return transactionQueue;
    }
}
