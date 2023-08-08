package com.sahan.core.Entities.Market;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Market implements Serializable {
    @Id
    @SequenceGenerator(
            name = "market_id_sequence",
            sequenceName = "market_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "market_id_sequence"
    )
    private Integer marketId;
    private String marketName;
//    private String description;
//    @OneToMany(mappedBy = "market", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Product> products;
//    @OneToMany(mappedBy = "market", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Category> categories;
//    @OneToMany(mappedBy = "market", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Order> orders;
//    @OneToMany(mappedBy = "market", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Review> reviews;
//    @OneToMany(mappedBy = "market", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Wishlist> wishlists;
//    @OneToMany(mappedBy = "market", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Cart> carts;
//    @OneToMany(mappedBy = "market", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<CartItem> cartItems;
//    @OneToMany(mappedBy = "market", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<WishlistItem> wishlistItems;
//    @OneToMany(mappedBy = "market", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<OrderItem> orderItems;
//    @OneToMany(mappedBy = "market", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<OrderReturn> orderReturns;
//    @OneToMany(mappedBy = "market", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<OrderReturnItem> orderReturnItems;
}
