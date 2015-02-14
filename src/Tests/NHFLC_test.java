package Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import NHFLC.NHFLC;

/**
 * NHFLC_test
 * 
 * Tests the non-singleton input controller
 * 
 * @author Vaidas
 *
 */
public class NHFLC_test {

	@Test
	public void test() {
		NHFLC controller = new NHFLC();
		assertNotNull(controller);
	}

}
