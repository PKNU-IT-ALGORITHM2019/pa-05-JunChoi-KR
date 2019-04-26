package report_05;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
	private static Tree root = null;
	private static String command = null;
	private static String input = null;
	private static Scanner fileReader = null;
	private static Scanner keyboard = new Scanner(System.in);

	public static void main(String[] args) {
		read();

		while (true) {
			System.out.print("$ ");
			command = keyboard.next();

			if (command.equals("find")) {
				input = keyboard.nextLine().trim();
				printDiction(find(root, input));
			} else if (command.equals("add")) {
				add();
			} else if (command.equals("size")) {
				System.out.println(size(root));
			} else if (command.equals("delete")) {
				input = keyboard.nextLine().trim();
				delete(input);
			} else if (command.equals("deleteall")) {
				deleteall();
			} else if (command.equals("exit"))
				break;
		}
	}

	public static void read() {

		try {
			fileReader = new Scanner(new File("shuffled_dict.txt"));

			while (fileReader.hasNext()) {

				String buffer = fileReader.nextLine();

				int start = buffer.indexOf("(");
				int end = buffer.indexOf(")");

				String word = buffer.substring(0, start - 1);
				String part = buffer.substring(start, end + 1);
				String mean = buffer.substring(start + 2);
				root = insert(root, word, part, mean);

			}

			fileReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("cannot find file.");
			System.exit(0);
		}
	}

	public static int size(Tree tree) {

		if (tree == null) {
			return 0;
		} else {
			return (size(tree.left) + 1 + size(tree.right));
		}
	}

	public static Tree find(Tree root, String word) {

		if (root == null || root.word.equalsIgnoreCase(word)) {
			return root;
		}
		if (root.word.compareToIgnoreCase(word) >= 0) {
			return find(root.left, word);
		}

		return find(root.right, word);
	}

	public static Tree insert(Tree root, String word, String part, String mean) {

		Tree parent = root;
		Tree newNode = new Tree(word, part, mean);

		if (parent == null) {
			return newNode;
		}

		if (parent.word.compareToIgnoreCase(newNode.word) >= 0) {
			parent.left = insert(parent.left, word, part, mean);
			return root;
		} else if (parent.word.compareToIgnoreCase(newNode.word) < 0) {
			parent.right = insert(parent.right, word, part, mean);
			return root;
		}

		return root;
	}

	public static void add() {
		System.out.print("word: ");
		String word = keyboard.next();

		System.out.print("class: ");

		String part = keyboard.next();
		part = "(".concat(part).concat(")");

		System.out.print("meaning: ");
		String mean = keyboard.next();

		root = insert(root, word, part, mean);

	}

	public static void delete(String word) {
		if (deleteNode(root, word) != null & command.equals("delete")) {
			System.out.println("Deleted successfully.");
			return;
		} else {
			System.out.println("Not Found.");
		}
	}

	public static Tree deleteNode(Tree root, String word) {
		if (root == null)
			return root;

		if (root.word.compareToIgnoreCase(word) > 0) {
			root.left = deleteNode(root.left, word);
		} else if (root.word.compareToIgnoreCase(word) < 0) {
			root.right = deleteNode(root.right, word);
		} else {
			if (root.right == null) {
				return root.left;
			} else if (root.left == null) {
				return root.right;
			}
			root.word = getMin(root.right);
			root.right = deleteNode(root.right, root.word);
		}

		return root;
	}

	public static String getMin(Tree root) {
		String min = root.word;

		while (root.left != null) {
			min = root.left.word;
			root = root.left;
		}

		return min;
	}

	public static void deleteall() {

		int count = 0;

		try {
			fileReader = new Scanner(new File("to_be_deleted_words.txt"));

			while (fileReader.hasNext()) {

				String buffer = fileReader.nextLine();
				delete(buffer);
				count++;

			}
			System.out.println(count + " words were deleted successfully.");
			fileReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("cannot find file.");
			System.exit(0);
		}

	}

	public static void printDiction(Tree foundWord) {
		if (foundWord.word != null) {
			System.out.println("word: " + foundWord.word);
		} else {
			System.out.println("word: ");
		}

		if (foundWord.part != null) {
			System.out.println("class:" + foundWord.part);
		} else {
			System.out.println("class:");
		}

		if (foundWord.mean != null) {
			System.out.println("meaning:" + foundWord.mean);
		} else {
			System.out.println("meaning:");
		}
	}

}