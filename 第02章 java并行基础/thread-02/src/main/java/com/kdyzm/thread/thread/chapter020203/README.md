# 线程中断

1.stop方法会立即终止线程，并且会立即释放这个线程持有的锁，会导致一致性问题  
2.interrupted方法  
3.如何让Thread.sleep方法产生异常？