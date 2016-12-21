# Competing Consumers Pattern
The Competing Consumers Pattern separates tasks to various consumers present in the application. Tasks are assigned to a consumer and the consumer processes the task. If a task arrives, it is assigned to a free consumer. If all consumers are busy then the tasks are placed in a queue.
The application can be run by executing the CompetingConsumersTest.java file.