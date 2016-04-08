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
