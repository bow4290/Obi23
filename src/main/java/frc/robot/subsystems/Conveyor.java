package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class Conveyor extends SubsystemBase {
  public static final int ID_TOP = 9;
  public static final int ID_BOTTOM = 7;
  public static final double POWER_FORWARD = 1;
  public static final double POWER_BACKWARD = -0.25;

  private final WPI_VictorSPX topMotor = new WPI_VictorSPX(ID_TOP);
  private final WPI_VictorSPX bottomMotor = new WPI_VictorSPX(ID_BOTTOM);
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
    return conveyBall(POWER_FORWARD);
  }

  public CommandBase conveyBallBackward() {
    return conveyBall(POWER_BACKWARD);
  }
}
