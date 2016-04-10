/*
 * Copyright (C) 2016 Ludovic Moncla <ludovic.moncla@univ-pau.fr>
 * 
 * This file is part of POSprocessing - Perdido project <http://erig.univ-pau.fr/PERDIDO/>
 *
 * POSprocessing is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * POSprocessing is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with POSprocessing.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package erig.postagger;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;


import erig.tools.StringTools;


/**
 * Talismane class : provide methods to execute Talismane POS tagger
 * @author Ludovic Moncla
 * @version 1.0
 */
public class Talismane extends POStagger {
	
	
	private String _languagePack = "";
	
	
	/**
	 * 
	 * @param installDirectory
	 * @param lang
	 * @param languagePack
	 */
	public Talismane(String installDirectory, String lang, String languagePack) {
		super(installDirectory, lang, "talismane");
		_languagePack = languagePack;
	}



	/**
	 * launch the Talismane POS analyser
	 * @param inputContent					input file to tag
	 * @param outputFile				path of the output file
	 * @throws Exception
	 */
	@Override
	public void run(String inputFile, String outputFile) throws Exception {

		//System.out.println("Begin run talismane");

		Runtime runtime = Runtime.getRuntime();
		Process proc;

		String[] commands = {
			"bash",
			"-c",
			"java -Xmx1024M -jar "+ _installDirectory +" command=analyse endModule=postag inFile="+inputFile+" outFile="+outputFile+" encoding=UTF-8 languagePack="+_languagePack+""};
	
		proc = runtime.exec(commands);
		proc.waitFor();
		
		System.out.println("_languagePack : "+_languagePack);
		System.out.println("_installDirectory : "+_installDirectory);
		System.out.println("inputFile : "+inputFile);
		System.out.println("outputFile : "+outputFile);
	
	}
		
	
	@Override
	public String tagger2pivot(String inputFile) throws Exception {

		//System.out.println("Begin tagger2pivot talismane");

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
	
				//4	route	route	NC	nc	g=f|n=s	
			
				//System.out.println("line : " + line);
	
				token = str[1];// .toLowerCase();
				pos = str[3];
				lemma = str[2];
	
				
				if(lemma.equals("_"))
					lemma = "null";
				
				token = StringTools.filtreString(token);
				lemma = StringTools.filtreString(lemma);
				
				
				if(lemma.equals("l'"))
					lemma = "le";
				if(lemma.equals("d'"))
					lemma = "de";
				
				
				outputPivot += token + "\t" + pos + "\t" + lemma +"\n";
				

			}
		}
		
		br.close();
		

		//System.out.println("End tagger2pivot talismane");
		
		return outputPivot;
	}
	
	
	
}


