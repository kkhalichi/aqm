package com.khalichi.atcq.data;

import java.io.Serializable;
import java.util.Comparator;
import java.util.UUID;

/**
 * @author Keivan Khalichi
 * @since Jul 01, 2017
 */
public class Aircraft implements Comparable<Aircraft>, Serializable {

    public enum AircraftType {
        PASSENGER,
        CARGO;
    }

    public enum AircraftSize {
        LARGE,
        SMALL;
    }

    private static final Comparator<Aircraft> TYPE_COMPARATOR = (o1, o2) -> (o1.type.ordinal() - o2.type.ordinal());
    private static final Comparator<Aircraft> SIZE_COMPARATOR = (o1, o2) -> (o1.size.ordinal() - o2.size.ordinal());

    private final UUID uuid;
    private AircraftType type;
    private AircraftSize size;

    private Aircraft() {
        this.uuid = UUID.randomUUID();
    }

    public Aircraft(final AircraftType theType, final AircraftSize theSize) {
        this();
        this.type = theType;
        this.size = theSize;
    }

    /** {@inheritDoc} */
    @Override
    public int compareTo(final Aircraft theOtherAircraft) {
        return TYPE_COMPARATOR.thenComparing(SIZE_COMPARATOR).compare(this, theOtherAircraft);
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public AircraftType getType() {
        return this.type;
    }

    public AircraftSize getSize() {
        return this.size;
    }
}
