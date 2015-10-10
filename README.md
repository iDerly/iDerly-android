# iDerly-android

#### How to use HttpPostRequest(String url[, Object mixed...])

Package:
```java
package com.iderly.control.HttpPostRequest;
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


