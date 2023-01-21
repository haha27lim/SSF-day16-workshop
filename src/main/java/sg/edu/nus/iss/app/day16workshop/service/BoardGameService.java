package sg.edu.nus.iss.app.day16workshop.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.app.day16workshop.model.Mastermind;

@Service
public class BoardGameService {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    // save the game to the redis cache
    public int saveGame(final Mastermind mds) {
        // print the game object in json format
        System.out.println("mds " + mds.toJSON().toString());
        // set the key-value pair in the redis cache, key is the game id, value is the game object in json format
        redisTemplate.opsForValue().set(mds.getId(), mds.toJSON().toString());
         // get the value from the redis cache
        String result = (String) redisTemplate.opsForValue().get(mds.getId());
        // if the value is not null, return 1 to indicate success
        if (result != null)
            return 1;
        // otherwise, return 0 to indicate failure
        return 0;
    }

    // find the game in the redis cache by its id
    public Mastermind findById(final String msid) throws IOException {
        // get the game object in json format from the redis cache
        String mdsStr = (String) redisTemplate.opsForValue().get(msid);
        // convert the json format to Mastermind object
        Mastermind m = Mastermind.create(mdsStr);
        // return the Mastermind object
        return m;
    }

    // update the game in the redis cache
    public int update(final Mastermind mds) {
        // print the upsert flag
        System.out.println("upsert ? " + mds.isUpSert());
        // print the game id
        System.out.println(mds.getId());
        // get the game object in json format from the redis cache
        String result = (String) redisTemplate.opsForValue()
                .get(mds.getId());
        // check the upsert flag
        if (mds.isUpSert()) {
            System.out.println("upsert is true");
            System.out.println(result);
            // if the game already exists in the redis cache, update it
            if(result != null)
                redisTemplate.opsForValue().set(mds.getId(), mds.toJSON().toString());
            else // otherwise, add the game to the redis cache with a new generated id
                mds.setId(mds.generateId(8));
                redisTemplate.opsForValue().setIfAbsent(mds.getId(), mds.toJSON().toString());
        } else {
            System.out.println("upsert is false");
            // if the game already exists in the redis cache, update it
            if(result != null)
                redisTemplate.opsForValue().set(mds.getId(), mds.toJSON().toString());
        }
        result = (String) redisTemplate.opsForValue()
            .get(mds.getId());

        // check if the update is successful
        if (null != result)
            return 1;
        return 0;
    }

}
