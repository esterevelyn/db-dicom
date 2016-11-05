package processor;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.component.Components;
import net.sf.dynamicreports.report.builder.datatype.DataTypes;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import processor.db.PatientRepository;
import processor.db.StudyRepository;
import processor.entity.Patient;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import static net.sf.jasperreports.engine.JasperExportManager.exportReportToPdfFile;

/**
 * Created by EsterIBm on 11/10/2016.
 */
@Component
public class Report {

    @Autowired private PatientRepository repositoryP;
    @Autowired private StudyRepository repositoryS;

    public void generateReport() throws JRException {
        JasperReport jasperReport;
        JasperPrint jasperPrint = null;
        jasperReport = JasperCompileManager
                .compileReport("C:\\Users\\EsterIBm\\Downloads\\mongodb-sb-jsaper-report\\" +
                        "mongodb-sb-jsaper-report\\src\\main\\resources\\static\\report.jrxml");
        List<Patient> custList = new ArrayList<Patient>();
        for (Patient customer : repositoryP.findAll()) {
            custList.add(customer);
        }
        CustomJRDataSource<Patient> dataSource = new CustomJRDataSource<Patient>().initBy(custList);
        jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap(),
                dataSource);
        exportReportToPdfFile(jasperPrint,
                "C:\\PDF");
    }


    public void generateDynamicReport(){

        JasperReportBuilder report = DynamicReports.report();//a new report
        JasperReportBuilder jasperReportBuilder = report;
        JasperReportBuilder columns =
                jasperReportBuilder.columns(
                        Columns.column("ID Patiente", "idPatient", DataTypes.stringType())
                                .setHorizontalTextAlignment(HorizontalTextAlignment.LEFT),
                        Columns.column("Nome", "namePatient", DataTypes.stringType()),
                        Columns.column("Data de Nascimento", "birthDatePatient", DataTypes.stringType())
                                 .setHorizontalTextAlignment(HorizontalTextAlignment.LEFT),
                        Columns.column("DLP Total", "dlpTotal", DataTypes.doubleType())
                                .setHorizontalTextAlignment(HorizontalTextAlignment.LEFT));
        jasperReportBuilder.title(//title of the report
                Components.text("Relatório")
                        .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER));
        jasperReportBuilder.pageFooter(Components.pageXofY());
        jasperReportBuilder.setDataSource(getCollections());

        try {
            //report.show();//show the report
            report.toPdf(new FileOutputStream("C:\\Users\\EsterIBm\\Documents\\PDF\\relatório.pdf"));//export the report to a pdf file
        } catch (DRException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Collection<Patient> getCollections(){

        Collection<Patient> list = new ArrayList<>();
        for (Patient customer : repositoryP.findAll()) {
            list.add(customer);
        }
        return list;
    }
}
