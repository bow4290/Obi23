// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drivetrain extends SubsystemBase {
  public static final int ID_RIGHT_DRIVE_1 = 5;
  public static final int ID_RIGHT_DRIVE_2 = 3;
  public static final int ID_RIGHT_DRIVE_3 = 6;
  public static final int ID_LEFT_DRIVE_1 = 12;
  public static final int ID_LEFT_DRIVE_2 = 11;
  public static final int ID_LEFT_DRIVE_3 = 10;

  private WPI_VictorSPX rightDrive1, rightDrive2, rightDrive3, leftDrive1, leftDrive2, leftDrive3;
  private DifferentialDrive differentialDrive;
  /** Creates a new Drivetrain. */
  public Drivetrain() {
    rightDrive1 = new WPI_VictorSPX(ID_RIGHT_DRIVE_1);
    rightDrive2 = new WPI_VictorSPX(ID_RIGHT_DRIVE_2);
    rightDrive3 = new WPI_VictorSPX(ID_RIGHT_DRIVE_3);
    leftDrive1 = new WPI_VictorSPX(ID_LEFT_DRIVE_1);
    leftDrive2 = new WPI_VictorSPX(ID_LEFT_DRIVE_2);
    leftDrive3 = new WPI_VictorSPX(ID_LEFT_DRIVE_3);

    MotorControllerGroup leftDriveGroup =
        new MotorControllerGroup(leftDrive1, leftDrive2, leftDrive3);
    MotorControllerGroup rightDriveGroup =
        new MotorControllerGroup(rightDrive1, rightDrive2, rightDrive3);

    // fun wiring stuff
    leftDriveGroup.setInverted(true);
    rightDriveGroup.setInverted(true);

    differentialDrive = new DifferentialDrive(leftDriveGroup, rightDriveGroup);
  }

  public void drive(double move, double rotation, double speed) {
    differentialDrive.arcadeDrive(move * speed, rotation * speed);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
