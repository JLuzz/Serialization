public class ObjectReferencesArray{

  protected Object[] objArray;
  private final int LENGTH = 5;

  public ObjectReferencesArray(){
    objArray = new Object[LENGTH];
    objArray[0] = new Object();
    objArray[1] = new Object();
    objArray[2] = new Object();
    objArray[3] = new Object();
    objArray[4] = new Object();
  }
}
