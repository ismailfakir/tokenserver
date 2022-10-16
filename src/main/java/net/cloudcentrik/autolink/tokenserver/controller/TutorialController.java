package net.cloudcentrik.autolink.tokenserver.controller;

import net.cloudcentrik.autolink.tokenserver.model.Tutorial;
import net.cloudcentrik.autolink.tokenserver.repository.TutorialRepository;
import net.cloudcentrik.autolink.tokenserver.utils.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class TutorialController {
    @Autowired
    TutorialRepository tutorialRepository;

    @PostMapping("/tutorials")
    public ResponseEntity<Tutorial> createTutorial(@RequestBody Tutorial tutorial) {
        tutorial.setToken(new TokenGenerator().generateToken());
        tutorialRepository.save(tutorial);

        return ResponseEntity.ok(tutorial);
    }
}
