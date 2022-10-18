package florent.lb.demo.craft.domain;

import java.util.Objects;
import java.util.Set;

public final class Engine {
    private final Set<DrinkName> drinkAllowed;
    private final String name;
    private EngineState engineState;
    private int nbUsage;

    protected final int nbUsageMax;
    public Engine(Set<DrinkName> drinkAllowed, String name) {
        this.nbUsageMax = 5;
        this.engineState = new CleanEngineState(this);
        this.drinkAllowed = drinkAllowed;
        this.name = name;
        this.nbUsage = 0;
    }

    public void use() {
        this.engineState.use();
    }

    public Set<DrinkName> drinkAllowed() {
        return drinkAllowed;
    }

    public String name() {
        return name;
    }

    public EngineState getEngineState() {
        return engineState;
    }

    public void setEngineState(EngineState engineState) {
        this.engineState = engineState;
    }

    public int getNbUsage() {
        return nbUsage;
    }

    public int setNbUsage(int nbUsage) {
        return this.nbUsage = nbUsage;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Engine) obj;
        return Objects.equals(this.drinkAllowed, that.drinkAllowed) &&
                Objects.equals(this.name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(drinkAllowed, name);
    }

    @Override
    public String toString() {
        return "Engine[" +
                "drinkAllowed=" + drinkAllowed + ", " +
                "name=" + name + ", " +
                "status=" + ']';
    }
}
