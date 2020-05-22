package community.flock.contracttestingconsumer;

import community.flock.contracttestingconsumer.zoo.adapter.ZooProducerHttpClient;
import community.flock.contracttestingconsumer.zoo.adapter.dto.ZooDto;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureStubRunner(ids = {"community.flock:contract-testing-producer:+:stubs:6565"},
		stubsMode = StubRunnerProperties.StubsMode.LOCAL)
class ContractTestingConsumerApplicationTests {

	@Autowired
	ZooProducerHttpClient zooProducerHttpClient;

	@Test
	void shouldGetZoos() throws IOException, InterruptedException {
		List<ZooDto> actualResult = zooProducerHttpClient.getZoos();
		Assert.assertNotNull(actualResult);
	}

	@Test
	void shouldReturn404() throws IOException, InterruptedException {
		ZooDto actualResult = zooProducerHttpClient.getZoo("abcde");
		Assert.assertNull(actualResult);
	}

}
