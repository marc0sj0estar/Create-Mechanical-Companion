package net.myr.createmechanicalcompanion.ponder;

import com.simibubi.create.foundation.ponder.CreateSceneBuilder;
import net.createmod.catnip.math.Pointing;
import net.createmod.ponder.api.PonderPalette;
import net.createmod.ponder.api.element.ElementLink;
import net.createmod.ponder.api.element.EntityElement;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.createmod.ponder.api.scene.Selection;
import net.minecraft.core.BlockPos;
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

        Selection companionFoundry = util.select().position(2, 1, 2);
        BlockPos companionFoundryPosition = util.grid().at(2, 1, 2);
        scene.world().showSection(companionFoundry, Direction.DOWN);

        scene.idle(10);

        scene.overlay().showText(80)
                .text("The Companion Foundry is used to create and customize the loadout of your Mechanical Companion.")
                .pointAt(companionFoundryPosition.getCenter());

        scene.idle(100);

        scene.world().showSection(util.select().position(2, 1, 3), Direction.DOWN);
        scene.idle(5);
        scene.world().showSection(util.select().position(2, 1, 4), Direction.DOWN);

        scene.idle(20);

        scene.overlay().showOutlineWithText(util.select().fromTo(2, 1, 2, 2, 1, 4), 80)
                .text("The Companion Foundry requires rotational power to operate.")
                .pointAt(util.vector().centerOf(2, 1, 3))
                .placeNearTarget()
                .colored(PonderPalette.GREEN)
                .attachKeyFrame();

        scene.idle(20);
        scene.world().setKineticSpeed(util.select().position(2, 1, 2), 32);
        scene.world().setKineticSpeed(util.select().position(2, 1, 3), -32);
        scene.world().setKineticSpeed(util.select().position(2, 1, 4), 32);
        scene.idle(80);

        scene.overlay().showText(80)
                .text("When the Companion Foundry is empty, it will display the required items on the menu.")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(companionFoundryPosition.getCenter());

        scene.overlay().showControls(companionFoundryPosition.getCenter(), Pointing.DOWN, 60).rightClick();
        scene.idle(100);

        scene.overlay().showText(80)
                .text("After each item is inserted, the Companion Foundry will begin to process.")
                .pointAt(companionFoundryPosition.getCenter());


        scene.idle(100);

        scene.overlay().showText(80)
                .text("The processing time can be reduced by increasing the rotational speed.")
                .pointAt(util.vector().centerOf(2, 1, 4));

        scene.idle(20);
        scene.effects().rotationSpeedIndicator(companionFoundryPosition);
        scene.effects().rotationSpeedIndicator(util.grid().at(2, 1, 3));
        scene.effects().rotationSpeedIndicator(util.grid().at(2, 1, 4));
        scene.world().setKineticSpeed(companionFoundry, 64);
        scene.world().setKineticSpeed(util.select().position(2, 1, 3), -64);
        scene.world().setKineticSpeed(util.select().position(2, 1, 4), 64);
        scene.idle(80);

        scene.world().showSection(util.select().fromTo(3, 1, 2, 3, 2, 2), Direction.DOWN);
        scene.idle(5);
        scene.world().showSection(util.select().fromTo(4, 1, 2, 4, 2, 2), Direction.DOWN);
        scene.idle(20);

        scene.overlay().showOutlineWithText(util.select().fromTo(2, 1, 2, 4, 1, 2), 80)
                .text("The creation process can also require fluids, which can be inserted with a fluid pipe.")
                .pointAt(util.vector().centerOf(3, 1, 2))
                .placeNearTarget()
                .colored(PonderPalette.GREEN)
                .attachKeyFrame();

        scene.idle(20);

        scene.world().setKineticSpeed(util.select().position(3, 1, 2), 32);
        scene.world().setKineticSpeed(util.select().fromTo(3, 2, 2, 4, 2, 2), -32);

        scene.idle(80);

        scene.overlay().showText(80)
                .text("After all the steps are complete, the link for the Mechanical Companion will be ready to collect.")
                .pointAt(companionFoundryPosition.getCenter())
                .placeNearTarget()
                .attachKeyFrame();

        scene.idle(20);
        scene.overlay().showControls(companionFoundryPosition.getCenter(), Pointing.DOWN, 60).rightClick();
        scene.idle(60);

        scene.markAsFinished();

    }
}

