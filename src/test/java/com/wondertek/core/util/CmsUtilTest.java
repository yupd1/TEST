package com.wondertek.core.util;

import static org.junit.Assert.*;

import org.junit.Test;

import com.wondertek.mobilevideo.core.util.CmsUtil;

public class CmsUtilTest {

	@Test
	public void testIdToPath() {
		assertTrue(CmsUtil.idToPath(1L).equals("0/0"));
		assertTrue(CmsUtil.idToPath(10L).equals("0/0"));
		assertTrue(CmsUtil.idToPath(101L).equals("0/0"));
		assertTrue(CmsUtil.idToPath(1001L).equals("0/1"));
		assertTrue(CmsUtil.idToPath(11001L).equals("0/11"));
		assertTrue(CmsUtil.idToPath(111001L).equals("0/111"));
		assertTrue(CmsUtil.idToPath(101001L).equals("0/101"));
		assertTrue(CmsUtil.idToPath(1111001L).equals("1/111"));
		assertTrue(CmsUtil.idToPath(10011001L).equals("10/11"));
		assertTrue(CmsUtil.idToPath(100011001L).equals("100/11"));
		assertTrue(CmsUtil.idToPath(1000011001L).equals("1000/11"));
		assertTrue(CmsUtil.idToPath(11000011001L).equals("11000/11"));
	}

	@Test
	public void testIdToName() {
		assertTrue(CmsUtil.idToName(1L).equals("0/0/1"));
		assertTrue(CmsUtil.idToName(10L).equals("0/0/10"));
		assertTrue(CmsUtil.idToName(101L).equals("0/0/101"));
		assertTrue(CmsUtil.idToName(1001L).equals("0/1/1"));
		assertTrue(CmsUtil.idToName(11001L).equals("0/11/1"));
		assertTrue(CmsUtil.idToName(111001L).equals("0/111/1"));
		assertTrue(CmsUtil.idToName(101001L).equals("0/101/1"));
		assertTrue(CmsUtil.idToName(1111001L).equals("1/111/1"));
		assertTrue(CmsUtil.idToName(10011001L).equals("10/11/1"));
		assertTrue(CmsUtil.idToName(100011001L).equals("100/11/1"));
		assertTrue(CmsUtil.idToName(1000011001L).equals("1000/11/1"));
		assertTrue(CmsUtil.idToName(11000011001L).equals("11000/11/1"));
	}

	@Test
	public void testIdToShortName() {
		assertTrue(CmsUtil.idToShortName(1L).equals("1"));
		assertTrue(CmsUtil.idToShortName(10L).equals("10"));
		assertTrue(CmsUtil.idToShortName(101L).equals("101"));
		assertTrue(CmsUtil.idToShortName(1001L).equals("1"));
		assertTrue(CmsUtil.idToShortName(11001L).equals("1"));
		assertTrue(CmsUtil.idToShortName(111001L).equals("1"));
		assertTrue(CmsUtil.idToShortName(101001L).equals("1"));
		assertTrue(CmsUtil.idToShortName(1111001L).equals("1"));
		assertTrue(CmsUtil.idToShortName(10011001L).equals("1"));
		assertTrue(CmsUtil.idToShortName(100011001L).equals("1"));
		assertTrue(CmsUtil.idToShortName(1000011001L).equals("1"));
		assertTrue(CmsUtil.idToShortName(11000011001L).equals("1"));
	}

	@Test
	public void testxml() {
		assertTrue(CmsUtil.idToFullPathImage(1L,"png").equals("/image/0/0/1.png"));
		assertTrue(CmsUtil.idToFullPathImage(10L,"png").equals("/image/0/0/10.png"));
		assertTrue(CmsUtil.idToFullPathImage(101L,"png").equals("/image/0/0/101.png"));
		assertTrue(CmsUtil.idToFullPathImage(1001L,"png").equals("/image/0/1/1.png"));
		assertTrue(CmsUtil.idToFullPathImage(11001L,"png").equals("/image/0/11/1.png"));
		assertTrue(CmsUtil.idToFullPathImage(111001L,"png").equals("/image/0/111/1.png"));
		assertTrue(CmsUtil.idToFullPathImage(101001L,"png").equals("/image/0/101/1.png"));
		assertTrue(CmsUtil.idToFullPathImage(1111001L,"png").equals("/image/1/111/1.png"));
		assertTrue(CmsUtil.idToFullPathImage(10011001L,"png").equals("/image/10/11/1.png"));
		assertTrue(CmsUtil.idToFullPathImage(100011001L,"png").equals("/image/100/11/1.png"));
		assertTrue(CmsUtil.idToFullPathImage(1000011001L,"png").equals("/image/1000/11/1.png"));
		assertTrue(CmsUtil.idToFullPathImage(11000011001L,"png").equals("/image/11000/11/1.png"));
	}

