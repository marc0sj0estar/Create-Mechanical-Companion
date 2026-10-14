package net.myr.createmechanicalcompanion.ponder;

import com.simibubi.create.foundation.ponder.CreateSceneBuilder;
import net.createmod.catnip.math.Pointing;
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

        scene.title("companion_foundry", "How to create a link with the Companion Foundry");
        scene.configureBasePlate(0, 0, 5);
        scene.showBasePlate();

        scene.idle(10);

        Selection companionFroundry = util.select().position(2, 1, 2);
        scene.world().showSection(companionFroundry, Direction.DOWN);

        scene.idle(10);
        scene.overlay().showText(80)
                .text("The Companion Foundry is used to create and change the loadout of your Mechanical Companion.")
                .pointAt(util.vector().topOf(2, 1, 2));

        scene.idle(100);

        scene.world().showSection(util.select().position(2, 1, 3), Direction.DOWN);
        scene.idle(5);
        scene.world().showSection(util.select().position(2, 1, 4), Direction.DOWN);

        scene.idle(20);

        scene.overlay().showOutlineWithText(util.select().fromTo(2, 1, 2, 2, 1, 4), 80)
                .text("The Companion Foundry requires rotational power to function.")
                .pointAt(util.vector().topOf(2, 1, 3))
                .attachKeyFrame();

        scene.idle(20);
        scene.world().setKineticSpeed(util.select().fromTo(2, 1, 2, 2, 1, 4), 32);
        scene.idle(80);

        scene.overlay().showText(80)
                .text("When the Companion Foundry is empty, it will display the required items on the screen.")
                .attachKeyFrame();
        scene.idle(100);


        scene.overlay().showText(80)
                .text("After the item is inserted it will take some time to process it.");
        scene.idle(100);


        scene.overlay().showText(80)
                .text("The processing time can be reduced by increasing the rotational speed.");
        scene.idle(20);
        scene.world().setKineticSpeed(util.select().fromTo(2, 1, 2, 2, 1, 4), 64);
        scene.idle(80);

        scene.world().showSection(util.select().position(3, 1, 2), Direction.DOWN);
        scene.idle(5);
        scene.world().showSection(util.select().position(4, 1, 2), Direction.DOWN);
        scene.idle(20);

        scene.overlay().showOutlineWithText(util.select().fromTo(2, 1, 2, 4, 1, 2), 80)
                .text("The creation process can also require fluids, which can be inserted with a fluid pipe.")
                .pointAt(util.vector().topOf(3, 1, 2))
                .attachKeyFrame();

        scene.idle(20);

        scene.world().setKineticSpeed(util.select().position(3, 1, 2), 64);

        scene.idle(80);

        scene.overlay().showText(80)
                .text("After all the required items are inserted, the link for the Mechanical Companion can be found inside.")
                .attachKeyFrame();

        scene.idle(20);
        scene.overlay().showControls(util.vector().topOf(2, 1, 2), Pointing.DOWN, 60).rightClick();

        scene.idle(80);




        /*
        ElementLink<EntityElement> wolf = scene.world().createEntity(w -> ModEntity.CUSTOM_WOLF.get().create(w));
        scene.world().modifyEntity(wolf, entity -> {
            entity.setPos(2, 2, 2);
            entity.absRotateTo(90, 0);
        });
         */


        scene.markAsFinished();

    }
}

