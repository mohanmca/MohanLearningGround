package ground.learning.java.concurrent;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * 
 * @author Mohan Naraynaswamy POC for - Object pool without global lock.
 */
public class DocumentContainer {

	private ConcurrentHashMap<String, Document> mapOfItems = new ConcurrentHashMap<>();
	private ConcurrentHashMap<String, Document> mapOfItemAndLock = new ConcurrentHashMap<>();

	public DocumentContainer() {
		for (int i = 0; i < Document.DEFAULT_DOCUMENTS_COUNT; i++) {
			mapOfItems.put(i + "", Document.createDocument());
		}
	}

	private void lockItem(String id) {

	}

	private void releaseItem(String id) {

	}

	private int size() {
		return mapOfItems.size();
	}

	private Document getDocument(String id) {
		return mapOfItems.get(id);
	}

	private void processTenRandomDocument() {
		DocumentContainer docContainer = this;
		Random documentIdGen = new Random();
		for (int i = 0; i < 10; i++) {
			String docId = documentIdGen.nextInt(docContainer.size()) + "";
			docContainer.lockItem(docId);
			docContainer.getDocument(docId).doCostlyOperation();
			docContainer.releaseItem(docId);
		}
	}

	/**
	 * 
	 * @param args
	 *            100 * 10 = Random documents would be processed. If container
	 *            has 20 unique document, every document is processed 50 times.
	 */
	public static void main(String args[]) {
		DocumentContainer docContainer = new DocumentContainer();
		int cores = Runtime.getRuntime().availableProcessors();
		ExecutorService executor = Executors.newFixedThreadPool(cores * 5);
		Runnable task = () -> {
			docContainer.processTenRandomDocument();
		};
		IntStream.rangeClosed(1, 100).forEach(i -> executor.execute(task));
		executor.shutdown();
	}

}

class Document {
	private String[] content = null;
	public static final int DEFAULT_DOCUMENTS_COUNT = 20;

	public static Document createDocument() {
		String[] element = new String[DEFAULT_DOCUMENTS_COUNT];
		for (int i = 0; i < DEFAULT_DOCUMENTS_COUNT; i++) {
			element[i] = UUID.randomUUID().toString()
					+ UUID.randomUUID().toString();
		}
		return new Document(element);
	}

	public Document(final String[] _content) {
		content = _content;
	}

	public void doCostlyOperation() {
		if (content != null) {
			for (String line : content) {
				System.out.println(line.toUpperCase());
				System.out.println(line.toLowerCase());
				System.out.println(new StringBuilder(line).reverse().toString()
						.toUpperCase());
				System.out.println(new StringBuilder(line).reverse().toString()
						.toLowerCase());
			}
		}
		doTimeConsumingWork();
	}

	private void doTimeConsumingWork() {
		Random rand = new Random();
		try {
			Thread.sleep(rand.nextInt(DEFAULT_DOCUMENTS_COUNT));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}