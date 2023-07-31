package com.sahan.core.Entities.Market;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Product {
    @Id
    @SequenceGenerator(
            name = "product_id_sequence",
            sequenceName = "product_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "product_id_sequence"
    )
    private String productId;
}
