# Queue-Based Load Levelling Pattern
The Queue-Based Load Levelling Pattern allows an application to handle any number of messages that is thrown its way by implementing a queue. This queue will buffer the messages and pass them on to a consumer and the consumer will process these messages.
The application can be run by executing QueueLoadLevellingTest.java