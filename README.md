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

However, you also NEED to load the datamodel (eg. downloading adverts) and call onInitViews()  (eg. set adapter to view, set clickers etc)

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
    onLoadModel();
    onInitViews();
}

```

### Handle orientation via onConfigurationChanged. 

There is another mutually exclusive method to handle orientation via onConfigurationChanged. This is when you are carrying a heavy load of data in the activity.

For comparison, 
- the default android method handles orientation by saving and restoring both view (android) + member (you) states. The full activity cycle is being called. listeners have to be reactivated etc. 
- onConfigurationChange allows you to manually restore a new view, initViews. The member states are preserved. So you do not need to make large arrays parcelable. listeners are also not disrupted. Progress dialogs however may ahve to be dismissed (need to check). It is also useful if you are using a lot of fragments. So the data of the fragments are not destroyed (how to handle initViews? - similar?)

The downside of onConfigurationChange however, is that you lose all viewstates. For example the firstvisibleviewposition of the listview is not saved. So you need to save that position as a member property as the user scrolls. 
However, you can manually save the view states using a bundle within the onConfigurationChanged. It is not hard. just tedious

```
private Bundle onSaveViewStates() {
    Bundle bundle = new Bundle();
    ...
    int position = getListView().getFirstVisiblePosition();
    bundle.putInt("save:position", position);
    bundle.putString("save:textview", getTextView().getText().toString());
    
    return bundle;
}

private void onRestoreViewStates(Bundle bundle) {
    ...
    int position = bundle.getInt("save:position");
    getListView().setSelection(position);
    String text = bundle.getString("save:textview");
    getTextView().setText(text);
}

public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    Bundle bundle = onSaveViewStates();
    if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
        logState("onConfigurationChanged: Configuration.ORIENTATION_PORTRAIT");
        setContentView(R.layout.activity_demo2_stateful);
    } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        logState("onConfigurationChanged: Configuration.ORIENTATION_LANDSCAPE");
        setContentView(R.layout.activity_demo2_stateful);
    }
    onInitViews();
    onRestoreViewStates(bundle);
}

```

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
