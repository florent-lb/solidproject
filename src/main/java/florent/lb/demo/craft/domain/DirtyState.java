package florent.lb.demo.craft.domain;

public class DirtyState extends EngineState {
    public DirtyState(Engine engine) {
        super(engine);
    }

    @Override
    public void use() {
        // TODO cleanuP
        engine.setNbUsage(0);
        this.engine.setEngineState(new CleanEngineState(engine));
    }
}
