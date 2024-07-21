package utilities;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import resources.GetProperties;

public class RequestSpecifications {
		static RequestSpecification BookReq;
		static RequestSpecification MovieReq;
		public static RequestSpecification getReqSpecification() 		{
			String Baseuri = GetProperties.getProperty("BaseURI");
			
			//if(BookReq==null) {
			
			//PrintStream log = new PrintStream(new FileOutputStream("logs.txt"));
			
			BookReq = new RequestSpecBuilder().setBaseUri(Baseuri).setContentType(ContentType.JSON)
					//.addFilter(RequestLoggingFilter.logRequestTo(log))
					//.addFilter(ResponseLoggingFilter.logResponseTo(log))
					.build();			
			//return BookReq;
			//}
			
			return BookReq;
		}
		
		public static RequestSpecification getMovieReqSpecfication()
		{
			String Baseuri = GetProperties.getProperty("BaseURI");
			String BearerToken=GetProperties.getProperty("BearerToken");
			MovieReq = new RequestSpecBuilder().setBaseUri(Baseuri).setContentType(ContentType.JSON).build();
			return MovieReq;
		}
	}

