package florent.lb.demo.craft.domain;

public abstract class EngineState {

    protected Engine engine;
    public EngineState(Engine engine) {
        this.engine = engine;
    }
    abstract void use();
}
