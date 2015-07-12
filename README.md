# Simple Tests (Backpress/Orientation/Home)

EditText@+id means an editText with an id
EditText@!id means an editText without an id

# A. Orientation/Backpress

All tests start in portrait mode
Three widgets (EditText@+id, TextView@+id, EditText@!id):

### A1. StatefulActivity at front. Generate user inputs for all.

Orientate

- Only EditText@+id keeps value

### A2. StatefulActivity behind. Generate user inputs for all.

Start RandomActivity -> backpress.

- All inputs keep value.

### A3. StatefulActivity behind. Generate user inputs for all.

Start RandomActivity -> orientate -> backpress

- Only EditText@+id keeps value

### A4. StatefulActivity behind. Generate user inputs for all.

Start RandomActivity -> orientate -> orientate (back to portrait) -> backpress

- All inputs keep value!!

**This means that StatefulActivity preserves its state until the moment it does OnStart() - to verify**

# B. Home

### B1. StatefulActivity at front. Generate user inputs for all

Press HOME. Return to app.

- Only EditText@+id keeps value

