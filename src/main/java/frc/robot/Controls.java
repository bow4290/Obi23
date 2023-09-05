package frc.robot;

import edu.wpi.first.wpilibj2.command.Commands;

public class Controls {
  // -- Control Scheme --
  // Left Stick Movement - 'Arcade' Drive
  // ⨉ / A - Conveyor Forward / Up
  // ○ / B - Conveyor Back / Down
  // △ / Y - Set Shooter Power (While holding Left Trigger)
  // Left Trigger - Power Adjust (Use △ / Y to set power.)

  public static double rawTriggerPower;

  public static void controlConfig(RobotContainer bot) {
    bot.drivetrain.setDefaultCommand(
      bot.drivetrain.run(
        // CONTROL: Drive - Left Joystick (Y Inverted required)
        () ->
          bot.drivetrain.drive(
            -bot.controller.leftY.getAsDouble(), bot.controller.leftX.getAsDouble())));

    bot.controller.cross_a.whileTrue(bot.conveyor.conveyBallForward());
    bot.controller.circle_b.whileTrue(bot.conveyor.conveyBallBackward());

    bot.controller.triangle_y.onTrue(
        Commands.runOnce(
            () -> {
              rawTriggerPower = bot.controller.leftTrigger.getAsDouble();
            }));
  }
}
