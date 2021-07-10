package service.impl;

import model.Region;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import service.RegionService;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RegionServiceImplTest {


    private static final Long id = 1L;
    private static final String regionName = "Barcelona";
    private static final Long writerId = 3L;

    private static final Region regionCreate = new Region(regionName, writerId);
    private static final Region regionUpdated = new Region(id, regionName, writerId);


    @Mock
    private Region region;

    @Mock
    private List<Region> regionList;

    @Spy
    private RegionService regionService;


    @Before
    public void setUp() {
        String str = "Canada";
        when(region.getRegionName()).thenReturn(str);
    }


    @Test
    public void getRegionTest() {
        assertEquals("Canada", region.getRegionName());
    }


    @Test
    public void createTest() {
        doReturn(regionCreate).when(regionService).create(new Region("Barcelona",writerId));
        assertEquals(regionCreate, regionService.create(new Region("Barcelona", 3L)));
    }


    @Test
    public void updateTest() {
        doReturn(regionUpdated).when(regionService).update(new Region(1L,"Barcelona", 3L));
        assertEquals(regionUpdated, regionService.update(new Region(1L, "Barcelona", writerId)));
    }


    @Test
    public void getAllTest() {
        doReturn(regionList).when(regionService).getAll();
        assertEquals(regionList, regionService.getAll());
    }

}
