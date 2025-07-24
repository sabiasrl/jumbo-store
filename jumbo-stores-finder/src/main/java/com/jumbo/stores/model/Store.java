package com.jumbo.stores.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Coordinate;

@Entity
@Table(name = "store")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Store {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonProperty("addressName")
	private String addressName;

    // PostGIS geography column
    @Column(columnDefinition = "geometry(Point,4326)")
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