package app.melon.infrastructure.batch;

import app.melon.domain.models.region.Region;
import org.junit.jupiter.api.Test;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.util.ResourceUtils;

@SpringBootTest(classes = {RegionBatchConfig.class})
public class TSVReaderTest {

    @Autowired
    private FlatFileItemReader<Region> itemReader;

    @Test
    public void readItem() throws Exception {
        ExecutionContext context = new ExecutionContext();
        itemReader.open(context);
        Region region;
        while ((region = itemReader.read()) != null) {
            System.out.println(region);
        }
        itemReader.close();
    }
}
