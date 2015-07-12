# Simple Tests (Backpress/Orientation/Home)

EditText@+id means an editText with an id
EditText@!id means an editText without an id

# A. Orientation/Backpress

All tests start in portrait mode
Three widgets (EditText@+id, TextView@+id, EditText@!id):

### A1. StatefulActivity at front. Generate user inputs for all.

```
Orientate
- Only EditText@+id keeps value
```

### A2. StatefulActivity behind. Generate user inputs for all.

```
Start RandomActivity -> backpress.
- All inputs keep value.
```

### A3. StatefulActivity behind. Generate user inputs for all.

```
Start RandomActivity -> orientate -> backpress
- Only EditText@+id keeps value
```

### A4. StatefulActivity behind. Generate user inputs for all.

```
Start RandomActivity -> orientate -> orientate (back to portrait) -> backpress
- All inputs keep value!!
```

# B. Home

### B1. StatefulActivity at front. Generate user inputs for all

```
Press HOME. Return to app.
- All inputs keep value
```

### B2. StatefulActivity behind. Generate user inputs for all.

```
Start RandomActivity. Press HOME. Return to app -> backpress.
- All inputs keep value.
```

# Summary of A & B. 

**This means that StatefulActivity preserves its state until the moment it does OnStart() - to verify**

```
For activities, it looks like most views implement inner statefuls to handle home / backpress. 
The problem child is perhaps only orientation changes. The states are probably ONLY lost when 
the oriented activity performs onStart(). Apparently when its in the background, the stateful 
activity happily accepts ALL orientation changes. So when it is portrait just before returning 
to the foreground, it keeps all values. 
```
