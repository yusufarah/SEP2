package client.gui;

import client.Controller;
import common.Response;
import common.User;
import common.helpers.Helpers;
import common.helpers.ResponseReader;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

 public class UserInfoController implements ResponseReader {

    private Controller controller;
    private User user;


    @FXML
    private Label userInfoUsernameLabel;
    @FXML
    private TextField userInfoPassword;
    @FXML
    private Label userInfoUserRole;
    @FXML
    private TextField userInfoFirstName;
    @FXML
    private TextField userInfoSecondName;
    @FXML
    private TextField userInfoLastName;
    @FXML
    private TextField userInfoDOB;
    @FXML
    private TextField userInfoAddress;
    @FXML
    private TextField userInfoPostcode;
    @FXML
    private TextField userInfoCity;
    @FXML
    private TextField userInfoMobile;
    @FXML
    private TextField userInfoLandline;
    @FXML
    private TextField userInfoEmail;
    @FXML
    private TextField userInfoKonto;
    @FXML
    private TextField userInfoRecNumber;
    @FXML
    private TextField userInfoLicencePlate;
    @FXML
    private TextField userInfoPreferredCommunication;
    @FXML
    private ImageView userInfoPicture;
    @FXML
    private TextField userInfoMoreInfo;

    @FXML
    private Label userInfoDepartmentsLabel;

    @FXML
    private Label userInfoCprLabel;
    @FXML
    private Label userInfoWageLabel;
    @FXML
    private TextField newPicture;

    @FXML
    private void handleUserInformationAdminPanel() {
        ArrayList<String> newUserInformation = new ArrayList<>();

        newUserInformation.add(newPicture.getText());
        newUserInformation.add(userInfoUsernameLabel.getText());
        newUserInformation.add(userInfoPassword.getText());
        newUserInformation.add(userInfoFirstName.getText());
        newUserInformation.add(userInfoSecondName.getText());
        newUserInformation.add(userInfoLastName.getText());
        newUserInformation.add(userInfoCprLabel.getText());
        newUserInformation.add(userInfoDOB.getText());
        newUserInformation.add(userInfoAddress.getText());
        newUserInformation.add(userInfoPostcode.getText());
        newUserInformation.add(userInfoCity.getText());
        newUserInformation.add(userInfoMobile.getText());
        newUserInformation.add(userInfoLandline.getText());
        newUserInformation.add(userInfoEmail.getText());
        newUserInformation.add(userInfoKonto.getText());
        newUserInformation.add(userInfoRecNumber.getText());
        newUserInformation.add(userInfoLicencePlate.getText());
        newUserInformation.add(userInfoPreferredCommunication.getText());
        newUserInformation.add(userInfoMoreInfo.getText());
        newUserInformation.add(userInfoWageLabel.getText());
        newUserInformation.add(userInfoUserRole.getText());

        controller.changeUserInformation(newUserInformation);
    }

    public void displayUser(User user) {

        //toDo : Fix Picture Null Problem
        if (user.getPicture() == null || user.getPicture().equals("") || user.getPicture().equals("null") ) {
            user.setPicture("https://supercharge.info/images/avatar-placeholder.png");
        }
        Image i = new Image(user.getPicture(), true);
        userInfoPicture.setImage(i);
        userInfoUsernameLabel.setText(user.getUsername());
        userInfoPassword.setText(user.getPassword());
        userInfoFirstName.setText(user.getFirstName());
        userInfoSecondName.setText(user.getSecondName());
        userInfoLastName.setText(user.getLastName());
        userInfoCprLabel.setText(user.getCpr());
        userInfoDOB.setText(user.getDob());
        userInfoAddress.setText(user.getAddress());
        userInfoPostcode.setText(user.getPostcode());
        userInfoCity.setText(user.getCity());
        userInfoMobile.setText(user.getMobile());
        userInfoLandline.setText(user.getLandline());
        userInfoEmail.setText(user.getEmail());
        userInfoKonto.setText(user.getKonto());
        userInfoRecNumber.setText(user.getRecnumber());
        userInfoLicencePlate.setText(user.getLicencePlate());
        userInfoPreferredCommunication.setText(user.getPrefferedCommunication());
        userInfoMoreInfo.setText(user.getMoreInfo());
        userInfoWageLabel.setText(user.getWage());
        userInfoUserRole.setText(user.getUserRole());
    }




     private void getDepartmentsEvent() {
         controller.getMyWorkingDepartments(user.getCpr());
         Helpers.getLastResponse(controller, this);
     }

     public void responseReader(Response res) {

         if (res != null) {
             if (res.getResponse().equals("getMyWorkingDepartments")) {
                 getWorkingDepartments((ArrayList<String>) res.getRespnoseObject());
             }
         }
     }


     public void getWorkingDepartments(ArrayList<String> strings){

         String str = "";

         for (String string: strings){
             str+= string + "  ";
         }

         userInfoDepartmentsLabel.setText(str);
     }

     public void setController(Controller controller) {
         this.controller = controller;
     }

     public void setUser(User user) {
         this.user = user;
         getDepartmentsEvent();
     }
 }
