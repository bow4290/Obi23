// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drivetrain extends SubsystemBase {
  private WPI_VictorSPX rightDrive1, rightDrive2, rightDrive3, leftDrive1, leftDrive2, leftDrive3;
  private DifferentialDrive differentialDrive;
  /** Creates a new Drivetrain. */
  public Drivetrain() {
    rightDrive1 = new WPI_VictorSPX(5);
    rightDrive2 = new WPI_VictorSPX(3);
    rightDrive3 = new WPI_VictorSPX(6);
    leftDrive1 = new WPI_VictorSPX(12); 
    leftDrive2 = new WPI_VictorSPX(11); 
    leftDrive3 = new WPI_VictorSPX(10);

    MotorControllerGroup rightDriveGroup = new MotorControllerGroup(rightDrive1, rightDrive2, rightDrive3);
    MotorControllerGroup leftDriveGroup = new MotorControllerGroup(leftDrive1, leftDrive2, leftDrive3);

    differentialDrive = new DifferentialDrive(leftDriveGroup, rightDriveGroup);
  }
  public void drive(double move, double rotation){
    // differentialDrive
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
