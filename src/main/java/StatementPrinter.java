import java.text.NumberFormat;
import java.util.*;

import javax.management.loading.PrivateClassLoader;

public class StatementPrinter {


  public StringBuffer print(Invoice invoice, Map<String, Play> plays) {
    int totalAmount = 0;
    int volumeCredits = 0;
    StringBuffer result = new StringBuffer("Statement for " + invoice.customer + "\n");

    NumberFormat frmt = NumberFormat.getCurrencyInstance(Locale.US);

    for (Performance perf : invoice.performances) {
      Play play = plays.get(perf.playID);
      int thisAmount = totalAmount(perf, play);

      // add volume credits
      volumeCredits += Math.max(perf.audience - 30, 0);
      // add extra credit for every ten comedy attendees
      if ("comedy".equals(play.type)) 
        volumeCredits += Math.floor(perf.audience / 5);

      // print line for this order
      result.append("  " + play.name + ": " + frmt.format(thisAmount / 100) + " (" + perf.audience + " seats)\n");
      totalAmount += thisAmount;
    }
    result.append("Amount owed is " + frmt.format(totalAmount / 100) + "\n");
    result.append("You earned " + volumeCredits + " credits\n");
    
    return result;
  }

  private int totalAmount(Performance perf, Play play)
  {
    int thisAmount = 0;

    switch (play.type) {
      case "tragedy":
        thisAmount = 40000;
        if (perf.audience > 30) {
          thisAmount += 1000 * (perf.audience - 30);
      }
      break;
      case "comedy":
        thisAmount = 30000;
        if (perf.audience > 20) {
          thisAmount += 10000 + 500 * (perf.audience - 20);
        }
        thisAmount += 300 * perf.audience;
        break;
        default:
          throw new Error("unknown type: ${play.type}");
      }
    return thisAmount;
  }

}
