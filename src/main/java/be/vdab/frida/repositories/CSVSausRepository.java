package be.vdab.frida.repositories;

import be.vdab.frida.domain.Saus;
import be.vdab.frida.exceptions.SausRepositoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Component
class CSVSausRepository implements SausRepository {
    private static final Path PAD = Paths.get("c:/data/sauzen.csv");
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public List<Saus> findAll() {
        List<Saus> sauzen = new ArrayList<>();
        try {       return Files.lines(PAD)
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
        String[] onderdelen = regel.split(",");
        if (onderdelen.length < 2) {
            String fout = PAD + ":" + regel + " bevat minder dan 2 onderdelen";
            logger.error(fout);
            throw new SausRepositoryException(fout);
        }
        try {
            List<String> ingredienten = new ArrayList<>(onderdelen.length - 2);
            for (int index = 2; index < onderdelen.length; index++) {
                ingredienten.add(onderdelen[index]);
            }
            return new Saus(Long.parseLong(onderdelen[0]), onderdelen[1],
                    ingredienten);
        } catch (NumberFormatException ex) {
            String fout = PAD + ":" + regel + " bevat verkeerde id";
            logger.error(fout, ex);
            throw new SausRepositoryException(fout);
        }
    }
}
