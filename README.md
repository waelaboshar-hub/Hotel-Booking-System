Hotel Booking System
A Java console-based hotel management and booking system that demonstrates object-oriented programming, role-based access, file persistence, room management, and booking operations.
Overview
The system supports three user roles:
- Admin — manages rooms and users
- Staff — manages room availability, pricing, bookings, and room reassignment
- Guest — views rooms, creates and cancels bookings, views booking history, and updates personal information
All users inherit from a common abstract User class, while the application stores users, rooms, and bookings in text files for persistence between runs.
Features
Authentication
- Login using username and password
- Role-specific menus after successful authentication
- Basic user input validation
Admin Features
- View all rooms
- Add rooms
- Remove rooms
- View all users
- Add guest or staff accounts
- Remove users
Staff Features
- View guest bookings
- Modify room availability
- Update room prices
- Reassign bookings to different rooms
Guest Features
- View room information and availability
- Book available rooms
- View personal booking history
- Cancel bookings
- Update email and phone information
Data Persistence
The application saves and loads data using:
- users.txt
- rooms.txt
- bookings.txt
This allows users, rooms, and bookings to persist between program runs.
Object-Oriented Design
The project demonstrates several core Java OOP concepts:
- Abstraction through the abstract User class
- Inheritance with Admin, Staff, and Guest
- Polymorphism through the overridden showMenu() method
- Encapsulation using private fields with getters and setters
- Composition through Booking objects containing User and Room references
Main Classes
Class	Responsibility
User	Abstract base class for all system users
Admin	Manages users and rooms
Staff	Handles operational room and booking management
Guest	Handles guest booking actions
Room	Stores room information such as type, capacity, rate, and status
Booking	Connects a guest with a room and booking dates
HotelSystem	Stores and manages users, rooms, bookings, and persistence
Project	Contains login logic and the main application entry point


User Validation
The base User class performs simple validation such as:
- User ID must be positive
- Name must contain at least 2 characters
- Username cannot be empty
- Password must contain at least 8 characters
- Email must contain @
- Phone number must contain 8–15 digits
Booking Workflow
A typical guest booking flow is:
1. Log in as a guest.
2. View available rooms.
3. Enter the desired Room ID.
4. Enter check-in and check-out dates.
5. The system creates a new booking.
6. The selected room status changes to Booked.
Rooms marked as Occupied or Booked cannot be booked again.
Running the Project
Requirements
- Java JDK 17+ recommended
- Any Java IDE or terminal
Compile
Because the source uses the package:
package hotelsystem;
place the file inside a matching package directory such as:
hotelsystem/HotelSystem.java
Then compile it with:
javac hotelsystem/HotelSystem.java
Note: The file currently contains a public User class, so Java may require the source to be reorganized into separate files or renamed to match the public class before compilation.

Run
The application entry point is the Project class:
java hotelsystem.Project
Main Menu
********* HOTEL BOOKING SYSTEM ********
1. Login
0. Exit
After login, the system displays a menu based on the user's role.
Example Project Structure
For a cleaner Java project, the classes can be separated as follows:
HotelBookingSystem/
├── src/
│   └── hotelsystem/
│       ├── User.java
│       ├── Admin.java
│       ├── Staff.java
│       ├── Guest.java
│       ├── Room.java
│       ├── Booking.java
│       ├── HotelSystem.java
│       └── Project.java
├── users.txt
├── rooms.txt
└── bookings.txt
Concepts Demonstrated
- Java Object-Oriented Programming
- Abstraction
- Inheritance
- Polymorphism
- Encapsulation
- ArrayList collections
- File I/O
- Authentication
- Role-based functionality
- CRUD-style operations
- Console application design
Possible Improvements
- Split each class into its own Java file
- Hash passwords instead of storing them in plain text
- Add stronger login and validation rules
- Prevent duplicate usernames and IDs
- Use LocalDate instead of storing dates as strings
- Check room availability by date range instead of only status
- Automatically make a room available after booking cancellation
- Add exception handling for invalid numeric input
- Use a database instead of text files
- Add unit tests
- Add a graphical or web interface
- Add booking prices, payments, and invoices
Security Note
This project is intended for educational and portfolio purposes. Passwords are currently stored in plain text, so the application should not be used as a production authentication system without stronger security controls.
License
This project is intended for educational and portfolio use. Add a license such as MIT if you plan to distribute or allow reuse of the project.
