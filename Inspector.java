/*
Inspector.java
Created by: Jeremy Luzzi
for assignment 2 cpsc 501

solutions for findFields and findFieldClasses influenced by
the inspectField, and inspectFieldsClasses in ObjectInspector.java
created by Jordan Kidney from the University of Calgary
*/


import java.lang.reflect.*;
import java.util.*;

public class Inspector{

  public Inspector(){

  }

  public void inspect(Object object, boolean recursive){
    Vector fieldsToInspect = new Vector();
    Class objClass = object.getClass();

    //find the declaring class
    System.out.println("***Declaring Class***");
    findClassName(objClass);

    //find the immediate superclass
    System.out.println("***Immediate superclass***");
    findImmidiateSuperClass(objClass);

    //find interfaces implemented by this class object
    System.out.println("***Interfaces***");
    findInterfaces(objClass);

    //find methods implemented by this class object
    System.out.println("***Methods***");
    findMethods(objClass);

    //find constructors for the class object
    System.out.println("***Constructors***");
    findContructors(objClass);

    //find the fields for the class object
    System.out.println("***Fields***");
    findFields(object, objClass, fieldsToInspect);

    //finds information on the fields of an object recursivley
    if(recursive){
      findFieldClasses(object, objClass, fieldsToInspect, recursive);
    }
  }

  public void findClassName(Class objClass){
    //print the declaring class name
    System.out.println(objClass.getName());
  }

  public void findImmidiateSuperClass(Class objClass){
    //find superclass
    Class objSuperclass = objClass.getSuperclass();
    //print superclass
    System.out.println(objSuperclass.getName());
  }

  public void findInterfaces(Class objClass){
    //find interfaces
    Class[] interfaces = objClass.getInterfaces();
    //print interfaces
    for (Class interf : interfaces){
      System.out.println("Interface: " + interf.getName());
    }
  }

  public void findMethods(Class objClass){
    Method[] methods = objClass.getDeclaredMethods();
    for (Method meth : methods){
      System.out.println("Method: " + meth.getName());
      findExceptions(meth);
      findParameters(meth);
      findReturnType(meth);
      findModifiers(meth);
    }
  }

  public void findContructors(Class objClass){
    Constructor[] constructors = objClass.getDeclaredConstructors();
    for (Constructor construct : constructors){
      System.out.println("Constructor: " + construct.getName());
      findParameters(construct);
      findModifiers(construct);
    }
  }

//Methods for finding info about an objects methods
  public void findExceptions(Method meth){
    Class[] exceptions = meth.getExceptionTypes();
    System.out.println("\t***Exceptions***");
    for (Class excep : exceptions){
      System.out.println("\tException:" + excep.getName());
    }
  }

  public void findParameters(Method meth){
    Class[] params = meth.getParameterTypes();
    System.out.println("\t***Parameters***");
    for (Class param : params){
      System.out.println("\tParameters:" + param.getName());
    }
  }

  public void findReturnType(Method meth){
    Class returnType = meth.getReturnType();
    System.out.println("\t***Return Type***\n\tReturn: " + returnType.getName());
  }

  public void findModifiers(Method meth){
      int modCode = meth.getModifiers();
      System.out.println("\t***Modifiers***");
      System.out.println("\tModifier: " + Modifier.toString(modCode));
  }

  //Methods for finding info on about an objects constructors
  public void findParameters(Constructor construct){
    Class[] params = construct.getParameterTypes();
    System.out.println("\t***Parameters***");
    for (Class param : params){
      System.out.println("\tParameter: " + param.getName());
    }
  }

  public void findModifiers(Constructor construct){
    int modCode = construct.getModifiers();
    System.out.println("\t***Modifiers***");
    System.out.println("\tModifier: " + Modifier.toString(modCode));
  }

  //Methods for finding out an objects fields recursivley
  public void findFields(Object obj, Class objClass, Vector fieldsToInspect){

    if(objClass.getDeclaredFields().length >= 1){
    	Field fld = objClass.getDeclaredFields()[0];

    	fld.setAccessible(true);

    	if(!fld.getType().isPrimitive())
    		    fieldsToInspect.addElement(fld);

    	try{
    		System.out.println("Field: " + fld.getName()
        + "\n\t Type: " + fld.getType()
        + "\n\t Modifiers: " + Modifier.toString(fld.getModifiers())
        + "\n\t Value: " + fld.get(obj));
    	}catch(Exception e) {}
    }

    if(objClass.getSuperclass() != null)
    	findFields(obj, objClass.getSuperclass() , fieldsToInspect);

  }

  public void findFieldClasses(Object obj, Class objClass, Vector fieldsToInspect, boolean recursive){

    if(fieldsToInspect.size() > 0 )
  	    System.out.println("<<<<<<Field Classes(Recursive Inspection)>>>>>>");

  	Enumeration e = fieldsToInspect.elements();
  	while(e.hasMoreElements())
  	    {
  		Field fld = (Field) e.nextElement();
  		System.out.println("Field: " + fld.getName() );

  		try
  		    {
  			System.out.println("xxxxxxxxxxxxxxxxxxxxxxxx");
  			inspect( fld.get(obj) , recursive);
  			System.out.println("xxxxxxxxxxxxxxxxxxxxxxxx");
  		    }
  		catch(Exception exp) { exp.printStackTrace();
      System.out.println("tacos");}
  	    }
      }
}
