package app.melon.infrastructure.batch;

import app.melon.helper.TestJPAConfig;
import app.melon.web.configs.ApplicationProperties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBatchTest
@SpringBootTest(classes = {RegionBatchConfig.class, TestJPAConfig.class, ApplicationProperties.class})
@EnableTransactionManagement
public class RegionBatchTests {

    @Autowired
    Job job;

    @Autowired
    JobLauncher launcher;

    @Autowired
    JobRepository jobRepository;

    @Autowired
    JobLauncherTestUtils launcherTestUtils;

    @Test
    public void testJob() throws Exception {
//        JobParameters params = new JobParametersBuilder()
//                .addString("filePath", "regions.tsv")
//                .toJobParameters();
//        BatchStatus status = this.launcherTestUtils.launchJob(params).getStatus();
//
//        Assertions.assertEquals(status.name(), "COMPLETED");
    }
}
