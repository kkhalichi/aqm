package com.khalichi.aqm.data;

import java.io.Serializable;
import java.util.Comparator;
import java.util.UUID;

/**
 * Data structure representing an aircraft in the system.  Only specification relevant data points are being stored.
 * @author Keivan Khalichi
 * @since Jul 01, 2017
 */
public class Aircraft implements Comparable<Aircraft>, Serializable {

    /**
     * Enumeration of aircraft types, in ordinal descending order of priority.
     */
    public enum AircraftType {
        PASSENGER,
        CARGO;
    }

    /**
     * Enumeration of aircraft sizes, in ordinal descending order of priority.
     */
    public enum AircraftSize {
        LARGE,
        SMALL;
    }

    /** Type comparator, based on ordinal value of related enumeration. */
    private static final Comparator<Aircraft> TYPE_COMPARATOR = (o1, o2) -> (o1.type.ordinal() - o2.type.ordinal());
    /** Size comparator, based on ordinal value of related enumeration. */
    private static final Comparator<Aircraft> SIZE_COMPARATOR = (o1, o2) -> (o1.size.ordinal() - o2.size.ordinal());

    /** Unique ID assigned to each aircraft. */
    private final UUID uuid;
    /** Aircraft type. */
    private AircraftType type;
    /** Aircraft size. */
    private AircraftSize size;

    /**
     * Private constructor, initializing unique ID.  Forces usage of public constructor in order to ensuring immutability.
     */
    private Aircraft() {
        this.uuid = UUID.randomUUID();
    }

    /**
     * Public constructor, initializes an aircraft per type and size specification.
     * @param theType the aircraft type
     * @param theSize the aircraft size
     */
    public Aircraft(final AircraftType theType, final AircraftSize theSize) {
        this();
        this.type = theType;
        this.size = theSize;
    }

    /**
     * Enforces specification ordering.<p/>
     * {@inheritDoc}
     * */
    @Override
    public int compareTo(final Aircraft theOtherAircraft) {
        return TYPE_COMPARATOR.thenComparing(SIZE_COMPARATOR).compare(this, theOtherAircraft);
    }

    /** Gets {@link #uuid}. */
    public UUID getUuid() {
        return this.uuid;
    }

    /** Gets {@link #type}. */
    public AircraftType getType() {
        return this.type;
    }

    /** Gets {@link #size}. */
    public AircraftSize getSize() {
        return this.size;
    }
}
