# volatile与JMM

1. volatile不能替代锁，也无法保证一些复合操作的原子性
2. volatile能够保证数据的可见性和有序性:JIT在server模式下，会观察不到普通变量的变化，需要使用volatile进行修饰