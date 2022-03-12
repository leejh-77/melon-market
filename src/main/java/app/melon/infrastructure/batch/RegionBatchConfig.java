package app.melon.infrastructure.batch;

import app.melon.domain.models.region.Region;
import app.melon.web.configs.ApplicationProperties;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.DefaultJobParametersValidator;
import org.springframework.batch.core.step.tasklet.MethodInvokingTaskletAdapter;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManagerFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Configuration
@EnableBatchProcessing
public class RegionBatchConfig {

    private static final String JOB_NAME = "regionImportJob";

    @Autowired
    EntityManagerFactory entityManagerFactory;

    @Autowired
    StepBuilderFactory stepBuilderFactory;

    @Autowired
    JobBuilderFactory jobBuilderFactory;

    @Bean
    public JobParametersValidator jobParametersValidator() {
        String[] required = new String[]{};
        String[] optional = new String[]{"executionTime"};
        return new DefaultJobParametersValidator(required, optional);
    }

    @Bean
    @Qualifier("regionImportJob")
    public Job regionImportJob() throws FileNotFoundException {
        return jobBuilderFactory.get(JOB_NAME)
                .validator(this.jobParametersValidator())
                .start(this.downloadCSVStep())
                .next(this.importRegionStep()).build();
    }

    @Bean
    public Step downloadCSVStep() {
        return stepBuilderFactory.get("downloadCSVStep")
                .tasklet(downloadCSVTasklet()).build();
    }

    @Bean
    public MethodInvokingTaskletAdapter downloadCSVTasklet() {
        MethodInvokingTaskletAdapter adapter = new MethodInvokingTaskletAdapter();
        adapter.setTargetObject(this.downloadSCVService());
        adapter.setTargetMethod("execute");
        return adapter;
    }

    @Bean
    public DownloadCSVService downloadSCVService() {
        return new DownloadCSVService();
    }

    @Bean
    public Step importRegionStep() throws FileNotFoundException {
        return stepBuilderFactory.get("importRegionStep").<Region, Region>chunk(100)
                .reader(fileItemReader())
                .writer(dbItemWriter()).build();
    }

    @Bean
    @StepScope
    public FlatFileItemReader<Region> fileItemReader() throws FileNotFoundException {
        DefaultLineMapper<Region> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames("code", "county", "town", "district");
        tokenizer.setDelimiter("\t");
        tokenizer.setStrict(false);
        lineMapper.setLineTokenizer(tokenizer);

        BeanWrapperFieldSetMapper<Region> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Region.class);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        String path = ResourceUtils.getURL("regions.tsv").toString();
        DefaultResourceLoader loader = new DefaultResourceLoader();

        return new FlatFileItemReaderBuilder<Region>()
                .name("TSV Reader")
                .resource(loader.getResource(path))
                .lineMapper(lineMapper)
                .linesToSkip(1).build();
    }

    @Bean
    @StepScope
    public ItemWriter<Region> dbItemWriter() {
        JpaItemWriter<Region> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(this.entityManagerFactory);
        return writer;
    }


    public static class DownloadCSVService {

        @Autowired
        private ApplicationProperties applicationProperties;

        public ExitStatus execute() throws IOException {
            UriComponents url = UriComponentsBuilder
                    .fromHttpUrl(this.applicationProperties.getRegionDownloadUrl())
                    .build();

            RestTemplate template = this.buildTemplate();
            ResponseEntity<String> response = template.exchange(url.toString(), HttpMethod.GET, null, String.class);
            this.saveToFile(response.getBody());
            return ExitStatus.COMPLETED;
        }

        private RestTemplate buildTemplate() {
            HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
            factory.setConnectionRequestTimeout(5000);
            factory.setReadTimeout(5000);
            return new RestTemplate(factory);
        }

        private void saveToFile(String body) throws IOException {
            String path = ResourceUtils.getURL("regions.tsv").getFile();
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
            if (file.createNewFile()) {
                Files.writeString(file.toPath(), body);
            }
        }
    }

}
