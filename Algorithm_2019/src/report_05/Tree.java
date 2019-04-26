package report_05;

public class Tree {
	public String word;
	public String part;
	public String mean;	
	public Tree left;
	public Tree right;
	public Tree parent;
	
	public Tree(String word,String part, String mean) {
		this.word = word;
		this.part = part;
		this.mean = mean;		
		left = null;
		right = null;
		parent = null;
	}
}