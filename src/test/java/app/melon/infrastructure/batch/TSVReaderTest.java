package app.melon.infrastructure.batch;

import app.melon.domain.models.region.Region;
import org.junit.jupiter.api.Test;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.util.ResourceUtils;

public class TSVReaderTest {

    @Test
    public void readItem() throws Exception {
        FlatFileItemReader<Region> reader = new FlatFileItemReader<>();
        DefaultResourceLoader loader = new DefaultResourceLoader();

        String path = ResourceUtils.getURL("regions.tsv").toString();
        reader.setResource(loader.getResource(path));

        DefaultLineMapper<Region> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames("code", "county", "town", "district");
        tokenizer.setDelimiter("\t");
        tokenizer.setStrict(false);
        lineMapper.setLineTokenizer(tokenizer);

        BeanWrapperFieldSetMapper<Region> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Region.class);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        reader.setLineMapper(lineMapper);
        reader.setLinesToSkip(1);

        ExecutionContext context = new ExecutionContext();
        reader.open(context);
        Region region;
        while ((region = reader.read()) != null) {
            System.out.println(region);
        }
        reader.close();
    }
}
