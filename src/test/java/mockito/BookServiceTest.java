package mockito;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BookServiceTest {

    @Test
    public void test() {
        Assert.assertTrue(true);
    }

    @Test
    public void testService() {
        BookService service = mock(BookService.class);
        Book b = new Book();
        when(service.findById(anyString())).thenReturn(b);
        Assert.assertEquals(service.findById("id"), b);
    }
}
