package com.example.service;

import com.example.model.MusicalArtist;
import com.example.model.Person;
import java.util.List;

/**
 *
 * @author Alejandro Téllez G. <java.util.fck@hotmail.com>
 * @date 26/11/2012 - 03:59:19 AM
 */
public interface MusicalArtistService {

    public void addMusicalArtist(MusicalArtist musicalArtist);

    public List<MusicalArtist> listMusicalArtist();

    public void removeMusicalArtist(String name);
}
