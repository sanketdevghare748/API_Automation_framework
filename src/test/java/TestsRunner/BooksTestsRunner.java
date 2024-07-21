package TestsRunner;

/** Testcases:
 * 1. Verify the number of books returned and status code of the request.
 * 2. Check if the list of books returned has Books with name= "The Return Of The King" if yes return book ID
 * 3. Get the name of the book from ID. (5cf58077b53e011a64671583, "The Two Towers".
 */
import java.io.FileNotFoundException;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import Locus_SDET_Assignment.Books_movies_APIs.BooksAPITest;

public class BooksTestsRunner {

	BooksAPITest BookResp = new BooksAPITest();

	// Verify total Books count.
	@Test
	public void verifyTotalBooks() throws FileNotFoundException {
		Assert.assertEquals(BookResp.getTotalBookCount(), 3);
		Reporter.log("Verify Total Book count test is passed..",true);
	}

	@Test
	public void VerifybookIdbyname() {
		//System.out.println(BookResp.checkIfBookExists("The Return Of The King"));
		Assert.assertEquals(BookResp.checkIfBookExists("The Return Of The King"), "5cf58080b53e011a64671584");
		Reporter.log("Verify BookID by bookname test is passed..",true);
	}

	@Test
	public void VerifyBookNameById() {
		//System.out.println(BookResp.checkIfBookExistsByID("5cf58080b53e011a64671584"));
		Assert.assertEquals(BookResp.checkIfBookExistsByID("5cf58077b53e011a64671583"), "The Two Towers");
		Reporter.log("Verify book name from book ID  test is passed..",true);
	}
}
