# ThreadLocal的实现原理

ThreadLocal是一个类似于HashMap的东西。更精确的说，它更加类似于WeakHashMap.

ThreadLocal实现了弱引用，弱引用是一种比强引用弱得多的引用。Java迅疾在垃圾回收时，如果发现弱引用，就会立即回收。
ThreadLocal内部由一系列Entry组成，每一个Entry都是WeakReference<ThreadLocal>