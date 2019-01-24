package guessTheHash.controllers;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@Controller
public class HashController {
    private static int length = 1;
    private static Random random = new Random();
    private static byte[] bytes;
    private static String hash;

    public HashController() {
        bytes = new byte[length];
        random.nextBytes(bytes);

        hash = md5Spring(bytes);
        String s = Base64.encodeBase64String(bytes);
        System.out.println("BYTES: " + bytes);
        System.out.println("BYTES STRING: " + s);
        System.out.println("BYTES LENGTH: " + Base64.encodeBase64String(Base64.decodeBase64(s)));
    }

    private static String md5Spring(byte[] bytes) {
        return DigestUtils.md5DigestAsHex(bytes);
    }

    private byte[] guess(String hash) {
        byte[] guessBytes = new byte[length];
        String guessHash;

        while (true) {
            random.nextBytes(guessBytes);
            guessHash = md5Spring(guessBytes);

            if (guessHash.equals(hash)) {
                return guessBytes;
            }
        }
    }

    @GetMapping("/hash")
    public String getHash(Model model) {
        //return hash and length - md5 digest and length of the random array

        model.addAttribute("hash", hash);
        model.addAttribute("length", length);

        return "hash";
    }

    @PostMapping("/hash")
    public String guessHash(Model model) {
        byte[] guessedBytes = guess(hash);

        model.addAttribute("hash", hash);
        model.addAttribute("length", length);
        model.addAttribute("guessedBytes", Base64.encodeBase64String(guessedBytes));

        return "hash";
    }

    @GetMapping("/check")
    public String checkBytes() {
        return "check";
    }

    @RequestMapping(value = "/check", method = RequestMethod.POST)
    public ResponseEntity checkBytesPost(@RequestParam("hash") String hash, @RequestParam("input") String input) {
        if (input.equals(Base64.encodeBase64String(bytes))) {
            bytes = new byte[length];
            random.nextBytes(bytes);

            this.hash = md5Spring(bytes);

            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.OK);
        }
    }
}
