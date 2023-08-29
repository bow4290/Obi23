package frc.robot;

public class Controls {
  public static void controlConfig(RobotContainer bot) {
    bot.drivetrain.setDefaultCommand(
        bot.drivetrain.run(
            // CONTROL: Drive - Left Joystick (Y Inverted required)
            () ->
                bot.drivetrain.drive(
                    -bot.controller.leftY.getAsDouble(), bot.controller.leftX.getAsDouble())));
    bot.controller.cross_a.whileTrue(bot.conveyor.conveyBallForward());
    bot.controller.circle_b.whileTrue(bot.conveyor.conveyBallBackward());
  }
}
