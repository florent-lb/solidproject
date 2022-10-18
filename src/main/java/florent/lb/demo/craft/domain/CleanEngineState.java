package florent.lb.demo.craft.domain;

import static florent.lb.demo.craft.domain.DrinkName.COFFEE;

public class CleanEngineState extends EngineState {


    public CleanEngineState(Engine engine) {
        super(engine);
    }

    @Override
    public void use() {
        if(engine.getNbUsage() > this.engine.nbUsageMax )
        {
            this.engine.setEngineState(new DirtyState(engine));
        }
        // create drink

    }
}
