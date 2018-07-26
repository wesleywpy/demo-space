**服务端步骤**
1. 服务端监听一个队列，监听客户端发送过来的消息
2. 收到消息之后调用RPC服务得到调用结果
3. 从消息属性中获取reply_to，correlation_id属性，把调用结果发送给reply_to指定的队列，发送的消息属性要带上correlation_id

**客户端步骤**
1. 监听reply_to队列
2. 发送消息，消息属性需要带上reply_to,correlation_id
3. 服务端处理完之后reply_to对应的队列就会收到异步处理结果消息
4. 收到消息之后进行处理，根据消息的correlation_id找到对应的请求