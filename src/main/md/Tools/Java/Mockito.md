## What are all test related objects are there?
1. Fake object
   1. Light weight - but not actual as production object
   2. H2 Database instead of Oracle
   3. Test Doubles
2. Dummy Objects
   1. Doesn't have business logic
   2. Only used for code compile
3. Stub
   1. Provides pre-defined answers to method executions made during the test
   2. Hardcode programmatically for a particular test
4. Spy
   1. Record the information about how they were invoked
   2. Its mostly used with stubbed external dependency + recording every interaction with external dependency
5. Mock
   1. Mocking help us in testing functionality of class in isolation
   2. Helps to test any class under test in isolation

## Simple mockito example

```java
	public void demoMockWithMockito(){
		BookRepository bookRepositoryMock = Mockito.mock(BookRepository.class);
		BookService bookService = new BookService(bookRepositoryMock);
		
		Book book1 = new Book("1234", "Mockito In Action", 500, LocalDate.now());
		Book book2 = new Book("1235", "JUnit 5 In Action", 400, LocalDate.now());
		
		bookService.addBook(book1); // return
		bookService.addBook(book2); // save will be called
		
		Mockito.verify(bookRepositoryMock, Mockito.times(1)).save(book2);
		Mockito.verify(bookRepositoryMock, Mockito.times(0)).save(book1);
	}
```

## Mockito Annotation
1. @Mock
   1. @ExtendWith(MockitoExtention.class) - For Junit5 test-classes  with @Mock annotation 
   2. @RunWith(MockitoJunitRunner.class) - can be used to for Junit 4 test-classes with @Mock annotation
2. @Mock - can be replaced with mock(mmm.class) method
3. [@InjectMock](https://github.com/dinesh-varyani/mockito/blob/master/src/test/java/com/hubberspot/mockito/annotations/support/AnnotationsTest.java)
   1. Can be used to inject mocks (from other annotation) can be injected into that service

## [Mockito Contributors](https://github.com/mockito/mockito/graphs/contributors)
1. [Mockito People](https://github.com/orgs/mockito/people)
2. [Szczepan Faber](https://www.linkedin.com/in/sfaber)
   1. [Mockito Presentation](https://jazoon.com/history/Portals/0/Content/ArchivWebsite/jazoon.com/jazoon09/download/presentations/8983.pdf)
   2. [Great tools for engineers: even cleaner tests with Mockito 2 by Szczepan Faber](https://www.youtube.com/watch?v=Rl7g0duuDkU)
   3. [Mockito blog](https://szczepiq.wordpress.com/category/mockito/)
   4. [Faber blog](https://szczepiq.wordpress.com/2008/04/26/asking-and-telling/)
3. [Brice Dutheil](https://blog.arkey.fr)
   1. [Brice Mocito Answers](https://stackoverflow.com/users/48136/brice?tab=answers)


## Mockito Tutorial

4. [Mockito Full Course in 7 Hours (Beginner to Pro)](https://github.com/dinesh-varyani/mockito)