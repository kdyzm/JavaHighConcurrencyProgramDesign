# Unsafe类

Unsafe类不能直接使用，会报出异常：
```
Exception in thread "main" java.lang.SecurityException: Unsafe
	at sun.misc.Unsafe.getUnsafe(Unsafe.java:90)
```
实际上Unsafe类的getUnsfae方法检查了调用类的ClassLoader，如果不为null就抛出异常，这就意味着用户的应用中无法调用该方法。