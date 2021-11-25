package controller;


import com.jfoenix.controls.JFXDatePicker;
import db.DbConnection;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;


public class SystemReportFormController {
    public AnchorPane SystemReportContext;
    public Button id;
    public Button idM;
    public Button idA;
    public JFXDatePicker dpFrom;
    public JFXDatePicker dpTo;
    public Button btnCustomReport;


    public void dailyIncomeOnAction(ActionEvent actionEvent) {

        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("../view/report/DailyReport.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null , DbConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void monthly(ActionEvent actionEvent) {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("../view/report/MonthlyReport.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null , DbConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void Annualy(ActionEvent actionEvent) {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("../view/report/AnnuallyReport.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null , DbConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void btnCustomReportOnAction(ActionEvent actionEvent) {
        LocalDate fromDateValue = dpFrom.getValue();
        LocalDate toDateValue = dpTo.getValue();
        String from = String.valueOf(fromDateValue);
        String to = String.valueOf(toDateValue);
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("../view/report/CustomReport.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);

            HashMap map=new  HashMap();
            map.put("fromDate",fromDateValue);
            map.put("toDate",toDateValue);
            map.put("from",from);
            map.put("to",to);

            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, map, DbConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}


