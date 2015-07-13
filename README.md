# Errors
Error handling library. Based on Haskell's Either, but with error semantics

## Usage

### Creating void and void errors

```java
ErrorOrVoid<ErrorType> myFunction() {
    if (errorCondition()) {
        final ErrorType error = ...
        return Errors.forVoidError(error);
    }
    return Errors.forVoid();
}
```

### Creating values and value errors

```java
ErrorOrValue<ErrorType, Integer> myFunction() {
    if (errorCondition()) {
        final ErrorType error = ...
        return Errors.forValueError(error);
    }
    return Errors.forValue(3);
}
````

### Using ErrorOrVoid

Lets say we have `myFunction` defined like this

    ErrorOrVoid<ErrorType> myFunction() {...}

Then we can use the return value of that function in any of these ways

```java
myFunction().ifError(e -> System.out.println(e)).otherwise(() -> System.out.println("No error!");
```

```java
myFunction().ifError(e -> System.out.println(e)).otherwiseDoNothing();
```

```java
int numErrors = myFunction().ifErrorReturn(e -> 1).otherwise(0);
```

```java
MyObject myObj = myFunction().ifErrorReturn(e -> e.transform()).otherwiseReturnNull();
```

### Using ErrorOrVoid

Lets say we have `myFunction` defined like this

    ErrorOrValue<ErrorType, Integer> myFunction() {...}

Then we can use the return value of that function in any of these ways

```java
myFunction().ifError(e -> System.out.println("Error " + e)).otherwise(v -> System.out.println("Returned value " + v);
```

```java
myFunction().ifError(e -> System.out.println(e)).otherwiseDoNothing();
```

```java
int numErrors = myFunction().ifErrorReturn(e -> 1).otherwise(v -> 0);
```

```java
MyObject myObj = myFunction().ifErrorReturn(e -> e.transform()).otherwiseReturnNull();
```

In both cases `ifNotError` can be used.
