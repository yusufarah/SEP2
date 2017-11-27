package server;

import common.Department;
import common.User;
import common.WorkingSchedule;

import java.util.ArrayList;
import java.util.Calendar;

public class DBAdapter implements IDBAdapter {
    private Calendar calendar;
    //TODO if set is not found return empty array

    public DBAdapter() {
        calendar = Calendar.getInstance();
    }

    /**
     * @param username
     * @return true if username exists.
     */
    @Override
    public boolean checkUsername(String username) {
        ArrayList temp = DBHandler.getResultSet("SELECT username from UserLogIn where username='" + username + "'; ");
        return temp.size() >= 1;
    }

    /**
     * @param username
     * @return user after checkUsername returns true to user.
     */
    @Override
    public String getUserPassword(String username) {
        if (checkUsername(username)) {
            String sql = "SELECT password from UserLogIn WHERE username = '" + username + "';";
            ArrayList temp = DBHandler.getResultSet(sql);
            return (String) temp.get(0);
        }
        return null;
    }

    public void changeUserInformation(User user) {
        DBHandler.executeStatements("update Employee set passEmp = '" + user.getPassword() +
                "', firstName = '" + user.getFirstName() +
                "', secondName = '" + user.getSecondName() +
                "', familyname = '" + user.getLastName() +
                "', dateOfBirth = '" + user.getDob() +
                "', address = '" + user.getAddress() +
                "', postcode = '" + user.getPostcode() +
                "', city = '" + user.getCity() +
                "', mobile = '" + user.getMobile() +
                "', landline = '" + user.getLandline() +
                "', email = '" + user.getEmail() +
                "', konto = '" + user.getKonto() +
                "', regnumber = '" + user.getRecnumber() +
                "', licencePlate = '" + user.getLicencePlate() +
                "', preferredCommunication = '" + user.getPrefferedCommunication() +
                "', moreInfo = '" + user.getMoreInfo() + "' where cpr = '" + user.getCpr() + "';");

    }

    @Override
    public void createDepartment(Department department) {
        String sql = "INSERT INTO department VALUES ('" + department.getdNumber() + "','" + department.getdName() + "','" + department.getdLocation() + "','" + department.getdManager() + "','" + department.getdEmployees() + "');";
        DBHandler.executeStatements(sql);

    }

    @Override
    public void editDepartment(Department department, Department oldDepartment) {
        String sql = "Update department set dno ='" + department.getdNumber() + "', dname ='" + department.getdName() + "',dlocation = '" + department.getdLocation() + "',dmanager ='" + department.getdManager() +
                "' where dno = '" + oldDepartment.getdNumber() + "';";
        DBHandler.executeStatements(sql);
    }

    @Override
    public Department viewDepartment(Department department) {
        String sql = "Select * from department where dno = '" + department.getdNumber().toUpperCase() + "';";
        ArrayList temp = DBHandler.getSingleRow(sql);
        Department d = new Department((String) temp.get(0), (String) temp.get(1), (String) temp.get(2), (String) temp.get(3), (String) temp.get(4));
        return d;
    }

    @Override
    public void deleteDepartment(Department department) {
        String sql = " Delete from department where dno='" + department.getdNumber() + "';";
        DBHandler.executeStatements(sql);
    }

    private ArrayList getUserByCPR(String CPR) {
        String sql = "SELECT * from UserLogIn WHERE CPR = '" + CPR + "';";
        ArrayList temp = DBHandler.getSingleRow(sql);
        return temp;
    }

    @Override
    public void createAccount(User user) {
        DBHandler.executeStatements("INSERT INTO UserLogIn VALUES (" +
                "'" + user.getUsername() + "'," +
                "'" + user.getCpr() + "'," +
                "'" + user.getPassword() + "'," +
                "'" + user.getUserRole() + "'" +
                ")");
    }

    @Override
    public void removeAccount(User user) {
        DBHandler.executeStatements("DELETE FROM userlogin WHERE cpr = '" + user.getCpr() + "';");
    }

    public void editAccount(User user) {
        DBHandler.executeStatements("update userlogin set username = '" + user.getUsername() + "', cpr = '" + user.getCpr() + "', pass = '" + user.getPassword() + "', userRole = '" + user.getUserRole() + "' where cpr = '" + user.getCpr() + "';");
    }

    @Override
    public ArrayList<Department> getAllDepartments() {
        System.out.println("I am hereeee");
        String sql = "Select * from department;";
        ArrayList<String[]> temp = DBHandler.getAllRows(sql);
        ArrayList<Department> departments = new ArrayList<>();
        for (String[] item : temp) {
            Department department = new Department(item[0], item[1], item[2], item[3], item[4]);
            departments.add(department);
        }
        System.out.println(departments);
        return departments;
    }

    @Override
    public ArrayList<WorkingSchedule> workingSchedulePerWeek(User user) {
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        String firstDayOfWeek = calendar.get(calendar.DATE) + "/" + (calendar.get(calendar.MONTH) + 1) + "/" + calendar.get(calendar.YEAR);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        String lastDayOfWeek = calendar.get(calendar.DATE) + "/" + (calendar.get(calendar.MONTH) + 1) + "/" + calendar.get(calendar.YEAR);
        String sql = "SELECT * FROM workingschedule WHERE employecpr = '" + user.getCpr() + "' AND workingday >='" + firstDayOfWeek + "' AND workingday <= '" + lastDayOfWeek + "';";
        System.out.println(sql);
        ArrayList<String[]> temp = DBHandler.getAllRows(sql);
        ArrayList<WorkingSchedule> workingSchedules = new ArrayList<>();
        for (String[] item : temp) {
            WorkingSchedule workingSchedule = new WorkingSchedule(item[1], item[2], item[3], item[4], item[5]);
            System.out.println(workingSchedule.toString());
            workingSchedules.add(workingSchedule);
        }
        System.out.println(firstDayOfWeek + "    " + lastDayOfWeek);
        System.out.println(workingSchedules);
        return workingSchedules;
    }

//    private void getToday() {
//        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
//        String firstDayOfWeek = calendar.get(calendar.DATE) + "/" + (calendar.get(calendar.MONTH) + 1) + "/" + calendar.get(calendar.YEAR);
//        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
//        String lastDayOfWeek = calendar.get(calendar.DATE) + "/" + (calendar.get(calendar.MONTH) + 1) + "/" + calendar.get(calendar.YEAR);
//        String sql = "SELECT *\n" +
//                "FROM workingschedule\n" +
//                "WHERE employecpr = '1234567890' AND workingday >='" + firstDayOfWeek + "' AND workingday <= '" + lastDayOfWeek + "';";
//        ArrayList<String[]> temp = DBHandler.getAllRows(sql);
//        ArrayList<WorkingSchedule> workingSchedules = new ArrayList<>();
//        for (String[] item : temp) {
//            WorkingSchedule workingSchedule = new WorkingSchedule(item[1], item[2], item[3], item[4], item[5]);
//            workingSchedules.add(workingSchedule);
//        }
//        System.out.println(workingSchedules);
////        return workingSchedules;
//    }
//
//    public static void main(String[] args) {
//        new DBAdapter().getToday();
//    }
}

