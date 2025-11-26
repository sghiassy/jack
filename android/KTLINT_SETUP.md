# ktlint Format-on-Save Setup for Android Studio

## What's Already Done
- ✅ ktlint gradle plugin installed (v12.1.2)
- ✅ Configuration file created (`.editorconfig`)
- ✅ Android-specific settings enabled

## Gradle Commands Available
```bash
# Check formatting issues
./gradlew ktlintCheck

# Auto-format all files
./gradlew ktlintFormat
```

## Setting Up Format-on-Save in Android Studio

### Option 1: Using Actions on Save (Recommended - Built-in)
This is the easiest method and uses Android Studio's native features.

1. Open **Android Studio > Settings** (or **File > Settings** on Windows/Linux)
2. Navigate to **Tools > Actions on Save**
3. Enable: **Reformat code**
4. Configure the formatter:
   - Go to **Editor > Code Style > Kotlin**
   - Click **"Set from..."** button (top right)
   - Select **"Predefined Style" > "Kotlin style guide"**
   - Click **Apply**
5. Enable EditorConfig:
   - Go to **Editor > Code Style**
   - Check **"Enable EditorConfig support"**
   - Click **OK**

### Option 2: Using External Tool + File Watcher
This method runs ktlint directly on save.

#### Step 1: Create External Tool
1. Go to **Settings > Tools > External Tools**
2. Click the **+** button to add a new tool
3. Configure:
   - **Name**: `ktlint format`
   - **Program**: `$ProjectFileDir$/gradlew`
   - **Arguments**: `ktlintFormat -PktlintFiles="$FilePath$"`
   - **Working directory**: `$ProjectFileDir$`
   - **Advanced Options**:
     - ✅ Check "Synchronize files after execution"
     - ✅ Check "Open console for tool output"
4. Click **OK**

#### Step 2: Set Up File Watcher
1. Install the **File Watchers** plugin (if not already installed):
   - Go to **Settings > Plugins**
   - Search for "File Watchers"
   - Install and restart Android Studio
2. Go to **Settings > Tools > File Watchers**
3. Click **+** to add a new watcher
4. Configure:
   - **Name**: `ktlint`
   - **File type**: `Kotlin`
   - **Scope**: `Project Files`
   - **Program**: `$ProjectFileDir$/gradlew`
   - **Arguments**: `ktlintFormat`
   - **Output paths to refresh**: `$FilePath$`
   - **Working directory**: `$ProjectFileDir$`
   - **Advanced Options**:
     - ✅ Auto-save edited files to trigger the watcher
     - ✅ Trigger the watcher on external changes
5. Click **OK**

### Option 3: Keyboard Shortcut (Manual Trigger)
If you prefer manual formatting:

1. Create the External Tool as described in Option 2, Step 1
2. Go to **Settings > Keymap**
3. Search for "External Tools > ktlint format"
4. Right-click and select **Add Keyboard Shortcut**
5. Set your preferred shortcut (e.g., `Ctrl+Alt+L` or `Cmd+Alt+L` on Mac)

## Verifying Setup

1. Open any Kotlin file in your project
2. Add some formatting issues:
   ```kotlin
   fun test(){val x=1+2;println(x)}
   ```
3. Save the file (Ctrl+S / Cmd+S)
4. The file should auto-format to:
   ```kotlin
   fun test() {
       val x = 1 + 2
       println(x)
   }
   ```

## Troubleshooting

**Format-on-save not working:**
- Ensure "Enable EditorConfig support" is checked in Code Style settings
- Verify the File Watcher is enabled (check the File Watchers panel)
- Check if gradlew has execute permissions: `chmod +x gradlew`

**Different formatting in IDE vs command line:**
- Make sure "Enable EditorConfig support" is enabled
- Restart Android Studio after changing `.editorconfig`
- Run `./gradlew ktlintFormat` to align formatting

**Performance issues with File Watcher:**
- Consider using Option 1 (Actions on Save) instead
- Or use Option 3 (Manual keyboard shortcut) for large projects

## Configuration Files

- **`.editorconfig`**: Main formatting rules (already created)
- **`build.gradle.kts`**: ktlint plugin configuration

## Additional Resources

- [ktlint docs](https://pinterest.github.io/ktlint/)
- [ktlint gradle plugin](https://github.com/JLLeitschuh/ktlint-gradle)
