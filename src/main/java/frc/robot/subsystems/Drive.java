package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import frc.robot.Constants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drive extends SubsystemBase {
  
  // Motor controllers
 public final VictorSPX leftMotor = new VictorSPX(Constants.LMotor);
 public final VictorSPX leftMotor2 = new VictorSPX(Constants.LMotor2);
 public final VictorSPX rightMotor = new VictorSPX(Constants.RMotor);
 public final VictorSPX rightMotor2 = new VictorSPX(Constants.RMotor2);

  // Joystick for manual control); 
public Drive() {
  reqDrive();

  }

  @Override
  public void periodic() {
  
  }

  public void reqDrive(){
    leftMotor.setInverted(false);
    leftMotor2.setInverted(false);
    rightMotor.setInverted(true);
    rightMotor2.setInverted(true);
  

    leftMotor.configNeutralDeadband(Constants.deadZone);
    leftMotor2.configNeutralDeadband(Constants.deadZone);
    rightMotor.configNeutralDeadband(Constants.deadZone);
    rightMotor2.configNeutralDeadband(Constants.deadZone);
    
    leftMotor.setNeutralMode(NeutralMode.Brake);
    leftMotor2.setNeutralMode(NeutralMode.Brake);
    rightMotor.setNeutralMode(NeutralMode.Brake);
    rightMotor2.setNeutralMode(NeutralMode.Brake);
  
  }
}