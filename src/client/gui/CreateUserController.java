package client.gui;

import client.Controller;
import common.Response;
import common.User;
import common.helpers.Helpers;
import common.helpers.ResponseReader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class CreateUserController implements ResponseReader {

    private ArrayList<User> userList;
    private Controller controller;
    private User user;
    private User selectedUser;

    @FXML
    private TextField userNameFieldCreate;
    @FXML
    private TextField CPRFieldCreate;
    @FXML
    private TextField passwordFieldCreate;
    @FXML
    private TextField userRoleCreate;
    @FXML
    private TextField userWageCreate;
    @FXML
    private ListView clientList;
    @FXML
    private TextField adminEditUserUsername;
    @FXML
    private TextField adminEditUserCPR;
    @FXML
    private TextField adminEditUserPassword;
    @FXML
    private TextField adminEditUserRole;
    @FXML
    private TextField adminEditWage;


    @FXML
    private void handleCreateUserAdminPanel() {
        String name = userNameFieldCreate.getText();
        String cpr = CPRFieldCreate.getText();
        String pass = passwordFieldCreate.getText();
        String role = userRoleCreate.getText();
        String wage = userWageCreate.getText();
        controller.createUser(name, pass, cpr, role, wage);
    }

    public void asd() {
        controller.getAllUsers();
        Helpers.getLastResponse(controller, this);
    }


    public void responseReader(Response res) {
        if (res != null) {
            if (res.getResponse().equals("getallusers")) {
                userList = (ArrayList<User>) res.getRespnoseObject();
                populateUserTable((ArrayList<User>) res.getRespnoseObject());
            }
            if (res.getResponse().equals("getuserinfoforadmin")) {
                createUserInfoWindow((User) res.getRespnoseObject());

            }
        }
    }


    private void populateUserTable(ArrayList<User> users) {
        ObservableList<String> items = FXCollections.observableArrayList();

        for (User user : users) {
            items.add(user.getUsername() + " " + user.getCpr());
        }

        clientList.setItems(items);

        clientList.setOnMouseClicked(event -> getListText(clientList.getSelectionModel().getSelectedItem()));

    }

    private void getListText(Object selected) {
        String str = selected.toString();
        String[] parts = str.split(" ");
        selectedUser = getUserByCPR(parts[1]);
        assert selectedUser != null;
        adminEditUserUsername.setText(selectedUser.getUsername());
        adminEditUserCPR.setText(selectedUser.getCpr());
        adminEditUserPassword.setText(selectedUser.getPassword());
        adminEditUserRole.setText(selectedUser.getUserRole());
        adminEditWage.setText(selectedUser.getWage().trim());
    }

    private User getUserByCPR(String cpr) {
        for (User user : userList) {
            if (user.getCpr().equals(cpr)) {
                return user;
            }
        }
        return null;
    }

    @FXML
    private void getSingleUserEvent() {
        controller.getUserForAdmin(selectedUser.getCpr());
        Helpers.getLastResponse(controller, this);
    }


    private void createUserInfoWindow(User user) {
        Parent root;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("UserInfo.fxml"));
            root = fxmlLoader.load();
            UserInfoController controller = fxmlLoader.getController();
            controller.setController(this.controller);
            controller.setUser(user);
            controller.displayUser(user);

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setTitle("User Info");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void viewAllWorkingDays() {
        Parent root;
        try {
            if (selectedUser != null) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AllWorkingDays.fxml"));
                root = fxmlLoader.load();
                AllWorkingDaysController controller = fxmlLoader.getController();
                controller.setController(this.controller);
                controller.setUser(user);
                controller.init(selectedUser);

                Scene scene = new Scene(root);
                Stage stage = new Stage();

                stage.setTitle("Working days");
                stage.setScene(scene);
                stage.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void removeUser() {
        controller.removeUser(adminEditUserUsername.getText(), adminEditUserPassword.getText(), adminEditUserCPR.getText(), adminEditUserRole.getText());
    }

    @FXML
    private void submitEdit() {
        controller.submitEdit(adminEditUserUsername.getText(), adminEditUserPassword.getText(), adminEditUserCPR.getText(), adminEditUserRole.getText());
        controller.changeWagePerHours(adminEditUserCPR.getText(), adminEditWage.getText());
    }

    @FXML
    private void workingHistory() {
        viewAllWorkingDays();
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
