package frc.robot;

import static edu.wpi.first.wpilibj.PS4Controller.Button.*;
import static edu.wpi.first.wpilibj.XboxController.Button.*;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import java.util.function.DoubleSupplier;

/**
 * Handles input from Xbox/Logitech or PS4/PS5 controllers connected to the Driver Station. Also has
 * code for handling Logitech F310 gamepad in DirectInput mode on simulation. (The Logitech gamepads
 * have a switch on the back, D/X, to switch between DirectInput and Xbox mode. The gamepad should
 * be set to X whenever connected to the Driver Station.) You should `import static
 * frc.robot.GenericGamepad.Button.*;` (same for Axis) to reduce verbosity.
 */
public class GenericGamepad2 {
  public static ControllerType defaultType = ControllerType.PS4; // a reasonable assumption

  public enum ControllerType {
    Unconnected,
    PS4,
    Xbox,
    Logi
  }

  public enum Button {
    /** Equivalent to × Blue Cross on PS4. Bottom button. */
    cross_a(kCross, kA, 2),
    /** Equivalent to ○ Red Circle on PS4. Right button. */
    circle_b(kCircle, kB, 3),
    /** Equivalent to □ Purple Square on PS4. Left button. */
    square_x(kSquare, kX, 1),
    /** Equivalent to △ Green Triangle on PS4. Top button. */
    triangle_y(kTriangle, kY, 4),
    /** Equivalent to L1 on PS4 */
    leftBumper(kL1, kLeftBumper, 4),
    /** Equivalent to R1 on PS4 */
    rightBumper(kR1, kRightBumper, 4),

    leftStickPushed(kL3, kLeftStick, 11),
    rightStickPushed(kR3, kRightStick, 12),
    /** Equivalent to Back on Xbox or Share on PS4 */
    leftMiddle(kShare, kBack, 9),
    /** Equivalent to Start on Xbox or Options on PS4 */
    rightMiddle(kOptions, kStart, 10),
    /** Equivalent to Touchpad on PS4 - No equivalent on xbox */
    topMiddle(kTouchpad, null, null),
    /** Equivalent to Options on PS4 - No equivalent on xbox */
    bottomMiddle(kOptions, null, null);

    public final int ps4;
    public final int xbox;
    public final int logi;

    Button(PS4Controller.Button ps4, XboxController.Button xbox, Integer logi) {
      this.ps4 = ps4 != null ? ps4.value : -1;
      this.xbox = xbox != null ? xbox.value : -1;
      this.logi = logi != null ? logi : -1;
    }

    public int getValue(ControllerType type) {
      return switch (type) {
        case Unconnected -> -1;
        case PS4 -> ps4;
        case Xbox -> xbox;
        case Logi -> logi;
      };
    }
  }

  public enum Axis {
    leftTrigger(PS4Controller.Axis.kL2, XboxController.Axis.kLeftTrigger, null),
    rightTrigger(PS4Controller.Axis.kL2, XboxController.Axis.kLeftTrigger, null),
    /** Note: joystick y values are -1 when fully pushed up * */
    leftY(PS4Controller.Axis.kLeftY, XboxController.Axis.kLeftY, 1),
    leftX(PS4Controller.Axis.kLeftX, XboxController.Axis.kLeftX, 0),
    /** Note: joystick y values are -1 when fully pushed up * */
    rightY(PS4Controller.Axis.kRightY, XboxController.Axis.kRightY, 3),
    rightX(PS4Controller.Axis.kRightX, XboxController.Axis.kRightX, 2);

    public final int ps4;
    public final int xbox;
    public final int logi;

    Axis(PS4Controller.Axis ps4, XboxController.Axis xbox, Integer logi) {
      this.ps4 = ps4 != null ? ps4.value : -1;
      this.xbox = xbox != null ? xbox.value : -1;
      this.logi = logi != null ? logi : -1;
    }

    public int getValue(ControllerType type) {
      return switch (type) {
        case Unconnected -> -1;
        case PS4 -> ps4;
        case Xbox -> xbox;
        case Logi -> logi;
      };
    }
  }

  public int port;
  public ControllerType currentType;

  public boolean periodic() {
    var name = DriverStation.getJoystickName(port);
    var unconnected = !DriverStation.isJoystickConnected(port);
    var xbox = DriverStation.getJoystickIsXbox(port);

    currentType =
        unconnected
            ? ControllerType.Unconnected
            : xbox
                ? ControllerType.Xbox
                : name.startsWith("Logitech Dual")
                    ? ControllerType.Logi
                    : name.startsWith("Wireless") ? ControllerType.PS4 : defaultType;

    return true;
  }

  // Magic hacks to make it run automatically
  private final Trigger periodicTrigger = new Trigger(this::periodic);

  /** Equivalent to × Blue Cross on PS4. Bottom button. */
  public Trigger a_cross = button(Button.cross_a);
  /** Equivalent to ○ Red Circle on PS4. Right button. */
  public Trigger b_circle = button(Button.circle_b);
  /** Equivalent to □ Purple Square on PS4. Left button. */
  public Trigger x_square = button(Button.square_x);
  /** Equivalent to △ Green Triangle on PS4. Top button. */
  public Trigger y_triangle = button(Button.triangle_y);
  /** Equivalent to L1 on PS4 */
  public Trigger leftBumper = button(Button.leftBumper);
  /** Equivalent to R1 on PS4 */
  public Trigger rightBumper = button(Button.rightBumper);

  public Trigger leftStickPushed = button(Button.leftStickPushed);
  public Trigger rightStickPushed = button(Button.rightStickPushed);
  // backwards compatibility
  public Trigger leftJoystickPushed = leftStickPushed;
  public Trigger rightJoystickPushed = rightStickPushed;
  /** Equivalent to Back on Xbox or Share on PS4 */
  public Trigger leftMiddle = button(Button.leftMiddle);
  /** Equivalent to Start on Xbox or Options on PS4 */
  public Trigger rightMiddle = button(Button.rightMiddle);
  /** Equivalent to Touchpad on PS4 - No equivalent on xbox */
  public Trigger topMiddle = button(Button.topMiddle);
  /** Equivalent to Options on PS4 - No equivalent on xbox */
  public Trigger bottomMiddle = button(Button.bottomMiddle);

  public DoubleSupplier leftTrigger = axis(Axis.leftTrigger);
  public DoubleSupplier rightTrigger = axis(Axis.rightTrigger);
  /** Note: joystick y values are -1 when fully pushed up * */
  public DoubleSupplier leftY = axis(Axis.leftY);

  public DoubleSupplier leftX = axis(Axis.leftX);
  /** Note: joystick y values are -1 when fully pushed up * */
  public DoubleSupplier rightY = axis(Axis.rightY);

  public DoubleSupplier rightX = axis(Axis.rightX);

  public Trigger button(Button button) {
    return new Trigger(
        () -> {
          var buttonValue = button.getValue(currentType);
          // if button value < 1, it is invalid
          return buttonValue > 0 && DriverStation.getStickButton(port, buttonValue);
        });
  }

  public DoubleSupplier axis(Axis axis) {
    return () -> {
      var axisValue = axis.getValue(currentType);
      // if axis value < 0, it is invalid
      return axisValue >= 0 ? DriverStation.getStickAxis(port, axisValue) : 0;
    };
  }

  public GenericGamepad2(int port) {
    this.port = port;
  }

  // backwards compatibility
  public static GenericGamepad2 from(int port) {
    return new GenericGamepad2(port);
  }

  public static GenericGamepad2 from(int port, boolean unused) {
    return new GenericGamepad2(port);
  }
}
