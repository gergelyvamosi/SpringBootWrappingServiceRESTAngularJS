package org.gvamosi.wrapping.test;

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
	public void testWrapText() {
		String sessionId = "ABC001";
		Wrapping wrapping = new Wrapping();
		wrapping.setWorkId(-1);
		wrapping.setTextToWrap("This is a test sentence to smoke-test line breaking.");
		wrapping = wrappingService.wrapText(wrapping, sessionId);
		Assert.assertNotEquals("Wrapping processed", wrapping.getWorkId(), -1);
		Assert.assertArrayEquals("Wrapped text", wrapping.getWrappedText().toArray(),
				new String[] { "This is a ", "test ", "sentence ", "to ", "smoke-test", "line ", "breaking." });
	}

}
