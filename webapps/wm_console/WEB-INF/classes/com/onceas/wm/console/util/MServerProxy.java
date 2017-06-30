package com.onceas.wm.console.util;

import java.util.Iterator;
import java.util.Vector;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

import org.apache.commons.modeler.Registry;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: TCSE, ISCAS</p>
 *
 * @author not attributable
 * @version 1.0
 */
public class MServerProxy
{
  private static MBeanServer mServer;

  public static MBeanServer getMBeanServer()
  {
    if (mServer == null) {
      return mServer = Registry.getRegistry(null, null).getMBeanServer();
    }
    return mServer;
  }

  /**
   * 查询Web Container中制定类型的服务
   * @param queryStr String
   * @return String[]
   */
  @SuppressWarnings("unchecked")
public static ObjectName[] queryMBeans(String queryStr)
  {
    mServer = Registry.getRegistry(null, null).getMBeanServer();

    Vector v = new Vector();

    ObjectName application = null;
    try {
      application = new ObjectName(queryStr);
    }
    catch (NullPointerException ex) {
    }
    catch (MalformedObjectNameException ex) {
    }
    Iterator apps = mServer.queryNames(application, null).iterator();

    while (apps.hasNext()) {
      //strBuffer.append(apps.next().toString()+"\n\n");
      v.add( (ObjectName) apps.next());
    }
    ObjectName[] result = new ObjectName[v.size()];
    v.copyInto(result);

    return result;
  }

  public static ObjectName getWebModuleObjectName(String name)
  {
    ObjectName[] objectNames = MServerProxy.queryMBeans(
        "*:j2eeType=WebModule,*,name=" + name.trim());

    return objectNames[0];

  }

  public static ObjectName getWebModuleSessionManagerObjectName(String path)
  {
    ObjectName[] objectNames = MServerProxy.queryMBeans(
        "*:*,path=" + path.trim() + ",type=SessionManager");

    return objectNames[0];

  }

  public static ObjectName getWebModuleCacheObjectName(String path)
  {
    ObjectName[] objectNames = MServerProxy.queryMBeans(
        "*:*,path=" + path.trim() + ",type=Cache");

    return objectNames[0];

  }

  public static String[] getAttruibutes(String[] att, ObjectName targetObjectName) throws Exception{
     String[] attList = new String[att.length];

     for(int i = 0; i < att.length; i++){
       if (MServerProxy.getMBeanServer().getAttribute(targetObjectName,
                                                      att[i]) != null) {
         attList[i] = MServerProxy.getMBeanServer().getAttribute(targetObjectName,
             att[i]).
             toString();
       }
       else{
         attList[i] = "-";
       }
     }
     return attList;

  }

}
