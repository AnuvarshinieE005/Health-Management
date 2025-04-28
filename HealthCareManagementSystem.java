import java.io.*;
import java.util.*;

// Base class
abstract class Report {
    protected String patientName;
    protected int reportValue;

    public Report(String patientName, int reportValue) {
        this.patientName = patientName;
        this.reportValue = reportValue;
    }

    public abstract String analyzeReport();

    public abstract String getSuggestions();
}

// Blood Pressure Report
class BloodPressureReport extends Report {

    public BloodPressureReport(String patientName, int reportValue) {
        super(patientName, reportValue);
    }

    @Override
    public String analyzeReport() {
        if (reportValue < 90) return "Low";
        else if (reportValue <= 120) return "Medium";
        else return "High";
    }

    @Override
    public String getSuggestions() {
        String status = analyzeReport();
        switch (status) {
            case "Low":
                return "Increase salt intake and stay hydrated.";
            case "Medium":
                return "Maintain a balanced diet and exercise regularly.";
            case "High":
                return "Reduce sodium intake and consult your doctor.";
            default:
                return "No suggestions.";
        }
    }
}

// Blood Sugar Report
class BloodSugarReport extends Report {

    public BloodSugarReport(String patientName, int reportValue) {
        super(patientName, reportValue);
    }

    @Override
    public String analyzeReport() {
        if (reportValue < 70) return "Low";
        else if (reportValue <= 140) return "Medium";
        else return "High";
    }

    @Override
    public String getSuggestions() {
        String status = analyzeReport();
        switch (status) {
            case "Low":
                return "Eat or drink something sugary immediately.";
            case "Medium":
                return "Maintain a healthy diet and regular monitoring.";
            case "High":
                return "Limit sugar intake and follow up with a doctor.";
            default:
                return "No suggestions.";
        }
    }
}

// Thread class for analyzing report
class ReportAnalyzer extends Thread {
    private Report report;
    private String reportType;

    public ReportAnalyzer(String reportType, Report report) {
        this.report = report;
        this.reportType = reportType;
    }

    @Override
    public void run() {
        String analysis = report.analyzeReport();
        String suggestion = report.getSuggestions();

        String result = "\n--- " + reportType + " Report for " + report.patientName + " ---\n" +
                        "Value: " + report.reportValue + "\n" +
                        "Status: " + analysis + "\n" +
                        "Suggestions: " + suggestion + "\n";

        System.out.println(result);

        // Save to file
        try (FileWriter fw = new FileWriter("report_output.txt", true)) {
            fw.write(result);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
}

// Main class
public class HealthCareManagementSystem {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Patient Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Blood Pressure (mmHg): ");
        int bp = sc.nextInt();

        System.out.print("Enter Blood Sugar (mg/dL): ");
        int sugar = sc.nextInt();

        // Create reports
        Report bpReport = new BloodPressureReport(name, bp);
        Report sugarReport = new BloodSugarReport(name, sugar);

        // Analyze in parallel using threads
        Thread bpThread = new ReportAnalyzer("Blood Pressure", bpReport);
        Thread sugarThread = new ReportAnalyzer("Blood Sugar", sugarReport);

        bpThread.start();
        sugarThread.start();

        try {
            bpThread.join();
            sugarThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Analysis complete. Check 'report_output.txt' for saved report.");
    }
}
