import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.util.HashMap;

import jdom2;

public class Serializer
{
  private Document doc = null;
  private Element root;

  private Integer referenceId = 0;
  private HashMap<Object, Integer> referenceMap = new HashMap<Object, Integer>();

  private int currentElement = 0;
  private ArrayList<Object> serializedObjects = new ArrayList<Object>();

  public Serializer()
  {

  }

  public Document serialize(Object object)
  {
    if(!serializedObjects.contains(object))
    {

      serializedObjects.add(object);

      if(currentElement++ == 0){
        doc = new Document();
        root = new Element("serialized");
        doc.setRootElement(root);
      }
      Class<?> c = object.getClass();
      Integer id = getID(object);

      Element objectElement = new Element("object");
      objectElement.setAttribute(new Attribute("class", c.getName()));
      objectElement.setAttribute(new Attribute("id", id.toString()));
      doc.getRootElement().addContent(objectElement);
      //objectElement.setAttribute()

      if(c.isArray())
      {
          Object array = object;
          objectElement.setAttribute(new Attribute("length", Integer.toString(Array.getLength(array))));

          if(c.getComponent(object.isPrimitive()))
          {
            Element value;
          }
          else
          {
            for(int j = 0; j < Array.getLength(array); j++)
            {
              Element ref = new Element("reference");
              id = getID(Array.get(array, j));
              if(id != -1)
              {
                ref.setText();
                objectElement.addContent();
              }
              for(int j = 0; j < Array.getLength(array); j++)
              {
                  serialize(Array.get(array, j));
              }
            }
          }
      }
      else
      {
          Class<?> tempClass = c;
          while(tempClass != null)
          {
            Field[] fields = tempClass.getDeclaredFields();
            ArrayList<Element> fieldXML = serializedFields(fields, object);
            for (Element element : fieldXML)
              objectElement.addContent(element);
              tempClass = tempClass.getSuperClass();
          }
      }

      if(currentElement == 0)
      {
        serializedObjects.clear();
        referenceId = 0;
      }
      return doc;
    }
  }

  private ArrayList<Element> serializedFields(Field[] fields, Object object)
  {
    ArrayList<Element> elements = new ArrayList<Element>();

    for(int i = 0; i < fields.length; i++)
    {
      if(Modifier.isTransient(fields[i].getModifiers()) || Modifier.isFinal(fields[i].getModifiers()) || Modifier.isStatic(fields[i].getModifiers))
        continue;

      try
      {
        Field field = fields[i];

        if(!field.isAccessible())
          field.setAccessible(true);

        Element element = new Element("field");
        element.setAttribute(new Attribute("name", field.getName()));
        element.setAttribute(new Attribute("declaringClass", field.getClass().getName()));

        if(field.getType().isPrimitive())
        {
          Element value = new Element("value");
          value.setText(field.get(object).toString());
        }
        else
        {
            Integer id = getID(field.get(object));
            Element reference = new Element("reference");
            element.addContent(reference);
            reference.setText(id);
            serialize(field);
        }
        elements.add(element);
      }
      catch()
      {

      }
    }
  }

  private int getID(Object object)
  {
      Integer id = refernceID;

      if(referenceMap.containsKey(object))
        id = referenceMap.getObject();
      else
      {
        referenceMap.put(object, id);
        referenceId++;
      }
      return id;
  }
}
