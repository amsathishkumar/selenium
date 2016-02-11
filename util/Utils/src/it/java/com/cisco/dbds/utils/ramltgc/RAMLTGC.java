/*
 * 
 */
package com.cisco.dbds.utils.ramltgc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.raml.model.Action;
import org.raml.model.ActionType;
import org.raml.model.MimeType;
import org.raml.model.Raml;
import org.raml.model.Resource;
import org.raml.model.Response;
import org.raml.parser.rule.ValidationResult;
import org.raml.parser.visitor.RamlDocumentBuilder;
import org.raml.parser.visitor.RamlValidationService;

// TODO: Auto-generated Javadoc
/**
 * The Class restparser.
 */
public class RAMLTGC {

	/** The Auto map. */
	public static LinkedHashMap<String, String> AutoMap = new LinkedHashMap<String, String>();

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 * @throws Exception
	 *             the exception
	 */
	public static void main(String args[]) throws Exception {
		System.out.println("SIT process..");

		String fname = "file:///C:/satraml/REST/R1/R1.yaml";

		//String fname = "file:///C:/satraml/student.yaml";

		List<ValidationResult> results = RamlValidationService.createDefault()
				.validate(fname);
		if (results.size() == 0)
			System.out.println("YAML file validattion ..... done");
		else {
			for (ValidationResult v1 : results) {
				System.out.println(v1.getMessage());
			}
		}

		System.out.println("Ram Praser Started..");
		Raml raml = new RamlDocumentBuilder().build(fname);

		System.out.println("raml Title:" + raml.getTitle());
		System.out.println("raml version:" + raml.getVersion());
		System.out.println("raml Document Title");
		int i = 0;
		/*
		 * for (DocumentationItem a : raml.getDocumentation()) {
		 * 
		 * System.out.println("Title No:" + i++ + " " + a.getTitle());
		 * System.out.println("      Content: " + a.getContent()); }
		 * System.out.println("Media Type:" + raml.getMediaType());
		 */

		System.out.println("base URI: " + raml.getBaseUri());
		String baseuri = raml.getBaseUri();

		Map<String, Resource> resources = raml.getResources();

		System.out.println(resources.toString());

		System.out.println("Main resources.......................Started");
		for (Map.Entry<String, Resource> entry : resources.entrySet()) {

			System.out.println("uri :" + entry.getValue().getUri());
			baseuri = baseuri + entry.getValue().getUri();
			Map<ActionType, Action> action = entry.getValue().getActions();
			for (Map.Entry<ActionType, Action> entry1 : action.entrySet()) {

				System.out.println("    Method:" + entry1.getKey());
				String mName = entry1.getKey().toString();
				if (mName.contains("POST")) {

					Map<String, MimeType> mmm = entry1.getValue().getBody();
					if (mmm != null) {
						for (Map.Entry<String, MimeType> mimetype : mmm
								.entrySet()) {

							mName = mName + "~"
									+ mimetype.getValue().getSchema();

						}
					}
				}
				System.out.println("    "
						+ entry1.getValue().getResponses().toString());

				Map<String, Response> response = entry1.getValue()
						.getResponses();
				for (Map.Entry<String, Response> response1 : response
						.entrySet()) {
					System.out.println("     responecode: "
							+ response1.getKey());

					System.out.println("     re "
							+ response1.getValue().getDescription());
					System.out.println("     re "
							+ response1.getValue().getBody());
					Map<String, MimeType> mm = response1.getValue().getBody();
					if (mm != null) {

						for (Map.Entry<String, MimeType> mm1 : mm.entrySet()) {

							System.out.println(mName + response1.getKey()
									+ mm1.getValue().getSchema());
							AutoMapUpdate(baseuri + "~" + mName + "~"
									+ response1.getKey(), mm1.getValue()
									.getSchema());

							System.out.println("      Sample: "
									+ mm1.getValue().getExample());
						}
					}
				}

			}
			System.out.println("Sub resources.......................Started");
			System.out.println("Sub resources:"
					+ entry.getValue().getResources().toString());
			if (entry.getValue().getResources().size() > 0)
				readsubResource(entry.getValue().getResources(), baseuri);
			System.out.println("Sub resources.......................Stopped");
		}
		System.out.println("Ram Parser............................. Stopped..");
		System.out
				.println("--------------------Automation Test Case count-------------------------------");
		System.out.println("Automation Test Case count" + AutoMap.size());

		System.out
				.println("AutoMap Read.....................................Started");

		// Delete all .feature file in it/resource folder
		File dir = new File(System.getProperty("user.dir")
				+ "\\src\\it\\resources\\");
		String[] extensions = new String[] { "feature" };
		List<File> files = (List<File>) FileUtils.listFiles(dir, extensions,
				true);
		for (File file : files)
			if (!file.isDirectory())
				file.delete();
		// Linked Hashmap
		for (String key : AutoMap.keySet()) {
			System.out
					.println(key.replaceAll("\\r\\n|\\r|\\n|\\t| ", "") + ":\t"
							+ AutoMap.get(key).replaceAll("\\r\\n|\\r|\\n", ""));
			// Create .feature file
			createFeaturefile(key.replaceAll("\\r\\n|\\r|\\n|\\t| ", ""),
					AutoMap.get(key).replaceAll("\\r\\n|\\r|\\n", ""));
		}
		System.out
				.println("AutoMap Read.....................................Stopped");

	}

