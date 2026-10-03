package hotelsystem;

import java.util.*;
import java.util.ArrayList;
import java.io.*;

// ----------------- USER -----------------
public abstract class User {
    private int userId;
    private String name;
    private String userName;
    private String password;
    private String email;
    private String phone;

    public User(int userId, String name, String userName, String password, String email, String phone) {
        setUserId(userId);
        setName(name);
        setUserName(userName);
        setPassword(password);
        setEmail(email);
        setPhone(phone);
    }

    public int getUserId() { return userId; }
    public String getName() { return name; }
    public String getUserName() { return userName; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }

    public void setUserId(int userId) {
        if (userId > 0) this.userId = userId;
        else System.out.println("User ID must be positive.");
    }

    public void setName(String name) {
        if (name != null && name.length() >= 2) this.name = name;
        else System.out.println("Name must be at least 2 characters.");
    }

    public void setUserName(String userName) {
        if (userName != null && !userName.isEmpty()) this.userName = userName;
        else System.out.println("Username cannot be empty.");
    }

    public void setPassword(String password) {
        if (password != null && password.length() >= 8) this.password = password;
        else System.out.println("Password must contain at least 8 characters.");
    }

    public void setEmail(String email) {
        if (email != null && email.contains("@")) this.email = email;
        else System.out.println("Invalid email format.");
    }

    public void setPhone(String phone) {
        if (phone != null && phone.matches("\\d{8,15}")) this.phone = phone;
        else System.out.println("Phone number must be 8-15 digits.");
    }

    public abstract void showMenu(HotelSystem system);

    @Override
    public String toString() {
        return "User [ID=" + userId + ", Name=" + name + ", Username=" + userName +
               ", Email=" + email + ", Phone=" + phone + "]";
    }
}

// ----------------- ROOM -----------------
class Room {
    private int roomId;
    private String type;
    private int capacity;
    private double nightlyRate;
    private String status;

    public Room(int roomId, String type, int capacity, double nightlyRate, String status) {
        this.roomId = roomId;
        this.type = type;
        this.capacity = capacity;
        this.nightlyRate = nightlyRate;
        this.status = status;
    }

    public int getRoomId() { return roomId; }
    public String getType() { return type; }
    public int getCapacity() { return capacity; }
    public double getNightlyRate() { return nightlyRate; }
    public String getStatus() { return status; }
    public void setNightlyRate(double nightlyRate) { this.nightlyRate = nightlyRate; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "Room [ID=" + roomId + ", Type=" + type + ", Capacity=" + capacity +
               ", Rate=" + nightlyRate + ", Status=" + status + "]";
    }
}

// ----------------- BOOKING -----------------
class Booking {
    private int bookingId;
    private User guest;
    private Room room;
    private String checkInDate;
    private String checkOutDate;

    public Booking(int bookingId, User guest, Room room, String checkInDate, String checkOutDate) {
        this.bookingId = bookingId;
        this.guest = guest;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public int getBookingId() { return bookingId; }
    public User getGuest() { return guest; }
    public Room getRoom() { return room; }
    public String getCheckInDate() { return checkInDate; }
    public String getCheckOutDate() { return checkOutDate; }
    public void setRoom(Room room) { this.room = room; }

    @Override
    public String toString() {
        return "Booking [ID=" + bookingId + ", Guest=" + guest.getName() +
               ", Room=" + room.getRoomId() + ", CheckIn=" + checkInDate +
               ", CheckOut=" + checkOutDate + "]";
    }
}

// ----------------- HOTEL SYSTEM -----------------
class HotelSystem {
    private List<User> users = new ArrayList<>();
    private List<Room> rooms = new ArrayList<>();
    private List<Booking> bookings = new ArrayList<>();

    public void addUser(User u) { users.add(u); }
    public void addRoom(Room r) { rooms.add(r); }
    public void addBooking(Booking b) { bookings.add(b); }
    public List<User> getUsers() { return users; }
    public List<Room> getRooms() { return rooms; }
    public List<Booking> getBookings() { return bookings; }

    public void bookRoomForGuest(User guest, Room room, String in, String out) {
        if (room.getStatus().equalsIgnoreCase("Occupied")
                || room.getStatus().equalsIgnoreCase("Booked")) {
            System.out.println("Room not available.");
            return;
        }
        int bid = bookings.size() + 1;
        Booking b = new Booking(bid, guest, room, in, out);
        bookings.add(b);
        room.setStatus("Booked");
        System.out.println("Booked room " + room.getRoomId());
    }

