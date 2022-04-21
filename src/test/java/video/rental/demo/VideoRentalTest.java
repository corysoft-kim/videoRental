package video.rental.demo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

public class VideoRentalTest {
	private GoldenMaster goldenMaster = new GoldenMaster();
	
	@Test
	@Disabled
	void should_generate_goldenmaster() {
		goldenMaster.generate();
	}
	
	@Test
	@Disabled
	@EnabledOnOs({OS.LINUX, OS.MAC})
	void check_run_results_against_goldenmaster_linux() {
		String expected = goldenMaster.getGoldenMaster();
		String actual = goldenMaster.getRunResult();
		assertEquals(expected, actual.replaceAll("\r\n", "\n"));
	}
	
	@Test
//	@Disabled
	@EnabledOnOs({OS.WINDOWS})
	void check_run_results_against_goldenmaster() {
		String expected = goldenMaster.getGoldenMaster();
		String actual = goldenMaster.getRunResult();
		assertEquals(expected+"\n", actual.replaceAll("\r\n", "\n"));
	}
}
