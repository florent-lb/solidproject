package florent.lb.demo.craft.service;

import florent.lb.demo.craft.domain.*;
import florent.lb.demo.craft.infra.OutOfStockException;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.unchecked.Unchecked;

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

import static florent.lb.demo.craft.domain.DrinkName.COFFEE;
import static florent.lb.demo.craft.domain.StockOperation.REMOVE;

@ApplicationScoped
public class BarService {

    private final StockDataPort stockDataPort;

    private final Map<DrinkName, Engine> engineForProduct =
            Map.of(
                    COFFEE, new Engine(Set.of(COFFEE), "Nespresso"),
                    DrinkName.BEER, new Engine(Set.of(DrinkName.BEER), "Tireuse frontale")
            );

    public BarService(StockDataPort stockDataPort) {
        this.stockDataPort = stockDataPort;
    }

    //WRONG WAY of Single Responsability
    //https://howtodoinjava.com/design-patterns/single-responsibility-principle/
    public Uni<Drink> command(String drink) throws IOException {
        DrinkName drinkEnum = DrinkName.valueOf(drink.toUpperCase());
        // Stock Check
        return stockDataPort.getStock(drinkEnum)
                .onFailure().transform(ErrorOnStockReading::new)
                .chain(Unchecked.function(currentStock -> {
                    if (currentStock == 0) {
                        throw new OutOfStockException(drink);
                    }
                    // Start command
                    engineForProduct.get(drinkEnum).use();
                    //Send command
                    return stockDataPort.updateStock(REMOVE, drinkEnum)
                            .map((ignore) -> new Drink(COFFEE.name(), BigDecimal.ONE));

                }));

    }


    public Uni<Void> sendFinancialReport() {
        return stockDataPort.sendStockToFinancialConsole();
    }
}


//.use / .cleanUp ==> Operation on Single objet but if we add get of Frigre for Beer in drink, why we have it on cofffe ????
//This method Service has too many task and if we need a new order for operation ?
// If we add a new Drink ?