# Condition条件：重入锁的好搭档 

 1. 注意condition需要使用方法await，不是Object类中的wait方法实现线程等待  
 2. `ArrayBlockingQueue`使用重入锁实现  