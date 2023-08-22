package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShootyThing extends SubsystemBase {
    public double shooterSpeedDefault = 0.90;
    public final static int idLeftShooter = 1;
    public final static int idRightShooter = 8;

    private final WPI_VictorSPX leftShooterMotor = new WPI_VictorSPX(idLeftShooter);
    private final WPI_VictorSPX rightShooterMotor = new WPI_VictorSPX(idRightShooter);
    
    public ShootyThing(){
        leftShooterMotor.setInverted(true);
        rightShooterMotor.setInverted(true);

    }

    public void setShooterPower(double power) {
        leftShooterMotor.set(power);
        rightShooterMotor.set(power);

    }

    public CommandBase shoot(double power) {
        return runEnd(() -> setShooterPower(power), () -> setShooterPower(0));
    }
}
