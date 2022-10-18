package florent.lb.demo.craft.domain;

import java.io.IOException;

public class ErrorOnStockReading extends RuntimeException {
    public ErrorOnStockReading(Throwable e) {
        super("Unable to open the stock",e);
    }
}
