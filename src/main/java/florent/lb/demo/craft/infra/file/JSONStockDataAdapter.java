package florent.lb.demo.craft.infra.file;

import florent.lb.demo.craft.domain.Drink;
import florent.lb.demo.craft.domain.DrinkName;
import florent.lb.demo.craft.domain.Stock;
import florent.lb.demo.craft.domain.StockOperation;
import florent.lb.demo.craft.service.StockDataPort;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.unchecked.Unchecked;

import javax.enterprise.context.ApplicationScoped;
import javax.json.bind.Jsonb;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@ApplicationScoped
public class JSONStockDataAdapter implements StockDataPort {

    private final Path fileToStock;

    private final Jsonb jsonb;

    public JSONStockDataAdapter(Jsonb jsonb) throws IOException {
        this.jsonb = jsonb;
        fileToStock = Files.createTempFile("stock-" + UUID.randomUUID(), ".json");
        Files.write(
                fileToStock,
                jsonb.toJson(List.of(
                        new Stock(new Drink("coffee", BigDecimal.ONE), 5)
                ).toArray(new Stock[]{})).getBytes());
    }

    @Override
    public Uni<Integer> getStock(DrinkName drinkName) {

        return Uni.createFrom().item(Unchecked.supplier(() ->
        {
            try (FileInputStream fis = new FileInputStream(fileToStock.toFile())) {
                Stock[] currentStock = jsonb.fromJson(fis, Stock[].class);
                return Arrays.stream(currentStock)
                        .filter(stock -> stock.getDrink().getName().equalsIgnoreCase(drinkName.name()))
                        .map(Stock::getQuantity)
                        .findFirst().orElse(0);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));
    }

    //Failed of Open/Closed principle
    //https://www.baeldung.com/java-open-closed-principle#:~:text=As%20the%20name%20suggests%2C%20this,be%20extended%2C%20but%20not%20modified.
    @Override
    public Uni<Void> updateStock(StockOperation operation, DrinkName drinkEnum) {
        return getStock(drinkEnum)
                .chain(currentStock ->
                        switch (operation) {
                            case REMOVE -> {
                                currentStock--;
                                yield updateStock(drinkEnum, currentStock);
                            }
                            case ADD -> {
                                currentStock++;
                                yield updateStock(drinkEnum, currentStock);
                            }
                        }
                );

    }

    @Override
    public Uni<Void> sendStockToFinancialConsole() {

        return Uni.createFrom()
                .item(() -> {
                    try (FileInputStream fis = new FileInputStream(fileToStock.toFile())) {
                        System.out.println(Arrays.toString(jsonb.fromJson(fis, Stock[].class)));

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    return null;
                });
    }

    private Uni<Void> updateStock(DrinkName drinkEnum, Integer newStock) {

        return Uni.createFrom().item(Unchecked.supplier(() ->
        {

            AtomicReference<Stock[]> atoStock = new AtomicReference<>();
            try (FileInputStream fis = new FileInputStream(fileToStock.toFile())) {
                atoStock.set(jsonb.fromJson(fis, Stock[].class));
                for (int i = 0; i < atoStock.get().length; i++) {
                    if (atoStock.get()[i].getDrink().getName().equalsIgnoreCase(drinkEnum.name())) {
                        Stock stock = atoStock.get()[i];
                        stock.setQuantity(newStock);

                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                Files.write(fileToStock, jsonb.toJson(atoStock.get()).getBytes(), StandardOpenOption.WRITE);


            }
            return null;
        }));
    }
}
