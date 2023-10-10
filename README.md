# ToDoList
A application that helps to strcuture your week.

It follows the principles of the MVC-Model.
Additionally the classes use a callback whenever events occur.
This helps to make the classes more independent of each other.

The Software follows an hierarchical order.

The Controller holds references to model and view and handles the exchange of information between those two.
It is at the top of the hierarchy.

Model holds the necessary data and manipulates it.
The data comes from an xml-file.
An Xml-File can be loaded and the changes can be saved.

View istelf shows the data and handles the users input.
View has a TaskView, to show the user all loaded tasks.
And it has a CalendarView, to show the user when a task is due.
The user can add, edit and delete tasks, aswell he can organize tasks in different groups.
