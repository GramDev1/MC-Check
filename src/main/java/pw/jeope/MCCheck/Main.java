package pw.jeope.MCCheck;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.module.kotlin.KotlinModule;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import pw.jeope.MCCheck.GUI.ConsoleGUI;
import pw.jeope.MCCheck.GUI.DesktopGUI;

import java.io.IOException;
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

        configUnirest();
        final List<String> args = Arrays.asList(args1);
        if (args.contains("--no-gui"))
        {
            ConsoleGUI.init();
            return;
        }
        primaryStage.setOnCloseRequest(event -> Platform.exit());
        DesktopGUI.init(primaryStage);
    }

    private void configUnirest()
    {
        Unirest.setObjectMapper(new ObjectMapper()
        {
            private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper = new com.fasterxml.jackson.databind.ObjectMapper().registerModule(new KotlinModule());

            public <T> T readValue(String value, Class<T> valueType)
            {
                try
                {
                    return jacksonObjectMapper.readValue(value, valueType);
                }
                catch (IOException e)
                {
                    throw new RuntimeException(e);
                }
            }

            public String writeValue(Object value)
            {
                try
                {
                    return jacksonObjectMapper.writeValueAsString(value);
                }
                catch (JsonProcessingException e)
                {
                    throw new RuntimeException(e);
                }
            }
        });

    }
}