    // ---------------- SAVE & LOAD ----------------
    public void saveUsers() throws Exception {
        FileWriter out = new FileWriter("users.txt");
        for (User u : users) {
            if (u instanceof Staff s) {
                out.write("staff," + s.getUserId() + "," + s.getName() + "," +
                        s.getUserName() + "," + s.getPassword() + "," +
                        s.getEmail() + "," + s.getPhone() + "," +
                        s.getStaffId() + "," + s.getPosition() + "\n");
            } else if (u instanceof Guest) {
                out.write("guest," + u.getUserId() + "," + u.getName() + "," +
                        u.getUserName() + "," + u.getPassword() + "," +
                        u.getEmail() + "," + u.getPhone() + "\n");
            } else if (u instanceof Admin) {
                out.write("admin," + u.getUserId() + "," + u.getName() + "," +
                        u.getUserName() + "," + u.getPassword() + "," +
                        u.getEmail() + "," + u.getPhone() + "\n");
            }
        }
        out.close();
    }

    public void saveRooms() throws Exception {
        FileWriter out = new FileWriter("rooms.txt");
        for (Room r : rooms) {
            out.write(r.getRoomId() + "," + r.getType() + "," +
                    r.getCapacity() + "," + r.getNightlyRate() + "," +
                    r.getStatus() + "\n");
        }
        out.close();
    }

    public void saveBookings() throws Exception {
        FileWriter out = new FileWriter("bookings.txt");
        for (Booking b : bookings) {
            out.write(b.getBookingId() + "," +
                    b.getGuest().getUserId() + "," +
                    b.getRoom().getRoomId() + "," +
                    b.getCheckInDate() + "," +
                    b.getCheckOutDate() + "\n");
        }
        out.close();
    }

    public void loadUsers() throws Exception {
        File f = new File("users.txt");
        if (!f.exists()) return;
        Scanner sc = new Scanner(f);
        while (sc.hasNextLine()) {
            String[] p = sc.nextLine().split(",");
            switch (p[0]) {
                case "admin" -> addUser(new Admin(p[3], p[2], p[4], p[5],
                        Integer.parseInt(p[1]), p[6]));
                case "guest" -> addUser(new Guest(
                        Integer.parseInt(p[1]), p[2], p[3], p[4], p[5], p[6]));
                case "staff" -> addUser(new Staff(
                        Integer.parseInt(p[1]), p[2], p[3], p[4],
                        p[5], p[6], p[7], p[8]));
            }
        }
    }

    public void loadRooms() throws Exception {
        File f = new File("rooms.txt");
        if (!f.exists()) return;
        Scanner sc = new Scanner(f);
        while (sc.hasNextLine()) {
            String[] p = sc.nextLine().split(",");
            addRoom(new Room(
                    Integer.parseInt(p[0]),
                    p[1],
                    Integer.parseInt(p[2]),
                    Double.parseDouble(p[3]),
                    p[4]
            ));
        }
    }

    public void loadBookings() throws Exception {
        File f = new File("bookings.txt");
        if (!f.exists()) return;
        Scanner sc = new Scanner(f);
        while (sc.hasNextLine()) {
            String[] p = sc.nextLine().split(",");
            int bid = Integer.parseInt(p[0]);
            int uid = Integer.parseInt(p[1]);
            int rid = Integer.parseInt(p[2]);
            User u = null;
            Room r = null;
            for (User x : users)
                if (x.getUserId() == uid) u = x;
            for (Room x : rooms)
                if (x.getRoomId() == rid) r = x;
            if (u != null && r != null) {
                addBooking(new Booking(bid, u, r, p[3], p[4]));
            }
        }
    }
}

// ---------------- STAFF ----------------
class Staff extends User {
    private String staffId;
    private String position;

    public Staff(int userId, String name, String userName, String password,
                 String email, String phone, String staffId, String position) {
        super(userId, name, userName, password, email, phone);
        this.staffId = staffId;
        this.position = position;
    }

    public String getStaffId() { return staffId; }
    public String getPosition() { return position; }

    public void viewGuestBookings(List<Booking> bookings) {
        System.out.println("Guest Bookings:");
        for (Booking b : bookings) System.out.println(b);
    }

    public void modifyRoomAvailability(Room room, boolean available) {
        room.setStatus(available ? "Available" : "Occupied");
    }

    public void updateRoomPrice(Room room, double newPrice) {
        room.setNightlyRate(newPrice);
    }

    public Room findRoomById(HotelSystem system, int roomId) {
        for (Room r : system.getRooms()) if (r.getRoomId() == roomId) return r;
        return null;
    }

    public Booking findBookingById(HotelSystem system, int bookingId) {
        for (Booking b : system.getBookings()) if (b.getBookingId() == bookingId) return b;
        return null;
    }

