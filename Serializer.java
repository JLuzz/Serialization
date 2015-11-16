import java.lang.reflect.*;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.Map;

import org.jdom2.*;

public class Serializer
{

  public Serializer()
  {

  }

  public Document serialize(Object object) throws Exception
  {
    return serializeHelper(object, new Document(new Element("serialized")), new IdentityHashMap());
  }

  public Document serializeHelper(Object object, Document target, Map referenceMap) throws Exception
  {
    //put object into table with unique id
    String id = Integer.toString(referenceMap.size());
    referenceMap.put(object, id);
    Class<?> c = object.getClass();

    //create Element for object
    Element objectElement = new Element("object");
    objectElement.setAttribute(new Attribute("class", c.getName()));
    objectElement.setAttribute(new Attribute("id", id));
    target.getRootElement().addContent(objectElement);

    if(!c.isArray())
    {
      Field[] fields = c.getDeclaredFields();
      for (int i = 0; i < fields.length; i++)
      {
        if(Modifier.isTransient(fields[i].getModifiers()) || Modifier.isFinal(fields[i].getModifiers()) || Modifier.isStatic(fields[i].getModifiers()))
          continue;
        if (!fields[i].isAccessible())
          fields[i].setAccessible(true);
        Element fieldElement = new Element("field");
        fieldElement.setAttribute(new Attribute("name", fields[i].getName()));
        fieldElement.setAttribute(new Attribute("declaringClass", fields[i].getDeclaringClass().getName()));

        Class fieldType = fields[i].getType();
        Object child = fields[i].get(object);

        fieldElement.addContent(serializeField(fieldType, child, target, referenceMap));

        objectElement.addContent(fieldElement);
      }
    }
    else
    {
      Class componentType = c.getComponentType();

      int length = Array.getLength(object);
      objectElement.setAttribute(new Attribute("length", Integer.toString(length)));

      for(int i = 0; i < length; i++){
        objectElement.addContent(serializeField(componentType, Array.get(object, i), target, referenceMap));
      }
    }
    return target;
  }

  private Element serializeField(Class fieldType, Object child, Document target, Map referenceMap) throws Exception
  {
    if(child == null){
      return new Element("null");
    }
    else if (!fieldType.isPrimitive())
    {
      Element reference = new Element("reference");

      if(referenceMap.containsKey(child))
      {
        reference.setText(referenceMap.get(child).toString());
      }
      else
      {
        reference.setText(Integer.toString(referenceMap.size()));
        serializeHelper(child, target, referenceMap);
      }
      return reference;
    }
    else
    {
      Element value = new Element("value");
      value.setText(child.toString());
      return value;
    }
  }
}
