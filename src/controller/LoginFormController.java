package controller;


import bo.BoFactory;
import bo.custom.LoginBo;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import dto.UserDto;
import entity.User;
import javafx.event.ActionEvent;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;


public class LoginFormController {
    public AnchorPane LoginContext;
    public JFXTextField TxtUserName;
    public JFXPasswordField TxtPassword;

    private final LoginBo loginBo = (LoginBo) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.LOGIN);


    public void BtnLoginOnAction(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {

        UserDto user = new UserDto(TxtUserName.getText(),TxtPassword.getText());
        String role = loginBo.login(user);

        if(role.equals("Admin")){
            URL resource = getClass().getResource("../view/AdminDashBoardForm.fxml");
            Parent load = FXMLLoader.load(resource);
            Stage window = (Stage) LoginContext.getScene().getWindow();
            window.setScene(new Scene(load));
            window.centerOnScreen();
            window.setResizable(false);

        }else if (role.equals("Cashier")){
            URL resource = getClass().getResource("../view/DashBoardForm.fxml");
            Parent load = FXMLLoader.load(resource);
            Stage window = (Stage) LoginContext.getScene().getWindow();
            window.setScene(new Scene(load));
            window.centerOnScreen();
            window.setResizable(false);
        }else{
            new Alert(Alert.AlertType.WARNING,"something went wrong").show();
        }

    }

    public void initialize() {

    }

    public void BtnCancelOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) LoginContext.getScene().getWindow();
        stage.close();
    }

}




