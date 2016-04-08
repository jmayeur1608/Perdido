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



public class Main {

	 public static void main(String[] args) {
		 
		 
		 POStagger pos = null;
			
			
		//pos = new Freeling("","French");

		//pos = new Talismane("/Users/lmoncla/Programme/Talismane/talismane-2.4.7b/talismane-core-2.4.7b.jar","French");

		pos = new Treetagger("/Users/lmoncla/Programme/TreeTagger3.2","French");
			
		
		
		try {
			pos.run("Ceci est un test", "/Users/lmoncla/Programme/Unitex3.1beta/webService/output/test.txt");
			
			//pos.loadTags();
			
			
			
			String pivot = pos.tagger2pivot("/Users/lmoncla/Programme/Unitex3.1beta/webService/output/test.txt");
			
			
			String result = pos.tagger2unitex(pivot);
			
			System.out.println("tags : " +pos.getTags().toString());
			
			System.out.println("result : " +result);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
			
			
	 }
}
