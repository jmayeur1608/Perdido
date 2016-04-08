package erig.postagger;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;


import erig.tools.StringTools;


/**
 * Annotation class : provide methods to execute POS taggers and cascade of transducers (Cassys)
 * @author Ludovic Moncla
 * @version 1.0
 */
public class Treetagger extends POStagger {
	
	
	
	
	//private static Hashtable<String, String> _tag = new Hashtable<String, String>();
	
	
	public Treetagger(String installDirectory, String lang)
	{
		super(installDirectory, lang, "treetagger");
	}
	
	
	
	/**
	 * launch the Treetagger POS analyser
	 * @param content				content to tag
	 * @param output				path of the output file
	 * @param lang					value : French or Spanish or Italian
	 * @param uriTreeTagger			uri of treetagger
	 * @throws Exception
	 */
	@Override
	public void run(String inputContent, String outputFile) throws Exception {

		//System.out.println("Begin run treetagger");

		
		//content = content.replaceAll("\"", "");
		
		
		Runtime runtime = Runtime.getRuntime();
		Process proc;

		String cmd_treetagger = _installDirectory + "/cmd/tree-tagger-"+_lang.toLowerCase()+"-utf8 > " + outputFile;
			
		String[] cmd = {
			"bash",
			"-c",
			"echo \"" + inputContent + "\" | " +  cmd_treetagger};
		
		proc = runtime.exec(cmd);
		proc.waitFor();

		//System.out.println("End run treetagger");
	
	}
	
	
	@Override
	public String tagger2pivot(String inputFile) throws Exception {

		//System.out.println("Begin tagger2pivot treetagger");

		InputStream ips = new FileInputStream(inputFile);
		InputStreamReader ipsr = new InputStreamReader(ips);
		BufferedReader br = new BufferedReader(ipsr);

		
		String outputPivot = "";
		String line = "";
		String token = "", pos = "", lemma = "";
		
		

		while ((line = br.readLine()) != null) 
		{
			if(!line.equals(""))
			{
				String str[] = line.split("\t");
	
				//System.out.println("line : " + line);
	
				token = str[0];// .toLowerCase();
				pos = str[1];

				
				
				if(str.length>2) //if a lemma exists
				{
					if(str[2] != null  && !str[2].equals("<unknown>"))
					{
						String lem_tmp = str[2];
						//System.err.println("lem_tmp : " + lem_tmp);
						
						
						if(lem_tmp.contains("|")) //if several lemma, we choose the first one
						{
							
							//String c[] = lem_tmp.split("|"); // problème au niveau du split...
							//System.out.println("c[0] : " + c[0]);
							//System.out.println("c[1] : " + c[1]);
							//lemma = c[0];
							
							//System.out.println("lem "+lem_tmp.substring(0, lem_tmp.indexOf("|")));
							lemma = lem_tmp.substring(0, lem_tmp.indexOf("|"));
							
							
							//lemma = str[2];
						}
						else
						{
							//System.out.println("else");
							lemma = str[2];
						}
					}
					else
						lemma = "null";
				}
				else
					lemma = "null";
				
				
				//System.err.println("lemma : " + lemma);
				
				token = StringTools.filtreString(token);
				lemma = StringTools.filtreString(lemma);
				
			
				outputPivot += token + "\t" + pos + "\t" + lemma +"\n";
				

			}
		}
		
		br.close();
	
		//System.out.println("End tagger2pivot treetagger");
		
		return outputPivot;
	}

}


