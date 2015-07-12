EditText@+id means an editText with an id
EditText@!id means an editText without an id

# Simple Tests (Backpress/Orientation/Home)

All tests start in portrait mode
Three widgets (EditText@+id, TextView@+id, EditText@!id):

### Orientation.

StatefulActivity at front.

- Only EditText@+id keeps value

StatefulActivity behind. Generate user inputs for all.
Start RandomActivity -> backpress.

- All inputs keep value.

StatefulActivity behind. Generate user inputs for all.
Start RandomActivity -> orientate -> backpress

- Only EditText@+id keeps value

StatefulActivity behind. Generate user inputs for all.
Start RandomActivity -> orientate -> orientate (back to portrait) -> backpress

- All inputs keep value!!



5. StatefulActivity at front.

- Only EditText@+id keeps value

