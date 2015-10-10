# iDerly-android

#### How to use HttpPostRequest(String url[, Object mixed...])

Package:
```java
package com.iderly.control.HttpPostRequest;
```

Optional:
```java
package com.iderly.control.HttpPostRequestListener;
```

Class:
```java
public abstract HttpPostRequest extends AsyncTask<Void, Void, String>
```

Constructor
```java
public HttpPostRequest(String url, Object... mixed)
```

Basic Usage:
```java
new HttpPostRequest("https://github.com/") {
    @Override
    public void onFinish(int statusCode, String responseText) {
        // Do something here
    }
}.send();
```

Advanced Usage:
```java
/*
    Block MainUI Thread with
        ProgressDialog.show(Context context, CharSequence title, CharSequence message, bool indeterminate)
*/
ProgressDialog pd = ProgressDialog.show(getActivity(), null, "Loading...", true);
```

But... how do you dismiss the dialog when the request is finish?

```java
new HttpPostRequest("https://github.com/", pd) {
    @Override
    public void onFinish(int statusCode, String responseText) {
        ((ProgressDialog) this.mixed[0]).dismiss();  // dismiss the dialog, releasing the block in MainUI Thread
    }
}.send();
```
