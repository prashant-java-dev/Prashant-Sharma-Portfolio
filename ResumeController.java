// ResumeController.java

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/resume")
public class ResumeController {

    @PostMapping("/upload")
    public String uploadResume(@RequestParam("file") MultipartFile file) {
        // Validate the file
        if (file.isEmpty()) {
            return "Please upload a file";
        }

        // Save the file to a specified location
        String filename = "Prashant_Sharma_Software_Developer_Resume.pdf";
        // Logic to save the file with the new filename

        return "Resume uploaded successfully: " + filename;
    }
}