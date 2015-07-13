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

```