	/**
	 * Creates the featurefile.
	 * 
	 * @param ffile
	 *            the ffile
	 * @param freponse
	 *            the freponse
	 * @throws Exception
	 *             the exception
	 */
	public static void createFeaturefile(String ffile, String freponse)
			throws Exception {
		ArrayList<String> testdataal = new ArrayList<String>();
		System.out.println("File Creation.......................started");
		String[] fname = null;
		String postschema = null;
		if (ffile.contains("POST")) {
			String[] pos = ffile.split("POST");
			String[] pos1 = pos[pos.length - 1].split("~");

			ffile = pos[0] + "POST" + "~" + pos1[pos1.length - 1];
			postschema = pos1[1];
			
			//INPUT SCHEMA creation
			String fschemaname = "schema.json";
			File file = new File(fschemaname);

			if (file.exists()) {
				System.out.println("Input schema file delete" + fschemaname);
				file.delete();
			} else if (file.createNewFile()) {
				System.out.println("Input schema file is created!" + fschemaname);
			}
			BufferedWriter output = new BufferedWriter(new FileWriter(file));
			output.write(postschema);
			output.close();
			System.out.println("INTPUT schema file path" + file.getAbsolutePath());
			
			//OUTPUT SCHEMA creation
			String foschemaname = "outputschema.json";
			File ofile = new File(foschemaname);

			if (ofile.exists()) {
				System.out.println("Output Schema file delete" + foschemaname);
				ofile.delete();
			} else if (ofile.createNewFile()) {
				System.out.println("Output Schema file  created!" + foschemaname);
			}
			BufferedWriter ooutput = new BufferedWriter(new FileWriter(ofile));
			ooutput.write(freponse);
			ooutput.close();
			System.out.println("Output schema file path" + file.getAbsolutePath());
			
			
			
            //TEST DATA generation  
			testdataal = testdata(file.getAbsolutePath(),ofile.getAbsolutePath());
			if (testdataal.size()>0)
			  testdataal.remove(0);
			System.out.println("Test Data count:"+testdataal.size());
		}
		
        // .Feature file creation		
		fname = ffile.split("~"); // .feature file name
		String url = fname[0];
		String[] uri = url.split("/");
		String ffname = System.getProperty("user.dir")
				+ "\\src\\it\\resources\\" + uri[uri.length - 2]
				+ uri[uri.length - 1] + fname[1] + fname[fname.length - 1]
				+ ".feature";
		System.out.println(ffname);
		File file = new File(ffname);

		if (file.exists()) {
			System.out.println("Feature File delete" + ffname);
			file.delete();
		} else if (file.createNewFile()) {
			System.out.println("Feature File is created!" + ffname);
		}
		BufferedWriter output = new BufferedWriter(new FileWriter(file));
		output.write("Feature: " + ffile);
		output.newLine();
		output.newLine();
		output.write("@RAML @BROWSER_NOT_NEEDED @COMPLETED");
		output.newLine();
		output.write("Scenario Outline: " + uri[uri.length - 2]
				+ uri[uri.length - 1] + fname[1] + fname[fname.length - 1]);
		output.newLine();
		output.write("Given Frame the request with below details");
		output.newLine();

		if (ffile.contains("POST")) {
			output.write("|httpurl|httpMethod|httpBody|");
			output.newLine();
			output.write("|" + fname[0] + "|" + fname[1] + "|" + "<testdata>"
					+ "|");
		} else {
			output.write("|httpurl|httpMethod|");
			output.newLine();
			output.write("|" + fname[0] + "|" + fname[1] + "|");
		}
		output.newLine();
		output.write("Then Verify the below reponse detail");
		output.newLine();
		output.write("|responsecode|responsedetail|");
		output.newLine();
		// output.write("|" + fname[fname.length - 1] + "|" + freponse + "|");
		output.write("|" + fname[fname.length - 1] + "|" + "<responsemessage>"
				+ "|");
		output.newLine();
		output.write("Examples:");
		output.newLine();
		if (ffile.contains("POST")) {
			output.write("|testdata|responsemessage|");
			output.newLine();
			for (String data : testdataal) {
				String [] splitdata =data.split("~");
				
				output.write("|" + splitdata[0] + "|" + splitdata[1] + "|");
				output.newLine();
			}

		} else {
			output.write("|responsemessage|");
			output.newLine();
			for (String data : testdataal) {
				output.write("|" + data + "|");
				output.newLine();
			}

		}
		output.close();

	}

