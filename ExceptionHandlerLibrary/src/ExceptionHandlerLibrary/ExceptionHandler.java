package ExceptionHandlerLibrary;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
//import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ExceptionHandler {
	private static Properties props=new Properties();
	private Action action; //initialize this at run time'

	
	static {
		try {
			props.load(new FileReader("ActionClassMaps.properties"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	static void handleException(String projName,String moduleName,Exception ex)
	{
		//read configuration file and find out the action to be taken based on configuration
		//instantiate the Action class relevant to the action
		//call takeAction method by passing params relevant to Action
		
		
		try
		{
			DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
			DocumentBuilder builder=factory.newDocumentBuilder();
			Document doc=builder.parse("D:\\Eclipse Workspace\\ExceptionHandlerLibrary\\config.xml");
			
			Element rootElement = doc.getDocumentElement();
			String projectName=rootElement.getAttribute("name");
			System.out.println(projectName);
			
			//modules
			NodeList moduleNodes=rootElement.getChildNodes();
			
			for(int i=0;i<moduleNodes.getLength();i++)  //module
			{
				Node moduleNode=moduleNodes.item(i);
				if(moduleNode.getNodeType()==Node.ELEMENT_NODE)
				{
					System.out.println("Module name is="+((Element)moduleNode).getAttribute("name"));
					
					//exceptions
					NodeList exceptionNodes=moduleNode.getChildNodes();   
					for(int j=0;j<exceptionNodes.getLength();j++)       
					{
						Node exceptionNode=exceptionNodes.item(j);
						if(exceptionNode.getNodeType() == Node.ELEMENT_NODE)
						{
							//System.out.println("Exception name is="+((Element)exceptionNode).getNodeName());

							String exceptionName=((Element)exceptionNode).getNodeName();
							System.out.println(exceptionName+" : "+ex.getClass().getSimpleName());

							
							
							if(exceptionName.equals(ex.getClass().getSimpleName()))
							{
								//action
								NodeList actionNodes=exceptionNode.getChildNodes();    //actions
								for(int k=0;k<actionNodes.getLength();k++)
								{
									Node actionNode=actionNodes.item(k);
									if(actionNode.getNodeType()==Node.ELEMENT_NODE)
									{
										String actionName=((Element)actionNode).getAttribute("name");
										System.out.println("Action name is="+actionName);
										
										NamedNodeMap attributeMap=actionNode.getAttributes();
										Map nvPair=new HashMap();

										for(int l=0;l<attributeMap.getLength();l++)
										{
											Node attrNode=attributeMap.item(l);
											if(attrNode!=null)
											{
												String attributeName=attributeMap.item(l).getNodeName();
												String attributeValue=attributeMap.item(l).getNodeValue();
												if(!attributeName.equals("name"))
												{
													nvPair.put(attributeName, attributeValue);
												}
											}

										}
										//get name of class for this action
										String className=props.getProperty(actionName);
										System.out.println("Class name="+className);
										Action action=(Action) Class.forName(className).newInstance();
										action.takeAction(nvPair);
										
									}
												
													
											
								}
							}
							
							
				}
			}
		}
		}
		}
		catch(Throwable t)
		{
			t.printStackTrace();
		}
		
	

}
}


