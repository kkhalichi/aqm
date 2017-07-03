package com.khalichi.aqm.manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;
import javax.validation.constraints.NotNull;
import com.khalichi.aqm.data.Aircraft;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * The core logic and data structure wrapper of the system.  A stateful bean that manages the queue based on specification logic.  This class is
 * implemented as thread-safe, and can be used as a singleton bean.  Multiple instances of the class will yield multiple data structures, which
 * affects the functionality, diminish the source of truth, as there will be multiple data sources.
 * @author Keivan Khalichi
 * @since Jul 01, 2017
 */
@Component
@Scope("singleton")
public class AQMProcessor {

    /** Initialize size of underlying data structure */
    private static final int SYSTEM_INITIAL_SIZE = 20;

    /** Core data structure; a thread-safe priority queue */
    private PriorityBlockingQueue<Aircraft> aircraftQueue;
    /** Indicates whether or not system is booted */
    private boolean systemReady = false;

    /**
     * Operation to boot the system.  Must be called before other operations are available.  Initializes the underlying data structure's priority.
     * @throws RuntimeException if system is already booted
     */
    public synchronized void boot() {
        if (this.systemReady) {
            throw new RuntimeException("System is already started.");
        }
        this.aircraftQueue = new PriorityBlockingQueue<>(SYSTEM_INITIAL_SIZE, Aircraft::compareTo);
        this.systemReady = true;
    }

    /**
     * Adds an aircraft into the system.
     * @param theAircraft the aircraft to be added.
     * @return whether or not add to underlying data structure was a success
     * @throws RuntimeException if system is not booted
     */
    public boolean enqueue(@NotNull final Aircraft theAircraft) {
        if (this.systemReady) {
            return this.aircraftQueue.add(theAircraft);
        }
        else {
            throw new RuntimeException("System is not ready.  Try booting system first.");
        }
    }

    /**
     * Adds a list of aircraft into the system.
     * @param theAircrafts list of aircraft
     * @throws RuntimeException if system is not booted
     */
    public void enqueue(final List<Aircraft> theAircrafts) {
        if (!CollectionUtils.isEmpty(theAircrafts)) {
            this.aircraftQueue.addAll(theAircrafts);
        }
    }

    /**
     * Removes an aircraft from the system, given specification priority.
     * @return the removed aircraft; null if there are no aircraft in the system
     * @throws RuntimeException if system is not booted
     */
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

    /**
     * Retrieves contents of aircraft in the system.  The list is unordered.
     * @return unordered list of aircraft in the system
     * @throws RuntimeException if system is not booted
     */
    public List<Aircraft> dump() {
        if (this.systemReady) {
            return new ArrayList<>(this.aircraftQueue);
        }
        else {
            throw new RuntimeException("System is not ready.  Try booting system first.");
        }
    }

    /**
     * Retrieves contents of aircraft in the system.  The list is sorted as per specification order criteria.
     * @return ordered list of aircraft in the system
     * @see Aircraft#compareTo(Aircraft)
     * @throws RuntimeException if system is not booted
     */
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
