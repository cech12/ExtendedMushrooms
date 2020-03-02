package cech12.extendedmushrooms.mixin;

import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.mixin.connect.IMixinConnector;

public class ModMixinConnector implements IMixinConnector {

    @Override
    public void connect() {
        Mixins.addConfiguration("assets/extendedmushrooms/mixins.json");
    }

}
