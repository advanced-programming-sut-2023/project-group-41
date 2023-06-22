package stronghold.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Popup;
import javafx.stage.Stage;
import stronghold.model.components.game.Government;
import stronghold.model.components.game.Unit;
import stronghold.model.components.game.building.BuildingType;
import stronghold.model.components.game.enums.Resource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class sampleController {
    private static Government currentGovernment;

    public static void setCurrentGovernment(Government currentGovernment) {
        sampleController.currentGovernment = currentGovernment;
    }

    public static Government getCurrentGovernment() {
        return currentGovernment;
    }

    public static void popularityMenu(Stage stage) throws FileNotFoundException {
        Popup popup=new Popup();
        popup.show(stage);
        popup.setX(600);
        popup.setY(280);

        Rectangle rectangle=new Rectangle(500,300);
        rectangle.setFill(new ImagePattern(new Image(new FileInputStream("F:\\Stronghold\\project-group-41\\src\\main\\java\\stronghold\\database\\Image\\back2.jpg"))));
        popup.getContent().add(rectangle);
        Button back=new Button("back");
        back.setOnAction(actionEvent -> popup.hide());
        popup.getContent().add(back);
        //////////////////////////////////
        Label fear=new Label("fear "+GameMenuController.getFearPopularity());
        fear.setLayoutX(rectangle.getX()+200);
        fear.setLayoutY(rectangle.getLayoutY());
        fear.setTextFill(Color.WHITE);
        fear.setStyle("-fx-font: bold 40 arial;");
        Rectangle fearR=new Rectangle(30,30);
        fearR.setX(fear.getLayoutX()+250);
        fearR.setY(fear.getLayoutY()+15);
        popup.getContent().add(fearR);
        if(GameMenuController.getFearPopularity()>0){
            fearR.setFill(new ImagePattern(new Image(new FileInputStream("F:\\Stronghold\\project-group-41\\src\\main\\java\\stronghold\\database\\Image\\greenFace.png"))));

        }else if(GameMenuController.getFearPopularity()<0){
            fearR.setFill(new ImagePattern(new Image(new FileInputStream("F:\\Stronghold\\project-group-41\\src\\main\\java\\stronghold\\database\\Image\\redFace.png"))));

        }else{
            fearR.setFill(new ImagePattern(new Image(new FileInputStream("F:\\Stronghold\\project-group-41\\src\\main\\java\\stronghold\\database\\Image\\yellowFace.png"))));

        }
        //////////////////////////////////////////

        Label tax=new Label("tax "+GameMenuController.getTaxPopularity());
        tax.setLayoutX(rectangle.getX()+200);
        tax.setLayoutY(rectangle.getLayoutY()+50);
        tax.setTextFill(Color.WHITE);
        tax.setStyle("-fx-font: bold 40 arial;");
        Rectangle taxR=new Rectangle(30,30);
        taxR.setX(tax.getLayoutX()+250);
        taxR.setY(tax.getLayoutY()+15);
        popup.getContent().add(taxR);
        if(GameMenuController.getTaxPopularity()>0){
            taxR.setFill(new ImagePattern(new Image(new FileInputStream("F:\\Stronghold\\project-group-41\\src\\main\\java\\stronghold\\database\\Image\\greenFace.png"))));

        }else if(GameMenuController.getTaxPopularity()<0){
            taxR.setFill(new ImagePattern(new Image(new FileInputStream("F:\\Stronghold\\project-group-41\\src\\main\\java\\stronghold\\database\\Image\\redFace.png"))));

        }else{
            taxR.setFill(new ImagePattern(new Image(new FileInputStream("F:\\Stronghold\\project-group-41\\src\\main\\java\\stronghold\\database\\Image\\yellowFace.jpg"))));

        }
        ///////////////////////////////////////
        Label food=new Label("food "+GameMenuController.getFoodPopularity());
        food.setLayoutX(rectangle.getX()+200);
        food.setLayoutY(rectangle.getLayoutY()+100);
        food.setTextFill(Color.WHITE);
        food.setStyle("-fx-font: bold 40 arial;");
        Rectangle foodR=new Rectangle(30,30);
        foodR.setX(food.getLayoutX()+250);
        foodR.setY(food.getLayoutY()+15);
        popup.getContent().add(foodR);
        if(GameMenuController.getFoodPopularity()>0){
            foodR.setFill(new ImagePattern(new Image(new FileInputStream("F:\\Stronghold\\project-group-41\\src\\main\\java\\stronghold\\database\\Image\\greenFace.png"))));

        }else if(GameMenuController.getFoodPopularity()<0){
            foodR.setFill(new ImagePattern(new Image(new FileInputStream("F:\\Stronghold\\project-group-41\\src\\main\\java\\stronghold\\database\\Image\\redFace.png"))));

        }else{
            foodR.setFill(new ImagePattern(new Image(new FileInputStream("F:\\Stronghold\\project-group-41\\src\\main\\java\\stronghold\\database\\Image\\yellowFace.jpg"))));

        }
        //////////////////////////////////////
        Label religion=new Label("religion "+GameMenuController.getReligionPopularity());
        religion.setLayoutX(rectangle.getX()+200);
        religion.setLayoutY(rectangle.getLayoutY()+150);
        religion.setTextFill(Color.WHITE);
        religion.setStyle("-fx-font: bold 40 arial;");
        Rectangle religionR=new Rectangle(30,30);
        religionR.setX(religion.getLayoutX()+250);
        religionR.setY(religion.getLayoutY()+15);
        popup.getContent().add(religionR);
        if(GameMenuController.getReligionPopularity()>0){
            religionR.setFill(new ImagePattern(new Image(new FileInputStream("F:\\Stronghold\\project-group-41\\src\\main\\java\\stronghold\\database\\Image\\greenFace.png"))));

        }else if(GameMenuController.getReligionPopularity()<0){
            religionR.setFill(new ImagePattern(new Image(new FileInputStream("F:\\Stronghold\\project-group-41\\src\\main\\java\\stronghold\\database\\Image\\redFace.png"))));

        }else{
            religionR.setFill(new ImagePattern(new Image(new FileInputStream("F:\\Stronghold\\project-group-41\\src\\main\\java\\stronghold\\database\\Image\\yellowFace.jpg"))));

        }
        //////////////////////////////////////////
        int total=GameMenuController.getReligionPopularity()+GameMenuController.getFoodPopularity()+GameMenuController.getTaxPopularity()+GameMenuController.getFearPopularity();
        Label month=new Label("this month: "+total);
        month.setLayoutX(rectangle.getX()+100);
        month.setLayoutY(rectangle.getLayoutY()+250);
        month.setTextFill(Color.WHITE);
        month.setStyle("-fx-font: bold 30 arial;");
        Rectangle totalR=new Rectangle(30,30);
        totalR.setX(month.getLayoutX()+250);
        totalR.setY(month.getLayoutY()+15);
        popup.getContent().add(totalR);

        if(total>0){
            totalR.setFill(new ImagePattern(new Image(new FileInputStream("F:\\Stronghold\\project-group-41\\src\\main\\java\\stronghold\\database\\Image\\greenFace.png"))));

        }else if(total<0){
            totalR.setFill(new ImagePattern(new Image(new FileInputStream("F:\\Stronghold\\project-group-41\\src\\main\\java\\stronghold\\database\\Image\\redFace.png"))));

        }else{
            totalR.setFill(new ImagePattern(new Image(new FileInputStream("F:\\Stronghold\\project-group-41\\src\\main\\java\\stronghold\\database\\Image\\yellowFace.jpg"))));

        }

        //////////////////////////////////////////
        popup.getContent().add(fear);
        popup.getContent().add(food);
        popup.getContent().add(tax);
        popup.getContent().add(religion);
        popup.getContent().add(month);

    }
    public static void briefing(Stage stage) throws FileNotFoundException {
        Popup popup=new Popup();
        popup.show(stage);
        popup.setX(600);
        popup.setY(280);

        Rectangle rectangle=new Rectangle(500,300);
        rectangle.setFill(new ImagePattern(new Image(new FileInputStream("F:\\Stronghold\\project-group-41\\src\\main\\java\\stronghold\\database\\Image\\back2.jpg"))));
        popup.getContent().add(rectangle);
        Button resource=new Button("Resource");
        resource.setLayoutX(210);
        resource.setLayoutY(80);
        Popup resourceP=new Popup();
        Rectangle rectangle1=new Rectangle(300,800);
        rectangle1.setFill(new ImagePattern(new Image(new FileInputStream("F:\\Stronghold\\project-group-41\\src\\main\\java\\stronghold\\database\\Image\\back2.jpg"))));
        resourceP.getContent().add(rectangle1);
        Button back=new Button("Back");
        back.setOnAction(actionEvent -> resourceP.hide());
        resourceP.getContent().add(back);
        Label resourceLabel=new Label(resource());
        resourceLabel.setTextFill(Color.WHITE);
        resourceLabel.setLayoutX(resourceLabel.getLayoutX()+100);
        resourceP.getContent().add(resourceLabel);
        resource.setOnAction(actionEvent -> resourceP.show(stage));
        ///////////////////////////////////////////////
        Button troops=new Button("Troops");
        troops.setLayoutX(210);
        troops.setLayoutY(130);
        Popup troopsP=new Popup();
        Rectangle rectangle2=new Rectangle(300,800);
        rectangle2.setFill(new ImagePattern(new Image(new FileInputStream("F:\\Stronghold\\project-group-41\\src\\main\\java\\stronghold\\database\\Image\\back2.jpg"))));
        troopsP.getContent().add(rectangle2);
        Button back3=new Button("Back");
        back3.setOnAction(actionEvent -> troopsP.hide());
        troopsP.getContent().add(back3);
        Label troopsLabel=new Label(troops());
        System.out.println(troopsLabel.getText());
        troopsLabel.setTextFill(Color.WHITE);
        troopsLabel.setLayoutX(troopsLabel.getLayoutX()+100);
        troopsP.getContent().add(troopsLabel);
        troops.setOnAction(actionEvent -> troopsP.show(stage));
        //////////////////////////////////////////////
        Button buildings=new Button("Buildings");
        buildings.setLayoutX(210);
        buildings.setLayoutY(180);
        Popup buildingsP=new Popup();
        Rectangle rectangle3=new Rectangle(300,800);
        rectangle3.setFill(new ImagePattern(new Image(new FileInputStream("F:\\Stronghold\\project-group-41\\src\\main\\java\\stronghold\\database\\Image\\back2.jpg"))));
        buildingsP.getContent().add(rectangle3);
        Button back4=new Button("Back");
        back4.setOnAction(actionEvent -> buildingsP.hide());
        buildingsP.getContent().add(back4);
        Label buildingsLabel=new Label(buildings());
        System.out.println(buildingsLabel.getText());
        buildingsLabel.setTextFill(Color.WHITE);
        buildingsLabel.setLayoutX(buildingsLabel.getLayoutX()+100);
        buildingsP.getContent().add(buildingsLabel);
        buildings.setOnAction(actionEvent -> buildingsP.show(stage));
        //////////////////////////////////////////////
        popup.getContent().add(resource);
        popup.getContent().add(troops);
        popup.getContent().add(buildings);
        Button back2=new Button("Back");
        back2.setOnAction(actionEvent -> popup.hide());
        popup.getContent().add(back2);



    }
    public static String resource(){
        String s="";
        for (Resource resource : Resource.getResources()) {
            s+=resource+">>>"+currentGovernment.getResourcesNum(resource)+"\n";
        }
        return s;
    }
    public static String buildings(){
        String s="";
        for (BuildingType buildingType : currentGovernment.getBuildingHash().keySet()) {
            s+=buildingType.getRegex()+">>>"+currentGovernment.getBuildingHash().get(buildingType)+"\n";
        }
        return s;

    }
    public static String troops(){
        String s="";
        for (Unit unit : currentGovernment.getUnits()) {
            s+=unit.getPeople().getRegex()+">>>"+unit.getCount()+"\n";
        }
        return s;

    }
    public static void report(Stage stage) throws FileNotFoundException {
        Popup popup=new Popup();
        popup.show(stage);
        Rectangle rectangle=new Rectangle(500,300);
        rectangle.setX(370);
        rectangle.setY(100);
        rectangle.setFill(new ImagePattern(new Image(new FileInputStream("F:\\Stronghold\\project-group-41\\src\\main\\java\\stronghold\\database\\Image\\back2.jpg"))));
        popup.getContent().add(rectangle);
        TextField food=new TextField();
        food.setPromptText("enter food rate");
        Button foodEnter=new Button("enter");
        foodEnter.setOnAction(actionEvent -> foodRate(food));
        food.setLayoutX(rectangle.getX()+150);
        food.setLayoutY(rectangle.getY()+50);
        foodEnter.setLayoutX(food.getLayoutX()+ 200);
        foodEnter.setLayoutY(food.getLayoutY());
        popup.getContent().add(food);
        popup.getContent().add(foodEnter);
        /////////////////////////////////////////////////////////
        TextField tax=new TextField();
        tax.setPromptText("enter tax rate");
        Button taxEnter=new Button("enter");
        taxEnter.setOnAction(actionEvent -> taxRate(tax));
        tax.setLayoutX(rectangle.getX()+150);
        tax.setLayoutY(rectangle.getY()+100);
        taxEnter.setLayoutX(tax.getLayoutX()+ 200);
        taxEnter.setLayoutY(tax.getLayoutY());
        popup.getContent().add(tax);
        popup.getContent().add(taxEnter);
        ////////////////////////////////////////
        Slider slider=new Slider();
        Label l = new Label("fear: "+currentGovernment.getFearRate());

        // set the color of the text
        l.setTextFill(Color.BLACK);
        popup.getContent().add(l);
        slider.setLayoutX(food.getLayoutX());
        slider.setLayoutY(food.getLayoutY()+100);
        slider.setMin(-4);
        slider.setMax(4);
        slider.setValue(1);
        l.setLayoutX(slider.getLayoutX()+200);
        l.setLayoutY(slider.getLayoutY());
       // slider.set

        // enable TickLabels and Tick Marks
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);


        slider.setBlockIncrement(10);

        // Adding Listener to value property.
        slider.valueProperty().addListener(
                new ChangeListener<Number>() {

                    public void changed(ObservableValue<? extends Number >
                                                observable, Number oldValue, Number newValue)
                    {

                        l.setText("fear: " + newValue);
                        currentGovernment.setFearRate((int) Math.floor((Double) newValue));
                        System.out.println(currentGovernment.getFearRate());
                    }
                });
        popup.getContent().add(slider);



    }
    public static void foodRate(TextField textField){
        currentGovernment.setFoodRate(Integer.parseInt(textField.getText()));
        textField.setPromptText("successful!");
    }
    public static void taxRate(TextField textField){
        currentGovernment.setTaxRate(Integer.parseInt(textField.getText()));
        textField.setPromptText("successful!");
    }

}
