package frc.robot;

import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.Intake;
public class Controls {
  // -- Control Scheme --
  // Left Stick Movement - 'Arcade' Drive
  // ⨉ / A - Conveyor Forward / Up
  // ○ / B - Conveyor Back / Down
  // Dpad ↑ / ↓ - Increase / Decrease Shooter Power
  // △ / Y - Reset Shooter Power

  // -- Constants --
  private static final PowerLevel defaultPowerLevel = PowerLevel.POWER_4;

  private enum PowerLevel {
    POWER_1 {
      @Override // Used to remove any errors from decreasing past lowest value
      PowerLevel decrease() {
        return this;
      }
    },
    POWER_2,
    POWER_3,
    POWER_4 {
      @Override // Used to remove any errors from increasing past highest value
      PowerLevel increase() {
        return this;
      }
    };

    // Don't directly call these, instead use incrementLevel() and decrementLevel()
    PowerLevel increase(){
      return values()[ordinal() + 1];
    }
    PowerLevel decrease(){
      return values()[ordinal() - 1];
    }
  }
  
  // Power level mappings go here
  private static double getPowerLevelPercentage(PowerLevel level){
    double p1 = 0.65;
    double p2 = 0.75;
    double p3 = 0.85;
    double p4 = 0.95;

    return switch (level){
      case POWER_1 -> p1;
      case POWER_2 -> p2;
      case POWER_3 -> p3;
      case POWER_4 -> p4;
    };
  }

  // Uses PowerLevel methods along with getPowerLevelPercentage to change currentLevel and currentLevelPercentage
  private static void incrementPowerLevel() {
    currentLevel = currentLevel.increase();
    currentLevelPercentage = getPowerLevelPercentage(currentLevel);
  }
  private static void decrementPowerLevel() {
    currentLevel = currentLevel.decrease();
    currentLevelPercentage = getPowerLevelPercentage(currentLevel);
  }
  private static void resetPowerLevel(){
    currentLevel = defaultPowerLevel;
    currentLevelPercentage = getPowerLevelPercentage(currentLevel);
  }

  private static PowerLevel currentLevel = defaultPowerLevel;
  private static double currentLevelPercentage = getPowerLevelPercentage(currentLevel);

  // Main Function (essentially)
  public static void controlConfig(RobotContainer bot) {

    bot.drivetrain.setDefaultCommand(
      bot.drivetrain.run(
        // CONTROL: Drive - Left Joystick (Y Inverted required)
        () ->
          bot.drivetrain.drive(
            -bot.controller.leftY.getAsDouble(), bot.controller.leftX.getAsDouble())));

    bot.controller.cross_a.whileTrue(bot.conveyor.conveyBallForward());
    bot.controller.circle_b.whileTrue(bot.conveyor.conveyBallBackward());

    bot.controller.dpadUp.onTrue(Commands.runOnce(() -> {
      incrementPowerLevel();
    }));
    bot.controller.dpadDown.onTrue(Commands.runOnce(() -> {
      decrementPowerLevel();
    }));
    bot.controller.triangle_y.onTrue(Commands.runOnce(() -> {
      resetPowerLevel();
    }));

    // Run in parallel so shooter flywheels have time to get to full speed
    bot.controller.rightTriggerB.whileTrue(Commands.parallel(
      bot.shooter.shoot(currentLevelPercentage),
      bot.conveyor.conveyBallForward().beforeStarting(Commands.waitSeconds(1))
    ));

    bot.controller.square_x.whileTrue(bot.intake.runIntake(Intake.intakePower));
  }
}
