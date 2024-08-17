package com.meowmentor.themeservice.subtheme;


import com.meowmentor.themeservice.exceptions.ThemeNotFoundException;
import com.meowmentor.themeservice.exceptions.SubthemeNotFoundException;
import com.meowmentor.themeservice.subtheme.dto.CreateSubthemeDto;
import com.meowmentor.themeservice.theme.Theme;
import com.meowmentor.themeservice.theme.ThemeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubthemeService {

    private final SubthemeRepository subthemeRepository;
    private final ThemeRepository themeRepository;


    public List<Subtheme> getAllSubthemes() {
        return subthemeRepository.findAll();
    }

    public Optional<Subtheme> getSubthemeById(Long id) {
        Optional<Subtheme> subtheme = subthemeRepository.findById(id);
        return subtheme;
    }

    public void createSubtheme(CreateSubthemeDto dto) {

        Theme theme = themeRepository.findById(dto.getThemeId())
                .orElseThrow(() -> new ThemeNotFoundException(dto.getThemeId()));

        Subtheme newSubtheme = new Subtheme();
        newSubtheme.setTitle(dto.getTitle());
        newSubtheme.setTheme(theme);


        subthemeRepository.save(newSubtheme);
        log.info("Created subtheme with title: " + newSubtheme.getTitle() + " under theme: " + theme.getTitle());

    }

    @Transactional
    public void updateSubtheme(Long id, Subtheme updatedSubtheme) {

        Subtheme existingSubtheme = subthemeRepository.findById(id)
                .orElseThrow(() -> new SubthemeNotFoundException(id));

        existingSubtheme.updateFrom(updatedSubtheme);

        subthemeRepository.save(existingSubtheme);
    }



    @Transactional
    public void deleteSubtheme(Long id) {

         subthemeRepository.findById(id)
                .orElseThrow(() -> new SubthemeNotFoundException(id));
         subthemeRepository.deleteById(id);
    }
}