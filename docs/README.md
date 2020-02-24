# User Guide for Jarvis

## Features 

### Feature 1. Add a task

## Usage

### `todo` - Adds a todo task

A todo type of task will be added to the current task list.

Format: `todo [description]`

Example of usage:`todo read book`
> - Adds a todo task described as "read book" to the list


Expected outcome:

<img src ="images/todo_example.png" width= "250">

&nbsp;

### `deadline` - Adds a deadline task

A deadline type of task will be added to the current task list.

Format:
 `deadline [DESCRIPTION] /by [DUE DATE & TIME]`

> - Adds a deadline and its' due date to the list

> - **/by** signals start of `[DUE DATE & TIME]`

> - Accepted `[DUE DATE & TIME]` format:
>  + Year-Month-Day -- 24HR Time 
>     + e.g. **2020-03-02 1800** 
>   
>  + Any String value
>     + e.g. **June 6th** 

Example of usage:
`deadline project submission /by 2020-03-02 1200`


Expected outcome:

<img src ="images/deadline_example.png" width= "350">

Example of usage:
`deadline return book /by June 6th`

Expected outcome:

<img src ="images/deadline_example1.png" width= "350">

&nbsp;

### `event` - Adds a event task

An event type of task will be added to the current task list.

Format:
 `event [DESCRIPTION] /at [EVENT TIME & VENUE]`

> - Adds an event and its' event time and/or place to the list

> - **/at** signals start of `[EVENT TIME & VENUE]`

> - Accepted `[EVENT TIME & VENUE]` format:
>  + Year-Month-Day -- 24HR Time 
>     + e.g. **2020-03-02 1800** 
>   
>  + Any String value
>     + e.g. **at Julia's house tmr** 

Example of usage:
`event project meeting /at 2020-03-02 1200`


Expected outcome:

<img src ="images/event_example.png" width= "350">

Example of usage:
`event party /at Julia's house tmr`

Expected outcome:

<img src ="images/event_example1.png" width= "350">
