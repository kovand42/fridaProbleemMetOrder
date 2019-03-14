package be.vdab.frida.repositories;

import be.vdab.frida.domain.Saus;
import be.vdab.frida.exceptions.SausRepositoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Order(2)
@Component
public class PropertiesSausRepository implements SausRepository {
    private static final Path PAD = Paths.get("c:/data/sauzen.properties");
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public List<Saus> findAll(){
        try {
            return Files.lines(PAD)
                .filter(regel -> ! regel.isEmpty())
                .map(regel -> maakSaus(regel))
                .collect(Collectors.toList());
        } catch (IOException ex) {
            String fout = "Fout bij lezen " + PAD;
            logger.error(fout, ex);
            throw new SausRepositoryException(fout);
        }
    }
    private Saus maakSaus(String regel) {
        String[] onderdelen = regel.split(":");
        if (onderdelen.length < 2) {
            String fout = PAD + ":" + regel + " bevat minder dan 2 onderdelen";
            logger.error(fout);
            throw new SausRepositoryException(fout);
        }
        try {
            String[] naamEnIngredienten = onderdelen[1].split(",");
            List<String> ingredienten = new ArrayList<>();
            for (int index = 1; index != naamEnIngredienten.length; index++) {
                ingredienten.add(naamEnIngredienten[index]);
            }
            Saus saus = new Saus(Long.parseLong(onderdelen[0]), naamEnIngredienten[0],
                    ingredienten);
            return saus;
            /* new Saus(Long.parseLong(onderdelen[0]), onderdelen[1],
                    ingredienten);*/
        } catch (NumberFormatException ex) {
            String fout = PAD + ":" + regel + " bevat verkeerde id";
            logger.error(fout, ex);
            throw new SausRepositoryException(fout);
        }
    }
}
