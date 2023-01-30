package CustomerInfo;

import dbConnector.mySQLCon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class CustomerInfo implements Initializable {
    Connection con= mySQLCon.getConnection();
    PreparedStatement ps = null;
    ResultSet rs = null;
    @FXML
    private TableView<Customer> customerTableView;
    @FXML
    private TableColumn<Customer,String> info_name;
    @FXML
    private TableColumn<Customer,String> info_AcNO;
    @FXML
    private TableColumn<Customer,String> info_ACType;
    @FXML
    private TableColumn<Customer,String> info_Balance;
    @FXML
    private TableColumn<Customer,String> info_DOB;
    @FXML
    private TableColumn<Customer,String> info_Address;
    public void info(){
        info_name.setCellValueFactory(new PropertyValueFactory<Customer, String>("name"));
        info_AcNO.setCellValueFactory(new PropertyValueFactory<Customer, String>("ACNo"));
        info_ACType.setCellValueFactory(new PropertyValueFactory<Customer, String>("ACType"));
        info_Balance.setCellValueFactory(new PropertyValueFactory<Customer, String>("Balance"));
        info_DOB.setCellValueFactory(new PropertyValueFactory<Customer, String>("DOB"));
        info_Address.setCellValueFactory(new PropertyValueFactory<Customer, String>("Address"));
        ObservableList<Customer> list = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * FROM userdata";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){

                list.add(new Customer(rs.getString("Name"),rs.getString("AccountNo"),rs.getString("AccountType"),rs.getString("Balance"),rs.getString("DOB"),rs.getString("Address")));
            }



        }catch(Exception e){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setHeaderText("Error in Fetching Data .");
            a.setContentText("There is some Error in Fetching Data. PLEASE TRY AGAIN..!!!"+e.getMessage());
            a.showAndWait();

        }
        customerTableView.setItems(list);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
           info();
    }
}
