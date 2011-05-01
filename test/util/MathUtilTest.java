package util;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.teamjava.tankwar.engine.util.MathUtil;

/**
 * @author Olav Jensen
 * @since 5/1/11
 */
public class MathUtilTest {

	@Test
	public void testGetYBetweenPoints() {
		float result = MathUtil.getYBetweenPoints(0, 0, 10, 10, 5);

		Assert.assertEquals(result, 5.0f);

		result = MathUtil.getYBetweenPoints(10, 0, 20, 10, 15);
		Assert.assertEquals(result, 5.0f);

		result = MathUtil.getYBetweenPoints(10, 5, 11, 7, 10.5f);
		Assert.assertEquals(result, 6.0f);

		result = MathUtil.getYBetweenPoints(10, 10, 11, 20, 10.25f);
		Assert.assertEquals(result, 12.5f);

		result = MathUtil.getYBetweenPoints(10, 20, 11, 10, 10.75f);
		Assert.assertEquals(result, 12.5f);
	}
}
