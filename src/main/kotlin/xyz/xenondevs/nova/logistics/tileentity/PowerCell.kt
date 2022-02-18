package xyz.xenondevs.nova.logistics.tileentity

import de.studiocode.invui.gui.GUI
import de.studiocode.invui.gui.builder.GUIBuilder
import de.studiocode.invui.gui.builder.guitype.GUIType
import xyz.xenondevs.nova.data.config.NovaConfig
import xyz.xenondevs.nova.data.serialization.cbf.element.CompoundElement
import xyz.xenondevs.nova.logistics.registry.Blocks
import xyz.xenondevs.nova.material.NovaMaterial
import xyz.xenondevs.nova.tileentity.NetworkedTileEntity
import xyz.xenondevs.nova.tileentity.network.energy.EnergyConnectionType.*
import xyz.xenondevs.nova.tileentity.network.energy.holder.BufferEnergyHolder
import xyz.xenondevs.nova.ui.EnergyBar
import xyz.xenondevs.nova.ui.config.side.OpenSideConfigItem
import xyz.xenondevs.nova.ui.config.side.SideConfigGUI
import xyz.xenondevs.nova.world.armorstand.FakeArmorStand
import java.util.*

open class PowerCell(
    creative: Boolean,
    val maxEnergy: Long,
    uuid: UUID,
    data: CompoundElement,
    material: NovaMaterial,
    ownerUUID: UUID,
    armorStand: FakeArmorStand,
) : NetworkedTileEntity(uuid, data, material, ownerUUID, armorStand) {
    
    final override val energyHolder = BufferEnergyHolder(this, maxEnergy, creative) { createEnergySideConfig(BUFFER) }
    
    override val gui = lazy { PowerCellGUI() }
    
    override fun handleTick() = Unit
    
    inner class PowerCellGUI : TileEntityGUI() {
        
        private val sideConfigGUI = SideConfigGUI(
            this@PowerCell,
            listOf(NONE, PROVIDE, CONSUME, BUFFER),
            null
        ) { openWindow(it) }
        
        override val gui: GUI = GUIBuilder(GUIType.NORMAL, 9, 5)
            .setStructure("" +
                "1 - - - - - - - 2" +
                "| s # # e # # # |" +
                "| # # # e # # # |" +
                "| # # # e # # # |" +
                "3 - - - - - - - 4")
            .addIngredient('s', OpenSideConfigItem(sideConfigGUI))
            .addIngredient('e', EnergyBar(3, energyHolder))
            .build()
        
    }
    
}

private val BASIC_CAPACITY = NovaConfig[Blocks.BASIC_POWER_CELL].getLong("capacity")!!
private val ADVANCED_CAPACITY = NovaConfig[Blocks.ADVANCED_POWER_CELL].getLong("capacity")!!
private val ELITE_CAPACITY = NovaConfig[Blocks.ELITE_POWER_CELL].getLong("capacity")!!
private val ULTIMATE_CAPACITY = NovaConfig[Blocks.ULTIMATE_POWER_CELL].getLong("capacity")!!

class BasicPowerCell(
    uuid: UUID,
    data: CompoundElement,
    material: NovaMaterial,
    ownerUUID: UUID,
    armorStand: FakeArmorStand,
) : PowerCell(
    false,
    BASIC_CAPACITY,
    uuid,
    data,
    material,
    ownerUUID,
    armorStand,
)

class AdvancedPowerCell(
    uuid: UUID,
    data: CompoundElement,
    material: NovaMaterial,
    ownerUUID: UUID,
    armorStand: FakeArmorStand,
) : PowerCell(
    false,
    ADVANCED_CAPACITY,
    uuid,
    data,
    material,
    ownerUUID,
    armorStand,
)

class ElitePowerCell(
    uuid: UUID,
    data: CompoundElement,
    material: NovaMaterial,
    ownerUUID: UUID,
    armorStand: FakeArmorStand,
) : PowerCell(
    false,
    ELITE_CAPACITY,
    uuid,
    data,
    material,
    ownerUUID,
    armorStand,
)

class UltimatePowerCell(
    uuid: UUID,
    data: CompoundElement,
    material: NovaMaterial,
    ownerUUID: UUID,
    armorStand: FakeArmorStand,
) : PowerCell(
    false,
    ULTIMATE_CAPACITY,
    uuid,
    data,
    material,
    ownerUUID,
    armorStand,
)

class CreativePowerCell(
    uuid: UUID,
    data: CompoundElement,
    material: NovaMaterial,
    ownerUUID: UUID,
    armorStand: FakeArmorStand,
) : PowerCell(
    true,
    Long.MAX_VALUE,
    uuid,
    data,
    material,
    ownerUUID,
    armorStand,
)
