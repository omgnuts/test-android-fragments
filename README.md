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

``

### Handle orientation / home

Don't restore onCreate. Just use onRestoreInstanceState ONLY.

```



