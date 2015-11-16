import java.lang.reflect.*;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

import org.jdom2.*;

public class Deserializer
{

  public Object deserialize(Document document) throws Exception
  {
    List objList = document.getRootElement().getChildren();

    Map table = new HashMap();

    createInstances(table, objList);

    assignFieldValues(table, objList);

    return table.get("0");
  }

  private void createInstances(Map table, List objList) throws Exception
  {
    for (int i = 0; i < objList.size(); i++)
    {
        Element objectElement = (Element) objList.get(i);
        Class c = Class.forName(objectElement.getAttributeValue("class"));
        Object instance = null;

        if(!c.isArray())
        {
          Constructor construct = c.getDeclaredConstructor(null);
            if(!Modifier.isPublic(construct.getModifiers()))
            {
              construct.setAccessible(true);
            }
            instance = construct.newInstance(null);
        }
        else
        {
            instance = Array.newInstance(c.getComponentType(), Integer.parseInt(objectElement.getAttributeValue("length")));
        }
        table.put(objectElement.getAttributeValue("id"), instance);
    }
  }

  private void assignFieldValues(Map table, List objList) throws Exception
  {
    for(int i = 0; i < objList.size(); i++)
    {
      Element objectElement = (Element) objList.get(i);
      Object instance = table.get(objectElement.getAttributeValue("id"));

      List fieldElts = objectElement.getChildren();

      if(!instance.getClass().isArray())
      {
        for(int j = 0; j < fieldElts.size(); j++)
        {
          Element fieldElement = (Element) fieldElts.get(j);
          String className = fieldElement.getAttributeValue("declaringClass");
          Class fieldDC = Class.forName(className);
          String fieldName = fieldElement.getAttributeValue("name");
          Field f = fieldDC.getDeclaredField(fieldName);

          if(!Modifier.isPublic(f.getModifiers()))
            f.setAccessible(true);

          Element valueElement = (Element) fieldElement.getChildren().get(0);
          f.set(instance, deserializeValue(valueElement, f.getType(), table));
        }
      }
      else
      {
        Class compType = instance.getClass().getComponentType();
        for(int j = 0; j < fieldElts.size(); j++)
        {
          Array.set(instance, j, deserializeValue((Element) fieldElts.get(j), compType, table));
        }
      }
    }
  }

  public Object deserializeValue(Element valueElement, Class fieldType, Map table) throws ClassNotFoundException
  {
    String valueType = valueElement.getName();

    if(valueType.equals("null"))
    {
      return null;
    }
    else if(valueType.equals("reference"))
    {
      return table.get(valueElement.getText());
    }
    else
    {
      if(fieldType.equals(Boolean.class))
      {
        if(valueElement.getText().equals("true"))
        {
          return Boolean.TRUE;
        }
        else
        {
          return Boolean.FALSE;
        }
      }
      else if(fieldType.equals(byte.class))
      {
        return Byte.valueOf(valueElement.getText());
      }
      else if(fieldType.equals(short.class))
      {
        return Short.valueOf(valueElement.getText());
      }
      else if(fieldType.equals(int.class))
      {
        return Integer.valueOf(valueElement.getText());
      }
      else if(fieldType.equals(long.class))
      {
        return Long.valueOf(valueElement.getText());
      }
      else if(fieldType.equals(float.class))
      {
        return Float.valueOf(valueElement.getText());
      }
      else if(fieldType.equals(double.class))
      {
        return Double.valueOf(valueElement.getText());
      }
      else if(fieldType.equals(char.class))
      {
        return new Character(valueElement.getText().charAt(0));
      }
      else
      {
        return valueElement.getText();
      }
    }
  }

}
