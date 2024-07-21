package Locus_SDET_Assignment.Books_movies_APIs;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.hamcrest.text.IsEqualIgnoringCase;
import org.testng.Assert;
import org.testng.annotations.Test;

import POJO.BooksResponse;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import resources.GetProperties;
import utilities.RequestSpecifications;

public class MovieAPITest {
	/**
	 * Tests: 1.Negative test to acess the getall movie API without token. 2.Postive
	 * Test: Fetch all movies and get count of all movies 3.Get the movie name which
	 * has recievd highest nominations and verify its name. 4.Get the movie quotes
	 * based on the name of the movie. E2E Scenario.
	 */
	static RequestSpecification MovieReq = RequestSpecifications.getReqSpecification();
	static String BearerToken = GetProperties.getProperty("BearerToken");
	static Response res = given().spec(MovieReq).header("Authorization", BearerToken).when().get("/movie").then()
			.statusCode(200).extract().response();
	static JsonPath js = new JsonPath(res.asString());
	static List<String> allDialogs;
	String movieID;

	@Test
	public int getCountMovies() {
		int totalAllMovies = js.getInt("total");
		// System.out.println("Total number of movies= "+totalAllMovies);
		return totalAllMovies;
	}

	@Test
	public String getMovieId_highest_awards() {
		// get all the awards in the list and then get max.
		List<Integer> list_awards = new ArrayList<>();
		int total = js.getInt("total");

		for (int i = 0; i < total; i++) {

			int awardCount = js.getInt("docs[" + i + "].academyAwardWins");
			list_awards.add(awardCount);

		}
		int max = Collections.max(list_awards);
		int a = list_awards.indexOf(max);

		movieID = js.getString("docs[" + a + "]._id");
		return movieID;
	}

//get quote of the movie from the movie ID.
	@Test
	public List<String> getAllDialogs_fromMovie_id(String movieId) {
		Response resQuotes = given().spec(MovieReq).header("Authorization", BearerToken).queryParam("movie", movieId)
				.when().get("/quote").then().extract().response();
		List<String> allQuotes_byMovie = new ArrayList<>();
		JsonPath jquote = new JsonPath(resQuotes.asString());
		int totalQuote_movie = jquote.getInt("total");
		for (int i = 0; i < totalQuote_movie; i++) {
			allQuotes_byMovie.add(jquote.getString("docs[" + i + "].dialog"));
		}
		return allQuotes_byMovie;
	}
//check if the dialog exists in a movie.

	public boolean checkif_dialogExistsIn(String Dialog, String movieID) {
		List<String> allDialogs = new ArrayList<>();
		allDialogs = getAllDialogs_fromMovie_id(movieID);
		// System.out.println(allDialogs);
		if (allDialogs.contains(Dialog)) {
			return true;
		}
		return false;
	}

}
