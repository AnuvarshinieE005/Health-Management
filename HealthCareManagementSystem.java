import java.util.*;

// Abstract base class
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

// Blood Pressure Report subclass
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

// Blood Sugar Report subclass
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

// Main class (partial)
public class HealthCareManagementSystem {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Enter Patient Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Blood Pressure (mmHg): ");
        int bp = sc.nextInt();

        System.out.print("Enter Blood Sugar (mg/dL): ");
        int sugar = sc.nextInt();

        // Create report objects
        Report bpReport = new BloodPressureReport(name, bp);
        Report sugarReport = new BloodSugarReport(name, sugar);

        // Simple output (no threading/file yet)
        System.out.println("\n--- Analysis Report ---");
        System.out.println("Blood Pressure Status: " + bpReport.analyzeReport());
        System.out.println("Suggestion: " + bpReport.getSuggestions());

        System.out.println("Blood Sugar Status: " + sugarReport.analyzeReport());
        System.out.println("Suggestion: " + sugarReport.getSuggestions());

        // Remaining: Threading, File Handling
    }
}
