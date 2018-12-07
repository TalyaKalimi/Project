package All;


public class LongestRepeatedSubstring {

  // Example usage
  public static void main(String[] args) {

    String str = "ABC$BCA$CAB";
    SuffixArray sa = new SuffixArray(str);
    System.out.printf("LRS(s) of %s is/are: %s\n", str, sa.lrs());

    str = "<p style=\"color:Tomato;\"><p style=\"color:DodgerBlue;\"></p><p style=\"color:MediumSeaGreen;\">";
    sa = new SuffixArray(str);
    //sa.display();
    System.out.printf("LRS(s) of %s is/are: %s\n", str, sa.lrs());

    str = "ARTISTBobDylanARTISTCOUNTRYUSACOUNTRYCOMPANYColumbiaCOMPANYPRICE10.90PRICEYEAR1985YEAR";
    sa = new SuffixArray(str);
    System.out.printf("LRS(s) of %s is/are: %s\n", str, sa.lrs());
    
    /*str =  "  <CD>\r\n" + 
			"    <TITLE>Empire Burlesque</TITLE>\r\n" + 
			"    <ARTIST>Bob Dylan</ARTIST>\r\n" + 
			"    <COUNTRY>USA</COUNTRY>\r\n" + 
			"    <COMPANY>Columbia</COMPANY>\r\n" + 
			"    <PRICE>10.90</PRICE>\r\n" + 
			"    <YEAR>1985</YEAR>\r\n" + 
			"  </CD>\r\n" + 
			"  <CD>\r\n" + 
			"    <TITLE>Hide your heart</TITLE>\r\n" + 
			"    <ARTIST>Bonnie Tyler</ARTIST>\r\n" + 
			"    <COUNTRY>UK</COUNTRY>\r\n" + 
			"    <COMPANY>CBS Records</COMPANY>\r\n" + 
			"    <PRICE>9.90</PRICE>\r\n" + 
			"    <YEAR>1988</YEAR>\r\n" + 
			"  </CD>\r\n" + 
			"  <CD>\r\n" + 
			"    <TITLE>Greatest Hits</TITLE>\r\n" + 
			"    <ARTIST>Dolly Parton</ARTIST>\r\n" + 
			"    <COUNTRY>USA</COUNTRY>\r\n" + 
			"    <COMPANY>RCA</COMPANY>\r\n" + 
			"    <PRICE>9.90</PRICE>\r\n" + 
			"    <YEAR>1982</YEAR>\r\n" + 
			"  </CD>";
    sa = new SuffixArray(str);
    System.out.printf("LRS(s) of %s is/are: %s\n", str, sa.lrs());*/
    
  }

}
