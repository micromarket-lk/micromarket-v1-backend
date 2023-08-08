package com.sahan.core.Entities.Market;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cart {
    @Id
    private Long id;

//    @ManyToOne(mappedBy = "cart", cascade = CascadeType.ALL)
//    private List<CartItem> cartItems;

}
