import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/*
 * Denna klass inneh�ller JUnit-testfall ni kan anv�nda f�r att testa er
 * implementation av listan. Som ni m�rker �r st�rsta delen av testen
 * bortkommenterade. Anledningen till det �r att det blir f�r mycket att f�rs�ka
 * f� allting att fungera fr�n b�rjan. F�rs�k ist�llet att implementera den
 * funktionalitet som kr�vs av de f�rsta testen innan ni g�r vidare. Ta sen ett
 * test i taget och avkommentera det.
 * 
 * N�r alla test fungerar s� har ni *TROLIGEN* en godk�nd l�sning om ni ocks�
 * f�ljt de krav som inte g�r att testa. Vi s�ger troligen d�rf�r att testen
 * inte �r hundraprocentiga, det g�r inte. Det finns alltid mer att testa, och
 * det �r fullt m�jligt att vi l�gger till test under veckan om vi kommer p� att
 * vi missat att testa n�got.
 * 
 * Beroende p� inst�llningar kommer ni eventuellt att f� varningar f�r ej
 * anv�nda import-satser. Dessa anv�nds av testfalls som fr�n b�rjan inte �r
 * aktiverade, s� l�t dem vara kvar.
 * 
 * @author Henrik
 */
public class ALDAListTest {

	ALDAList<String> list = new MyALDAList<String>();

	private void testField(java.lang.reflect.Field f) {
		assertTrue("Alla attribut bör vara privata",
				java.lang.reflect.Modifier.isPrivate(f.getModifiers()));
		assertFalse("Finns ingen anledning att använda några arrayer", f
				.getType().isArray());
		assertFalse(
				"Finns antagligen ingen anledning att ha några statiska attribut",
				java.lang.reflect.Modifier.isStatic(f.getModifiers()));
		for (Class<?> i : f.getType().getInterfaces()) {
			assertFalse(
					"Du ska inte använda någon funktionalitet ur java.util utan skriva all kod själv",
					i.getName().startsWith("java.util"));
		}
	}

	@Test
	public void testObviousImplementationErrors() {
		for (java.lang.reflect.Field f : list.getClass().getDeclaredFields()) {
			testField(f);
		}
	}

