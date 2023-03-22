package org.touchhome.bundle.firmata.workspace;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.firmata4j.I2CDevice;
import org.touchhome.bundle.api.EntityContext;
import org.touchhome.bundle.api.state.State;
import org.touchhome.bundle.api.workspace.WorkspaceBlock;
import org.touchhome.bundle.api.workspace.scratch.MenuBlock;
import org.touchhome.bundle.firmata.FirmataBundleEntrypoint;

@Log4j2
@Getter
//@Component
/**
 * For now it's too much work to implement i2c devices
 */
public class Scratch3FirmataI2CBlocks extends Scratch3FirmataBaseBlock {

  private final MenuBlock.StaticMenuBlock<BME280ValueMenu> bme280ValueMenu;

  public Scratch3FirmataI2CBlocks(EntityContext entityContext, FirmataBundleEntrypoint firmataBundleEntrypoint) {
    super("#E0D225", entityContext, firmataBundleEntrypoint, "ic");

    this.bme280ValueMenu = menuStatic("bme280ValueMenu", BME280ValueMenu.class, BME280ValueMenu.Temp);

    blockReporter(9, "BME280", "BME280 [TYPE] of [FIRMATA]", this::getBME280ValueEvaluate, block -> {
      block.addArgument(FIRMATA, this.firmataIdMenu);
      block.addArgument("TYPE", this.bme280ValueMenu);
    });
  }

  private State getBME280ValueEvaluate(WorkspaceBlock workspaceBlock) {
    return execute(workspaceBlock, false, entity -> {
      BME280ValueMenu type = workspaceBlock.getMenuValue("TYPE", this.bme280ValueMenu);
      I2CDevice i2CDevice = entity.getDevice().getIoDevice().getI2CDevice((byte) 0x77);

      return null; // entity.getDevice().getIoDevice().getProtocol();
    });
  }

  private enum BME280ValueMenu {
    Temp, Pressure, Humidity
  }
}
