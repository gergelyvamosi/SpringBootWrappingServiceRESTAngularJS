package org.gvamosi.wrapping.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import org.gvamosi.wrapping.model.Wrapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service("wrappingService")
public class WrappingService {

	@Autowired
	@Qualifier("fixedThreadPool")
	private ExecutorService executorService;

	private Map<Long, Wrapping> results = new ConcurrentHashMap<Long, Wrapping>();

	@Async
	public Wrapping getWrapping(long workId) {
		return results.get(workId);
	}
	
	@Async
	public Wrapping wrapText(Wrapping wrapping) {
		return executeWorkerThread(wrapping);
	}

	@Async
	private Wrapping executeWorkerThread(Wrapping wrapping) {
		if (wrapping.getWorkId() == -1) {
			WorkerThread worker = new WorkerThread(wrapping);
			
			Future<Void> task = (Future<Void>) executorService.submit(worker);
			
			if (task == null) {
				// all right :)
			}
			
			// wait for getting a real work ID
			while (worker.getWrapping().getWorkId() == -1) {}
			
			return worker.getWrapping();
			// return wrapping;
		} else {
			return results.get(wrapping.getWorkId());
		}
	}

	private class WorkerThread implements Runnable {

		private Wrapping wrapping;

		public WorkerThread(Wrapping wrapping) {
			this.wrapping = wrapping;
		}

		@Async
		public Wrapping getWrapping() {
			return wrapping;
		}

		@Override
		public void run() {
			long threadId = Thread.currentThread().getId();
			if (getWrapping().getWorkId() == -1) {
				
				// set workId
				getWrapping().setWorkId(threadId);
				getWrapping().setProcessed(false);
				results.put(getWrapping().getWorkId(), getWrapping());
				
				// wrapping
				wrapTextGivenLength(getWrapping());
				results.put(getWrapping().getWorkId(), getWrapping());
				
				// processed true
				getWrapping().setProcessed(true);
				results.put(getWrapping().getWorkId(), getWrapping());
			}
		}
	}

	private void wrapTextGivenLength(Wrapping wrapping) {
		String splitted[] = wrapping.getTextToWrap().split("\\s+");
		for (int i = 0; i < splitted.length; i++) {
			StringBuffer sb = new StringBuffer();
			int j = 0;
			do {
				sb.append(splitted[i + j]);
				j++;
				if (i + j < splitted.length && sb.length() < wrapping.getWrapLength()) {
					sb.append(" ");
				}
			} while (i + j < splitted.length && sb.length() + splitted[i + j].length() <= wrapping.getWrapLength());
			i += j - 1;
			wrapping.getWrappedText().add(sb.toString());
		}
	}
}
