package stronghold.controller.graphical;

public class GraphicalCaptchaController {
    public static boolean controlSubmission(String text, String answer){
        return text.equals(answer);
    }
}
