package cofh.thermal.expansion.tileentity.dynamo;

import cofh.core.inventory.ItemStorageCoFH;
import cofh.thermal.core.tileentity.DynamoTileBase;
import cofh.thermal.expansion.inventory.container.dynamo.DynamoNumismaticContainer;
import cofh.thermal.expansion.util.managers.dynamo.NumismaticFuelManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;

import javax.annotation.Nullable;

import static cofh.core.util.StorageGroup.INPUT;
import static cofh.thermal.core.common.ThermalConfig.dynamoAugments;
import static cofh.thermal.expansion.init.TExpReferences.DYNAMO_NUMISMATIC_TILE;

public class DynamoNumismaticTile extends DynamoTileBase {

    protected ItemStorageCoFH fuelSlot = new ItemStorageCoFH(NumismaticFuelManager.instance()::validFuel);

    public DynamoNumismaticTile() {

        super(DYNAMO_NUMISMATIC_TILE);

        inventory.addSlot(fuelSlot, INPUT);

        addAugmentSlots(dynamoAugments);
        initHandlers();
    }

    // region PROCESS
    @Override
    protected boolean canProcessStart() {

        return NumismaticFuelManager.instance().getEnergy(fuelSlot.getItemStack()) > 0;
    }

    @Override
    protected void processStart() {

        fuel += fuelMax = Math.round(NumismaticFuelManager.instance().getEnergy(fuelSlot.getItemStack()) * energyMod);
        fuelSlot.consume(1);
    }
    // endregion

    @Nullable
    @Override
    public Container createMenu(int i, PlayerInventory inventory, PlayerEntity player) {

        return new DynamoNumismaticContainer(i, world, pos, inventory, player);
    }

}
