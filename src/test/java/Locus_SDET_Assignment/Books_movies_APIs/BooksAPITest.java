package Locus_SDET_Assignment.Books_movies_APIs;

/** Testcases:
 * 1. Verify the number of books returned and status code of the request.
 * 2. Check if the list of books returned has Books with name= "The Return Of The King" if yes return book ID
 * 3. Get the name of the book from ID. (5cf58077b53e011a64671583, "The Two Towers".
 */

import java.io.FileNotFoundException;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;

import org.testng.Assert;
import org.testng.annotations.Test;

import POJO.BooksResponse;
import POJO.Doc;
import io.restassured.specification.RequestSpecification;
import utilities.RequestSpecifications;

public class BooksAPITest {
	RequestSpecification  BookReq= RequestSpecifications.getReqSpecification();
	BooksResponse Books = given().spec(BookReq).when().get("/book").then().statusCode(200).extract().as(BooksResponse.class);
	
	public int getTotalBookCount() throws FileNotFoundException  {
		
		int totalBooks = Books.getTotal();
		System.out.println("Total book "+ totalBooks);
		return totalBooks;
	}

	

	public String checkIfBookExists(String Book_name) {
		for (Doc Bookname : Books.getDocs() ) {
			if (Bookname.getName().equalsIgnoreCase(Book_name)) {
				return Bookname.get_id();
			}
		}
		return "Book does not exists for entered BookName!!";
	}



	public String checkIfBookExistsByID(String Book_id) {
		for (Doc Bookname : Books.getDocs()) {
			if (Bookname.get_id().equalsIgnoreCase(Book_id)) {
				return Bookname.getName();
			}
		}
		return "Book does not exists for entered Book ID!!";
	}

}
