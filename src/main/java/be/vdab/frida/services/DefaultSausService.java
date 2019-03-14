package be.vdab.frida.services;

import be.vdab.frida.domain.Saus;
import be.vdab.frida.exceptions.SausRepositoryException;
import be.vdab.frida.repositories.SausRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DefaultSausService implements SausService {
    private final SausRepository[] sausRepositories;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    DefaultSausService(SausRepository[] sausRepositories){
        this.sausRepositories = sausRepositories;
    }
    @Override
    public List<Saus> findAll(){
        for(SausRepository sausRepository : sausRepositories){
            try{
                return sausRepository.findAll();
            }catch(SausRepositoryException ex){
                logger.error("bestanden niet gevonden", ex);
            }
        }
        logger.error("kan sausen van geen enkele bron lezen");
        return null;
    }
    @Override
    public List<Saus> findByNaamBegintMet(char letter) {
        for(SausRepository sausRepository : sausRepositories){
            try{
                return sausRepository.findAll().stream()
                        .filter(saus -> saus.getNaam().charAt(0) == letter)
                        .collect(Collectors.toList());
            }catch(SausRepositoryException ex){
                throw new SausRepositoryException("bestanden niet gevonden");
            }
        }
        return null;
    }
    @Override
    public Optional<Saus> findById(long id){
        for(SausRepository sausRepository : sausRepositories){
            try{
                return sausRepository.findAll().stream()
                        .filter(saus -> saus.getId() == id).findFirst();
            }catch(SausRepositoryException ex){
                throw new SausRepositoryException("Bestanden niet gevonden");
            }
        }
        return null;
    }
}
