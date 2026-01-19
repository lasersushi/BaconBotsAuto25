# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is an FTC (FIRST Tech Challenge) robotics project for team BaconBots, built for the 2025-2026 DECODE season. The project uses the Pedro Pathing library for autonomous navigation.

## Build Commands

```bash
# Build the project (from project root)
./gradlew build

# Build and install to connected device
./gradlew TeamCode:installDebug

# Clean build
./gradlew clean build
```

## Project Structure

- **TeamCode/** - Team-specific code (main development area)
  - `src/main/java/org/firstinspires/ftc/teamcode/pedroPathing/` - All custom OpModes and configuration
- **FtcRobotController/** - Core FTC SDK (do not modify)

## Key Files

- `Constants.java` - All hardware configuration, motor names, PIDF tuning values, and localizer settings. Edit this for robot-specific tuning.
- `PedroAutoBlue.java` / `PedroAutoRed.java` - Autonomous OpModes using state machine pattern
- `Tuning.java` - Comprehensive tuning menu system with localization, velocity, and PIDF tuning OpModes

## Architecture

### Pedro Pathing Framework
The codebase uses Pedro Pathing (v2.0.5) for autonomous path following:
- `Follower` - Manages pose tracking and path execution
- `PathChain` - Sequential paths built from `BezierLine` and `BezierCurve` segments
- Drive encoder localization (no external odometry pods)

### Autonomous Pattern
Autonomous OpModes follow a state machine pattern with `pathState` variable:
```java
switch (pathState) {
    case 0: // Initial action
        follower.followPath(startPath, true);
        setPathState(1);
        break;
    case 1: // Wait for path completion
        if (!follower.isBusy()) setPathState(2);
        break;
    // ...
}
```

### Hardware Configuration
Motor names defined in Constants.java (must match Driver Station config):
- Drive motors: `LeftFront`, `RightFront`, `LeftBack`, `RightBack`
- Mechanisms: `ShooterMotor`, `IntakeMotor1`, `IntakeMotor2`

## Dependencies

Key external libraries (defined in `build.dependencies.gradle`):
- `com.pedropathing:ftc:2.0.5` - Pedro Pathing core
- `com.pedropathing:telemetry:1.0.0` - Pedro telemetry
- `com.bylazar:fullpanels:1.0.9` - Dashboard visualization (ByLazar Panels)
- FTC SDK 11.0.0

## Tuning Workflow

Use the `Tuning` OpMode (TeleOp) which provides a menu system for:
1. **Localization** - Test pose tracking, calibrate encoder multipliers
2. **Automatic** - Measure velocity and deceleration profiles
3. **Manual PIDF** - Tune translational, heading, and drive PIDF
4. **Tests** - Run line, triangle, and circle path tests

Visualization is available via ByLazar Panels dashboard.
