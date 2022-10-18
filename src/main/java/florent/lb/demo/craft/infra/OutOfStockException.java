package florent.lb.demo.craft.infra;

public class OutOfStockException extends RuntimeException {
    public OutOfStockException(String drink) {
        super("No Stock for %s".formatted(drink));
    }
}
