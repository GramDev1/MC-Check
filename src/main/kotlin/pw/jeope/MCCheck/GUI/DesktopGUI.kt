package pw.jeope.MCCheck.GUI

import com.google.common.io.Files
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.Node
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.stage.FileChooser
import javafx.stage.Stage
import pw.jeope.MCCheck.Auth.CheckKey
import pw.jeope.MCCheck.Auth.GetKey
import java.io.File
import java.net.URL
import java.nio.charset.Charset
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * This file was created by @author thejp for the use of
For Profit. Please note, all rights to code are retained by
afore mentioned thejp unless otherwise stated.
File created: Saturday, February, 2017
 */
object DesktopGUI {
    private val CheckService: ExecutorService = Executors.newCachedThreadPool()

    @JvmStatic
    fun init(stage: Stage) {
        val loader = FXMLLoader(DesktopGUI::class.java.classLoader.getResource("dgui.fxml"))
        loader.setController(DesktopController())
        val root: Parent = loader.load()
        stage.scene = Scene(root)
        stage.isResizable = false
        stage.title = "MC Check"
        stage.show()
    }

    class DesktopController : Initializable, BaseGUI() {
        @FXML
        lateinit var startb: Button
        @FXML
        lateinit var namecheck: TextField
        @FXML
        lateinit var namecheckf: Button
        @FXML
        lateinit var avnames: TextField
        @FXML
        lateinit var avnamesf: Button
        @FXML
        lateinit var output: TextArea


        override fun initialize(location: URL?, resources: ResourceBundle?) {
            println("Controller initialized")
            var available: File? = null;
            var names: List<String>? = null;
            var verified = GetKey.getValidKey()

            while (! verified) {
                val (check,key) = verifyKey();
                verified = check;
                if (! verified) {
                    alert("Invalid Key! Please enter a valid MC-Checker key!")
                    continue
                }
                if(key != null) GetKey.createKey(key)

            }
            namecheckf.setOnAction {
                val chooser = FileChooser()
                chooser.extensionFilters.add(FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt"))
                val file = chooser.showOpenDialog((it.source as Node).scene.window);
                names = Files.readLines(file, Charset.defaultCharset())
                namecheck.text = file.absolutePath
            }
            avnamesf.setOnAction {
                val chooser = FileChooser()
                chooser.extensionFilters.add(FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt"))
                available = chooser.showOpenDialog((it.source as Node).scene.window)
                avnames.text = (available as File).absolutePath
            }
            //Start
            startb.setOnAction {
                if (names == null) {
                    if (namecheck.text == null || namecheck.text.isEmpty()) {
                        alert("You must specify a names file! Please input a path or use the button!")
                        return@setOnAction
                    }
                    val namesfile = File(namecheck.text)
                    if (! namesfile.exists()) {
                        alert("The file you specified for names does not exist!")
                        return@setOnAction
                    }
                    names = Files.readLines(namesfile, Charset.defaultCharset())
                }
                if (available == null) {
                    if (avnames.text == null || avnames.text.isEmpty()) {

                    }
                    available = File(avnames.text)
                    (available as File).createIfNotExist()
                }
                if (names == null) {
                }
                this.init(names !!, available !!)


            }
        }

        override fun say(message: String) {
            if (message.equals("Checking complete, writing to output file")) {
                alert(message)
            }
            output.appendText(message + "\n")
        }

        private fun alert(message: String) {
            val alert = Alert(Alert.AlertType.INFORMATION)
            alert.contentText = message
            alert.headerText = "MC Check Info"
            alert.title = "MC Check"
        }

        private fun verifyKey(): Pair<Boolean,String?> {
            val dialog = TextInputDialog()
            dialog.title = "MC Check Key"
            dialog.headerText = "Verify your MC Check Key!"
            dialog.contentText = "Please input you key"
            val result = dialog.showAndWait()
            if (result.isPresent) {
                if (result.get().isEmpty() || result.get().isBlank()) {
                    return Pair(false,null)
                }
                return Pair(CheckKey.check(result.get()),result.get())
            }
            else {
                //Hit cancel
                System.exit(0)
            }
            return Pair(false,null)
        }
    }

}