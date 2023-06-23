package stronghold.controller.graphical;

import stronghold.view.graphics.CaptchaView;

public class GraphicalCaptchaController {

    public static boolean lastResult = false;
    public static boolean controlSubmission(String text, String answer){
        boolean result = text.equals(answer);
        lastResult = result;
        return result;
    }

    public static boolean generateCaptcha(){
        CaptchaView.main(null);
        return lastResult;
    }

    public static void main(String[] args) {
        System.out.println(generateCaptcha());
    }
}
