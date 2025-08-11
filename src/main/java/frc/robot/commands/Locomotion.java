package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.Drive;
import frc.robot.Calc;
import frc.robot.Calc.Speed;

public class Locomotion extends Command {

  private final Drive driveSubsystem;
  private final Joystick joystick;
  Speed speeds;
  
    private double multiplier = 0.5;
    private boolean a;
    private boolean b;
    private boolean x;
  
    public Loc(Drive driveSubsystem,Joystick joystick) {
      this.driveSubsystem = driveSubsystem;
      this.joystick = joystick;
    addRequirements(driveSubsystem);
  }

  @Override
  public void initialize() {
    driveSubsystem.reqDrive();
  }

  @Override
  public void execute() {
    button();
    MainControl();
    SmartDashboard();
  }

  private void setSpeed(double leftSpeed, double rightSpeed) {
    driveSubsystem.leftMotor.set(ControlMode.PercentOutput, leftSpeed);
    driveSubsystem.leftMotor2.set(ControlMode.PercentOutput, leftSpeed);
    driveSubsystem.rightMotor.set(ControlMode.PercentOutput, rightSpeed);
    driveSubsystem.rightMotor2.set(ControlMode.PercentOutput, rightSpeed);
  }

  private void stopDrive() {
    setSpeed(0, 0);
  }

  public void button(){
    a = joystick.getRawButton(Constants.button_A);
    b = joystick.getRawButton(Constants.button_B);
    x = joystick.getRawButton(Constants.button_X);

    if (a) multiplier = 0.25;
    else if (b) multiplier = 0.5;
    else if (x) multiplier = 1.0;
  }

  public void MainControl() {
    double left_X = joystick.getX();
    double left_Y = joystick.getY();
    double right_X = joystick.getRawAxis(Constants.right_X);
    double right_Y = joystick.getRawAxis(Constants.right_Y);
    double RT = joystick.getRawAxis(Constants.RTrigger);
    double LT = joystick.getRawAxis(Constants.LTrigger);
    double POV = joystick.getPOV();

    if (RT > Constants.deadZone || LT > Constants.deadZone) {
      speeds = Calc.calculateTrigger(joystick, multiplier);
    } else if (POV != Constants.povDeadZone) {
      speeds = Calc.calculatePov(joystick, multiplier);
    } else if (left_X > Constants.deadZone || left_X < Constants.negativeDeadZone || left_Y > Constants.deadZone || left_Y < Constants.negativeDeadZone) {
      speeds = Calc.calculateLeftAnalogic(joystick, multiplier);
    } else if (right_X > Constants.deadZone || right_X < Constants.negativeDeadZone || right_Y > Constants.deadZone || right_Y < Constants.negativeDeadZone) {
      speeds = Calc.calculateRightAnalogic(joystick, multiplier);
    } else {
      speeds = new Speed(0, 0);
      stopDrive();
      return;
    }
    setSpeed(speeds.m_left, speeds.m_right);
  }

  public void SmartDashboard(){
    if (speeds != null) {
      SmartDashboard.putNumber("Left Speed", speeds.m_left);
      SmartDashboard.putNumber("Right Speed", speeds.m_right);
    } else {
      SmartDashboard.putNumber("Left Speed", 0);
      SmartDashboard.putNumber("Right Speed", 0);
    }
    SmartDashboard.putBoolean("Button A", a);
    SmartDashboard.putBoolean("Button B", b);
    SmartDashboard.putBoolean("Button X", x);
    SmartDashboard.putNumber("Multiplicador", multiplier);
  }  

  @Override
  public void end(boolean interrupted) {
    stopDrive();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