	@Test
	public void testEmpty() {
		list = new MyALDAList<String>();
		assertEquals(0, list.size());
		assertEquals("[]", list.toString());
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testGetOnEmptyList() {
		list = new MyALDAList<String>();
		list.get(0);
	}

	@Before
	public void setUp() {
		list.add("First");
		list.add("Second");
		list.add("Third");
		list.add("Fourth");
		list.add("Fifth");
		}

	 @Test
	 public void testSimpleMethodsOnDefaultList() {
	 assertEquals(5, list.size());
	 assertEquals("First", list.get(0));
	 assertEquals("Third", list.get(2));
	 assertEquals("Fifth", list.get(4));
	 assertEquals("[First, Second, Third, Fourth, Fifth]", list.toString());

	 list.add("Second");
	 assertEquals(6, list.size());
	 assertEquals("Second", list.get(5));
	 }
	
	 @Test(expected = IndexOutOfBoundsException.class)
	 public void testIndexBelowZero() {
	 list.get(-1);
	 }

	 @Test(expected = IndexOutOfBoundsException.class)
	 public void testIndexAboveMax() {
	 list.get(5);
	 }

	 @Test
	 public void addWithIndex() {
	 list.add(0, "A");
	 list.add(6, "B");
	 list.add(2, "C");
	 assertEquals(8, list.size());
	 assertEquals("A", list.get(0));
	 assertEquals("C", list.get(2));
	 assertEquals("B", list.get(7));
	 assertEquals("[A, First, C, Second, Third, Fourth, Fifth, B]",
	 list.toString());
	 }

	 @Test(expected = IndexOutOfBoundsException.class)
	 public void testAddIndexBelowZero() {
	 list.add(-1, "ABC");
	 }

	 @Test(expected = IndexOutOfBoundsException.class)
	 public void testAddIndexAboveMax() {
	 list.add(6, "ABC");
	 }

	 @Test
	 public void testClear() {
	 list.clear();
	 assertEquals(0, list.size());
	 list.add("First");
	 list.add(0, "Second");
	 assertEquals(2, list.size());
	 assertEquals("First", list.get(1));
	 assertEquals("Second", list.get(0));
	 }

	 @Test
	 public void testContains() {
	 assertTrue(list.contains("First"));
	 assertTrue(list.contains("Third"));
	 assertFalse(list.contains("ABC"));
	 assertFalse(list.contains(""));
	 }

	 @Test
	 public void testIndexOf() {
	 assertEquals(0, list.indexOf("First"));
	 assertEquals(4, list.indexOf("Fifth"));
	 assertEquals(-1, list.indexOf("ABC"));
	 list.add("Second");
	 assertEquals(1, list.indexOf("Second"));
	 }

	 @Test
	    public void testRemoveWithIndex() {
	        assertEquals("Third", list.remove(2));
	        assertEquals(4, list.size());
	        assertEquals("Second", list.get(1));
	        assertEquals("Fourth", list.get(2));

	        assertEquals("First", list.remove(0));
	        assertEquals(3, list.size());
	        assertEquals("Second", list.get(0));

	        assertEquals("Fifth", list.remove(2));
	        assertEquals(2, list.size());
	        assertEquals("Fourth", list.get(1));
	    }
	   // Nytt test som speciellt kontrollerar borttag i slutet av listan, ett vanligt problem
	    @Test
	    public void testRemoveAtEnd() {
	        list.remove(4);
	        assertEquals(4, list.size());
	        list.remove(3);
	        assertEquals(3, list.size());
	        assertEquals("[First, Second, Third]", list.toString());

	        list.add("A");
	        assertEquals(4, list.size());
	        assertEquals("[First, Second, Third, A]", list.toString());
	        list.add("B");
	        assertEquals(5, list.size());
	        assertEquals("[First, Second, Third, A, B]", list.toString());

	        list.remove(4);
	        assertEquals(4, list.size());
	        list.remove(3);
	        assertEquals(3, list.size());
	        assertEquals("[First, Second, Third]", list.toString());

	        list.add(3, "A");
	        assertEquals(4, list.size());
	        assertEquals("[First, Second, Third, A]", list.toString());
	        list.add(4, "B");
	        assertEquals(5, list.size());
	        assertEquals("[First, Second, Third, A, B]", list.toString());
	    }

	    // Nytt test som kontrollerar borttag av dubletter i listan
	    @Test
	    public void testRemoveDuplicates() {
	        list.add("First");
	        list.add("Third");
	        list.add("Fifth");
	        assertEquals(
	                "[First, Second, Third, Fourth, Fifth, First, Third, Fifth]",
	                list.toString());

	        list.remove("Third");
	        assertEquals("[First, Second, Fourth, Fifth, First, Third, Fifth]",
	                list.toString());
	        list.remove("Third");
	        assertEquals("[First, Second, Fourth, Fifth, First, Fifth]",
	                list.toString());
	        list.remove("First");
	        assertEquals("[Second, Fourth, Fifth, First, Fifth]", list.toString());
	        list.remove("Second");
	        assertEquals("[Fourth, Fifth, First, Fifth]", list.toString());
	        list.remove("Fifth");
	        assertEquals("[Fourth, First, Fifth]", list.toString());
	        list.remove("Fifth");
	        assertEquals("[Fourth, First]", list.toString());

	        list.add(0, "A");
	        list.add("B");
	        assertEquals("[A, Fourth, First, B]", list.toString());
	    }

	 @Test(expected = IndexOutOfBoundsException.class)
	 public void testRemoveIndexBelowZero() {
	 list.remove(-1);
	 }

	 @Test(expected = IndexOutOfBoundsException.class)
	 public void testRemoveIndexAboveMax() {
	 list.remove(5);
	 }

	 @Test
	 public void testRemoveObject() {
	 assertTrue(list.remove("Third"));
	 assertEquals("Second", list.get(1));
	 assertEquals("Fourth", list.get(2));

	 list.remove("First");
	 assertEquals(3, list.size());
	 assertEquals("Second", list.get(0));

	 list.remove("Fifth");
	 assertEquals(2, list.size());
	 assertEquals("Fourth", list.get(1));

	 list.remove("ABC");
	 assertEquals(2, list.size());
	 assertEquals("Second", list.get(0));
	 assertEquals("Fourth", list.get(1));
	 }

	 private static final java.util.Random rnd = new java.util.Random();
	 private static final String[] names = { "Adam", "Bertil", "Cesar",
	 "David",
	 "Erik", "Filip", "Gustav", "Helge", "Ivar", "Johan", "Kalle",
	 "ludvig", "Martin", "Niklas" };

	 private String randomName() {
	 return names[rnd.nextInt(names.length)];
	 }

	 @Test
	 public void testMix() {
	 list.clear();
	 java.util.List<String> oracle = new java.util.ArrayList<String>();

	 for (int n = 0; n < 1000; n++) {
	 String name = randomName();

	 //G�r en slumpm�ssig ins�ttning
	 switch (rnd.nextInt(5)) {
	 case 0:
	 list.add(name);
	 oracle.add(name);
	 break;
	 case 1:
	 list.add(0, name);
	 oracle.add(0, name);
	 break;
	 case 2:
	 list.add(list.size(), name);
	 oracle.add(oracle.size(), name);
	 break;
	 case 3:
	 case 4:
	 int index = list.size() == 0 ? 0 : rnd.nextInt(list.size());
	 list.add(index, name);
	 oracle.add(index, name);
	 break;
	 }

	 if (oracle.size() > 0) {

	// G�r ett slumpm�ssigt borttag i 70% av fallen
	 switch (rnd.nextInt(10)) {
	 case 3:
	 list.remove(0);
	 oracle.remove(0);
	 break;
	 case 4:
	 list.remove(list.size() - 1);
	 oracle.remove(oracle.size() - 1);
	 break;
	 case 5:
	 case 6:
	 int index = rnd.nextInt(list.size());
	 list.remove(index);
	 oracle.remove(index);
	 break;
	 case 7:
	 case 8:
	 name = randomName();
	 list.remove(name);
	 oracle.remove(name);
	 break;
	 case 9:
	 if (rnd.nextInt(10) < 2) {
	 list.clear();
	 oracle.clear();
	 }
	 }
	 }

	 if (oracle.size() == 0) {
	 assertEquals(0, list.size());
	 } else {
	// G�r en slumpm�ssig kontroll
	 switch (rnd.nextInt(10)) {
	 case 0:
	 assertEquals(oracle.size(), list.size());
	 break;
	 case 1:
	 assertEquals(oracle.get(0), list.get(0));
	 break;
	 case 2:
	 assertEquals(oracle.get(oracle.size() - 1),
	 list.get(list.size() - 1));
	 break;
	 case 3:
	 case 4:
	 case 5:
	 case 6:
	 case 7:
	 case 8:
	 int index = rnd.nextInt(list.size());
	 assertEquals(oracle.get(index), list.get(index));
	 break;
	 case 9:
	 assertEquals(oracle.toString(), list.toString());
	 break;
	 }
	 }
	 }

	 }

	 @Test
	 public void testIsItearble() {
	 for (String s : list)
	 s.length(); // Ointressant men tar bort en varning om att s inte
	 // anv�nds
	 }

	 @Test
	 public void testBasicIteration() {
	 Iterator<String> i = list.iterator();
	 assertTrue(i.hasNext());
	 assertEquals("First", i.next());
	 assertTrue(i.hasNext());
	 assertEquals("Second", i.next());
	 assertTrue(i.hasNext());
	 assertEquals("Third", i.next());
	 assertTrue(i.hasNext());
	 assertEquals("Fourth", i.next());
	 assertTrue(i.hasNext());
	 assertEquals("Fifth", i.next());
	 assertFalse(i.hasNext());
	 }

	 @Test(expected=NoSuchElementException.class)
	 public void testToLongIteration(){
	 Iterator<String> i = list.iterator();
	 for(int n=0; n<=list.size(); n++){
	 i.next();
	 }
	 }
	 @Test(expected=NoSuchElementException.class)
	 public void testIterationOnEmptyList(){
	 list.clear();
	 Iterator<String> i = list.iterator();
	 assertFalse(i.hasNext());
	 i.next();
	 }

	 @Test
	 public void testMultipleConcurrentIterators() {
	 Iterator<String> i1 = list.iterator();
	 assertTrue(i1.hasNext());
	 assertEquals("First", i1.next());
	 assertEquals("Second", i1.next());
	 Iterator<String> i2 = list.iterator();
	 assertTrue(i2.hasNext());
	 assertEquals("First", i2.next());
	 assertEquals("Third", i1.next());
	 assertEquals("Second", i2.next());
	 assertEquals("Fourth", i1.next());
	 assertEquals("Third", i2.next());
	 assertEquals("Fourth", i2.next());
	 assertEquals("Fifth", i2.next());
	 assertEquals("Fifth", i1.next());
	 assertFalse(i1.hasNext());
	 assertFalse(i1.hasNext());
	 }

	 @Test
	 public void testRemoveOnIterator(){
	 Iterator<String> i = list.iterator();
	 assertEquals("First",i.next());
	 i.remove();
	 assertEquals(4, list.size());
	 assertEquals("Second", list.get(0));
	 assertEquals("Second",i.next());
	 assertEquals("Third",i.next());
	 i.remove();
	 assertEquals(3, list.size());
	 assertEquals("Second", list.get(0));
	 assertEquals("Fourth", list.get(1));
	 assertEquals("Fourth",i.next());
	 assertEquals("Fifth",i.next());
	 i.remove();
	 assertEquals(2, list.size());
	 assertEquals("Second", list.get(0));
	 assertEquals("Fourth", list.get(1));
	 }

	 @Test(expected = IllegalStateException.class)
	 public void testRemoveOnIteratorWithoutNext(){
		 Iterator<String> i = list.iterator();
	 i.remove();
	 }
	 @Test(expected = IllegalStateException.class)
	 public void testRemoveOnIteratorTwice(){
		 Iterator<String> i = list.iterator();
	 i.next();
	 i.remove();
	 i.remove();
	 }

}