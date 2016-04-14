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

package erig.disambiguation;

import java.util.Vector;

import erig.tools.MapsFunctions;
import erig.elements.Toponyme;




/**
 * Gazetteer class : provide some methods for toponyms resolution, query gazeteers and apply clustering methods.
 * @author Ludovic Moncla
 * @version 1.0
 */
public class DuplicatePoints {
	
	
	public DuplicatePoints() 
	{
		
	}
	
	

	public static Vector<Toponyme> removeDuplicatePoints(Vector<Toponyme> toponyms) 
	{
		
		boolean notAdded[] = new boolean[toponyms.size()+1];
		Vector<Toponyme> topoFilter = new Vector<Toponyme>();
		
		for(int i=0;i<toponyms.size();i++)
		{
			notAdded[i] = true;
		}
		
		for(int i=0;i<toponyms.size();i++)
		{
			if(notAdded[i])
			{
				notAdded[i] = false;
				topoFilter.add(toponyms.get(i));
				
				for(int j=0;j<toponyms.size();j++)
				{
					if(notAdded[j])
					{
						//System.err.println("toponyms.get(i).getGid() : "+toponyms.get(i).getGid()+" + toponyms.get(j).getGid() "+toponyms.get(j).getGid()+" ");
						
						if(toponyms.get(i).getGid() != toponyms.get(j).getGid())
						{
							//System.err.println("toponyms.get(i).getId() : "+toponyms.get(i).getId()+" + toponyms.get(j).getId() "+toponyms.get(j).getId()+" ");
							
							if(toponyms.get(i).getId() == toponyms.get(j).getId())
							{
								
								double distance = MapsFunctions.getDistance(toponyms.get(i).getLat(), toponyms.get(i).getLng(), toponyms.get(j).getLat(), toponyms.get(j).getLng());
								if(distance > 7000)
								{
									//System.err.println("distance > 7000 ("+distance+") : "+toponyms.get(i).getId()+" - "+toponyms.get(j).getId());
								//	notAdded[j] = false;
								//	topoFilter.add(toponyms.get(j));
								}
								else
								{
									//System.out.println("distance < 7000 ("+distance+") : "+toponyms.get(i).getId()+" - "+toponyms.get(j).getId());
									notAdded[j] = false;
								}
							}
						}
					}
				}
			}
		}
		
		
		return topoFilter;
		

	}
}
