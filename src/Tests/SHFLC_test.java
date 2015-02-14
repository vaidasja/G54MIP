package Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import NHFLC.NHFLC;

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
		NHFLC controller = new NHFLC();
		assertNotNull(controller);
	}

}
