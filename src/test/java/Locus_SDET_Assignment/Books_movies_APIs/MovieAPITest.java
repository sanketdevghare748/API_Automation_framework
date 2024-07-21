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
//Get the total movies count coming as response.

	public int getCountMovies() {
		int totalAllMovies = js.getInt("total");
		// System.out.println("Total number of movies= "+totalAllMovies);
		return totalAllMovies;
	}
//Get the movieID with max awards won.

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
//Get the movie ID with mximum Rotten tomatoes Score.

	public String getMovie_With_maxScore() {
		// get all the awards in the list and then get max.
		List<Integer> list_scores = new ArrayList<>();
		int total = js.getInt("total");

		for (int i = 0; i < total; i++) {

			double RTScore = js.getDouble("docs[" + i + "].rottenTomatoesScore");
			
			list_scores.add((int) RTScore);

		}
		int max = Collections.max(list_scores);

		int a = list_scores.indexOf(max);

		movieID = js.getString("docs[" + a + "]._id");
		return movieID;
	}

//get all the dialogs from movie based on movie ID.

	public static List<String> getAllDialogs(String movieid) {

		Response resQuotes = given().spec(MovieReq).header("Authorization", BearerToken).queryParam("movie", movieid)
				.when().get("/quote").then().extract().response();
		
		List<String> allQuotes_byMovie = new ArrayList<>();
		JsonPath jquote = new JsonPath(resQuotes.asString());
		int totalQuote_movie = jquote.getInt("total");
		for (int i = 0; i < totalQuote_movie; i++) {
			
			if(jquote.getString("docs[" + i + "].dialog") != null)
			{
			allQuotes_byMovie.add(jquote.getString("docs[" + i + "].dialog"));
			}
		}
		// System.out.println(allQuotes_byMovie);
		return allQuotes_byMovie;

	}

//check if the dialog exists in a movie given Dialog and movie ID.

	public boolean checkif_dialogExistsIn(String Dialog, String movieID) {
		List<String> allDialogs = new ArrayList<>();
		allDialogs = getAllDialogs(movieID);
	
		for(String dialog:allDialogs)
		{
			if(Dialog.equalsIgnoreCase(dialog))
			{
				return true;
			}
		}
		return false;
	}

}
