import java.text.NumberFormat;

import java.util.*;


public class StatementPrinter {

  final NumberFormat frmt = NumberFormat.getCurrencyInstance(Locale.US);

  public String toText(Invoice invoice, Map<String, Play> plays) {
    StringBuffer result = new StringBuffer("Statement for " + invoice.customer.name + "\n");

    for (Performance perf : invoice.performances) {
      // print every play
      result.append("  " + perfPlay(perf, plays).name + ": " + frmt.format(perfPlay(perf, plays).getPrice(perf.audience)) + " (" + perf.audience + " seats)\n");
    }
    if(invoice.customer.soldCredits >= 150){
      int totalPrices
   = totalPrices
  (invoice, plays) - 15;
      int totalCredits = invoice.customer.soldCredits - 150;

      result.append("Congratulations!\nYou have been deducted of 150 credits and 15$\n");
      result.append("Amount owed is " + frmt.format(totalPrices
  ) + "\n");
      result.append("You earned " + totalCredits + " credits\n");

      return result.toString();
    }
    result.append("Amount owed is " + frmt.format(totalPrices
(invoice, plays)) + "\n");
    result.append("You earned " + totalCredits(invoice, plays) + " credits\n");
    
    return result.toString();
  }

  public String toHTML(Invoice invoice, Map<String, Play> plays) {
    StringBuffer result = new StringBuffer("<!DOCTYPE html> \n"+"\t<html> \n"
    + "\t\t<body> \n"
    + "\t\t\t<h1>Invoice</h1> \n"+"\t\t\t<p><b>Client :</b> " 
    + invoice.customer.name + "</h2> \n"
    + "\t\t\t<table style=\"width: 400px\" border=\"1px solid black\"> \n"
    + "\t\t\t\t<tr> \n"+"\t\t\t\t\t <th>Piece</th> \n"+"\t\t\t\t\t <th>Seats sold</th> \n"
    + "\t\t\t\t\t <th>Price</th> \n"+"\t\t\t\t</tr> \n");
    
    for (Performance perf : invoice.performances) {
      result.append("\t\t\t\t<tr> \n"
        +"\t\t\t\t\t <td>" + perfPlay(perf, plays).name + "</td> \n"
        +"\t\t\t\t\t <td>" + perf.audience + "</td> \n"
        +"\t\t\t\t\t <td>" + frmt.format(perfPlay(perf, plays).getPrice(perf.audience)) + "</td> \n"
        +"\t\t\t\t</tr> \n");
    }

    if(invoice.customer.soldCredits >= 150){
      int totalPrices
   = totalPrices
  (invoice, plays) - 15;
      int totalCredits = invoice.customer.soldCredits - 150;

      result.append("\t\t\t\t<tr> \n"
      + "\t\t\t\t\t <td colspan=\"2\" style=\"text-align: right;\"><b>Total owed:<b></td> \n"
      + "\t\t\t\t\t <td>" + frmt.format(totalPrices
  )+ "</td> \n"
      + "\t\t\t\t</tr> \n");
      result.append("\t\t\t\t<tr> \n"
      + "\t\t\t\t\t <td colspan=\"2\" style=\"text-align: right;\"><b>Fidelity points earned:<b></td> \n"
      + "\t\t\t\t\t <td>" + totalCredits + "</td> \n"
      + "\t\t\t\t</tr> \n");

      result.append("\t\t\t</table> \n"
      + "\t\t\t<p>Congratulations! You have been deducted of 150 credits and 15$</p> \n"
      + "\t\t\t<p>Payment is required under 30 days. We can break your knees if you don't do so.</p> \n"
      + "\t\t</body> \n"
      + "\t</html> \n");
      return result.toString();
    }    

    result.append("\t\t\t\t<tr> \n"
    +"\t\t\t\t\t <td colspan=\"2\" style=\"text-align: right;\"><b>Total owed:<b></td> \n"
    +"\t\t\t\t\t <td>" + frmt.format(totalPrices
(invoice, plays))+ "</td> \n"
    +"\t\t\t\t</tr> \n");

    result.append("\t\t\t\t<tr> \n"
    + "\t\t\t\t\t <td colspan=\"2\" style=\"text-align: right;\"><b>Fidelity points earned:<b></td> \n"
    + "\t\t\t\t\t <td>" + totalCredits(invoice, plays) + "</td> \n"
    + "\t\t\t\t</tr> \n");

    result.append("\t\t\t</table> \n"
    + "\t\t\t<p>Payment is required under 30 days. We can break your knees if you don't do so.</p> \n"
    + "\t\t</body> \n"
    + "\t</html> \n");


    return result.toString();
  }

  private int totalPrices(Invoice invoice, Map<String, Play> plays){
    int result = 0;

    for(Performance perf: invoice.performances){
      result += perfPlay(perf, plays).getPrice(perf.audience);
    }
    return result;
  }

  private Play perfPlay(Performance perf, Map<String, Play> plays) {
    return plays.get(perf.playID);
  }

  private int totalCredits(Invoice invoice, Map<String, Play> plays){
    int result = 0;

    for(Performance perf: invoice.performances){
      result += perfPlay(perf, plays).getCredits(perf.audience);
    }
    return result;
  }

}
