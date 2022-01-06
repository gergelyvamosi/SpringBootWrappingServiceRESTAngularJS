package org.gvamosi.wrapping.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
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

	Map<Long, Wrapping> results = new HashMap<Long, Wrapping>();

	public Wrapping getWrapping(long id) {
		return results.get(id);
	}

	public Wrapping wrapText(Wrapping wrapping) {
		return executeWorkerThread(wrapping);
	}

	private Wrapping executeWorkerThread(Wrapping wrapping) {
		if (wrapping.getWorkId() == -1) {
			Future<Wrapping> futureResult = executorService.submit(new WorkerThread(wrapping));
			try {
				results.put((futureResult.get()).getWorkId(), futureResult.get());
				return futureResult.get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return wrapping;
		} else {
			return results.get(wrapping.getWorkId());
		}
	}

	private class WorkerThread implements Callable<Wrapping> {

		private Wrapping wrapping;

		public WorkerThread(Wrapping wrapping) {
			this.wrapping = wrapping;
		}

		@Async
		public Wrapping getWrapping() {
			return wrapping;
		}

		@Override
		public Wrapping call() {
			long threadId = Thread.currentThread().getId();
			if (wrapping.getWorkId() == -1) {
				wrapping.setWorkId(threadId);
				wrapping = wrapTextGivenLength(wrapping);
			}
			wrapping.setProcessed(true);
			return wrapping;
		}
	}

	private Wrapping wrapTextGivenLength(Wrapping wrapping) {
		String splitted[] = wrapping.getTextToWrap().split("\\s+");
		for (int i = 0; i < splitted.length; i++) {
			StringBuffer sb = new StringBuffer();
			int j = 0;
			do {
				sb.append(splitted[i+j]);
				j++;
				if (i+j < splitted.length && sb.length() < wrapping.getWrapLength()) {
					sb.append(" ");
				}
			} while (i+j < splitted.length && sb.length() + splitted[i+j].length() <= wrapping.getWrapLength());
			i += j - 1;
			wrapping.getWrappedText().add(sb.toString());
		}
		return wrapping;
	}
}
