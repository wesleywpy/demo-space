scala中的集合分为两种，一种是可变的集合，另一种是不可变的集合。
* mutable 可变集合，修改，添加、删除、修改元素将操作原集合
* immutable 不可变集合，改变、添加、删除元素将返回一个新的集合，老集合不变

默认使用的都是immutable集合，如果要使用mutable集合，需要在程序中引入

### Option,None,Some类型
None、Some是Option的子类，它主要解决值为null的问题

### Tuple
元组可以是不同类型值的聚集