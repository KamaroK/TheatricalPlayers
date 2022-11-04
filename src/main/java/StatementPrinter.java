import java.text.NumberFormat;
import java.util.*;


public class StatementPrinter {


  public StringBuffer print(Invoice invoice, Map<String, Play> plays) {
    int totalAmount = 0;
    int volumeCredits = 0;
    StringBuffer result = new StringBuffer("Statement for " + invoice.customer + "\n");

    NumberFormat frmt = NumberFormat.getCurrencyInstance(Locale.US);

    for (Performance perf : invoice.performances) {

      volumeCredits += volumeCredits(perf, plays);

      // print line for this order
      result.append("  " + perfPlay(perf, plays).name + ": " + frmt.format(totalAmount(perf, plays) / 100) + " (" + perf.audience + " seats)\n");
      totalAmount += totalAmount(perf, plays);
    }
    result.append("Amount owed is " + frmt.format(totalAmount / 100) + "\n");
    result.append("You earned " + volumeCredits + " credits\n");
    
    return result;
  }

  private int totalAmount(Performance perf, Map<String, Play> plays)
  {
    int result = 0;

    switch (perfPlay(perf, plays).type) {
      case "tragedy":
        result = 40000;
        if (perf.audience > 30) {
          result += 1000 * (perf.audience - 30);
      }
      break;
      case "comedy":
        result = 30000;
        if (perf.audience > 20) {
          result += 10000 + 500 * (perf.audience - 20);
        }
        result += 300 * perf.audience;
        break;
        default:
          throw new Error("unknown type: ${perfPlay(perf, plays).type}");
      }
    return result;
  }

  private Play perfPlay(Performance perf, Map<String, Play> plays) {
    return plays.get(perf.playID);
  }

  private int volumeCredits(Performance perf, Map<String, Play> plays){
    int result = 0;
    result += Math.max(perf.audience - 30, 0);

    // add extra credit for every ten comedy attendees
    if ("comedy".equals(perfPlay(perf, plays).type)) 
      result += Math.floor(perf.audience / 5);
    return result;
  }
}
