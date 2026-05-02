# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

FTC (FIRST Tech Challenge) robotics project for team BaconBots, 2025-2026 DECODE season. Uses Pedro Pathing for autonomous navigation on a mecanum drivetrain with drive-encoder localization (no external odometry pods).

## Build Commands

```bash
# Build the project (from project root)
./gradlew build

# Build and install to a connected Robot Controller (ADB)
./gradlew TeamCode:installDebug

# Clean build
./gradlew clean build
```

There is no test suite — FTC code is validated on-robot by running OpModes from the Driver Station.

## Project Structure

- **TeamCode/** — All custom robot code (this is the development surface).
  - `src/main/java/org/firstinspires/ftc/teamcode/pedroPathing/` — Constants, autonomous OpModes, and the tuning menu.
- **FtcRobotController/** — Vendored FTC SDK. Do not modify.

## Key Files

- `pedroPathing/Constants.java` — Single source of truth for hardware names, motor directions, encoder tick conversions, mass, and all PIDF/velocity/acceleration tuning values. `Constants.createFollower(hardwareMap)` is the factory every OpMode uses.
- `pedroPathing/PedroAutoBlue.java` / `PedroAutoRed.java` — Competition autonomous OpModes. Same shape, different starting pose and field side.
- `pedroPathing/Tuning.java` — TeleOp menu (`SelectableOpMode`) that exposes every Pedro Pathing tuner. Holds a static `Follower` reused by tuner sub-OpModes.

## Architecture

### Pedro Pathing Framework
- `Follower` manages pose tracking and path execution. Always built via `Constants.createFollower(hardwareMap)` so tuned values stay consistent.
- Paths are `Path` (single Bezier) or `PathChain` (built with `follower.pathBuilder()`). Heading along a path is set with `setConstantHeadingInterpolation` (or other interpolators); without it the robot will rotate during the move.
- Localization is drive-encoder-based — `DriveEncoderConstants` in `Constants.java`. Re-tune `forwardTicksToInches` / `strafeTicksToInches` / `turnTicksToInches` if the chassis or wheels change.

### Autonomous State Machine
Both `PedroAuto*` OpModes use one `pathState` int dispatched in `autonomousPathUpdate()`. The structure intentionally mixes two flavors of state:

- **Movement states** (e.g. `2`, `3`, `4`, `5` …) start a path with `follower.followPath(...)` and advance when `!follower.isBusy()`.
- **Timed action states** (e.g. `20`, `21`, `22`, `23`) sit at a scoring position and gate intake/shooter motor power on `pathTimer.getElapsedTimeSeconds()` windows. `pathTimer.resetTimer()` is called when entering them.

State numbers are not sequential — movement states flow `0 → 20 → 2 → 3 → 4 → 21 → 5 …`. When adding a step, follow the existing pattern (reset `pathTimer` whenever a timed window starts) rather than renumbering.

`stop()` zeros all mechanism motors; preserve that behavior on any new OpMode so a disable doesn't leave the shooter spinning.

### Hardware Configuration
Names below must match the Driver Station robot configuration exactly:
- Drive: `LeftFront`, `RightFront`, `LeftBack`, `RightBack` (left side reversed in `Constants.driveConstants`)
- Mechanisms: `ShooterMotor`, `IntakeMotor1`, `IntakeMotor2` (declared as `DcMotorSimple` in the auto OpModes)

Field coordinates are in inches with heading in radians. Current starting poses:
- Blue: `(22, 122)` heading `143°`
- Red: `(56, 8)` heading `90°`

## Dependencies

From `build.dependencies.gradle`:
- `com.pedropathing:ftc:2.1.1` — Pedro Pathing core
- `com.pedropathing:telemetry:1.0.0`
- `com.pedropathing:ivy:1.0.0`
- `com.bylazar:fullpanels:1.0.12` — ByLazar Panels dashboard
- FTC SDK `11.0.0`

## Tuning Workflow

Run the `Tuning` TeleOp from the Driver Station. The menu groups:
1. **Localization** — Localization test, Forward/Lateral/Turn tuners (calibrate encoder tick multipliers).
2. **Automatic** — Forward/Lateral velocity tuners and zero-power-acceleration tuners. Their results map directly into `FollowerConstants` and `MecanumConstants` in `Constants.java`.
3. **Manual** — Translational, Heading, Drive, Line, and Centripetal PIDF tuners.
4. **Tests** — Line, Triangle, Circle path validation.

Live visualization (pose, paths, history) is published to ByLazar Panels.
