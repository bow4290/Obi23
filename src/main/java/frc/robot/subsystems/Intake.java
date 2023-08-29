package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
  public static final int intakeId = 13;

  private final WPI_VictorSPX intake = new WPI_VictorSPX(intakeId);

  public Intake() {
    intake.setInverted(false);
  }

  public void setIntakePower(double power) {
    intake.set(power);
  }

  public CommandBase runIntake(double power) {
    return runEnd(() -> setIntakePower(power), () -> setIntakePower(0));
  }
}
