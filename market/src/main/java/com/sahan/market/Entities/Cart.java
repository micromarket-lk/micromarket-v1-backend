package com.sahan.market.Entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cart {
    @Id
    private Long id;

    @ManyToOne(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<CartItem> cartItems;

    public Market
}
