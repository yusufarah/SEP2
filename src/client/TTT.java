package client;

import common.Department;
import common.User;

public class TTT {
    public static void main(String[] args) {
        Controller c = new Controller();
        User u = new User("ABSDASDASDASD", "Asdsa12345678", "1213421213", "Admin");
//        User n = new User("James", "Asd12345678", "1212121213", "Admin");
//
//
//        User j = new User("Jess", "12345678A", "1029384756", "Admin");
//        User k = new User("Robert", "12345678A", "0987654322", "Admin");
//
//        User t = new User("Check", "123456789A", "1213141517", "Manager");
//        User tt = new User("Check12", "123456789A", "1213141517", "Admin");
//
////         c.createUser(tt);
//
        c.createUser(u);
////        c.editUser(j);
//
//        // c.editUser(tt);
//         c.removeUser(tt);
//        Department d = new Department("D001", "Freezer0", "London", "0123456789", "0123456789");
//        Department d2 = new Department("D002", "Freezer0", "London", "1123456789", "1234567890");
//////
////        c.createDepartment(d);
////        c.createDepartment(d2);
//        Department d3 = new Department("D003", "Jelly", "Paris", "1123456789", "1234567890");
////        c.editDepartment(d3, d2);
//        Department d4 = new Department("D003", null, null, null, null);
//        c.createDepartment(d2);
//        c.createDepartment(d3);
//        c.createDepartment(d4);
        c.getAllDepartments();
////        c.viewDepartment(d4);
//        c.deleteDepartment(d4);
    }
}
