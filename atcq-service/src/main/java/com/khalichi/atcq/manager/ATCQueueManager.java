package com.khalichi.atcq.manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;
import com.khalichi.atcq.data.Aircraft;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * @author Keivan Khalichi
 * @since Jul 01, 2017
 */
@Component
@Scope("singleton")
public class ATCQueueManager {

    public static final int SYSTEM_INITIAL_SIZE = 20;

    private PriorityBlockingQueue<Aircraft> aircraftQueue;
    private boolean systemReady = false;

    public synchronized void boot() {
        if (this.systemReady) {
            throw new RuntimeException("System is already started.");
        }
        this.aircraftQueue = new PriorityBlockingQueue<>(SYSTEM_INITIAL_SIZE, Aircraft::compareTo);
        this.systemReady = true;
    }

    public boolean enqueue(final Aircraft theAircraft) {
        if (this.systemReady) {
            return this.aircraftQueue.add(theAircraft);
        }
        else {
            throw new RuntimeException("System is not ready.  Try booting system first.");
        }
    }

    public void enqueue(final List<Aircraft> theAircrafts) {
        if (!CollectionUtils.isEmpty(theAircrafts)) {
            this.aircraftQueue.addAll(theAircrafts);
        }
    }

    public Aircraft dequeue() {
        if (this.systemReady) {
            if (this.aircraftQueue.isEmpty()) {
                return null;
            }
            else {
                return this.aircraftQueue.remove();
            }
        }
        else {
            throw new RuntimeException("System is not ready.  Try booting system first.");
        }
    }

    public List<Aircraft> dump() {
        if (this.systemReady) {
            return new ArrayList<>(this.aircraftQueue);
        }
        else {
            throw new RuntimeException("System is not ready.  Try booting system first.");
        }
    }

    public List<Aircraft> sortDump() {
        if (this.systemReady) {
            final Aircraft[] anAircraftArray = this.aircraftQueue.toArray(new Aircraft[this.aircraftQueue.size()]);
            Arrays.<Aircraft>sort(anAircraftArray);
            return Arrays.asList(anAircraftArray);
        }
        else {
            throw new RuntimeException("System is not ready.  Try booting system first.");
        }
    }
}
