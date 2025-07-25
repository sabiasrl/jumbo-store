package com.jumbo.stores.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Coordinate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Store {

    private Long id;
    private String addressName;
    private Point location;

    public Store(Long id, String addressName, double latitude, double longitude) {
        this.id = id;
        this.addressName = addressName;
        GeometryFactory gf = new GeometryFactory();
        Point p = gf.createPoint(new Coordinate(longitude, latitude));
        p.setSRID(4326);
        this.location = p;
    }

    public double getLatitude() {
        return location != null ? location.getY() : 0;
    }

    public double getLongitude() {
        return location != null ? location.getX() : 0;
    }
} 