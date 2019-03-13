package be.vdab.frida.controllers;

import be.vdab.frida.domain.Saus;
import be.vdab.frida.services.SausService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SausControllerTest {
    private SausController controller;
    @Mock
    private SausService sausService;
    @Before
    public void before(){
        when(sausService.findById(3)).thenReturn(Optional.of(new Saus(3L, "", null)));
        controller = new SausController(sausService);
    }
    @Test
    public void sauzenGebruiktSauzenHtml(){
        assertEquals("sauzen", controller.sauzen().getViewName());
    }
    @Test
    public void sauzenGeeftSauzenDoor(){
        //assertTrue(controller.sauzen().getModel().get("sauzen") instanceof Saus[]);
        assertTrue(controller.sauzen().getModel().get("sauzen") instanceof List); }
    @Test
    public void sausGebruiktSausHtml(){
        assertEquals("saus", controller.saus(12).getViewName());
    }
    @Test
    public void sausGeeftSausDoor(){
        Saus saus = (Saus) controller.saus(12).getModel().get("saus");
        assertEquals(12, saus.getId());
    }
}