    public void reassignRoom(Booking booking, Room newRoom) {
        booking.setRoom(newRoom);
    }

    @Override
    public void showMenu(HotelSystem system) {
        Scanner input = new Scanner(System.in);
        int choice = 0;
        while (true) {
            System.out.println("\nStaff Menu:");
            System.out.println("1. View Guest Bookings");
            System.out.println("2. Modify Room Availability");
            System.out.println("3. Update Room Price");
            System.out.println("4. Reassign Room");
            System.out.println("0. Logout");
            System.out.print("Enter choice: ");
            choice = input.nextInt();
            if (choice == 0) break;
            switch (choice) {
                case 1:
                    viewGuestBookings(system.getBookings());
                    break;
                case 2:
                    System.out.print("Room ID: ");
                    int rid = input.nextInt();
                    Room r = findRoomById(system, rid);
                    if (r == null) { System.out.println("Room not found."); break; }
                    System.out.print("1 Available, 0 Occupied: ");
                    modifyRoomAvailability(r, input.nextInt() == 1);
                    System.out.println("Updated.");
                    break;
                case 3:
                    System.out.print("Room ID: ");
                    int roomId = input.nextInt();
                    Room priceRoom = findRoomById(system, roomId);
                    if (priceRoom == null) { System.out.println("Room not found."); break; }
                    System.out.print("New price: ");
                    updateRoomPrice(priceRoom, input.nextDouble());
                    System.out.println("Updated.");
                    break;
                case 4:
                    System.out.print("Booking ID: ");
                    int bid = input.nextInt();
                    Booking b = findBookingById(system, bid);
                    if (b == null) { System.out.println("Booking not found."); break; }
                    System.out.print("New Room ID: ");
                    Room newRoom = findRoomById(system, input.nextInt());
                    if (newRoom == null) { System.out.println("Room not found."); break; }
                    reassignRoom(b, newRoom);
                    System.out.println("Room reassigned.");
                    break;
                default:
                    System.out.println("Invalid.");
            }
        }
    }

    @Override
    public String toString() {
        return "Staff [StaffID=" + staffId + ", Position=" + position +
               ", UserID=" + getUserId() + ", Name=" + getName() + "]";
    }
}

// ----------------- ADMIN -----------------
class Admin extends User {
    public Admin(String userName, String name, String password, String email, int userId, String phone) {
        super(userId, name, userName, password, email, phone);
    }

