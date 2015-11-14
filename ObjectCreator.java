import java.util.Vector;
import java.util.Scanner;

public class ObjectCreator
{

  //scanner for user Input
  private Scanner user_input;


    public ObjectCreator()
    {
      user_input = new Scanner(System.in);
    }

  //classes to represent the different Objects
  private class SimpleObject{

    private int a;
    private int b;

    public SimpleObject(Int a, Int b){
        this.a = a;
        this.b = b;
    }
  }

  private class ObjectReferenceObjects{

    private Object objA;
    private Object objB;

    public ObjectReferenceObjects(Object objA, Object objB){
      this.objA = objA;
      this.objB = objB;
    }
  }

  private class ObjectPrimitivesArray{

    private int[] intArray;
    private final int LENGTH = 5;

    public ObjectPrimitivesArray(){
      intArray = new int[LENGTH];
    }

  }


  public Object createObject()
  {
    //get user Input for selection
    System.out.println("Please enter the type of object you want to create: (0-5)");
    System.out.println("(0) Nothing [World Explodes!] don't do it!!!");
    System.out.println("(1) SimpleObject");
    System.out.println("(2) ObjectReferenceObjects");
    System.out.println("(3) ObjectPrimitivesArray");
    System.out.println("(4) ObjectReferencesArray");
    System.out.println("(5) ObjectsCollectionObject");

    int selection = Integer.parseInt(user_input.next());

    Object object = null;

    switch(selection)
    {
      case 0:
        sender.connected = false;
        break;
      case 1:
        object = createSimpleObject();
        break;
      case 2:
        object = createObjectReferenceObjects();
        break;
      case 3:
        object = createObjectPrimitivesArray();
        break;
      case 4:
        object = createObjectReferencesArray();
      case 5:
        object = createObjectsCollectionObject();
        break;
    }
    return object;
  }

  private SimpleObject createSimpleObject()
  {
    System.out.println("---Creating Simple Object---");
    System.out.println("Enter an int value for field a: ");
    int a = Integer.parseInt(user_input.next());
    System.out.println("Enter an int value for field b: ");
    int b = Integer.parseInt(user_input.next());
    System.out.println("---Finished Simple Object---");

    return SimpleObject(a, b);
  }

  private ObjectReferenceObjects createObjectReferenceObjects(){
    System.out.println("---Creating Object Reference Objects---");
    Object objA = createSimpleObject();
    Object objB = createSimpleObject();
    System.out.println("---Finished Object Reference Objects---");

    return ObjectReferenceObjects(objA, objB);
  }

  private ObjectPrimitivesArray createObjectPrimitivesArray(){
    System.out.println("---Creating Object Primitives Array---");
    ObjectPrimitivesArray objPrimsArray = new ObjectPrimitivesArray();
    System.out.println("Please enter int values:");
    System.out.println("Primitive array:");
    for(i = 0; i < objPrimsArray.intArray.length; i++){
      System.out.print("[" + (5 - i) + "] : ");
      objPrimsArray.intArray[i] = Integer.parseInt(user_input.next());
      System.out.print("\n");
    }
    System.out.println("---Finished Object Primitives Array---");

    return objPrimsArray;
  }

  private ObjectReferencesArray createObjectReferencesArray(){

  }

  private ObjectsCollectionObject createObjectsCollectionObject()
  {
    Vector<>
  }
}
