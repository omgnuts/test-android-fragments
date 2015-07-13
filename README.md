# General Activity (Self-reference)

This is for future me. The design pattern for activities

### Handling of data models

Implement onLoadModel()

```
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ... create views ...

    // load data
    onLoadModel();
}

protected void onRestart() {
    super.onRestart();

    // load data
    onLoadModel(); --?? Is this necessary to call it here
}

private void onLoadModel() {
    // maybe this is where you should load all data
    // onCreate + onRestart will call this
}

```

### Handling of listeners / database etc

Implement onRelease()

```
protected void onStart() {
    super.onStart();
    // register observables
    // no need to create additional method unless complicated
}

protected void onStop() {
    super.onStop();
    onRelease();
}

public void finish() {
    super.finish();
    // called externally to force finish.
    // save persistent or do finishing work. 
    
    onRelease() 
}

private void onRelease() {
  // deregister 
}

```

### Handle orientation / home

Don't restore onCreate. Just use onRestoreInstanceState ONLY.
http://developer.android.com/training/basics/activity-lifecycle/recreating.html#RestoreState

This will also be sufficient to handle orientation changes using different layouts. However, the ids implemented must be similar. Therefore, try to write codes in layout-port & layout-land. only use layout/ for shared resources such as includes. 

However, you should NOT load the datamodel (eg. downloading adverts) if you are restoring states. Just onInitViews() will suffice (eg. set adapter to view, set clickers etc)

```
public void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);

    // Just handle your property members. Don't save view states. 
    
    // eg. edittext - auto saves its text
    // eg. listview - auto saves its firstvisibleposition
    
    // however, the text in textview is not a state. 
    // it displays text based on some member property. 
    // so you need to save the member property and display it.
    
    // dont jump in to write code to handle this unless you are sure it is not already handled
}

protected void onSaveInstanceState(Bundle savedInstanceState) {
    super.onSaveInstanceState(savedInstanceState);
}

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView();

    if (savedInstanceState == null) {
        onLoadModel();
    }
    
    onInitViews();
}

```

### Handle orientation via onConfigurationChanged. 

There is another mutually exclusive method to handle orientation via onConfigurationChanged. 

For comparison, 
- the default android method handles orientation by saving and restoring both view + member states. The full activity cycle is being called. 
- onConfigurationChange allows you to manually restore a new view, initViews. The member states are preserved. So you do not need to make large arrays parcelable. listeners are also not disrupted. Progress dialogs however may ahve to be dismissed (need to check).

Checked:
- Configuration change also switches to use layout-land from layout-port 
- onpause/onstop are not called. 
- it jumps back to recreate just the views, but everything else is not lost.
- you need to however, a) setup ui clickers, b) load the data back to views.

Good explanation from https://github.com/codepath/android_guides/wiki/Handling-Configuration-Changes

If your application doesn't need to update resources during a specific configuration change and you have a performance limitation that requires you to avoid the activity restart, then you can declare that your activity handles the configuration change itself, which prevents the system from restarting your activity.

However, this technique should be considered a last resort when you must avoid restarts due to a configuration change and is not recommended for most applications. To take this approach, we must add the android:configChanges node to the activity within the AndroidManifest.xml:

```
<activity android:name=".MyActivity"
          android:configChanges="orientation|screenSize|keyboardHidden"
          android:label="@string/app_name">
```

Now, when one of these configurations change, the activity does not restart but instead receives a call to onConfigurationChanged():

```
// Within the activity which receives these changes
// Checks the current device orientation, and toasts accordingly
@Override
public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);

    // Checks the orientation of the screen
    if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
    } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
        Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
    }
}
```
