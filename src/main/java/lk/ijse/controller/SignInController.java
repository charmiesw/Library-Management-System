package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.service.ServiceFactory;
import lk.ijse.service.custom.UserService;

import java.io.IOException;

public class SignInController {
    @FXML
    private AnchorPane signInPane;

    @FXML
    private CheckBox checkBox;

    @FXML
    private PasswordField hidePassword;

    @FXML
    private TextField txtUsername;

    public static String LoginPageUserName;

    UserService userService = (UserService) ServiceFactory.getServiceFactory().getService(ServiceFactory.ServiceTypes.USER);

    @FXML
    void btnLoginOnAction(ActionEvent event) {
        LoginPageUserName = txtUsername.getText();
        String password = hidePassword.getText();

        try {

            boolean isIn = userService.searchUser(LoginPageUserName, password);
            if (!isIn) {
                new Alert(Alert.AlertType.WARNING, "Invalid UserName or Password").show();
            } else {
                System.out.println("user in");
                isAdmin(LoginPageUserName);
            }

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private void isAdmin(String username) {
        try {

            boolean isAdmin = userService.checkAdmin(username);

            if(isAdmin){
                System.out.println("admin");
               Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/AdminNavPane.fxml"));

                Scene scene = new Scene(rootNode);
                Stage stage = (Stage) this.signInPane.getScene().getWindow();
                stage.setTitle("DASHBOARD");
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.show();
            }else{
                System.out.println("customer");
//                Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/CustomerNavPane.fxml"));

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CustomerNavPane.fxml"));

                Parent rootNode = loader.load();

                CustomerNavPane customerNavPane = loader.getController();

                customerNavPane.setUserName(
                        username
                );

                Scene scene = new Scene(rootNode);
                Stage stage = (Stage) this.signInPane.getScene().getWindow();
                stage.setTitle("DISCOVER");
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.show();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void hyperSignupOnAction (ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/SignUp.fxml"));

        Scene scene = new Scene(rootNode);

        signInPane.getChildren().clear();
        Stage primaryStage = (Stage) signInPane.getScene().getWindow();

        primaryStage.setScene(scene);
        primaryStage.setTitle("Signup Page");
    }
}
