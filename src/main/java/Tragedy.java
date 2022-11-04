public class Tragedy extends Play{

    public Tragedy(String name) {
        this.name = name;
      }
    
      public float getPrice(int audience) {
        return 400 + ((audience > 30) ? 10 * (audience - 30) : 0);
      }
    
      public int getCredits(int audience) {
        return Math.max(audience - 30, 0);
      }
}
