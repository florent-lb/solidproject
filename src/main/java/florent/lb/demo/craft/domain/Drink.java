package florent.lb.demo.craft.domain;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.json.bind.annotation.JsonbCreator;
import java.math.BigDecimal;
import java.util.Objects;

@Schema
public final class Drink {
    private final String name;
    private final BigDecimal price;

    @JsonbCreator
    public Drink(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Drink) obj;
        return Objects.equals(this.name, that.name) &&
                Objects.equals(this.price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price);
    }

    @Override
    public String toString() {
        return "Drink[" +
                "name=" + name + ", " +
                "price=" + price + ']';
    }


}
