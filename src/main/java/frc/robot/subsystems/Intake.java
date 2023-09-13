package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
  // - Constants - 
  public static final double intakePower = 1.00;

  public static final int intakeId = 13;

  private final WPI_VictorSPX intake = new WPI_VictorSPX(intakeId);

  public Intake() {
    intake.setInverted(false);
  }

  public CommandBase runIntake(double power) {
    return runEnd(() -> intake.set(power), () -> intake.set(0));
  }
}
