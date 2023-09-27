package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShootyThing extends SubsystemBase {
  public static final int ID_LEFT_SHOOTER = 8;
  public static final int ID_RIGHT_SHOOTER = 1;

  private final WPI_VictorSPX leftShooterMotor = new WPI_VictorSPX(ID_LEFT_SHOOTER);
  private final WPI_VictorSPX rightShooterMotor = new WPI_VictorSPX(ID_RIGHT_SHOOTER);

  public ShootyThing() {
    leftShooterMotor.setInverted(true);
    rightShooterMotor.setInverted(false);
  }

  public void setShooterPower(double power) {
    leftShooterMotor.set(power);
    rightShooterMotor.set(power);
  }

  public CommandBase shoot(double power) {
    return runEnd(() -> setShooterPower(power), () -> setShooterPower(0));
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber(
        "Current Left Shooter Motor %", leftShooterMotor.getMotorOutputPercent());
    SmartDashboard.putNumber(
        "Current Right Shooter Motor %", rightShooterMotor.getMotorOutputPercent());
  }
}
