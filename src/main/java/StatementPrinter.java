import java.text.NumberFormat;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


public class StatementPrinter {

  final NumberFormat frmt = NumberFormat.getCurrencyInstance(Locale.US);

  public String printHTML(Invoice invoice, Map<String, Play> plays) {
    String result = "";
    try {
      result = Files.readString(Paths.get(getClass().getResource("templates\\HTMLTemplate.txt").toURI()));
    } catch (Exception e) {
      throw new Error("Cannot read template");
    }
    
    StringBuffer invoiceItems = new StringBuffer();
    for (Performance perf : invoice.performances) {
      invoiceItems.append(
              "<tr>\n"
              + "<td>" + perfPlay(perf, plays).name  + "</td>\n"
              + "<td>" + frmt.format(perfPlay(perf, plays).getPrice(perf.audience)) + "</td>\n"
              + "<td>" + perf.audience + "</td>\n"
              + "</tr>\n");
    }

    result.replace("{$Invoice_Items}", invoiceItems.toString());
    result.replace("{@Invoice_Amount}", Integer.toString(totalAmount(invoice, plays)));
    result.replace("{$Total_Credits}", Integer.toString(volumeCredits(invoice, plays))); 

    return result;
  }

  public String print(Invoice invoice, Map<String, Play> plays) {
    StringBuffer result = new StringBuffer("Statement for " + invoice.customer + "\n");

    for (Performance perf : invoice.performances) {
      // print every play
      result.append("  " + perfPlay(perf, plays).name + ": " + frmt.format(perfPlay(perf, plays).getPrice(perf.audience)) + " (" + perf.audience + " seats)\n");
    }

    result.append("Amount owed is " + frmt.format(totalAmount(invoice, plays)) + "\n");
    result.append("You earned " + volumeCredits(invoice, plays) + " credits\n");
    
    return result.toString();
  }

  private int totalAmount(Invoice invoice, Map<String, Play> plays){
    int result = 0;

    for(Performance perf: invoice.performances){
      result += perfPlay(perf, plays).getPrice(perf.audience);
    }
    return result;
  }

  private Play perfPlay(Performance perf, Map<String, Play> plays) {
    return plays.get(perf.playID);
  }

  private int volumeCredits(Invoice invoice, Map<String, Play> plays){
    int result = 0;

    for(Performance perf: invoice.performances){
      result += perfPlay(perf, plays).getCredits(perf.audience);
    }
    return result;
  }

}
