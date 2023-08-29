package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class Conveyor extends SubsystemBase {
  public static final int idTop = 9;
  public static final int idBottom = 7;
  public static final double powerForward = 1;
  public static final double powerBackward = -0.25;

  private final WPI_VictorSPX topMotor = new WPI_VictorSPX(idTop);
  private final WPI_VictorSPX bottomMotor = new WPI_VictorSPX(idBottom);
  private DigitalInput button1 = new DigitalInput(6);
  private DigitalInput button2 = new DigitalInput(7);
  Trigger triggers = new Trigger(button2::get).negate().or(new Trigger(button1::get).negate());

  public Conveyor() {
    topMotor.setInverted(true);
    triggers.whileTrue(conveyBallForward());
  }

  public void setMotorPower(double power) {
    topMotor.set(power);
    bottomMotor.set(power);
  }

  public CommandBase conveyBall(double power) {
    return runEnd(() -> setMotorPower(power), () -> setMotorPower(0));
  }

  public CommandBase conveyBallForward() {
    return conveyBall(powerForward);
  }

  public CommandBase conveyBallBackward() {
    return conveyBall(powerBackward);
  }
}
