package sg.edu.nus.iss.app.day16workshop.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.nus.iss.app.day16workshop.model.Mastermind;
import sg.edu.nus.iss.app.day16workshop.service.BoardGameService;

@RestController // combines @Controller and @ResponseBody
@RequestMapping(path = "/api/boardgame", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class BoardGameController {

    // Autowiring BoardGameService
    @Autowired
    private BoardGameService bgSvc;

    // createBoardGame method with PostMapping annotation
    @PostMapping
    public ResponseEntity<String> createBoardGame(@RequestBody Mastermind ms) {
        // print statement for debugging
        System.out.println("MS >" + ms.toString());
        // variable to store insert count
        int insertCnt = bgSvc.saveGame(ms);
        Mastermind result = new Mastermind();
        result.setId(ms.getId());
        result.setInsertCount(insertCnt);

        // check if insert was successful
        if (insertCnt == 0) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR) // Setting the HTTP status code to 500 (Internal Server Error)
                    .contentType(MediaType.APPLICATION_JSON) // Setting the content-type of the response to JSON
                    .body(result.toJSONInsert().toString());
        }
        // return success response
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result.toJSONInsert().toString());
    }

    // getBoardGame method with GetMapping annotation and PathVariable
    @GetMapping(path = "{msId}")
    public ResponseEntity<String> getBoardGame(@PathVariable String msId) throws IOException {
        Mastermind ms = bgSvc.findById(msId);
        // check if the Mastermind object is null
        if (ms == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND) // Setting the HTTP status code to 404 (Not Found)
                    .contentType(MediaType.APPLICATION_JSON) // Setting the content-type of the response to JSON
                    .body("");
        } // return success response
        return ResponseEntity
                .status(HttpStatus.OK) // Setting the HTTP status code to 200 (OK) indicating a successful request
                .contentType(MediaType.APPLICATION_JSON) // Setting the content-type of the response to JSON
                .body(ms.toJSON().toString()); // Setting the response body to the JSON string representation 
                //of the "ms" object, using a "toJSON()" method.
    }

    // updateBoardGame method with PutMapping annotation, PathVariable and RequestParam
    @PutMapping(path = "{msId}")
    public ResponseEntity<String> updateBoardGame(@RequestBody Mastermind ms,
            @PathVariable String msId, @RequestParam(required=false) boolean isUpsert) throws IOException {
        Mastermind result = null;
        // print statement for debugging
        System.out.println("ctrl > " + isUpsert);
        ms.setUpSert(isUpsert);

        // check if the Mastermind object is null and if it is not an upsert
        if (!isUpsert) {
            result = bgSvc.findById(msId);

            if (null == result)
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST) // Setting the HTTP status code to 400 (Bad Request) 
                        .contentType(MediaType.APPLICATION_JSON) // Setting the content-type of the response to JSON
                        .body("400");
        }
        // if (isUpsert)
        // set the id of the Mastermind object
        ms.setId(msId);
        // variable to store update count
        int updatedCount = bgSvc.update(ms);
        ms.setUpdateCount(updatedCount);
        // return success response
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ms.toJSONUpdate().toString());
    }
}
