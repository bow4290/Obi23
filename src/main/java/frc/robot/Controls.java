package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.Intake;

public class Controls {
  // -- Control Scheme --
  // Left Trigger (As Button) - Aim drivetrain speed (slow drivetrain)
  // Left Stick Movement - 'Arcade' Drive
  // □ / X - Intake
  // ⨉ / A - Conveyor Forward / Up
  // ○ / B - Conveyor Back / Down
  // Dpad ↑ / ↓ - Increase / Decrease Shooter Power
  // △ / Y - Reset Shooter Power
  // R2 / Right Trigger - Shoot (Spisn flywheels + move up conveyor)

  private static final PowerLevel DEFAULT_SHOOTER_POWER_LEVEL = PowerLevel.POWER_4;
  private static final double DEFAULT_DRIVETRAIN_DRIVESPEED_PERCENTAGE = 1;
  private static final double DEFAULT_DRIVETRAIN_ROTATESPEED_PERCENTAGE = 1;
  private static final double LOW_DRIVETRAIN_DRIVESPEED_PERCENTAGE = 0.35;
  private static final double LOW_DRIVETRAIN_ROTATESPEED_PERCENTAGE = 0.8;

  private static PowerLevel currentShooterLevel = DEFAULT_SHOOTER_POWER_LEVEL;
  private static double currentShooterLevelPercentage =
      getPowerLevelPercentage(currentShooterLevel);
  private static double drivetrainDriveSpeedPercentage = DEFAULT_DRIVETRAIN_DRIVESPEED_PERCENTAGE;
  private static double drivetrainRotateSpeedPercentage = DEFAULT_DRIVETRAIN_ROTATESPEED_PERCENTAGE;

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
    PowerLevel increase() {
      return values()[ordinal() + 1];
    }

    PowerLevel decrease() {
      return values()[ordinal() - 1];
    }
  }

  // Shooter power level mappings go here
  private static double getPowerLevelPercentage(PowerLevel level) {
    double p1 = 0.65;
    double p2 = 0.75;
    double p3 = 0.85;
    double p4 = 0.95;

    return switch (level) {
      case POWER_1 -> p1;
      case POWER_2 -> p2;
      case POWER_3 -> p3;
      case POWER_4 -> p4;
    };
  }

  // Uses PowerLevel methods along with getPowerLevelPercentage to change currentLevel and
  // currentLevelPercentage
  private static void incrementPowerLevel() {
    currentShooterLevel = currentShooterLevel.increase();
    currentShooterLevelPercentage = getPowerLevelPercentage(currentShooterLevel);
  }

  private static void decrementPowerLevel() {
    currentShooterLevel = currentShooterLevel.decrease();
    currentShooterLevelPercentage = getPowerLevelPercentage(currentShooterLevel);
  }

  private static void resetPowerLevel() {
    currentShooterLevel = DEFAULT_SHOOTER_POWER_LEVEL;
    currentShooterLevelPercentage = getPowerLevelPercentage(currentShooterLevel);
  }

  // helper method to change drive speeds (implemented for easier aiming)
  private static void lowDriveSpeed() {
    drivetrainDriveSpeedPercentage = LOW_DRIVETRAIN_DRIVESPEED_PERCENTAGE;
    drivetrainRotateSpeedPercentage = LOW_DRIVETRAIN_ROTATESPEED_PERCENTAGE;
  }

  private static void defaultDriveSpeed() {
    drivetrainDriveSpeedPercentage = DEFAULT_DRIVETRAIN_DRIVESPEED_PERCENTAGE;
    drivetrainRotateSpeedPercentage = DEFAULT_DRIVETRAIN_ROTATESPEED_PERCENTAGE;
  }

  // Main Function (essentially)
  public static void controlConfig(RobotContainer bot) {

    // Drive
    bot.drivetrain.setDefaultCommand(
        bot.drivetrain.run(
            // CONTROL: Drive - Left Joystick (Y Inverted required)
            () ->
                bot.drivetrain.drive(
                    bot.controller.leftX.getAsDouble(),
                    bot.controller.leftY.getAsDouble(),
                    drivetrainDriveSpeedPercentage,
                    drivetrainRotateSpeedPercentage)));

    // Conveyor
    bot.controller.cross_a.whileTrue(bot.conveyor.conveyBallForward());
    bot.controller.circle_b.whileTrue(bot.conveyor.conveyBallBackward());

    // Power Levels
    bot.controller.dpadUp.onTrue(
        Commands.runOnce(
            () -> {
              incrementPowerLevel();
            }));
    bot.controller.dpadDown.onTrue(
        Commands.runOnce(
            () -> {
              decrementPowerLevel();
            }));
    bot.controller.triangle_y.onTrue(
        Commands.runOnce(
            () -> {
              resetPowerLevel();
            }));

    // Drivetrain aim speed buttons
    bot.controller.leftTriggerB.onFalse(
        Commands.runOnce(
            () -> {
              defaultDriveSpeed();
            }));
    bot.controller.leftTriggerB.onTrue(
        Commands.runOnce(
            () -> {
              lowDriveSpeed();
            }));

    // Shoot
    bot.controller.rightTriggerB.whileTrue(
        Commands.parallel( // Run in parallel so shooter flywheels have time to get to full speed
            bot.shooter.shoot(currentShooterLevelPercentage),
            bot.conveyor.conveyBallForward().beforeStarting(Commands.waitSeconds(0.75))));

    // Intake
    bot.controller.square_x.whileTrue(bot.intake.runIntake(Intake.INTAKE_POWER));
  }

  public static void periodic() {
    SmartDashboard.putNumber("Target Shooter Power %", currentShooterLevelPercentage);
    SmartDashboard.putString("Shooter Level", currentShooterLevel.toString());
    SmartDashboard.putNumber("Target Drivetrain Speed %", drivetrainDriveSpeedPercentage);
    SmartDashboard.putNumber("Target Drivetrain Speed %", drivetrainRotateSpeedPercentage);

    // Get Shooter Power Level in ordinal, add 1 since it starts at 0.
    SmartDashboard.putNumber("QuickView Shooter Power Level", currentShooterLevel.ordinal() + 1);

    // Multiplied by 100 to get a number between 0-100
    SmartDashboard.putNumber("QuickView Target Shooter %", currentShooterLevelPercentage * 100);
  }
}
