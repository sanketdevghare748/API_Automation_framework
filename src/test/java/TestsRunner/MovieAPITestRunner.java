package TestsRunner;

import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;

import Locus_SDET_Assignment.Books_movies_APIs.MovieAPITest;
import Reports.Extentreporting;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utilities.RequestSpecifications;

/**
 * Testcases: 1. Verify the status code when used without api token. 2. Check if
 * the list of books returned has Books with name= "The Return Of The King" if
 * yes return book ID 3. Get the name of the book from ID.
 * (5cf58077b53e011a64671583, "The Two Towers".
 */

public class MovieAPITestRunner {
	RequestSpecification Moviereq = RequestSpecifications.getMovieReqSpecfication();
	static MovieAPITest movies = new MovieAPITest();
	static String movieID_max_awards;
	static Extentreporting ext = new Extentreporting();
	static ExtentReports test = ext.getReportObject();

	// Test 1: Verify the status code when used without api token.
	@Test
	public void acessAPIWithoutToken() {
		Response movieRes = given().spec(Moviereq).when().get("/movie").then().extract().response();
		System.out.println("Status code with out token " + movieRes.getStatusCode());
		Assert.assertEquals(movieRes.getStatusCode(), 401);

		Reporter.log("Access API without Token Key negative test is passed.");
	}

	// Test 2: Verify the total number of moview recieved in a response.
	@Test
	public void verifyTotalMovieCount() {
		System.out.println("Total number of moview in response= " + movies.getCountMovies());
		Assert.assertEquals(movies.getCountMovies(), 8);
		Reporter.log("Verify total count is passed!!");
	}

	// Test 3: Verify movie ID with highest awards.
	@Test
	public void verifyMovieID_with_highest_awards_Won() {
		movieID_max_awards = movies.getMovieId_highest_awards();
		System.out.println("MovieID having most awards won= " + movieID_max_awards);
		Assert.assertEquals(movieID_max_awards, "5cd95395de30eff6ebccde56");
		Reporter.log("verifyMovieID_with_highest_awards_Won is Passed.");
	}

	// For the getQuote API, the API itself is not working. hence tried this one.
	// Test 4:Verify given dialog is existing in the movieID obtained from above
	// scenario
	@Test
	public void verify_ifDialogExists() {
		movieID_max_awards = movies.getMovieId_highest_awards();
		String Dialog = "Give us that! Deagol my love";
		System.out.println(movieID_max_awards);
		Assert.assertFalse(movies.checkif_dialogExistsIn(Dialog, movieID_max_awards));
		Reporter.log("If Dialog exits test is passed.");

	}

}
