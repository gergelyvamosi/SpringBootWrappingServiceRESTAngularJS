package org.gvamosi.wrapping;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.gvamosi.wrapping.model.Wrapping;
import org.gvamosi.wrapping.service.WrappingService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class WrappingServiceTest {

	@Mock
	private ExecutorService executorServiceMock;

	private WrappingService wrappingService;

	@Before
	public void setUp() {
		ExecutorService executorServiceMock = Executors.newCachedThreadPool();
		wrappingService = new WrappingService(executorServiceMock);
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testWrapText10() {
		String sessionId = "ABC001";
		Wrapping wrapping = new Wrapping();
		wrapping.setWorkId(-1);
		wrapping.setTextToWrap("This is a test sentence to smoke-test line breaking.");
		wrapping = wrappingService.wrapText(wrapping, sessionId);
		// sleep 1 sec, and rerun service - otherwise unit tests not working!
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		wrapping = wrappingService.getWrapping(wrapping.getWorkId(), sessionId);
		//
		Assert.assertNotEquals("Wrapping processed", wrapping.getWorkId(), -1);
		Assert.assertArrayEquals("Wrapped text", wrapping.getWrappedText().toArray(),
				new String[] { "This is a ", "test ", "sentence ", "to ", "smoke-test", "line ", "breaking." });
	}

	@Test
	public void testWrapText5() {
		String sessionId = "ABC001";
		Wrapping wrapping = new Wrapping();
		wrapping.setWorkId(-1);
		wrapping.setTextToWrap("This is a test where n is very tiny.");
		wrapping.setWrapLength(5);
		wrapping = wrappingService.wrapText(wrapping, sessionId);
		// sleep 1 sec, and rerun service - otherwise unit tests not working!
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		wrapping = wrappingService.getWrapping(wrapping.getWorkId(), sessionId);
		//
		Assert.assertNotEquals("Wrapping processed", wrapping.getWorkId(), -1);
		Assert.assertArrayEquals("Wrapped text", wrapping.getWrappedText().toArray(),
				new String[] { "This ", "is a ", "test ", "where", "n is ", "very ", "tiny." });
	}

}
