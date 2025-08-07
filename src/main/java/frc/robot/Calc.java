package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.Constants;

public class Calc {
    Joystick joystick;

    public static class Speed {
        public final double m_left;
        public final double m_right;
        public Speed(double m_left, double m_right) {
            this.m_left = m_left;
            this.m_right = m_right;
        }
    }

    public static Speed calculateAnalogic(Joystick joystick, double multiplier) {
        double left_X = joystick.getX();
        double left_Y = (joystick.getY()) * -1;

        double magnitude = Math.hypot(left_X, left_Y);
        if (magnitude < Constants.deadZone) return new Speed(0, 0);
        
        magnitude = Math.min(1, Math.max(-1, magnitude));
        double sen = left_Y / magnitude;

        double leftSpeed = 0;
        double rightSpeed = 0;

        if (left_X > 0 && left_Y < 0) {
            // Quadrante 1
            leftSpeed = magnitude * multiplier;
            rightSpeed = ((2 * sen + 1) * magnitude * -1) * multiplier;
          } else if (left_X < -0 && left_Y < -0) {
            // Quadrante 2
            leftSpeed = ((2 * sen + 1) * -1) * magnitude * multiplier;
            rightSpeed = magnitude * multiplier;
          } else if (left_X < -0 && left_Y > 0) {
            // Quadrante 3
            leftSpeed = magnitude * multiplier * -1;
            rightSpeed = ((2 * sen - 1) * -1) * magnitude * multiplier;
          } else if (left_X > 0 && left_Y > 0) {
            // Quadrante 4
            leftSpeed = ((2 * sen - 1) * -1) * magnitude * multiplier;
            rightSpeed = magnitude * multiplier * -1;
          }

          return new Speed(leftSpeed, rightSpeed);
    }

    public static Speed calculateTrigger(Joystick joystick, double multiplier) {
        double L_Trigger = joystick.getRawAxis(Constants.LTrigger);
        double R_Trigger = joystick.getRawAxis(Constants.RTrigger);

        if (R_Trigger > Constants.deadZone) {
            double value = R_Trigger * multiplier;
        } else if (L_Trigger > Constants.deadZone) {
            double value = (L_Trigger * multiplier) * -1;
            return new Speed(value, value);
        }

        return new Speed(0, 0);
    }

    public static Speed calculatePov(Joystick joystick, double multiplier) {
        switch (joystick.getPOV()) {
            case 0: 
                return new Speed(0.5, 0.5);
            case 90:
                return new Speed(0.5, -0.5);
            case 180:
                return new Speed(-0.5, -0.5);
            case 270:
                return new Speed(-0.5, 0.5);
            case -1: 
                return new Speed(0, 0);
        }
    }
}