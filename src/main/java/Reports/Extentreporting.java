package Reports;

import java.net.InetAddress;
import java.net.UnknownHostException;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class Extentreporting {
	public  static ExtentReports extent;
	public  static ExtentReports getReportObject() {
		String path = System.getProperty("user.dir") + "//Reports//report.html";
		ExtentSparkReporter reporter = new ExtentSparkReporter(path);
		reporter.config().setReportName("Automation Results");
		reporter.config().setDocumentTitle("Test Results");

		extent = new ExtentReports();
		extent.attachReporter(reporter);
		extent.setSystemInfo("Tester", "Sanket Devghare");
		try {
			extent.setSystemInfo("Machine", InetAddress.getLocalHost().getHostName());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			System.out.println("Unable to capture hostname.");
		}

		return extent;
	}
}