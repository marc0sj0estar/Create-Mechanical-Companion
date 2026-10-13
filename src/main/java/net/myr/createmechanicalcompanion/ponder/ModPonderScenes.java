package net.myr.createmechanicalcompanion.ponder;

import com.simibubi.create.foundation.ponder.CreateSceneBuilder;
import net.createmod.ponder.api.PonderPalette;
import net.createmod.ponder.api.element.ElementLink;
import net.createmod.ponder.api.element.EntityElement;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.createmod.ponder.api.scene.Selection;
import net.minecraft.core.Direction;
import net.myr.createmechanicalcompanion.entity.CustomWolf;
import net.myr.createmechanicalcompanion.entity.ModEntity;

public class ModPonderScenes {
    public static void companionFoundry(SceneBuilder builder, SceneBuildingUtil util) {
        CreateSceneBuilder scene = new CreateSceneBuilder(builder);

        scene.title("companion_foundry", "Companion Foundry");
        scene.configureBasePlate(0, 0, 5);
        scene.showBasePlate();

        scene.idle(10);

        scene.world().showSection(util.select().layersFrom(1), Direction.DOWN);

        scene.idle(10);

        ElementLink<EntityElement> wolf = scene.world().createEntity(w -> ModEntity.CUSTOM_WOLF.get().create(w));
        scene.world().modifyEntity(wolf, entity -> {
            entity.setPos(1, 1, 1);
            entity.absRotateTo(90, 0);
        });
        
        scene.idle(10);

        scene.markAsFinished();

    }
}