	@Test
	public void testIdToFullPathContent() {
		assertTrue(CmsUtil.idToFullPathContent(1L,"xml").equals("/cont/0/0/1.xml"));
		assertTrue(CmsUtil.idToFullPathContent(10L,"xml").equals("/cont/0/0/10.xml"));
		assertTrue(CmsUtil.idToFullPathContent(101L,"xml").equals("/cont/0/0/101.xml"));
		assertTrue(CmsUtil.idToFullPathContent(1001L,"xml").equals("/cont/0/1/1.xml"));
		assertTrue(CmsUtil.idToFullPathContent(11001L,"xml").equals("/cont/0/11/1.xml"));
		assertTrue(CmsUtil.idToFullPathContent(111001L,"xml").equals("/cont/0/111/1.xml"));
		assertTrue(CmsUtil.idToFullPathContent(101001L,"xml").equals("/cont/0/101/1.xml"));
		assertTrue(CmsUtil.idToFullPathContent(1111001L,"xml").equals("/cont/1/111/1.xml"));
		assertTrue(CmsUtil.idToFullPathContent(10011001L,"xml").equals("/cont/10/11/1.xml"));
		assertTrue(CmsUtil.idToFullPathContent(100011001L,"xml").equals("/cont/100/11/1.xml"));
		assertTrue(CmsUtil.idToFullPathContent(1000011001L,"xml").equals("/cont/1000/11/1.xml"));
		assertTrue(CmsUtil.idToFullPathContent(11000011001L,"xml").equals("/cont/11000/11/1.xml"));
	}

	@Test
	public void testNameToId() {
		assertTrue(CmsUtil.nameToId("0/0/1")==1L);
		assertTrue(CmsUtil.nameToId("0/0/10")==10L);
		assertTrue(CmsUtil.nameToId("0/0/101").equals(101L));
		assertTrue(CmsUtil.nameToId("0/1/1").equals(1001L));
		assertTrue(CmsUtil.nameToId("0/11/1").equals(11001L));
		assertTrue(CmsUtil.nameToId("0/111/1").equals(111001L));
		assertTrue(CmsUtil.nameToId("0/101/1").equals(101001L));
		assertTrue(CmsUtil.nameToId("1/111/1").equals(1111001L));
		assertTrue(CmsUtil.nameToId("10/11/1").equals(10011001L));
		assertTrue(CmsUtil.nameToId("100/11/1").equals(100011001L));
		assertTrue(CmsUtil.nameToId("1000/11/1").equals(1000011001L));
		assertTrue(CmsUtil.nameToId("11000/11/1").equals(11000011001L));
	}

	@Test
	public void testReplaceSeparator() {
		assertTrue(CmsUtil.replaceSeparator("0/0/1").equals("0/0/1"));
		assertTrue(CmsUtil.replaceSeparator("0\\0/1").equals("0/0/1"));
		assertTrue(CmsUtil.replaceSeparator("\\0\\0/1").equals("/0/0/1"));
		assertTrue(CmsUtil.replaceSeparator("\\\\0\\0/1").equals("/0/0/1"));
		assertTrue(CmsUtil.replaceSeparator("\\0\\0\\1").equals("/0/0/1"));
		assertTrue(CmsUtil.replaceSeparator("\\\\0\\\\0/1").equals("/0/0/1"));
	}
/*
	@Test
	public void testIsAvalidIdPath() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetErrorTextStringObjectArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetErrorTextString() {
		fail("Not yet implemented");
	}

	@Test
	public void testTransObjectPropertiesToFieldsMap() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetContentUrl() {
		fail("Not yet implemented");
	}

	@Test
	public void testSort() {
		fail("Not yet implemented");
	}

	@Test
	public void testFormatNumber() {
		fail("Not yet implemented");
	}

	@Test
	public void testRandom() {
		fail("Not yet implemented");
	}

	@Test
	public void testCheckVersion() {
		fail("Not yet implemented");
	}

	@Test
	public void testVersionToLong() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetFileType() {
		fail("Not yet implemented");
	}

	@Test
	public void testExistsIgnoreCase() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemoveIgnoreCase() {
		fail("Not yet implemented");
	}

	@Test
	public void testStrToList() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsAvalidStringData() {
		fail("Not yet implemented");
	}

	@Test
	public void testStrXMLFormat() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsFileAccept() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetHSSFCellValue() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetContentThreeDir() {
		fail("Not yet implemented");
	}

	@Test
	public void testChangeCompany() {
		fail("Not yet implemented");
	}

	@Test
	public void testStringToList() {
		fail("Not yet implemented");
	}

	@Test
	public void testStringToLongList() {
		fail("Not yet implemented");
	}

	@Test
	public void testBigListToLongList() {
		fail("Not yet implemented");
	}

	@Test
	public void testObjectToLong() {
		fail("Not yet implemented");
	}
*/
}
