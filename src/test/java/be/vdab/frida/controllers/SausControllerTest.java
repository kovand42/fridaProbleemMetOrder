package be.vdab.frida.controllers;

import be.vdab.frida.domain.Saus;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
public class SausControllerTest {
    private SausController controller;
    @Before
    public void before(){
        controller = new SausController();
    }
    @Test
    public void sauzenGebruiktSauzenHtml(){
        assertEquals("sauzen", controller.sauzen().getViewName());
    }
    @Test
    public void sauzenGeeftSauzenDoor(){
        assertTrue(controller.sauzen().getModel().get("sauzen") instanceof Saus[]);
    }
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