    @Override
    public void showMenu(HotelSystem system) {
        Scanner input = new Scanner(System.in);
        int choice = 0;
        while (true) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. View Rooms");
            System.out.println("2. Add Room");
            System.out.println("3. Remove Room");
            System.out.println("4. View Users");
            System.out.println("5. Add User");
            System.out.println("6. Remove User");
            System.out.println("0. Logout");
            System.out.print("Enter choice: ");
            choice = input.nextInt();
            if (choice == 0) break;
            switch (choice) {
                case 1:
                    for (Room r : system.getRooms()) System.out.println(r);
                    break;
                case 2:
                    System.out.print("Enter Room ID: ");
                    int id = input.nextInt();
                    System.out.print("Enter Type: ");
                    String type = input.next();
                    System.out.print("Enter Capacity: ");
                    int cap = input.nextInt();
                    System.out.print("Enter Rate: ");
                    double rate = input.nextDouble();
                    system.addRoom(new Room(id, type, cap, rate, "Available"));
                    System.out.println("Room added.");
                    break;
                case 3:
                    System.out.print("Enter Room ID: ");
                    int rid = input.nextInt();
                    boolean removed = system.getRooms().removeIf(r -> r.getRoomId() == rid);
                    System.out.println(removed ? "Room removed." : "Room not found.");
                    break;
                case 4:
                    for (User u : system.getUsers()) System.out.println(u);
                    break;
                case 5:
                    addUser(system);
                    break;
                case 6:
                    System.out.print("User ID: ");
                    removeUser(system, input.nextInt());
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    public void addUser(HotelSystem system) {
        Scanner input = new Scanner(System.in);
        System.out.println("1. Guest");
        System.out.println("2. Staff");
        int type = input.nextInt();
        System.out.print("User ID: ");
        int id = input.nextInt();
        System.out.print("Name: ");
        String name = input.next();
        System.out.print("Username: ");
        String username = input.next();
        System.out.print("Password: ");
        String password = input.next();
        System.out.print("Email: ");
        String email = input.next();
        System.out.print("Phone: ");
        String phone = input.next();
        if (type == 1) {
            system.addUser(new Guest(id, name, username, password, email, phone));
            System.out.println("Guest added.");
        } else if (type == 2) {
            System.out.print("Staff ID: ");
            String staffId = input.next();
            System.out.print("Position: ");
            String position = input.next();
            system.addUser(new Staff(id, name, username, password, email, phone, staffId, position));
            System.out.println("Staff added.");
        }
    }

    public void removeUser(HotelSystem system, int userId) {
        boolean removed = system.getUsers().removeIf(u -> u.getUserId() == userId);
        System.out.println(removed ? "User removed." : "User not found.");
    }
}

// ----------------- GUEST -----------------
class Guest extends User {
    public Guest(int userId, String name, String userName, String password, String email, String phone) {
        super(userId, name, userName, password, email, phone);
    }

    public void viewBookingHistory(HotelSystem system) {
        boolean found = false;
        for (Booking b : system.getBookings()) {
            if (b.getGuest().getUserId() == getUserId()) {
                System.out.println(b);
                found = true;
            }
        }
        if (!found) System.out.println("No bookings found.");
    }

    public void updatePersonalInfo() {
        Scanner input = new Scanner(System.in);
        System.out.print("New email: ");
        setEmail(input.nextLine());
        System.out.print("New phone: ");
        setPhone(input.nextLine());
        System.out.println("Updated.");
    }

    public void viewRoomStatus(List<Room> rooms) {
        for (Room r : rooms) {
            System.out.println(r);
        }
    }

    public void bookRoom(HotelSystem system, Room room, String in, String out) {
        system.bookRoomForGuest(this, room, in, out);
    }

    public void cancelBooking(HotelSystem system, int bookingId) {
        Booking remove = null;
        for (Booking b : system.getBookings()) {
            if (b.getBookingId() == bookingId && b.getGuest().getUserId() == getUserId()) {
                remove = b;
                break;
            }
        }
        if (remove != null) {
            system.getBookings().remove(remove);
            System.out.println("Canceled.");
        } else {
            System.out.println("Not found.");
        }
    }

    @Override
    public void showMenu(HotelSystem system) {
        Scanner input = new Scanner(System.in);
        int choice = 0;
        while (true) {
            System.out.println("\nGuest Menu:");
            System.out.println("1. View Rooms");
            System.out.println("2. Book Room");
            System.out.println("3. View Booking History");
            System.out.println("4. Cancel Booking");
            System.out.println("5. Update Info");
            System.out.println("0. Logout");
            System.out.print("Choice: ");
            choice = input.nextInt();
            if (choice == 0) break;
            switch (choice) {
                case 1:
                    viewRoomStatus(system.getRooms());
                    break;
                case 2:
                    System.out.print("Room ID: ");
                    int rid = input.nextInt();
                    Room selected = null;
                    for (Room r : system.getRooms())
                        if (r.getRoomId() == rid) selected = r;
                    if (selected == null) {
                        System.out.println("Room not found.");
                        break;
                    }
                    System.out.print("Check-in: ");
                    String in = input.next();
                    System.out.print("Check-out: ");
                    String out = input.next();
                    bookRoom(system, selected, in, out);
                    break;
                case 3:
                    viewBookingHistory(system);
                    break;
                case 4:
                    System.out.print("Booking ID: ");
                    cancelBooking(system, input.nextInt());
                    break;
                case 5:
                    updatePersonalInfo();
                    break;
            }
        }
    }
}

// ----------------- MAIN -----------------
class Project {
    public static User login(HotelSystem system, String username, String password) {
        for (User u : system.getUsers()) {
            if (u.getUserName().equals(username) &&
                    u.getPassword().equals(password)) {
                return u;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        HotelSystem system = new HotelSystem();
        try {
            system.loadUsers();
            system.loadRooms();
            system.loadBookings();
        } catch (Exception e) {
            System.out.println("Loading error.");
        }
        int choice;
        while (true) {
            System.out.println("\n********* HOTEL BOOKING SYSTEM ********");
            System.out.println("1. Login");
            System.out.println("0. Exit");
            System.out.print("Choose: ");
            choice = input.nextInt();
            if (choice == 0) {
                try {
                    system.saveUsers();
                    system.saveRooms();
                    system.saveBookings();
                } catch (Exception e) {
                    System.out.println("Saving error.");
                }
                System.out.println("Exiting...");
                break;
            }
            if (choice == 1) {
                System.out.print("Username: ");
                String username = input.next();
                System.out.print("Password: ");
                String password = input.next();
                User logged = login(system, username, password);
                if (logged == null) {
                    System.out.println("Incorrect username or password!");
                } else {
                    System.out.println("Welcome " + logged.getName());
                    logged.showMenu(system);
                }
            }
        }
    }
}
