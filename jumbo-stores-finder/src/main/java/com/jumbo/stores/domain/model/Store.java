package com.jumbo.stores.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Store {

    private Long id;
    private String addressName;
    private Point location;
    private double distance;
    
    // Helper methods to work with coordinates
    @JsonProperty("latitude")
    public double getLatitude() {
        return location != null ? location.getY() : 0.0;
    }
    
    @JsonProperty("longitude")
    public double getLongitude() {
        return location != null ? location.getX() : 0.0;
    }
    
    // Constructor for backward compatibility
    public Store(Long id, String addressName, double latitude, double longitude, double distance) {
        this.id = id;
        this.addressName = addressName;
        this.distance = distance;
        org.locationtech.jts.geom.GeometryFactory factory = 
            new org.locationtech.jts.geom.GeometryFactory(new org.locationtech.jts.geom.PrecisionModel(), 4326);
        this.location = factory.createPoint(new org.locationtech.jts.geom.Coordinate(longitude, latitude));
    }
} 