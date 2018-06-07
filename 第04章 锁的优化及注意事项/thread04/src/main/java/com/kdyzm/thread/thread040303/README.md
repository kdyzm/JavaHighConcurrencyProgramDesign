# ThreadLocal的使用对性能有何影响

使用多线程产生随机数的方法测试使用ThreadLocal和不使用ThreadLocal的情况下产生相同数量的随机数需要的时间：  
结果表明，使用ThreadLocal用时更少，效率更高，但是，很明显，ThreadLocal是使用空间换时间的一种做法。  
运行结果：  
```
多线程访问同一个Random示例：163ms
使用ThreadLocal包装的Random示例：：44ms

```