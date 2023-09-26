package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
  // - Constants -
  public static final int INTAKE_ID = 13;
  public static final double INTAKE_POWER = 1.00;

  private final WPI_VictorSPX intake = new WPI_VictorSPX(INTAKE_ID);

  public Intake() {
    intake.setInverted(false);
  }

  public CommandBase runIntake(double power) {
    return runEnd(() -> intake.set(power), () -> intake.set(0));
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Current Intake Motor Percent", intake.getMotorOutputPercent());
  }
}
