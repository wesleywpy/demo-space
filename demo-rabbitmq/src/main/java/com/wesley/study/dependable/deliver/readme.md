### 生产者保证消息可靠投递

为了保证消息被正确投递到消息中间件，RabbitMQ提供了如下两个配置来保证消息投递的可靠性:

1.在发送消息的时候我们可以设置Mandatory属性。如果设置了Mandatory属性则当消息不能被正确路由到队列中去时将会触发Return Method，这样我们可以在Return Method中进行相关业务处理，如果Mandatory没有设置则当消息不能正确路由到队列中去的时候，Broker将会丢弃该消息

2.RabbitMQ还提供了消息确认机制(Publisher Confirm)。生产者将Channel设置成Confirm模式，当设置Confirm模式后所有在该信道上面发布的消息都会被指派一个唯一的ID(从1开始，ID在同个Channel范围是唯一的)，一旦消息被投递到所有匹配的队列之后Broker就会发送一个确认给生产者(包含消息的唯一ID)，这就使得生产者知道消息已经正确到达目的队列了

**mandatory**  
当mandatory标志位设置为true时，如果exchange根据自身类型和消息routeKey无法找到一个符合条件的queue，那么会调用basic.return方法将消息返回给生产者（Basic.Return + Content_Header + Content_Body）；当mandatory设置为false时，出现上述情形broker会直接将消息扔掉。