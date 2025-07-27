package com.jumbo.stores.adapter.out.persistence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("store")
public class StoreR2dbcEntity {

    @Id
    private Long id;
    private String addressName;
    private double latitude;
    private double longitude;
} 