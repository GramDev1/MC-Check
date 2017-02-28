package pw.jeopy.MCCheck;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import pw.jeopy.MCCheck.GUI.ConsoleGUI;
import pw.jeopy.MCCheck.GUI.DesktopGUI;

import java.util.Arrays;
import java.util.List;

/**
 * This file was created by @author thejp for the use of
 * For Profit. Please note, all rights to code are retained by
 * afore mentioned thejp unless otherwise stated.
 * File created: Saturday, February, 2017
 */
public class Main extends Application
{

    private static String[] args1;

    public static void main(String... args2)
    {
        args1 = args2;
        Application.launch(args2);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        final List<String> args = Arrays.asList(args1);
        if (args.contains("--no-gui"))
        {
            ConsoleGUI.init();
            return;
        }
        primaryStage.setOnCloseRequest(event -> Platform.exit());
        DesktopGUI.init(primaryStage);
    }
}
