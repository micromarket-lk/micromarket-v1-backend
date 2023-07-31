package com.sahan.core.Repostitories;

import com.sahan.core.Entities.Market.Market;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarketRepository extends JpaRepository<Market, String> {
    Market findMarketByMarketName(String marketName);
    Market deleteMarketByMarketName(String marketName);
}
