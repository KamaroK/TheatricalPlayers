public abstract class Play {
  
  public String name;

  public Play() { }

  public Play(String name) {
    this.name = name;
  }

  public abstract float getPrice(int audience);

  public abstract int getCredits(int audience);
}
