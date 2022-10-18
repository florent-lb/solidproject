package florent.lb.demo.craft.service;

import florent.lb.demo.craft.domain.DrinkName;
import florent.lb.demo.craft.domain.StockOperation;
import io.smallrye.mutiny.Uni;

import java.io.IOException;

public interface StockDataPort {

    Uni<Integer> getStock(DrinkName drinkName) throws IOException;

    Uni<Void> updateStock(StockOperation remove, DrinkName drinkEnum);

    Uni<Void> sendStockToFinancialConsole();
}