	/**
	 * Readsub resource.
	 * 
	 * @param subresource
	 *            the subresource
	 * @param basuri
	 *            the basuri
	 */
	public static void readsubResource(Map<String, Resource> subresource,
			String basuri) {
		for (Map.Entry<String, Resource> subentry : subresource.entrySet()) {
			// String parenturi =
			// subentry.getValue().getParentResource().getUri();
			String suburi = subentry.getValue().getUri();
			String submName = basuri + suburi;
			if (subentry.getValue().getActions().size() > 0)
				readsubAction(subentry.getValue().getActions(), submName);

		}
	}

	/**
	 * Readsub action.
	 * 
	 * @param subaction
	 *            the subaction
	 * @param subnName
	 *            the subn name
	 */
	public static void readsubAction(Map<ActionType, Action> subaction,
			String subnName) {
		String method = subnName;
		for (Map.Entry<ActionType, Action> action : subaction.entrySet()) {
			method = subnName + "~" + action.getKey().toString();
			if (method.contains("POST")) {

				Map<String, MimeType> mm = action.getValue().getBody();
				if (mm != null) {
					for (Map.Entry<String, MimeType> mimetype : mm.entrySet()) {
						System.out.println("satbody Schema"
								+ mimetype.getValue().getSchema());
						method = method + "~" + mimetype.getValue().getSchema();

					}
				}
			}
			if (action.getValue().getResponses().size() >= 0) {

				readsubResponse(action.getValue().getResponses(), method);
			}

		}
	}

	/**
	 * Readsub response.
	 * 
	 * @param subresponse
	 *            the subresponse
	 * @param method
	 *            the method
	 */
	public static void readsubResponse(Map<String, Response> subresponse,
			String method) {
		String submine = method;
		for (Map.Entry<String, Response> response : subresponse.entrySet()) {
			submine = method + "~" + response.getKey();
			// if (response.getValue().getBody()!=null ||
			// response.getValue().getBody().size()>0)
			// submine=submine+ "@"+readsubMIME(response.getValue().getBody());
			Map<String, MimeType> mm = response.getValue().getBody();
			if (mm != null) {
				for (Map.Entry<String, MimeType> mimetype : mm.entrySet()) {
					System.out.println(mimetype.getValue().getSchema());
					AutoMapUpdate(submine, mimetype.getValue().getSchema());
				}
			}
		}

	}

	/*
	 * public static String readsubMIME(Map<String, MimeType> submimetype){
	 * String schemav=null; for (Map.Entry<String, MimeType> mimetype :
	 * submimetype.entrySet()) { System.out.println(
	 * mimetype.getValue().getSchema());
	 * schemav=mimetype.getValue().getSchema(); } return schemav; }
	 */

	/**
	 * Auto map update.
	 * 
	 * @param autokey
	 *            the autokey
	 * @param autovalue
	 *            the autovalue
	 */
	public static void AutoMapUpdate(String autokey, String autovalue) {
		AutoMap.put(autokey, autovalue);

	}

	/**
	 * Testdata.
	 * 
	 * @param schema
	 *            the schema
	 * @return the array list
	 * @throws Exception
	 *             the exception
	 */
	public static ArrayList<String> testdata(String ischema,String oschema) throws Exception {
		ArrayList<String> al = new ArrayList<String>();
		String cmd="C:\\Python27\\python.exe C:\\sathack\\jsontestdata\\testdata\\testdata1.py "
				+ ischema +" "+oschema;
		
		System.out.println(cmd);
		Process p = Runtime.getRuntime().exec(cmd);
		p.waitFor();
		BufferedReader input = new BufferedReader(new InputStreamReader(
				p.getInputStream()));

		String line;
		int start = 0;
		while ((line = input.readLine()) != null) {
			System.out.println(line);			
			if (line.contains("My JSON files are:"))
				start = 1;
			if (start == 1)
				al.add(line);

		}
		return al;

	}
}
