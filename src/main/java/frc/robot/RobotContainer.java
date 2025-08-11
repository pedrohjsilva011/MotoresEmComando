package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.subsystems.Drive;
import frc.robot.commands.Locomotion;

public class RobotContainer {
  
  private final Drive drivesubsystem = new Drive();
  public final Joystick joystick = new Joystick(Constants.controller);

  public RobotContainer() {
    drivesubsystem.setDefaultCommand(new Locomotion(drivesubsystem, joystick));
  }
}
