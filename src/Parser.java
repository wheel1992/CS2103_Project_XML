import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;


public class Parser {

	final String NODE_ROOT_TAG = "tasks";
	final String NODE_TASK_TAG = "task";
	final String NODE_TASK_ID_TAG = "id";
	final String NODE_TASK_TITLE_TAG = "title";
	final String NODE_TASK_START_MILLISECOND_TAG = "start_millisecond";
	final String NODE_TASK_END_MILLISECOND_TAG = "end_millisecond";
	final String NODE_TASK_START_TIME_TAG = "start_time";
	final String NODE_TASK_START_DATE_TAG = "start_date";
	final String NODE_TASK_END_TIME_TAG = "end_time";
	final String NODE_TASK_END_DATE_TAG = "end_date";
	final String NODE_TASK_IS_DONE_TAG = "is_done";
	//default constructor
	public Parser(){
		
	}
	
	
	public boolean tableToXml(String xmlFilePath, HashMap<Integer, Task> mHashMap){
		boolean isSaved = false;
		try{
			Node nRoot = null;
			Node nTask = null;
			
			File mXmlFile = new File(xmlFilePath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(mXmlFile);
			
			normalize(doc);
			
			//remove all nodes from XML
			xmlRemoveAllTask(doc);
			
			//create a new root node
			nRoot = doc.createElement(NODE_ROOT_TAG);
			
			for(Task mTask : mHashMap.values()){
				//new task node
				nTask = doc.createElement(NODE_TASK_TAG);

				//create new node method
				createNode(nTask, NODE_TASK_ID_TAG, String.valueOf(mTask.getID()));
				createNode(nTask, NODE_TASK_TITLE_TAG, mTask.getTitle());
				createNode(nTask, NODE_TASK_START_MILLISECOND_TAG, mTask.getStartMillisecond());
				createNode(nTask, NODE_TASK_END_MILLISECOND_TAG, mTask.getEndMillisecond());
				createNode(nTask, NODE_TASK_START_TIME_TAG, mTask.getStartTime());
				createNode(nTask, NODE_TASK_END_TIME_TAG, mTask.getEndTime());
				createNode(nTask, NODE_TASK_START_DATE_TAG, mTask.getStartDate());
				createNode(nTask, NODE_TASK_END_DATE_TAG, mTask.getEndDate());
				createNode(nTask, NODE_TASK_IS_DONE_TAG, String.valueOf(mTask.getIsDone()));
				
				//add task node to root
				nRoot.appendChild(nTask);
				
			}//end for 
			
			//add root node to doc
			doc.appendChild(nRoot);
			
			//Save the document
			isSaved = saveXml(doc, xmlFilePath);			
	
			if (isSaved){
				return true;
			}else{
				return false;
			}
			
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	
		
	}
	
	
	
	/*
	 * HashMap
	 * key  id of task (String)
	 * value  task object
	 * */
	public HashMap<Integer, Task> XmltoTable(String xmlFilePath){
		
		HashMap<Integer, Task> taskTable = new HashMap<Integer, Task>();
		
		try{
			File mXmlFile = new File(xmlFilePath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(mXmlFile);
			
			normalize(doc);
				
			NodeList nList = doc.getElementsByTagName(NODE_TASK_TAG);
			
			for (int temp = 0; temp < nList.getLength(); temp++) {
				 
				Node nNode = nList.item(temp);
		 
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		 
					Element eElement = (Element) nNode;
		 
					Task mTask = null;
					
					int m_id = 0;
					String m_title = "";
					String m_start_millisecond = "";
					String m_end_millisecond = "";
					String m_start_time = "";
					String m_end_time = "";
					String m_start_date = "";
					String m_end_date = "";
					boolean m_is_done = false;
					
					
					m_id = Integer.parseInt(eElement.getElementsByTagName(NODE_TASK_ID_TAG).item(0).getTextContent());
					m_title = eElement.getElementsByTagName(NODE_TASK_TITLE_TAG).item(0).getTextContent();
					m_start_millisecond = eElement.getElementsByTagName(NODE_TASK_START_MILLISECOND_TAG).item(0).getTextContent();
					m_end_millisecond = eElement.getElementsByTagName(NODE_TASK_END_MILLISECOND_TAG).item(0).getTextContent();
					m_start_time = eElement.getElementsByTagName(NODE_TASK_START_TIME_TAG).item(0).getTextContent();
					m_end_time = eElement.getElementsByTagName(NODE_TASK_END_TIME_TAG).item(0).getTextContent();
					m_start_date = eElement.getElementsByTagName(NODE_TASK_START_DATE_TAG).item(0).getTextContent();
					m_end_date = eElement.getElementsByTagName(NODE_TASK_END_DATE_TAG).item(0).getTextContent();
					m_is_done = Boolean.parseBoolean(eElement.getElementsByTagName(NODE_TASK_IS_DONE_TAG).item(0).getTextContent());
					
					
					//create new task object
					mTask = new Task(m_id, 
									m_title, 
									m_start_millisecond,
									m_end_millisecond,
									m_start_time,
									m_start_date,
									m_end_time,
									m_end_date,
									m_is_done);
					
					taskTable.put(m_id, mTask); //add new task into table
					
				}//end if 
			}//end for
			
			
			
		}catch(Exception e){
			e.printStackTrace();
		}//end try
		
		
		
		
		return taskTable;
		
	}//end XMLtoJava
	
	
	
	public boolean XmlAddTask(String xmlFilePath, Task newTask){
		boolean isSaved = false;
		
		try{

			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(xmlFilePath);
		
			normalize(doc);
			
			//get root node
			Node nRoot = doc.getFirstChild();
			
			//Create a new child of the root
			Element nTask = doc.createElement(NODE_TASK_TAG);
			
			//create new node method
			createNode(nTask, NODE_TASK_ID_TAG, String.valueOf(newTask.getID()));
			createNode(nTask, NODE_TASK_TITLE_TAG, newTask.getTitle());
			createNode(nTask, NODE_TASK_START_MILLISECOND_TAG, newTask.getStartMillisecond());
			createNode(nTask, NODE_TASK_END_MILLISECOND_TAG, newTask.getEndMillisecond());
			createNode(nTask, NODE_TASK_START_TIME_TAG, newTask.getStartTime());
			createNode(nTask, NODE_TASK_END_TIME_TAG, newTask.getEndTime());
			createNode(nTask, NODE_TASK_START_DATE_TAG, newTask.getStartDate());
			createNode(nTask, NODE_TASK_END_DATE_TAG, newTask.getEndDate());
			createNode(nTask, NODE_TASK_IS_DONE_TAG, String.valueOf(newTask.getIsDone()));
			
			//add new task node to root
			nRoot.appendChild(nTask);
		
			//Save the document
			isSaved = saveXml(doc, xmlFilePath);
					
			
			if (isSaved){
				return true;
			}else{
				return false;
			}
			
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}	
		
	}
	
	public boolean xmlDeleteTask(String xmlFilePath, Task deleteTask){
		boolean isSaved = false;
		
		try{

			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(xmlFilePath);
		
			//normalize(doc);
			Node nRoot = doc.getFirstChild();
			
			XPathFactory xpf = XPathFactory.newInstance();
	        XPath xpath = xpf.newXPath();
	        
	        //find task node with specific ID (//tasks/task[id=??])
	        XPathExpression expression = xpath.compile("//"+ NODE_ROOT_TAG +"/" + NODE_TASK_TAG +"["+ NODE_TASK_ID_TAG +"="+ deleteTask.getID() +"]");

	        //get task node
	        Node removeNode = (Node) expression.evaluate(doc, XPathConstants.NODE);
	        
	        //remove task node from root
	        nRoot.removeChild(removeNode);
		
			//Save the document
			isSaved = saveXml(doc, xmlFilePath);
					
			
			if (isSaved){
				return true;
			}else{
				return false;
			}
			
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
	}
	
	private void createNode(Node parent, String tagName, String value){
		Document doc = parent.getOwnerDocument();
		
		//create a new node
		Node mNode  = doc.createElement(tagName);
		//set content of the node
		mNode.setTextContent(value);
		
		//add new node to the parent node
		parent.appendChild(mNode);
		
	}	
	
	private Node searchNode(Document doc, String findID){
		
		Node findNode = null;
		Node nRoot = doc.getDocumentElement();
		NodeList nl = nRoot.getChildNodes();
		
		for(int i=0; i< nl.getLength(); i++){
			
			Node nID = nl.item(i).getChildNodes().item(0);
			
			if(nID.getTextContent().equals(findID)){
				return nID;
			}
		}
		
		return null;		
		
	}
	
	
	private boolean saveXml(Document doc, String xmlFilePath){
		//Save the document
		try{
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(xmlFilePath));
			transformer.transform(source, result);
			transformer.setOutputProperty(OutputKeys.INDENT, "yes"); 
			
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;		
		}//end try
		
	}//end saveXML
	
	
	
	private static void xmlRemoveAllTask(Node node) {
		 
		 NodeList nl = node.getChildNodes();
		 for(int i=0; i< nl.getLength(); i++){
			
			 Node n = nl.item(i); 
				 
			 node.removeChild(n);
		 }//end for	 
		 
	}//end removeAllFromXML
	 
	 
	 private void normalize(Document doc){
		 Element root = doc.getDocumentElement();
		 root.normalize();		 
	 }
	
	 
	 
	 /*=============================== HashMap ==============================*/
	 
	 public Task searchTaskFromHashMap(HashMap<Integer, Task> mMap, int id){		 
		 Task searchTask = mMap.get(id);
		 return searchTask;
	 }
	 
	 public Task searchTaskFromHashMap(HashMap<Integer, Task> mMap, String title){

		 for(Task searchTask : mMap.values()){
			 if(searchTask.getTitle().contains(title)){
				 return searchTask;
			 }//end if 
		 }//end for
		 
		 return null;
	 }
	 
	 public Task searchTaskFromHashMap(HashMap<Integer, Task> mMap, String start_ms, String end_ms){

		 for(Task searchTask : mMap.values()){
			 //if(searchTask.getStartMillisecond()){
				 
			 //}
		 }
		 
		 return null;
	 }
	 
	 public Task searchTaskFromHashMap(HashMap<Integer, Task> mMap, boolean mIsDone){
		 Task searchTask = null;
		 
		 
		 return searchTask;
	 }

	 
	 
}//end class


