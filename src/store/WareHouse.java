package store;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class WareHouse {

    private final List<Integer> products = new ArrayList<>();
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);
    private final Lock readLock = readWriteLock.readLock();
    private final Lock writeLock = readWriteLock.writeLock();

    public int consulStockOfProduct(int id) {
        readLock.lock();
        try{
            return countProductById(id);
        } finally {
            readLock.unlock();
        }
    }

    public void addStockProduct(int id) {
        writeLock.lock();
        try{
            products.add(id);
        } finally {
            writeLock.unlock();
        }
    }

    private int countProductById(int id) {
        int count = 0;

        for (Integer product: products) {
            if(id == product) {
                count++;
            }
        }
        return count;
    }
}
