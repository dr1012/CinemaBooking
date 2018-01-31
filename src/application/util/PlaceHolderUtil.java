package application.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class PlaceHolderUtil {
	
	private static Random rand = new Random();
	private static Text quoteText = new Text();
	private static Text messageText = new Text();
	
	private static ArrayList<String> quotes = new ArrayList<String>(
			Arrays.asList("\"Be not afraid of greatness.\" \n\n ― William Shakespeare, Twelfth Night",
					"\"Come, seeling night, \n Scarf up the tender eye of pitiful day\" \n\n ― William Shakespeare, Macbeth",
					"\"This majestical roof fretted with golden fire, why, appears no other thing to me than a foul and pestilent congregation of vapours.\" \n\n ― William Shakespeare, Hamlet")
			);
	
	private static ArrayList<String> messages = new ArrayList<String>(
			Arrays.asList("Nothing to Display!",
					"Nothing to see here...",
					"Table empty!")
			);
	
	
	public static Text getMessage() {
		String message;
		
		message = messages.get(rand.nextInt(3));
		
		messageText.setWrappingWidth(300);
		messageText.setFill(Color.WHITE);
		messageText.setTextAlignment(TextAlignment.CENTER);
		messageText.setFont(new Font(18));
		
		messageText.setText(message);
		
		return messageText;
	}
	
	
	public static Text getQuote() {
		String quote;
		
		quote = quotes.get(rand.nextInt(3));
		
		quoteText.setWrappingWidth(300);
		quoteText.setFill(Color.BEIGE);
		quoteText.setTextAlignment(TextAlignment.CENTER);
		quoteText.setFont(new Font(12));
		
		quoteText.setText(quote);
		
		
		return quoteText;
	}
	
	
	
	
	
	
	

}
