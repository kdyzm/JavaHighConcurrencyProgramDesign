# 线程阻塞工具类：LockSupport

1. LockSupport类是一个十分实用的线程阻塞工具，它可以在线程任意位置让线程阻塞。和Thread.suspend相比，它弥补了由于resume在前发生，导致线程无法继续执行的情况。和wait方法相比，他不需要先获取某个对象的锁，也不会抛出InterruptedException异常。  
2. LockSupport类使用类似信号量的机制。  
3. 即使unpark方法发生在park方法之前，它也可以使下一次的park操作立即返回。
4.处于park挂起状态的线程不会像suspend方法一样给出一个令人费解的Runnable的状态，它会给出一个非常明确的WAITing状态，甚至还会注明是park方法引起的。
5.LockSupport方法支持中断影响，但是它不会抛出中断异常，他只会默默返回，但是可以通过判断中断标志位获取中断标记。
