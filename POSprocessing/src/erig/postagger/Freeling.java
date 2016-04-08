package erig.postagger;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import erig.tools.StringTools;


/**
 * Freeling class : provides methods to execute the Freeling POS tagger
 * @author Ludovic Moncla
 */
public class Freeling extends POStagger {
	

	protected String _lang = null;
	
	
	/**
	 * constructor
	 * @param installDirectory
	 * @param lang
	 */
	public Freeling(String installDirectory, String lang)
	{
		super(installDirectory, lang, "freeling");
		
		if(lang.equals("French"))
			this._lang = "fr";
		if(lang.equals("Spanish"))
			this._lang = "es";
		if(lang.equals("Italian"))
			this._lang = "it";
		if(lang.equals("Portugese"))
			this._lang = "pt";
	}
	

	/**
	 * launch the Freeling POS analyser
	 * @param content				content to tag
	 * @param output				path of the output file
	 * @param lang					value : French or Spanish or Italian
	 * @param uriTreeTagger			uri of treetagger
	 * @throws Exception
	 */
	@Override
	public void run(String inputFile, String outputFile) throws Exception {

		//System.out.println("Begin launchFreeling");
		
		Runtime runtime = Runtime.getRuntime();
		Process proc;
	
		String cmd_freeling = _installDirectory + " -f "+this._lang+".cfg --noloc --inpf plain --outf tagged <"+ inputFile+" >"+ outputFile;
		
		//System.err.println("cmd : "+cmd_freeling);
		
		proc = runtime.exec(cmd_freeling);
		proc.waitFor();

		//System.out.println("End launchFreeling");
	
	}
	
	
	/**
	 * tagger2pivot : turn POS tags into generic ones
	 * @param inputFile
	 * @throws Exception
	 */
	@Override
	public String tagger2pivot(String inputFile) throws Exception {

		//System.out.println("Begin freeling2pivot");

		InputStream ips = new FileInputStream(inputFile);
		InputStreamReader ipsr = new InputStreamReader(ips);
		BufferedReader br = new BufferedReader(ipsr);

		
		String line = "";
		String token = "", pos = "", lemma = "";
		String outputPivot = "";
		

		while ((line = br.readLine()) != null) 
		{
			if(!line.equals(""))
			{
				String str[] = line.split(" ");
	
				//route route NCFS000 0.993873
				
				pos = str[2].substring(0, 1);
				if(pos.equals("N"))
				{
					pos = str[2].substring(0, 2);
				}
				
				if(str[0].contains("_"))
				{ 
					String token_tmp[] = str[0].split("_");
					String lemma_tmp[] = str[1].split("_");
					
					for(int i=0;i<token_tmp.length;i++)
					{
						token = token_tmp[i];
						lemma = lemma_tmp[i];
						
						token = StringTools.filtreString(token);
						lemma = StringTools.filtreString(lemma);
	
						outputPivot += token + "\t" + pos + "\t" + lemma +"\n";
					}
				}
				else
				{
					
					//System.out.println("line : " + line);
		
					token = str[1];
					pos = str[3];
					lemma = str[2];
					
					token = StringTools.filtreString(token);
					lemma = StringTools.filtreString(lemma);
					
					outputPivot += token + "\t" + pos + "\t" + lemma +"\n";
				}
			}
		}
		
		br.close();
		
		//System.out.println("End freeling2pivot");
		return outputPivot;	
	}

}


