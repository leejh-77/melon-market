package app.melon.web.controllers;

import app.melon.domain.models.region.Region;
import app.melon.domain.services.RegionService;
import app.melon.helper.DataCreator;
import app.melon.web.configs.SecurityConfiguration;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest
@ContextConfiguration(classes = {RegionController.class, SecurityConfiguration.class})
public class RegionControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RegionService regionService;

    @Test
    public void getCounty_shouldSuccess() throws Exception {
        Region region = DataCreator.newRegion();
        doReturn(List.of(region)).when(regionService).findByCode(anyString());

        mvc.perform(get("/api/regions"))
                .andExpect(status().isOk());
    }

}
