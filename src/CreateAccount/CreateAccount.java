package CreateAccount;

import Login.LoginScreen;
import dbConnector.mySQLCon;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateAccount implements Initializable {
    private FileChooser fileChooser = new FileChooser();
    private File file;
    private FileInputStream fis;

    @FXML
    private ImageView pic;
    @FXML
    private TextField name;
    @FXML
    private TextField idcardno;
    @FXML
    private TextField mobileno;
    @FXML
    private TextField city;
    @FXML
    private TextField address;
    @FXML
    private TextField accountno ;
    @FXML
    private TextField  pin;
    @FXML
    private TextField balance;
    @FXML
    private TextField answer;
    @FXML
    private DatePicker dob;
    @FXML
    private ComboBox<String> gender;
    @FXML
    private ComboBox<String> martialStatus;
    @FXML
    private ComboBox<String> religion;
    @FXML
    private ComboBox<String> accountype;
    @FXML
    private ComboBox<String> questions;
    ObservableList<String> list = FXCollections.observableArrayList("Male","Female");
    ObservableList<String> list1 = FXCollections.observableArrayList("Single","Married");
    ObservableList<String> list2 = FXCollections.observableArrayList("Islam","Hindu","Christian","Others");
    ObservableList<String> list3 = FXCollections.observableArrayList("Current","Savings");
    ObservableList<String> list4 = FXCollections.observableArrayList("What is your pet name?","What is your childhood town?","What is your nick name?");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gender.setItems(list);
        martialStatus.setItems(list1);
        religion.setItems(list2);
        accountype.setItems(list3);
        questions.setItems(list4);
        accountno.setText(String.valueOf(generateAccountNo()));
        accountno.setEditable(false);
    }
    public void setUpPic(MouseEvent mouseEvent){
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images Files","*.png","*jpg"));
        file =fileChooser.showOpenDialog(null);
        if(file!=null){
            Image img =new Image(file.toURI().toString(),150,150,true,true);
            pic.setImage(img);
            pic.setPreserveRatio(true);
        }


    }
    public void closeApp(MouseEvent mouseEvent) {
        Platform.exit();
        System.exit(0);

    }

    public void backToLogin(MouseEvent mouseEvent) throws IOException
    {
        LoginScreen.stage.getScene().setRoot(FXMLLoader.load(getClass().getResource("/Login/LoginScreen.fxml")));

    }

 public boolean validateName(){
     Pattern p = Pattern.compile("[a-zA-Z]+");
     Matcher m = p.matcher(name.getText());
     if (m.find()&&m.group().equals(name.getText())) {
         return true;
     }
     else{
         Alert a =new Alert(Alert.AlertType.INFORMATION);
         a.setTitle("Wrong name format");
         a.setHeaderText("Your naming format is not valid");
         a.setContentText("Please use only the English alphabets");
         a.showAndWait();
         return  false;
     }
 }
    public boolean validateMobileNO(){
        Pattern p = Pattern.compile("[0-9]+");
        Matcher m = p.matcher(mobileno.getText());
        if (m.find()&&m.group().equals(mobileno.getText())) {
            return true;
        }
        else{
            Alert a =new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Wrong Mobile NO format");
            a.setHeaderText("Your Mobile NO format is not valid");
            a.setContentText("Please use only Integers");
            a.showAndWait();
            return  false;
        }
    }
    public void clearAllFields(){
        name.clear();
        idcardno.clear();
        mobileno.clear();
        gender.getSelectionModel().clearSelection();
        religion.getSelectionModel().clearSelection();
        martialStatus.getSelectionModel().clearSelection();
        dob.getEditor().clear();
        city.clear();
        address.clear();
        pin.clear();
        accountype.getSelectionModel().clearSelection();
        balance.clear();
        questions.getSelectionModel().clearSelection();
        answer.clear();
        Image img = new Image("/images/default_pic.jpg");
        pic.setImage(img);
        accountno.setText(String.valueOf(generateAccountNo()));

    }
    public int  generateAccountNo(){
        Random random = new Random();
       int num = random.nextInt(899999)+100000;
       return num;
    }
    public void newAccount(MouseEvent mouseEvent)
    {
        Connection con= mySQLCon.getConnection();
        PreparedStatement ps=null;
        try{
            if (validateName()) {
                String sql = "INSERT INTO userdata (Name, ICN, MobileNo, Gender, MartialStatus,Religion, DOB, City, Address, AccountNo, PIN, AccountType, Balance, SecurityQuestion, Answer, ProfilePic)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                ps = con.prepareStatement(sql);
                ps.setString(1, name.getText());
                ps.setString(2, idcardno.getText());
                ps.setString(3, mobileno.getText());
                ps.setString(4, gender.getValue());
                ps.setString(5, martialStatus.getValue());
                ps.setString(6, religion.getValue());
                ps.setString(7, ((TextField) dob.getEditor()).getText());
                ps.setString(8, city.getText());
                ps.setString(9, address.getText());
                ps.setString(10, accountno.getText());
                ps.setString(11, pin.getText());
                ps.setString(12, accountype.getValue());
                ps.setString(13, balance.getText());
                ps.setString(14, questions.getValue());
                ps.setString(15, answer.getText());
                fis = new FileInputStream(file);
                ps.setBinaryStream(16, (InputStream) fis, (int) file.length());
                int i = ps.executeUpdate();
                if (i > 0) {
                    Alert a = new Alert(Alert.AlertType.INFORMATION);
                    a.setTitle("Account Created");
                    a.setHeaderText("Account created successfully");
                    a.setContentText("You account has been created successfully.You can login with your id and pin");
                    a.showAndWait();
                    clearAllFields();


                } else {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setTitle("Error");
                    a.setHeaderText("Error in creating account");
                    a.setContentText("There are some errors.Try again");
                    a.showAndWait();

                }
            }

        }catch (Exception e){
            Alert a =new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setHeaderText("Error in creating account");
            a.setContentText("You account is not created.There is some technical issue "+  e.getMessage());
            a.showAndWait();
        }
    }
}
