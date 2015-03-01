package Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import HFLC.SHFLC.SHFLC;

/**
 * SHFLC_test
 * 
 * Tests the singleton input controller
 * 
 * @author Vaidas
 *
 */
public class SHFLC_test {

	@Test
	public void test() {
		SHFLC controller = new SHFLC();
		assertNotNull(controller);
	}
}
