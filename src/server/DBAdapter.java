package server;

import common.Department;
import common.User;

import java.util.ArrayList;

public class DBAdapter implements IDBAdapter {
    //TODO if set is not found return empty array

    public DBAdapter() {

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

    @Override
    public void createDepartment(Department department) {
        String sql = "INSERT INTO department VALUES ('" + department.getdNumber() + "','" + department.getdName() + "','" + department.getdLocation() + "','" + department.getdManager() + "','" + department.getdEmployees() + "');";
        DBHandler.executeStatements(sql);

    }

    @Override
    public void editDepartment(Department department, Department oldDepartment) {
        String sql = "Update department set dno = " + department.getdNumber() + ", dname =" + department.getdName() + ",dlocation = " + department.getdLocation() + ",dmanager =" + department.getdManager() +
                "where dno = " + oldDepartment.getdNumber() + ";";
        DBHandler.executeStatements(sql);
    }

    @Override
    public Department viewDepartment() {
        return null;
    }

    @Override
    public void deleteDepartment(Department department) {

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
}

