package florent.lb.demo.craft.domain;

import javax.json.bind.annotation.JsonbCreator;
import java.util.Objects;

public final class Stock {
    private final Drink drink;
    private int quantity;

    @JsonbCreator
    public Stock(Drink drink, int quantity) {
        this.drink = drink;
        this.quantity = quantity;
    }

    public Drink getDrink() {
        return drink;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Stock) obj;
        return Objects.equals(this.drink, that.drink) &&
                this.quantity == that.quantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(drink, quantity);
    }

    @Override
    public String toString() {
        return "Stock[" +
                "drink=" + drink + ", " +
                "quantity=" + quantity + ']';
    }

}
