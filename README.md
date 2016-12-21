# Cloud Design Patterns
This repository contains the implementation of select cloud patterns. It is intended to provide a clear understanding of the workings of the Cloud Design Patterns.

- Priority Queue Pattern:
Messages created by an application are added to a message queue. Each message is assigned a priority. Messages with higher priority are executed first.

- Retry Pattern:
Whenever a request fails, the application waits for a certain interval of time and then sends the request again. Subsequent failures cause the wait time to increase. This is repeated until a certain number of retries are done or the request executes successfully.

- Valet Key Pattern:
In order to access resources directly, the application is provided with a key or token which is referred to as a valet key. Using this key the application can directly access resources for a certain period of time.

- External Configuration Store Pattern:
The configuration files related to an application are stored in an external storage and an interface is provided to read and update the configuration settings.

- Competing Consumers Pattern:
Incoming tasks are placed in a queue and are assigned to the available consumers in the application.

- Gatekeeper Pattern:
A Gatekeeper entity is used as a middle-man to handle all requests between clients and the resources.

- Health Endpoint Monitoring Pattern:
A service keeps track of all other services/endpoints and monitors their state to see if they are working or have some fault.

- Queue-Based Load Levelling Pattern:
A queue buffers messages which are coming at an irregular rate and transfers them to a consumer which processes them at a much more stable rate.