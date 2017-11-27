package server;


import common.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Connection implements Runnable, OurObserver {
    private Server server;
    private ObjectInputStream inFromClient;
    private ObjectOutputStream outToClient;
    private IDBAdapter adapter;

    @Override

    /**
     * Run method from runnable interface.
     *
     * Reads object and returns username  //ToDO -- unfinished.
     */
    public void run() {
        while (true) {
            Request s;
            try {
                Object temp = inFromClient.readObject();
                s = (Request) temp;
                String requestText = s.getType();
                if (requestText.equalsIgnoreCase("createAccount")) {
                    System.out.println(s.getRequestObject());
                    adapter.createAccount((User) s.getRequestObject());
                } else if (requestText.equalsIgnoreCase("removeAccount")) {
                    System.out.println(s.getRequestObject());
                    adapter.removeAccount((User) s.getRequestObject());
                } else if (requestText.equalsIgnoreCase("editAccount")) {
                    System.out.println(s.getRequestObject());
                    adapter.editAccount((User) s.getRequestObject());
                } else if (requestText.equalsIgnoreCase("changeUserInfo")) {
                    adapter.changeUserInformation((User) s.getRequestObject());
                } else if (requestText.equalsIgnoreCase("createDepartment")) {
                    adapter.createDepartment((Department) s.getRequestObject());
                } else if (requestText.equalsIgnoreCase("editDepartment")) {
                    adapter.editDepartment((Department) s.getRequestObjects()[0], (Department) s.getRequestObjects()[1]);
                } else if (requestText.equalsIgnoreCase("getDepartment")) {
                    Department department = (Department) adapter.viewDepartment((Department) s.getRequestObject());
                    getOutputStream().writeObject(new Response<>("viewDepartment", department));
                } else if (requestText.equalsIgnoreCase("deleteDepartment")) {
                    adapter.deleteDepartment((Department) s.getRequestObject());
                } else if (requestText.equalsIgnoreCase("getAllDepartments")) {
                    ArrayList<Department> department = adapter.getAllDepartments();
                    getOutputStream().writeObject(new Response<>("getAllDepartments", department));
                } else if (requestText.equalsIgnoreCase("getWorkingSchedule")) {
                    ArrayList<WorkingSchedule> workingSchedules = adapter.workingSchedulePerWeek((User) s.getRequestObject());
                    getOutputStream().writeObject(new Response<>("getWorkingSchedule", workingSchedules));
                } else if (requestText.equalsIgnoreCase("getWagePerHours")) {
                    String wage = adapter.getWagePerHour((User) s.getRequestObject());
                    getOutputStream().writeObject(new Response<>("getWagePerHours", wage));
                } else if (requestText.equalsIgnoreCase("changeWagePerHour")) {
                    adapter.changeWagePerHours((User) s.getRequestObject());
                } else if (requestText.equalsIgnoreCase("getWorkingColleagues")) {
                    ArrayList<User> workingCollegues = adapter.getWorkingCollegues((User) s.getRequestObject());
                    getOutputStream().writeObject(new Response<>("getWorkingColleagues", workingCollegues));
                } else if (requestText.equalsIgnoreCase("logIn")) {
                    getOutputStream().writeObject(new Response<>("login",
                            adapter.logIn((User) s.getRequestObject())));
                }
            } catch (Exception e) {
                server.removeObserver(this);
            }
        }
    }

    /**
     * Constructor.
     * Instantiates streams.
     *
     * @param socket
     * @param server
     */
    public Connection(Socket socket, Server server, IDBAdapter adapter) {
        this.server = server;
        this.adapter = adapter;
        try {
            inFromClient = new ObjectInputStream(socket.getInputStream());
            outToClient = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ObjectOutputStream getOutputStream() {
        return outToClient;
    }


    private OurObserver getMe() {
        return this;
    }

    /**
     * Abstract method for writing response to observer.
     *
     * @param response
     */
    public void writeObject(Response response) {
        new Thread(() -> {
            try {
                getOutputStream().writeObject(response);
            } catch (IOException e) {
                server.removeObserver(getMe());
            }

        }).start();
    }

    @Override
    public void update(Response asd) {
        writeObject(asd);
    }
}
