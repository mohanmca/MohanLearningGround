package ground.learning.java.concurrent;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.stream.IntStream;

/**
 * 
 * @author Mohan Naraynaswamy POC for - Object pool without global lock.
 */
public class DocumentContainer {

	private static final Logger logger = Logger.getLogger(DocumentContainer.class
			.getName());

	private final ConcurrentHashMap<String, Document> mapOfItems = new ConcurrentHashMap<>();
	private final ConcurrentHashMap<String, Lock> mapOfLock = new ConcurrentHashMap<>();

	public DocumentContainer() {
		for (int i = 0; i < Document.DEFAULT_DOCUMENTS_COUNT; i++) {
			mapOfItems.put(i + "", Document.createDocument());
		}
	}

	private void lockItem(String id) {
		Lock newLock = new ReentrantLock();
		logger.info(id + " - being locked << " + newLock.hashCode());
		Lock existingLock = mapOfLock.putIfAbsent(id, newLock);
		if (existingLock == null && mapOfLock.containsKey(id)) {
			// Lock acquired.
		} else {
			while (existingLock != newLock) {
				existingLock = mapOfLock.putIfAbsent(id, newLock);
				if (existingLock != null)
					logger.info(id + " locked by >> " + existingLock.hashCode());
			}
		}
		logger.info(id + " - locked by " + newLock.hashCode());
	}

	private void releaseItem(String id) {
		if (!mapOfLock.containsKey(id)) {
			logger.severe(id + " Not locked by any!, RACE condition.");
		} else {
			mapOfLock.remove(id);
		}
	}

	private int size() {
		return mapOfItems.size();
	}

	private Document getDocument(String id) {
		return mapOfItems.get(id);
	}

	private void processRandomDocuments(int count) {
		DocumentContainer docContainer = this;
		Random documentIdGen = new Random();
		for (int i = 0; i < count; i++) {
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
	 * @throws IOException
	 * @throws SecurityException
	 */
	public static void main(String[] args) throws SecurityException,
			IOException {

		FileInputStream fis = new FileInputStream("logger.properties");
		LogManager.getLogManager().readConfiguration(fis);

		DocumentContainer docContainer = new DocumentContainer();
		int cores = Runtime.getRuntime().availableProcessors();
		ExecutorService executor = Executors.newFixedThreadPool(cores * 5);
		Runnable task = () -> {
			docContainer.processRandomDocuments(1000);
		};
		IntStream.rangeClosed(1, 100).forEach(i -> executor.execute(task));
		executor.shutdown();
	}

}

class Document {
	private static final Logger logger = Logger.getLogger(Document.class.getName());

	private String[] content = null;
	public static final int DEFAULT_DOCUMENTS_COUNT = 5;
	private static final int DEFAULT_LINE_COUNT = 5;	

	public static Document createDocument() {
		String[] element = new String[DEFAULT_LINE_COUNT];
		for (int i = 0; i < element.length; i++) {
			element[i] = UUID.randomUUID().toString()
					+ UUID.randomUUID();
		}
		return new Document(element);
	}

	public Document(final String[] _content) {
		content = _content;
	}

	public void doCostlyOperation() {
		if (content != null) {
			for (String line : content) {
				logger.log(Level.FINE, line.toUpperCase());
				logger.log(Level.FINE, line.toLowerCase());
				logger.log(Level.FINE, new StringBuilder(line).reverse()
						.toString().toUpperCase());
				logger.log(Level.FINE, new StringBuilder(line).reverse()
						.toString().toLowerCase());
			}
		}
		doTimeConsumingWork();
	}

	private void doTimeConsumingWork() {
		Random rand = new Random();
		try {
			Thread.sleep(rand.nextInt(100));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}