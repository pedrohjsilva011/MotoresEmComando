package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.Drive;
import frc.robot.Calc;
import frc.robot.Calc.Speed;

public class Loc extends Command {

  private final Drive driveSubsystem;
  private final Joystick joyDeliciu;
  Speed speeds;
  
    private double multiplier = 0;
    private boolean a;
    private boolean b;
    private boolean x;
  
    public Loc(Drive driveSubsystem,Joystick joyDeliciu) {
      this.driveSubsystem = driveSubsystem;
      this.joyDeliciu = joyDeliciu;
    addRequirements(driveSubsystem);
  }

  @Override
  public void initialize() {
    driveSubsystem.reqDrive();
  }

  @Override
  public void execute() {
    Smart();
    button();
    MainControl();
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
    a = joyDeliciu.getRawButton(Constants.button_A);
    b = joyDeliciu.getRawButton(Constants.button_B);
    x = joyDeliciu.getRawButton(Constants.button_X);

    if (a) multiplier = 0.25;
    else if (b) multiplier = 0.5;
    else if (x) multiplier = 1.0;
  }

  public void MainControl() {
    double X = joyDeliciu.getX();
    double Y = joyDeliciu.getY();
    double RT = joyDeliciu.getRawAxis(Constants.RTrigger);
    double LT = joyDeliciu.getRawAxis(Constants.LTrigger);

    if (joyDeliciu.getPOV() != Constants.povDeadZone){
      speeds = Calc.calculatePov(joyDeliciu, multiplier);
    }
    else if (RT > Constants.deadZone || LT > Constants.deadZone) {
      speeds = Calc.calculateTrigger(joyDeliciu, multiplier);
    }
    else if (Math.abs(X) >= Constants.deadZone || Math.abs(Y) >= Constants.deadZone || Math.abs(X) < Constants.negativeDeadZone || Math.abs(Y) < Constants.negativeDeadZone) {
      speeds = Calc.calculateAnalogic(joyDeliciu, multiplier);
    }
      else {
      stopDrive();
      return;
    }
  
    setSpeed(speed.leftSpeed, speed.rightSpeed);
  }

  public void Smart(){
    if (speeds != null) {
      SmartDashboard.putNumber("Left Speed", speed.leftSpeed);
      SmartDashboard.putNumber("Right Speed", speed.rightSpeed);
    } else {
      SmartDashboard.putNumber("Left Speed", 0);
      SmartDashboard.putNumber("Right Speed", 0);
    }
    SmartDashboard.putBoolean("Button A", a);
    SmartDashboard.putBoolean("Button B", b);
    SmartDashboard.putBoolean("Button X", x);
    SmartDashboard.putNumber("VelB", multiplier);
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