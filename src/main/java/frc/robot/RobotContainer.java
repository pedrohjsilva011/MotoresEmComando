package frc.robot;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.RunCommand;

public class RobotContainer {
  
  private final Drive drivesubsystem = new Drive();
  public final Joystick joystick = new Joystick(Constants.controller);
}