/*
 * Copyright (C) 2016 Ludovic Moncla <ludovic.moncla@univ-pau.fr>
 * 
 * This file is part of ToponymResolution - Perdido project <http://erig.univ-pau.fr/PERDIDO/>
 *
 * ToponymResolution is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * ToponymResolution is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with ToponymResolution. If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package erig.gazetteers;

import java.util.Vector;

import org.geonames.Style;
import org.geonames.Toponym;
import org.geonames.ToponymSearchCriteria;
import org.geonames.ToponymSearchResult;
import org.geonames.WebService;

import erig.elements.Toponyme;




/**
 * Gazetteer class : provide some methods for toponyms resolution, query gazeteers and apply clustering methods.
 * @author Ludovic Moncla
 * @version 1.0
 */
public class Geonames extends Gazetteer {
	
	private String _APIkey = "";
	
	public Geonames(boolean doStrictQuery, int maxResults, String lang, String geonamesAPIkey) {
		super(doStrictQuery, maxResults, lang);
		_gazetteerName = "Geonames";
		_APIkey = geonamesAPIkey;
		// TODO Auto-generated constructor stub
	}

	public Vector<Toponyme> searchToponym(String toponymCandidate) throws Exception
	{
		
		Vector<Toponyme> toponyms = new Vector<Toponyme>();
		
		//System.out.println("username :"+_APIkey);
		
		WebService.setUserName(_APIkey); // add your username here
		 
		ToponymSearchCriteria searchCriteria = new ToponymSearchCriteria();
		
		if(_doStrictQuery)
			searchCriteria.setNameEquals(toponymCandidate);	
		else
			searchCriteria.setName(toponymCandidate);
		
		//searchCriteria.setQ(topoCandidat);
		
		
		//searchCriteria.setLanguage("FR");
		
		searchCriteria.setMaxRows(_maxResults);
	
		searchCriteria.setStyle(Style.FULL);
		ToponymSearchResult searchResult;
		
	
		searchResult = WebService.search(searchCriteria);
	

		for (Toponym toponym : searchResult.getToponyms()) {
			
			
			if(_doStrictQuery)
			{

				String c = toponym.getName();
				String ch = toponymCandidate;
				//if(res.getString("name").equals(topoCandidat))
				
				if(ch.equalsIgnoreCase(c) || ch.equalsIgnoreCase("le "+c) || ch.equalsIgnoreCase("la "+c) || ch.equalsIgnoreCase("les "+c) || c.equalsIgnoreCase("le "+ch) || c.equalsIgnoreCase("la "+ch) || c.equalsIgnoreCase("les "+ch))
				{
					toponyms.add(new Toponyme(toponym.getName(),toponymCandidate,toponym.getLongitude(),toponym.getLatitude(),_gazetteerName,toponym.getCountryCode(),toponym.getContinentCode(),toponym.getFeatureClassName(),_lang));
						  
				}
			}
			else
			{
				toponyms.add(new Toponyme(toponym.getName(),toponymCandidate,toponym.getLongitude(),toponym.getLatitude(),_gazetteerName,toponym.getCountryCode(),toponym.getContinentCode(),toponym.getFeatureClassName(),_lang));
			}
				

			//throw new Exception();
			//throw new GazetteerException();
		}
		
		
		return toponyms;
		
	}
	
	
	
	
}
